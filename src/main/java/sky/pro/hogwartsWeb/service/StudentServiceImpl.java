package sky.pro.hogwartsWeb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sky.pro.hogwartsWeb.exception.StudentException;
import sky.pro.hogwartsWeb.model.Faculty;
import sky.pro.hogwartsWeb.model.Student;
import sky.pro.hogwartsWeb.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        logger.info("Был вызван метод create с данными " + student);
        if (studentRepository.findByNameAndAge(student.getName(), student.getAge()).isPresent()) {
            throw new StudentException("Такой студент уже есть");
        }
        Student savedStudent = studentRepository.save(student);
        logger.info("Из метода create вернули" + student);
        return savedStudent;
    }

    @Override
    public Student read(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        logger.info("Был вызван метод read с данными " + id);
        if (student.isEmpty()) {
            throw new StudentException("Такого студента нет");
        }

        logger.info("Из метода read вернули " + student.get());
        return student.get();
    }

    @Override
    public Student updateStudent(Student student) {
        logger.info("Был вызван метод update с данными " + student);
        if (studentRepository.findById(student.getId()).isEmpty()) {
            throw new StudentException("Такого студента нет");
        }

        logger.info("Из метода update вернули" + student);
        return studentRepository.save(student);
    }

    @Override
    public Student deleteStudent(Long id) {
        logger.info("Был вызван метод deleteStudent с данными " + id);
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentException("Такого студента нет");
        }
        studentRepository.deleteById(id);
        logger.info("Метод delete удалил " + student.get());
        return student.get();
    }

    @Override
    public List<Student> readAll(int age) {
        logger.info("Был вызван метод readAll с данными " + age);
        List<Student> student = studentRepository.findByAge(age);
        if (student.isEmpty()) {
            throw new StudentException("Студентов с таким возрастом нет," +
                    "введите другой возраст");
        }
        logger.info("Метод readAll вернул студентов по возрасту " + student);
        return studentRepository.findByAge(age);

    }

    @Override
    public List<Student> findByAgeBetween(int min, int max) {
        logger.info("Был вызван метод findByAgeBetween с данными " + min + max);
        List<Student> students = studentRepository
                .findByAgeBetween(min, max);
        if (students.isEmpty()) {
            throw new StudentException("Студентов с таким возрастом нет," +
                    "введите другой возраст");
        }
        List<Student> ageBetween = studentRepository.findByAgeBetween(min, max);
        logger.info("Метод findByAgeBetween вернул студентов  по возрасту " + ageBetween);
        return ageBetween;
    }

    @Override
    public Faculty readFaculty(long id) {
        logger.info("Был вызван метод readFaculty c данными" + id);
        logger.info("Метод readFaculty вернул факультет студента " + read(id).getFaculty());
        return read(id).getFaculty();
    }

    @Override
    public Integer findAllStudentCount() {
        logger.info("Был вызван метод findAllStudentCount");
        Integer allStudents = studentRepository.findAllStudentCount();
        logger.info("Метод вернул количество учащихся " + allStudents);
        return allStudents;
    }

    @Override
    public Integer findAvgAge() {
        logger.info("Был вызван метод findAvgAge");
        Integer avgAgeStudent = studentRepository.findAvgAge();
        logger.info("Метод подсчитал средний возраст учащихся" + avgAgeStudent);
        return avgAgeStudent;
    }

    @Override
    public List<Student> findLastFiveStudent() {
        logger.info("Был вызван метод dLastFiveStudent");
        List<Student> students = studentRepository.findLastStudent(5);
        logger.info("Метод вернул последние 5 учеников в списке"+students);
        return students;
    }

}

