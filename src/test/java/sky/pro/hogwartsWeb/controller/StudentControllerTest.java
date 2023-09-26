package sky.pro.hogwartsWeb.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sky.pro.hogwartsWeb.model.Faculty;
import sky.pro.hogwartsWeb.model.Student;
import sky.pro.hogwartsWeb.repository.FacultyRepository;
import sky.pro.hogwartsWeb.repository.StudentRepository;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    int port;

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    FacultyRepository facultyRepository;

    Student student = new Student(1L, "Harry", 13);
    Faculty faculty = new Faculty(1L, "Griffindor", "red");

    @AfterEach
    void afterEach() {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }

    @Test
    void create__returnStatus200AndStudent() {
        ResponseEntity<Student> studentResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/student",
                student,
                Student.class);
        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
        assertEquals(student.getName(), Objects.requireNonNull(studentResponseEntity.getBody()).getName());
        assertEquals(student.getAge(), Objects.requireNonNull(studentResponseEntity.getBody()).getAge());
    }

    @Test
    void read_studentNotInDb_returnStatus400AndExceptionTest() {
        ResponseEntity<String> studentResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/" +
                        student.getId(), String.class);
        assertEquals(HttpStatus.BAD_REQUEST, studentResponseEntity.getStatusCode());
        assertEquals("Такого студента нет", studentResponseEntity.getBody());
    }

    @Test
    void updateStudent__returnStatus200AndStudent() {
        studentRepository.save(student);
        ResponseEntity<Student> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student"
                , HttpMethod.PUT
                , new HttpEntity<>(student)
                , Student.class);
        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
        assertEquals(student.getAge(), Objects.requireNonNull(studentResponseEntity.getBody()).getAge());
        assertEquals(student.getName(), Objects.requireNonNull(studentResponseEntity.getBody()).getName());
    }

    @Test
    void deleteStudent_returnStatus200() {
        studentRepository.save(student);
        ResponseEntity<Student> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student/" + student.getId()
                ,HttpMethod.DELETE
                ,HttpEntity.EMPTY
                ,Student.class
        );
        assertEquals(HttpStatus.OK,studentResponseEntity.getStatusCode());
    }

    @Test
    void readAllAge__returnStatus200AndStudentList() {
        studentRepository.save(student);
        ResponseEntity<List<Student>> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student/age/age?age=" + student.getAge() + "&age2=" + student.getAge(),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() {
                });
        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
        assertEquals(List.of(student), studentResponseEntity.getBody());
    }

    @Test
    void getFaculty__returnStatus200() {
            Faculty f = facultyRepository.save(faculty);
            student.setFaculty(f);
            Student s = studentRepository.save(student);
            ResponseEntity<Faculty> studentResponseEntity = restTemplate.getForEntity(
                    "http://localhost:" + port + "/student/" + s.getId() + "/faculty",
                    Faculty.class);
            assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
            assertEquals(student.getFaculty(), studentResponseEntity.getBody());
        }
}

