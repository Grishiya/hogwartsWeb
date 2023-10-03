package sky.pro.hogwartsWeb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sky.pro.hogwartsWeb.model.Faculty;
import sky.pro.hogwartsWeb.model.Student;
import sky.pro.hogwartsWeb.repository.FacultyRepository;
import sky.pro.hogwartsWeb.repository.StudentRepository;
import sky.pro.hogwartsWeb.service.StudentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {StudentController.class})
public class StudentControllerMvcTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    StudentController studentController;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    FacultyRepository facultyRepository;
    @MockBean
    StudentRepository studentRepository;
    @SpyBean
    StudentServiceImpl studentService;
    Student student = new Student(1L, "Harry", 15);
    List<Student> students = List.of(student);
    Faculty faculty = new Faculty(1L, "Griffindor", "Gold");

    @Test
    void createStudent__status200AndSaveStudent() throws Exception {
        when(studentRepository.save(student)).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(objectMapper.writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }

    @Test
    void readStudent__status200AndReturnStudent() throws Exception {
        when(studentRepository.findById(anyLong()))
                .thenReturn(Optional.of(student));
        mockMvc.perform(get("/student/" + student.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }
    @Test
    void readStudent_studentIsNotInBd_returnException() throws Exception {
        when(studentRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        mockMvc.perform(get("/student/" + student.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Такого студента нет"));
    }

    @Test
    void updateStudent__status200AndReturnStudent() throws Exception {
        when(studentRepository.save(any()))
                .thenReturn(student);
        when(studentRepository.findById(any()))
                .thenReturn(Optional.of(student));
        mockMvc.perform(put("/student")
                        .content(objectMapper.writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }

    @Test
    void deleteStudent__status200AndReturnStudent() throws Exception {
        when(studentRepository.findById(any()))
                .thenReturn(Optional.of(student));
        mockMvc.perform(delete("/student/" + student.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }

    @Test
    void readAll__returnStudentByAge() throws Exception {
        when(studentRepository.findByAge(student.getAge())).thenReturn(List.of(student));
        when(studentRepository.findByAgeBetween(anyInt(),anyInt()))
                .thenReturn(List.of(student));
        mockMvc.perform(get("/student/age/" + student.getAge())
                .param("age", String.valueOf(student.getAge()))
                .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value(student.getName()))
                .andExpect(jsonPath("$.[0].age").value(student.getAge()));
    }
    @Test
    void getFaculty__returnStatus200AndStudentWithFaculty() throws Exception {
        student.setFaculty(faculty);
        when(studentRepository.findById(student.getId()))
                .thenReturn(Optional.of(student));
        mockMvc.perform(get("/student/"+student.getId()+"/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student.getFaculty().getName()))
                .andExpect(jsonPath("$.color").value(student.getFaculty().getColor()));
    }

    @Test
    void findStudentNameStartCharA__returnStatus200() throws Exception {
        when(studentRepository.findAll()).thenReturn(students);
        mockMvc.perform(get("/student/find-student-name-start-a"))
                .andExpect(status().isOk());
    }

    @Test
    void avgAgeByStream__returnStatus200() throws Exception {
        when(studentRepository.findAll()).thenReturn(students);
        mockMvc.perform(get("/student/avg-age-stream"))
                .andExpect(status().isOk());
    }
}
