package sky.pro.hogwartsWeb.service;

import org.springframework.stereotype.Service;
import sky.pro.hogwartsWeb.exception.StudentException;
import sky.pro.hogwartsWeb.model.Student;
import sky.pro.hogwartsWeb.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

@Override
    public Student createStudent(Student student) {
        if (studentRepository.findByNameAndAge(student.getName(),student.getAge()).isPresent()) {
            throw new StudentException("Такой студент уже есть");
        }
        return studentRepository.save(student);
    }
@Override
    public Student getStudent(Long id) {
     Optional<Student> student= studentRepository.findById(id);
        if (student.isEmpty()){
            throw new StudentException("Такого студента нет");
        }
        return student.get();
    }
@Override
    public Student updateStudent(Student student) {
        if (!studentRepository.findById(student.getId()).isEmpty()){
            throw new StudentException("Такого студента нет");
        }
        return studentRepository.save(student);
    }
@Override
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
}