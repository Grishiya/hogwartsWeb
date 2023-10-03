package sky.pro.hogwartsWeb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sky.pro.hogwartsWeb.model.Faculty;
import sky.pro.hogwartsWeb.model.Student;
import sky.pro.hogwartsWeb.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable long id) {
        return ResponseEntity.ok(facultyService.getFaculty(id));
    }

    @PutMapping
    public Faculty setFaculty(@RequestBody Faculty faculty) {
        return facultyService.updateFaculty(faculty);
    }

    @DeleteMapping("/{id}")
    public Faculty deleteFaculty(@PathVariable long id) {
        return facultyService.deleteFaculty(id);
    }

    @GetMapping("/color/{color}")
    public Faculty readAll(@PathVariable String color) {
        return facultyService.readColor(color);
    }

    @GetMapping("/find")
    public Faculty findByColorAndNameIgnoreCase(@RequestParam(required = false) String name,
                                                @RequestParam(required = false) String color) {
        return facultyService.findByNameOrColorIgnoreCase(name, color);
    }

    @GetMapping("/students")
    public List<Student> getStudentsByFaculty(@RequestParam long id) {
        return facultyService.findStudentsByFaculty_id(id);
    }

    @GetMapping("/find-longer-name")
    public String findByLongerName() {
        return facultyService.findByLongerName();
    }
}
