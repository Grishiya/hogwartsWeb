package sky.pro.hogwartsWeb.service;

import org.springframework.stereotype.Service;
import sky.pro.hogwartsWeb.model.Faculty;
import sky.pro.hogwartsWeb.model.faculty;

import java.util.Collection;
import java.util.HashMap;

@Service
public class FacultyService {
    private final HashMap<Long, Faculty> facultyHashMap = new HashMap<>();
    private long id = 0;

    public Faculty createfaculty(Faculty faculty) {
        faculty.setId(++id);
        facultyHashMap.put(id, faculty);
        return faculty;
    }

    public Faculty getfaculty(Long id) {
        return facultyHashMap.get(id);
    }

    public Faculty updatefaculty(Faculty faculty) {
        if (facultyHashMap.containsKey(faculty.getId())) {
            return faculty;

        }
        return null;
    }

    public Faculty deletefaculty(Long id) {
        return facultyHashMap.remove(id);
    }

    public Collection<Faculty> getAll() {
        return facultyHashMap.values();
    }
}

}
