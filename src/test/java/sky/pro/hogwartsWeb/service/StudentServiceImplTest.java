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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            "AGrisha",
            29
    );
    Student student2 = new Student(
            2L,
            "Grrisha",
            29
    );
    Student student3 = new Student(
            3L,
            "Grissha",
            29
    );
    Student student4 = new Student(
            4L,
            "Griisha",
            29
    );
    Student student5= new Student(
            5L,
            "Ggrisha",
            29
    );
    List<Student> students = List.of(student);


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
        Student result = underTest.read(student.getId());
        assertEquals(student, result);
    }

    @Test
    void getStudent_checkExceptionIfStudentNotInMap_returnedStudentException() {
        when(studentRepository.findById(
                student.getId()))
                .thenReturn(Optional.empty());
        StudentException exception = assertThrows(StudentException.class,
                () -> underTest.read(student.getId()));
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

    @Test
    void readAll_checkReadAllExceptionIfInRepositoryNotStudentWithThisAge_throwsException() {
        when(studentRepository.findByAge(student.getAge()))
                .thenReturn(Collections.emptyList());
        StudentException exception = assertThrows(StudentException.class
                , () -> underTest.readAll(student.getAge()));
        assertEquals("Студентов с таким возрастом нет," +
                "введите другой возраст", exception.getMessage());
    }

    @Test
    void findByAgeBetween_checkAgeBetweenMinAgeAndMaxAge_returnedAgeBetween() {
        when(studentRepository.findByAgeBetween(12
                , 30))
                .thenReturn(List.of(student));
        List<Student> result = underTest.findByAgeBetween(12, 30);
        assertEquals(List.of(student), result);
    }

    @Test
    void findByAgeBetween_checkExceptionIfAgeBetweenMinAgeAndMaxAgeNotRepository_throwsException() {
        when(studentRepository.findByAgeBetween(student.getAge()
                , student.getAge())).thenReturn(Collections.emptyList());
        StudentException exception = assertThrows(StudentException.class
                , () -> underTest.findByAgeBetween(student.getAge(), student.getAge()));
        assertEquals("Студентов с таким возрастом нет," +
                "введите другой возраст", exception.getMessage());
    }

    @Test
    void readFaculty_checkReturnedReadFacultyById_returnedReadFaculty() {
        when(studentRepository.findById(student.getId()))
                .thenReturn(Optional.of(student));
        Faculty result = underTest.readFaculty(student.getFaculty().getId());
        assertEquals(faculty, result);
    }

    @Test
    void readFaculty_checkExceptionIfStudentNotFacultyId_throwsException() {
        when(studentRepository.findById(student.getId()))
                .thenReturn(Optional.empty());
        StudentException exception = assertThrows(StudentException.class
                , () -> underTest.readFaculty(student.getFaculty().getId()));
        assertEquals("Такого студента нет", exception.getMessage());
    }

    @Test
    void findAllStudentCount__returnNumberAllStudentInList() {
        when(studentRepository.findAllStudentCount()).thenReturn(students.size());
        Integer result = underTest.findAllStudentCount();
        assertEquals(students.size(), result);
    }

    @Test
    void findAvgAge__returnAvgAgeByStudent() {
        when(studentRepository.findAvgAge())
                .thenReturn((int) (students.stream().mapToInt(Student::getAge).average().getAsDouble()));
        Integer result = underTest.findAvgAge();
        assertEquals(29, result);
    }

    @Test
    void findFiveLustStudent__returnLustStudentsInMap() {
        when(studentRepository.findLastStudent(5)).thenReturn(students
                .stream()
                .sorted(
                        Comparator.comparing(
                                Student::getId).
                                reversed())
                .limit(5).collect(Collectors.toList()));
        List<Student> result = underTest.findLastFiveStudent();
        assertEquals(students,result);
    }

    @Test
    void studentNameStartA() {
        when(studentRepository.findAll()).thenReturn(students);
        List<String> result = underTest.studentNameStartA();
        assertEquals(List.of("AGRISHA"),result);
    }

    @Test
    void findAvgAgeStream() {
        when(studentRepository.findAll()).thenReturn(students);
        Double result = underTest.findAvgAgeStream();
        assertEquals(29,result);
    }
}