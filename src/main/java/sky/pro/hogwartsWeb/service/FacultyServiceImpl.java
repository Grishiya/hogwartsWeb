package sky.pro.hogwartsWeb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        logger.info("Был вызван метод createFaculty c данными" + faculty);
        if (facultyRepository.findByNameAndColor(faculty.getName(), faculty.getColor()).isPresent()) {
            throw new FacultyException("Такой факультет уже есть");
        }
        logger.info("Метод сохранил и вернул факультет" + facultyRepository.save(faculty));
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFaculty(long id) {
        logger.info("Был вызван метод getFaculty с параметрами" + id);
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyException("Такого факультета нет");
        }
        logger.info("Метод вернул факультет" + faculty.get());
        return faculty.get();
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Был вызван метод updateFaculty c параметрами" + faculty);
        if (facultyRepository.findById(faculty.getId()).isEmpty()) {
            throw new FacultyException("Такого факультета нет");
        }
        Faculty updateFaculty = facultyRepository.save(faculty);
        logger.info("Метод обновил факультет" + updateFaculty);
        return updateFaculty;
    }

    @Override
    public Faculty deleteFaculty(long id) {
        logger.info("Был вызван метод deleteFaculty c параметрами" + id);
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyException("Такого факультета нет");
        }
        facultyRepository.deleteById(id);
        logger.info("Метод удалил факультет" + faculty.get());
        return faculty.get();
    }

    @Override
    public Faculty readColor(String color) {
        logger.info("Был вызван метод readColor c параметром" + color);
        Optional<Faculty> faculty = facultyRepository.findByColor(color);
        if (faculty.isEmpty()) {
            throw new FacultyException("Такого факультета нет");
        }
        logger.info("Метод вернул факультет по параметру" + color + "--------" + faculty);
        return faculty.get();
    }

    @Override
    public Faculty findByNameOrColorIgnoreCase(String name, String color) {
        logger.info("Был вызван метод findByNameOrColorIgnoreCase с параметрами" + name + "---" + color);
        Optional<Faculty> faculty = facultyRepository
                .findByNameIgnoreCaseOrColorIgnoreCase(name, color);
        if (faculty.isEmpty()) {
            throw new FacultyException("Такого факультета нет");
        }
        logger.info("Метод возвращает факультет игнорируя регистр" + faculty.get());
        return faculty.get();
    }

    @Override
    public List<Student> findStudentsByFaculty_id(long id) {
        logger.info("Был вызван метод findStudentsByFaculty_id с параметром " + id);
        if (!facultyRepository.existsById(id)) {
            throw new FacultyException("Такого факультета нет");
        }
        List<Student> students = studentRepository.findByFaculty_id(id);
        logger.info("Метод вернул список студентов по факультету" +id +" студенты "+ students);
        return students;
    }
}


