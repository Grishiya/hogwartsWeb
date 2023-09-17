package sky.pro.hogwartsWeb.service;

import org.springframework.stereotype.Service;
import sky.pro.hogwartsWeb.exception.FacultyException;
import sky.pro.hogwartsWeb.model.Faculty;


import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Service
public class FacultyService {
    private final HashMap<Long, Faculty> facultyHashMap = new HashMap<>();
    private long id = 0;

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


