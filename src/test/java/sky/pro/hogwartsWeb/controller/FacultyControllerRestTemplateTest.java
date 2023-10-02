package sky.pro.hogwartsWeb.controller;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import sky.pro.hogwartsWeb.model.Faculty;
import sky.pro.hogwartsWeb.model.Student;
import sky.pro.hogwartsWeb.repository.FacultyRepository;
import sky.pro.hogwartsWeb.repository.StudentRepository;
import org.springframework.http.*;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerRestTemplateTest {
    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    FacultyRepository facultyRepository;
    @Autowired
    StudentRepository studentRepository;
    @LocalServerPort
    int port;
    Faculty faculty = new Faculty(1L, "гриффиндор", "золотой");



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
    void delete__returnStatus200() {
        Faculty f = facultyRepository.save(faculty);
        ResponseEntity<Faculty> facultyResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/" + f.getId(),
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Faculty.class);
        assertEquals(HttpStatus.OK, facultyResponseEntity.getStatusCode());
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
        assertEquals(faculty.getColor(), Objects.requireNonNull(facultyResponseEntity.getBody().getColor()));
    }

    @Test
    void readStudentsInFaculty__returnStatus200AndStudentsList() {
       facultyRepository.save(faculty);
        Student student1 = new Student(0L, "Harry", 13);
        Student student2 = new Student(0L, "Ron", 13);
        student1.setFaculty(faculty);
        student2.setFaculty(faculty);
        studentRepository.save(student1);
        studentRepository.save(student2);
        ResponseEntity<List<Student>> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/faculty/students?id=" + faculty.getId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>(){}
                );
        List<Student> students = responseEntity.getBody();
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(List.of(student1, student2), students);
    }
}
