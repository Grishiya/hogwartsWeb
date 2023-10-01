package sky.pro.hogwartsWeb.service;

import org.springframework.stereotype.Service;
import sky.pro.hogwartsWeb.exception.StudentException;
import sky.pro.hogwartsWeb.model.Faculty;
import sky.pro.hogwartsWeb.model.Student;
import sky.pro.hogwartsWeb.repository.FacultyRepository;
import sky.pro.hogwartsWeb.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentServiceImpl(StudentRepository studentRepository,
                              FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Student createStudent(Student student) {
        if (studentRepository.findByNameAndAge(student.getName(), student.getAge()).isPresent()) {
            throw new StudentException("Такой студент уже есть");
        }
        return studentRepository.save(student);
    }

    @Override
    public Student read(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentException("Такого студента нет");
        }
        return student.get();
    }

    @Override
    public Student updateStudent(Student student) {
        if (studentRepository.findById(student.getId()).isEmpty()) {
            throw new StudentException("Такого студента нет");
        }
        return studentRepository.save(student);
    }

    @Override
    public Student deleteStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentException("Такого студента нет");
        }
        studentRepository.deleteById(id);
        return student.get();
    }

    @Override
    public List<Student> readAll(int age) {
        List<Student> student = studentRepository.findByAge(age);
        if (student.isEmpty()) {
            throw new StudentException("Студентов с таким возрастом нет," +
                    "введите другой возраст");
        }
       return studentRepository.findByAge(age);

    }

    @Override
    public List<Student> findByAgeBetween(int min, int max) {
        List<Student> students = studentRepository
                .findByAgeBetween(min, max);
        if (students.isEmpty()) {
            throw new StudentException("Студентов с таким возрастом нет," +
                    "введите другой возраст");
        }
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Faculty readFaculty(long id) {

        return read(id).getFaculty();
    }
}

