package sky.pro.hogwartsWeb.controller;

import org.springframework.web.bind.annotation.*;
import sky.pro.hogwartsWeb.model.Student;
import sky.pro.hogwartsWeb.service.StudentServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentServiceImpl studentServiceImpl;

    public StudentController(StudentServiceImpl studentServiceImpl) {
        this.studentServiceImpl = studentServiceImpl;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentServiceImpl.createStudent(student);
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        Student student = studentServiceImpl.getStudent(id);
        return student;
    }

    @PutMapping
    public Student setStudent(@RequestBody Student student) {
        return studentServiceImpl.updateStudent(student);
    }

    @DeleteMapping("/{id}")
    public Student deleteStudent(@PathVariable Long id) {
        return studentServiceImpl.deleteStudent(id);
    }

    @GetMapping("/age/{age}")
    public List<Student> readAll(@PathVariable int age) {
        return studentServiceImpl.readAll(age);
    }
}
