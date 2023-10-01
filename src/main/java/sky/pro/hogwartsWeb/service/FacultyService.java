package sky.pro.hogwartsWeb.service;

import sky.pro.hogwartsWeb.model.Faculty;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);

    Faculty getFaculty(long id);

    Faculty updateFaculty(Faculty faculty);

    Faculty deleteFaculty(long id);
}
