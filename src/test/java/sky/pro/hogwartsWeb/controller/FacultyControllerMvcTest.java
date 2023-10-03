package sky.pro.hogwartsWeb.controller;


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
import sky.pro.hogwartsWeb.service.FacultyServiceImpl;
import sky.pro.hogwartsWeb.service.StudentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.param;

@WebMvcTest(controllers = {FacultyController.class})
public class FacultyControllerMvcTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    FacultyController facultyController;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    FacultyRepository facultyRepository;
    @MockBean
    StudentRepository studentRepository;
    @SpyBean
    FacultyServiceImpl facultyService;
    @SpyBean
    StudentServiceImpl studentService;
    Faculty faculty = new Faculty(1L, "griffindor", "gold");
    Student student = new Student(1L, "potnik", 5);
    List<Faculty> faculties = List.of(faculty);
    @Test
    void createFaculty__status200AndSaveFaculty() throws Exception {
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(objectMapper.writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));

    }

    @Test
    void readFaculty__status200AndReturnFaculty() throws Exception {
        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));
        mockMvc.perform(get("/faculty/" + faculty.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    void readFaculty_facultyIsNotInDb_returnException() throws Exception {
        when(facultyRepository.findById(anyLong())).thenReturn(Optional.empty());
        mockMvc.perform(get("/faculty/" + faculty.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Такого факультета нет"));
    }

    @Test
    void updateFaculty__status200AndReturnStudent() throws Exception {
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        when(facultyRepository.findById(any())).thenReturn(Optional.of(faculty));
        mockMvc.perform(put("/faculty")
                        .content(objectMapper.writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    void deleteFaculty__status200AndReturnStudent() throws Exception {
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));
        mockMvc.perform(delete("/faculty/" + faculty.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(faculty));
    }

    @Test
    void readAll() throws Exception {
        when(facultyRepository.findByColor(any())).thenReturn(Optional.of(faculty));
        mockMvc.perform(get("/faculty/color/" + faculty.getColor()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    void findByColorAndNameIgnoreCase() throws Exception {
        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(faculty.getName()
                , faculty.getColor())).thenReturn(Optional.of(faculty));
        mockMvc.perform(get("/faculty/find")
                        .param("name", String.valueOf(faculty.getName()))
                        .param("color", String.valueOf(faculty.getColor()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getStudentByFaculty() throws Exception {

        when(studentRepository.findByFaculty_id(anyLong()))
                .thenReturn(List.of(student));
        when(facultyRepository.existsById(anyLong())).thenReturn(true);
        mockMvc.perform(get("/faculty/students")
                        .param("id", String.valueOf(faculty.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value(student.getName()))
                .andExpect(jsonPath("$.[0].age").value(student.getAge()));
    }

    @Test
    void findByLongerName__returnStatus200() throws Exception {
        when(facultyRepository.findAll()).thenReturn(faculties);
        mockMvc.perform(get("/faculty/find-longer-name"))
                .andExpect(status().isOk());
    }
}
