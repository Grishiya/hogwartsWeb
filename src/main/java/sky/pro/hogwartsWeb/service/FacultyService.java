package sky.pro.hogwartsWeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sky.pro.hogwartsWeb.exception.FacultyException;
import sky.pro.hogwartsWeb.model.Faculty;
import sky.pro.hogwartsWeb.repository.FacultyRepository;


import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        if (facultyRepository.findByNameAndColor(faculty.getName(), faculty.getColor()).isPresent()) {
            throw new FacultyException("Такой факультет уже есть");
        }
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyException("Такого факультета нет");
        }
        return faculty.get();
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (!facultyRepository.findById(faculty.getId()).isEmpty()) {
            throw new FacultyException("Такого факультета нет");
        }

        return facultyRepository.save(faculty);
    }

    public Faculty deleteFaculty(long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyException("Такого факультета нет");
        }
        facultyRepository.deleteById(id);
        return faculty.get();
    }

    public List<Faculty> readAll(String color) {
        return facultyRepository.findByColor(color);
    }
}


