package sky.pro.hogwartsWeb.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sky.pro.hogwartsWeb.exception.StudentException;
import sky.pro.hogwartsWeb.model.Faculty;
import sky.pro.hogwartsWeb.model.Student;
import sky.pro.hogwartsWeb.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @Mock
    StudentRepository studentRepository;
    @InjectMocks
    StudentServiceImpl underTest;
    Faculty faculty = new Faculty(
            1L
            , "griffindor"
            , "gold");
    Student student = new Student(
            1L,
            "Grisha",
            29,faculty
    );


    @Test
    void createStudent_checkCreateNewStudent_createAndReturnedStudent() {
        when(studentRepository.save(student)).thenReturn(student);
        Student result = underTest.createStudent(student);
        assertEquals(student, result);
        assertEquals(student.getId(), result.getId());
    }

    @Test
    void createStudent_checkExceptionIfNewStudentInMap_returnedStudentException() {
        when(studentRepository.findByNameAndAge(
                student.getName()
                , student.getAge()))
                .thenReturn(Optional.of(student));
        StudentException exception = assertThrows(StudentException.class, ()
                -> underTest.createStudent(student));
        assertEquals("Такой студент уже есть", exception.getMessage());
    }

    @Test
    void getStudent_checkGetStudentInMap_returnedStudent() {
        underTest.createStudent(student);
        when(studentRepository.findById(
                student.getId()))
                .thenReturn(Optional.of(student));
        Student result = underTest.getStudent(student.getId());
        assertEquals(student, result);
    }

    @Test
    void getStudent_checkExceptionIfStudentNotInMap_returnedStudentException() {
        when(studentRepository.findById(
                student.getId()))
                .thenReturn(Optional.empty());
        StudentException exception = assertThrows(StudentException.class,
                () -> underTest.getStudent(student.getId()));
        assertEquals("Такого студента нет", exception.getMessage());
    }

    @Test
    void updateStudent_checkUpdateStudentInMap_returnedStudent() {
        when(studentRepository.findById(
                student.getId()))
                .thenReturn(Optional.of(student));
        Student result = underTest.updateStudent(student);
        assertNotEquals(student, result);
    }

    @Test
    void updateStudent_checkUpdateExceptionIfStudentNotFoundMap_throwsException() {
        when(studentRepository.findById(
                student.getId()))
                .thenReturn(Optional.empty());
        StudentException exception = assertThrows(StudentException.class,
                () -> underTest.updateStudent(student));
        assertEquals("Такого студента нет", exception.getMessage());
    }

    @Test
    void deleteStudent_checkDeleteStudentFromMap_deleteAndReturnedStudent() {
        underTest.createStudent(student);
        when(studentRepository.findById(student.getId()))
                .thenReturn(Optional.of(student));
        Student result = underTest.deleteStudent(student.getId());
        assertEquals(student, result);
    }

    @Test
    void deleteStudent_checkDeleteExceptionIfStudentNotFoundMap_throwsException() {
        when(studentRepository.findById(
                student.getId()))
                .thenReturn(Optional.empty());
        StudentException exception = assertThrows(StudentException.class,
                () -> underTest.deleteStudent(student.getId()));
        assertEquals("Такого студента нет", exception.getMessage());
    }

    @Test
    void readAll_checkSortGetStudentsByAge_returnedStudentByAge() {
        when(studentRepository.findByAge(
                student.getAge()))
                .thenReturn(List.of(student));
        List<Student> result = underTest.readAll(student.getAge());
        assertEquals(List.of(student), result);
    }
}