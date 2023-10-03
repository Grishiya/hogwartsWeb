package sky.pro.hogwartsWeb.controller;

import org.checkerframework.checker.units.qual.A;
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
public class FacultyControllerRestTemplateTest {
    @Autowired
    TestRestTemplate restTemplate;
    @LocalServerPort
    int port;
    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    StudentRepository studentRepository;
    Faculty faculty = new Faculty(1L, "Griffindor", "gold");


    @AfterEach
    void afterEach() {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }

    @Test
    void createFaculty__status200AndSaveFaculty() {
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/faculty", faculty, Faculty.class);
        assertEquals(HttpStatus.OK, facultyResponseEntity.getStatusCode());
        assertEquals(faculty.getName(), Objects.requireNonNull(facultyResponseEntity.getBody().getName()));
        assertEquals(faculty.getColor(), Objects.requireNonNull(facultyResponseEntity.getBody().getColor()));
    }

    @Test
    void readFaculty_facultyIsNotBd_returnStatus400AndException() {
        ResponseEntity<String> facultyResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/" + faculty.getId(), String.class);
        assertEquals(HttpStatus.BAD_REQUEST, facultyResponseEntity.getStatusCode());
        assertEquals("Такого факультета нет", facultyResponseEntity.getBody());
    }

    @Test
    void updateFaculty__status200AndSaveFaculty() {
        facultyRepository.save(faculty);
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/"
                , HttpMethod.PUT
                , new HttpEntity<>(faculty)
                , Faculty.class
        );
        assertEquals(HttpStatus.OK, facultyResponseEntity.getStatusCode());
        assertEquals(faculty.getName(), Objects.requireNonNull(facultyResponseEntity.getBody().getName()));
        assertEquals(faculty.getColor(), Objects.requireNonNull(facultyResponseEntity.getBody().getColor()));
    }

    @Test
    void deleteFaculty__status200AndReturnFaculty() {
        facultyRepository.save(faculty);
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty"
                , HttpMethod.DELETE
                , HttpEntity.EMPTY
                , Faculty.class
        );
        assertEquals(HttpStatus.OK, facultyResponseEntity.getStatusCode());
        assertEquals(faculty.getName(), Objects.requireNonNull(facultyResponseEntity.getBody().getName()));
        assertEquals(faculty.getColor(), Objects.requireNonNull(facultyResponseEntity.getBody().getColor()));
    }

    @Test
    void readALl__status200AndReturnFacultyByColor() {
        facultyRepository.save(faculty);
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/color/" + faculty.getColor()
                , Faculty.class
        );
        assertEquals(HttpStatus.OK, facultyResponseEntity.getStatusCode());
        assertEquals(faculty.getName(), Objects.requireNonNull(facultyResponseEntity.getBody().getName()));
        assertEquals(faculty.getColor(), Objects.requireNonNull(facultyResponseEntity.getBody().getColor()));
    }

    @Test
    void findByColorAndNameIgnoreCase__status200() {
        facultyRepository.save(faculty);
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/find?name=" + faculty.getName()
                        + "&color=" + faculty.getColor()
                , HttpMethod.GET
                , HttpEntity.EMPTY
                , Faculty.class
        );
        assertEquals(HttpStatus.OK, facultyResponseEntity.getStatusCode());
        assertEquals(faculty.getName(), Objects.requireNonNull(facultyResponseEntity.getBody().getName()));
        assertEquals(faculty.getColor(),Objects.requireNonNull(facultyResponseEntity.getBody().getColor()));
    }

    @Test
    void getStudentsByFaculty__returnStatus200AndStudentList() {
        facultyRepository.save(faculty);
        Student student = new Student(1L, "d,jf", 15);
        student.setFaculty(faculty);
        studentRepository.save(student);
        ResponseEntity<List<Student>> facultyResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/students?id=" + faculty.getId(),
                HttpMethod.GET
                , null
                , new ParameterizedTypeReference<List<Student>>() {
                });
        List<Student> students = facultyResponseEntity.getBody();
        assertEquals(200,facultyResponseEntity.getStatusCodeValue());
        assertEquals(List.of(student),students);
    }
}
