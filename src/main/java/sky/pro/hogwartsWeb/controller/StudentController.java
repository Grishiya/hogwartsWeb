package sky.pro.hogwartsWeb.controller;

import org.springframework.web.bind.annotation.*;
import sky.pro.hogwartsWeb.model.Faculty;
import sky.pro.hogwartsWeb.model.Student;
import sky.pro.hogwartsWeb.service.StudentService;

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
    public Student readStudent(@PathVariable Long id) {
        Student student = studentService.read(id);
        return student;
    }

    @PutMapping
    public Student updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/{id}")
    public Student deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping("/age/{age}")

    public List<Student> readAll(@RequestParam int age, @RequestParam(defaultValue = "0") int age2) {
        if (age2 == 0) {
            return studentService.readAll(age);
        }
        return studentService.findByAgeBetween(age, age2);
    }

    @GetMapping("/{id}/faculty")
    public Faculty getFaculty(@PathVariable long id) {
        return studentService.readFaculty(id);
    }

    @GetMapping("/count")
    public Integer findStudentCount() {
        return studentService.findAllStudentCount();
    }

    @GetMapping("/average-age")
    public Integer findStudentAvgAge() {
        return studentService.findAvgAge();
    }

    @GetMapping("/lust-five-Student")
    public List<Student> findLustFiveStudent() {
        return studentService.findLastFiveStudent();
    }

    @GetMapping("/find-student-name-start-a")
    public List<String> findStudentNameStartCharA() {
        return studentService.studentNameStartA();
    }

    @GetMapping("/avg-age-stream")
    public Double avgAgeByStream() {
        return studentService.findAvgAgeStream();
    }

    @GetMapping("/read-student-with-threads")
    public void readStudentWithThreads() {
        studentService.readStudentWithThreads();
    }

    @GetMapping("/read-student-thread-synchronized")
    public void readStudentWithThreadsSynchronized() {
        studentService.readStudentWithThreadsSynchronized();
    }
}
