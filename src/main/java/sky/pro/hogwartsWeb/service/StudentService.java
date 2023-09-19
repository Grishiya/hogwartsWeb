package sky.pro.hogwartsWeb.service;

import org.springframework.stereotype.Service;
import sky.pro.hogwartsWeb.exception.StudentException;
import sky.pro.hogwartsWeb.model.Student;
import sky.pro.hogwartsWeb.repository.StudentRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student createStudent(Student student) {
        if (studentRepository.findByNameAndAge(student.getName(),student.getAge()).isPresent()) {
            throw new StudentException("Такой студент уже есть");
        }
        return studentRepository.save(student);
    }

    public Student getStudent(Long id) {
     Optional<Student> student= studentRepository.findById(id);
        if (student.isEmpty()){
            throw new StudentException("Такого студента нет");
        }
        return student.get();
    }

    public Student updateStudent(Student student) {
        if (!studentRepository.findById(student.getId()).isEmpty()){
            throw new StudentException("Такого студента нет");
        }
        return studentRepository.save(student);
    }

    public Student deleteStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new RuntimeException("Такого студента нет");
        }
        studentRepository.deleteById(id);
        return student.get();
    }

    public List<Student> readAll(int age) {
        return studentRepository.findByAge(age);
    }

    public List<Student> findByMinAndMaxAge(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }
}
