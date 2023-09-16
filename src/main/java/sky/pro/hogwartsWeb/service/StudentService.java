package sky.pro.hogwartsWeb.service;

import org.springframework.stereotype.Service;
import sky.pro.hogwartsWeb.model.Student;

import java.util.Collection;
import java.util.HashMap;

@Service
public class StudentService {
    private final HashMap<Long, Student> studentHashMap = new HashMap<>();
    private long id = 0;

    public Student createStudent(Student student) {
        student.setId(++id);
        studentHashMap.put(id, student);
        return student;
    }

    public Student getStudent(Long id) {
        return studentHashMap.get(id);
    }

    public Student updateStudent(Student student) {
        if (studentHashMap.containsKey(student.getId())) {
            return student;

        }
        return null;
    }

    public Student deleteStudent(Long id) {
        return studentHashMap.remove(id);
    }

    public Collection<Student> getAll() {
        return studentHashMap.values();
    }
}
