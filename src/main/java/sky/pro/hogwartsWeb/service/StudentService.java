package sky.pro.hogwartsWeb.service;

import sky.pro.hogwartsWeb.model.Faculty;
import sky.pro.hogwartsWeb.model.Student;

import java.util.List;

public interface StudentService {
    Student createStudent(Student student);

    Student read(Long id);

    Student updateStudent(Student student);

    Student deleteStudent(Long id);

    List<Student> readAll(int age);

    List<Student> findByAgeBetween(int min, int max);

    Faculty readFaculty(long id);

    Integer findAllStudentCount();

    Integer findAvgAge();


    List<Student> findLastFiveStudent();

    List<String> studentNameStartA();

    Double findAvgAgeStream();
}
