package sky.pro.hogwartsWeb.controller;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sky.pro.hogwartsWeb.exception.StudentException;
import sky.pro.hogwartsWeb.model.Faculty;
import sky.pro.hogwartsWeb.model.Student;
import sky.pro.hogwartsWeb.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        Student student = studentService.getStudent(id);
        return student;
    }

    @PutMapping
    public Student setStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/{id}")
    public Student deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping("/age/{age}")

    public List<Student> readAllAge(@RequestParam int age, @RequestParam(defaultValue = "0") int age2) {
        if (age2 == 0) {
            return studentService.readAll(age);
        }
        return studentService.findByAgeBetween(age, age2);
    }

    @GetMapping("/{id}/faculty")
    public Faculty readFaculty(@PathVariable long id) {
        return studentService.findStudentByFacultyId(id);
    }
}
