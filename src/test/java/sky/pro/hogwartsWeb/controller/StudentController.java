package sky.pro.hogwartsWeb.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import sky.pro.hogwartsWeb.model.Student;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentController {
    @Autowired
    TestRestTemplate restTemplate;
    @LocalServerPort
    int port;

    @Test
    void create__returnedStatus200() {
        Student student = new Student(
                1L
                , "Harry"
                , 12);
        restTemplate.postForEntity("http:localhost:"+port
                +"/student"
                ,student)
    }
}
