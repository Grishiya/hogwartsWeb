package sky.pro.hogwartsWeb.service;

import org.junit.jupiter.api.Test;
import sky.pro.hogwartsWeb.exception.FacultyException;
import sky.pro.hogwartsWeb.exception.StudentException;
import sky.pro.hogwartsWeb.model.Student;
import sky.pro.hogwartsWeb.repository.StudentRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
    StudentService underTest = new StudentService();
    Student student = new Student(
            1,
            "Grisha",
            29
    );


    @Test
    void createStudent_checkCreateNewStudent_createAndReturnedStudent() {
        Student result = underTest.createStudent(student);
        assertEquals(student, result);
        assertEquals(1, result.getId());
    }

    @Test
    void createStudent_checkExceptionIfNewStudentInMap_returnedStudentException() {
        underTest.createStudent(student);
        StudentException exception = assertThrows(StudentException.class, ()
                -> underTest.createStudent(student));
        assertEquals("Такой студент уже есть", exception.getMessage());
    }

    @Test
    void getStudent_checkGetStudentInMap_returnedStudent() {
        underTest.createStudent(student);
        Student result = underTest.getStudent(1);
        assertEquals(student, result);
    }

    @Test
    void getStudent_checkExceptionIfStudentNotInMap_returnedStudentException() {
        StudentException exception = assertThrows(StudentException.class,
                () -> underTest.getStudent(student.getId()));
        assertEquals("Такого студента нет", exception.getMessage());
    }

    @Test
    void updateStudent_checkUpdateStudentInMap_returnedStudent() {
        underTest.createStudent(student);
        Student setStudent = new Student(
                1,
                "Grisha",
                28
        );
        Student result = underTest.updateStudent(setStudent);
        assertNotEquals(student, result);
    }

    @Test
    void updateStudent_checkUpdateExceptionIfStudentNotFoundMap_throwsException() {
        StudentException exception = assertThrows(StudentException.class,
                () -> underTest.updateStudent(student));
        assertEquals("Такого студента нет", exception.getMessage());
    }

    @Test
    void deleteStudent_checkDeleteStudentFromMap_deleteAndReturnedStudent() {
        underTest.createStudent(student);
        Student result = underTest.deleteStudent(student.getId());
        assertEquals(student, result);
    }

    @Test
    void deleteStudent_checkDeleteExceptionIfStudentNotFoundMap_throwsException() {
        StudentException exception = assertThrows(StudentException.class,
                () -> underTest.deleteStudent(student.getId()));
        assertEquals("Такого студента нет", exception.getMessage());
    }

    @Test
    void readAll_checkSortGetStudentsByAge_returnedStudentByAge() {
        underTest.createStudent(student);
        List<Student> result = underTest.readAll(29);
        assertEquals(List.of(student), result);
    }
}