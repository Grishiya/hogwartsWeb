package sky.pro.hogwartsWeb.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sky.pro.hogwartsWeb.exception.FacultyException;
import sky.pro.hogwartsWeb.model.Faculty;
import sky.pro.hogwartsWeb.repository.FacultyRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {
    @Mock
    FacultyRepository facultyRepository;
    @InjectMocks
    FacultyServiceImpl underTest;
    Faculty faculty = new Faculty(
            1L,
            "Griffindor",
            "brown"
    );


    @Test
    void createFaculty_checkCreateNewFaculty_createAndReturnedFaculty() {
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        Faculty result = underTest.createFaculty(faculty);
        assertEquals(faculty, result);
    }

    @Test
    void createFaculty_checkExceptionIfNewFacultyInMap_returnedFacultyException() {
        when(facultyRepository.findByNameAndColor(
                faculty.getName(), faculty.getColor())).
                thenReturn(Optional.of(faculty));
        FacultyException exception = assertThrows(
                FacultyException.class,
                () -> underTest.createFaculty(faculty));
        assertEquals("Такой факультет уже есть",
                exception.getMessage());
    }

    @Test
    void getFaculty_checkGetFacultyByIdInMap_returnedFaculty() {
        underTest.createFaculty(faculty);
        when(facultyRepository.findById(faculty.getId())).
                thenReturn(Optional.of(faculty));
        Faculty result = underTest.getFaculty(faculty.getId());
        assertEquals(faculty, result);
    }

    @Test
    void getFaculty_checkExceptionIfFacultyNotInMap_returnedFacultyException() {
        when(facultyRepository.findById(faculty.getId())).
                thenReturn(Optional.empty());
        FacultyException exception = assertThrows(
                FacultyException.class,
                () -> underTest.getFaculty(faculty.getId()));
        assertEquals("Такого факультета нет", exception.getMessage());
    }

    @Test
    void updateFaculty_checkUpdateFacultyInMap_returnedFaculty() {
        underTest.createFaculty(faculty);
        when(facultyRepository.findById(faculty.getId())).
                thenReturn(Optional.of(faculty));
        when(facultyRepository.save(faculty)).
                thenReturn(faculty);
        Faculty result = underTest.updateFaculty(faculty);
        assertEquals(faculty, result);
    }

    @Test
    void updateFaculty_checkUpdateExceptionIfFacultyNotFoundMap_throwsException() {
        when(facultyRepository.findById(faculty.getId()))
                .thenReturn(Optional.empty());
        FacultyException exception = assertThrows(FacultyException.class,
                () -> underTest.updateFaculty(faculty));
        assertEquals("Такого факультета нет", exception.getMessage());
    }

    @Test
    void deleteFaculty_checkDeleteFacultyFromMap_deleteAndReturnedFaculty() {
        underTest.createFaculty(faculty);
        when(facultyRepository.findById(faculty.getId()))
                .thenReturn(Optional.of(faculty));
        Faculty result = underTest.deleteFaculty(faculty.getId());
        assertEquals(faculty, result);
    }

    @Test
    void deleteFaculty_checkDeleteExceptionIfFacultyNotFoundMap_throwsException() {
        when(facultyRepository.findById(faculty.getId()))
                .thenReturn(Optional.empty());
        FacultyException exception = assertThrows(FacultyException.class,
                () -> underTest.deleteFaculty(faculty.getId()));
        assertEquals("Такого факультета нет", exception.getMessage());
    }

    @Test
    void readAll_checkSortGetFacultyByNameOrColor_returnedFacultyByNameOrColor() {
        underTest.createFaculty(faculty);
        when(facultyRepository.findByColor(
                faculty.getColor()))
                .thenReturn(List.of(faculty));
        List<Faculty> result = underTest.readAll("brown");
        assertEquals(List.of(faculty), result);
    }
}

