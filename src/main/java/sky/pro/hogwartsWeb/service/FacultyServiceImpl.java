package sky.pro.hogwartsWeb.service;

import org.springframework.stereotype.Service;
import sky.pro.hogwartsWeb.exception.FacultyException;
import sky.pro.hogwartsWeb.model.Faculty;
import sky.pro.hogwartsWeb.model.Student;
import sky.pro.hogwartsWeb.repository.FacultyRepository;
import sky.pro.hogwartsWeb.repository.StudentRepository;


import java.util.List;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        if (facultyRepository.findByNameAndColor(faculty.getName(), faculty.getColor()).isPresent()) {
            throw new FacultyException("Такой факультет уже есть");
        }
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFaculty(long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyException("Такого факультета нет");
        }
        return faculty.get();
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        if (facultyRepository.findById(faculty.getId()).isEmpty()) {
            throw new FacultyException("Такого факультета нет");
        }

        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty deleteFaculty(long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyException("Такого факультета нет");
        }
        facultyRepository.deleteById(id);
        return faculty.get();
    }

    @Override
    public Faculty readColor(String color) {
        Optional<Faculty> faculty = facultyRepository.findByColor(color);
        if (faculty.isEmpty()) {
            throw new FacultyException("Такого факультета нет");
        }
        return faculty.get();
    }

    @Override
    public Faculty findByNameOrColorIgnoreCase(String name, String color) {
        Optional<Faculty> faculty = facultyRepository
                .findByNameIgnoreCaseOrColorIgnoreCase(name, color);
        if (faculty.isEmpty()) {
            throw new FacultyException("Такого факультета нет");
        }
        return faculty.get();
    }

    @Override
    public List<Student> findStudentsByFacultyId(long id) {
        if (!facultyRepository.existsById(id)) {
            throw new FacultyException("Такого факультета нет");
        }
        return studentRepository.findByFaculty_id(id);
    }
}


