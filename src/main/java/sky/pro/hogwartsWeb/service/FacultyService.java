package sky.pro.hogwartsWeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sky.pro.hogwartsWeb.exception.FacultyException;
import sky.pro.hogwartsWeb.model.Faculty;
import sky.pro.hogwartsWeb.repository.FacultyRepository;


import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        if (facultyHashMap.containsValue(faculty)) {
            throw new FacultyException("Такой факультет уже есть");
        }
        faculty.setId(++id);
        facultyHashMap.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty getFaculty(long id) {
        if (!facultyHashMap.containsKey(id)) {
            throw new FacultyException("Такого факультета нет");
        }
        return facultyHashMap.get(id);
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (!facultyHashMap.containsKey(faculty.getId())) {
            throw new FacultyException("Такого факультета нет");
        }
        facultyHashMap.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty deleteFaculty(long id) {
        Faculty faculty = facultyHashMap.remove(id);
        if (faculty == null) {
            throw new FacultyException("Такого факультета нет");
        }
        return faculty;
    }

    public List<Faculty> readAll(String color) {
        return facultyHashMap.values().stream().
                filter(faculty -> faculty.getColor().equals(color)).
                toList();
    }
}


