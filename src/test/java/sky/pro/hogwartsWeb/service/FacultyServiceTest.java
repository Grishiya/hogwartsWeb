package sky.pro.hogwartsWeb.service;

import org.junit.jupiter.api.Test;
import sky.pro.hogwartsWeb.exception.FacultyException;
import sky.pro.hogwartsWeb.model.Faculty;
import sky.pro.hogwartsWeb.model.Faculty;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FacultyServiceTest {
    FacultyService underTest = new FacultyService();
    Faculty faculty = new Faculty(
            1L,
            "Griffindor",
            "brown"
    );


    @Test
    void createFaculty_checkCreateNewFaculty_createAndReturnedFaculty() {
        Faculty result = underTest.createFaculty(faculty);
        assertEquals(faculty, result);
        assertEquals(1, result.getId());
    }

    @Test
    void createFaculty_checkExceptionIfNewFacultyInMap_returnedFacultyException() {
        underTest.createFaculty(faculty);
        FacultyException exception = assertThrows(FacultyException.class, ()
                -> underTest.createFaculty(faculty));
        assertEquals("Такой факультет уже есть", exception.getMessage());
    }

    @Test
    void getFaculty_checkGetFacultyInMap_returnedFaculty() {
        underTest.createFaculty(faculty);
        Faculty result = underTest.getFaculty(1);
        assertEquals(faculty, result);
    }

    @Test
    void getFaculty_checkExceptionIfFacultyNotInMap_returnedFacultyException() {
        FacultyException exception = assertThrows(FacultyException.class,
                () -> underTest.getFaculty(faculty.getId()));
        assertEquals("Такого факультета нет", exception.getMessage());
    }

    @Test
    void updateFaculty_checkUpdateFacultyInMap_returnedFaculty() {
        underTest.createFaculty(faculty);
        Faculty setFaculty = new Faculty(
                1L,
                "Puffenduy",
                "brown"
        );
        Faculty result = underTest.updateFaculty(setFaculty);
        assertNotEquals(faculty, result);
    }

    @Test
    void updateFaculty_checkUpdateExceptionIfFacultyNotFoundMap_throwsException() {
        FacultyException exception = assertThrows(FacultyException.class,
                () -> underTest.updateFaculty(faculty));
        assertEquals("Такого факультета нет", exception.getMessage());
    }

    @Test
    void deleteFaculty_checkDeleteFacultyFromMap_deleteAndReturnedFaculty() {
        underTest.createFaculty(faculty);
        Faculty result = underTest.deleteFaculty(faculty.getId());
        assertEquals(faculty, result);
    }

    @Test
    void deleteFaculty_checkDeleteExceptionIfFacultyNotFoundMap_throwsException() {
        FacultyException exception = assertThrows(FacultyException.class,
                () -> underTest.deleteFaculty(faculty.getId()));
        assertEquals("Такого факультета нет", exception.getMessage());
    }

    @Test
    void readAll_checkSortGetFacultyByColor_returnedFacultyByColor() {
        underTest.createFaculty(faculty);
        List<Faculty> result = underTest.readAll("brown");
        assertEquals(List.of(faculty), result);
    }
}

