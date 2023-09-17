package sky.pro.hogwartsWeb.service;

import org.springframework.stereotype.Service;
import sky.pro.hogwartsWeb.exception.StudentException;
import sky.pro.hogwartsWeb.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final HashMap<Long, Student> studentHashMap = new HashMap<>();
    private long id;

    public Student createStudent(Student student) {
        if (studentHashMap.containsValue(student)){
            throw new StudentException("Такой студент уже есть");
        }
        student.setId(++id);
        studentHashMap.put(student.getId(), student);
        return student;
    }

    public Student getStudent(long id) {
        if (!studentHashMap.containsKey(id)) {
            throw new StudentException("Такого студента нет");
        }
        return studentHashMap.get(id);
    }

    public Student updateStudent(Student student) {
        if (!studentHashMap.containsKey(student.getId())) {
            throw new StudentException("Такого студента нет");
        }
        studentHashMap.put(student.getId(), student);
        return student;
    }

    public Student deleteStudent(long id) {
        Student student = studentHashMap.remove(id);
        if (student == null) {
            throw new StudentException("Такого студента нет");
        }
        return student;
    }
    public List<Student> readAll(int age) {
        return studentHashMap.values().stream().
                filter(student -> student.getAge() == age).
                collect(Collectors.toUnmodifiableList());
    }
}
