package sky.pro.hogwartsWeb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sky.pro.hogwartsWeb.model.Faculty;
import sky.pro.hogwartsWeb.service.FacultyServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyServiceImpl facultyServiceImpl;

    public FacultyController(FacultyServiceImpl facultyServiceImpl) {
        this.facultyServiceImpl = facultyServiceImpl;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyServiceImpl.createFaculty(faculty);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable long id) {
        return ResponseEntity.ok(facultyServiceImpl.getFaculty(id));
    }

    @PutMapping
    public Faculty setFaculty(@RequestBody Faculty faculty) {
        return facultyServiceImpl.updateFaculty(faculty);
    }

    @DeleteMapping("/{id}")
    public Faculty deleteFaculty(@PathVariable long id) {
        return facultyServiceImpl.deleteFaculty(id);
    }

    @GetMapping("/color/{color}")
    public List<Faculty> readAll(@PathVariable String color) {
        return facultyServiceImpl.readAll(color);
    }
}
