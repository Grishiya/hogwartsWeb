package sky.pro.hogwartsWeb.service;

import sky.pro.hogwartsWeb.model.Student;

public interface StudentService {
    Student createStudent(Student student);

    Student getStudent(Long id);

    Student updateStudent(Student student);

    Student deleteStudent(Long id);
}
