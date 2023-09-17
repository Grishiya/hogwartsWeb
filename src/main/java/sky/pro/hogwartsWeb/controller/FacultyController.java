package sky.pro.hogwartsWeb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sky.pro.hogwartsWeb.model.Faculty;
import sky.pro.hogwartsWeb.model.Student;
import sky.pro.hogwartsWeb.service.FacultyService;
import sky.pro.hogwartsWeb.service.StudentService;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

        private final FacultyService facultyService;

        public  FacultyController(FacultyService facultyService) {
            this.facultyService = facultyService;
        }

        @PostMapping
        public Faculty createFaculty(@RequestBody Faculty faculty) {
            return facultyService.createFaculty(faculty);

    }
    @GetMapping("{id}")
    public ResponseEntity getFaculty(@PathVariable Long id) {
        Faculty faculty=facultyService.getFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }
}
