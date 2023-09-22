package sky.pro.hogwartsWeb.service;

import sky.pro.hogwartsWeb.model.Faculty;
import sky.pro.hogwartsWeb.model.Student;

import java.util.List;

public interface FacultyService {

    Faculty createFaculty(Faculty faculty);

    Faculty getFaculty(long id);

    Faculty updateFaculty(Faculty faculty);

    Faculty deleteFaculty(long id);

    Faculty readColor(String color);

    Faculty findByColorOrNameIgnoreCase(String name, String color);

    List<Student> findStudentsByFacultyId(long id);
}
