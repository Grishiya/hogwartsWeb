package sky.pro.hogwartsWeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.hogwartsWeb.model.Student;

import javax.swing.text.Position;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByNameAndAge(String name, int age);

    List<Student> findByAge(int minAge);

    List<Student> findByAgeBetween(int min, int max);

    List<Student> findByFaculty_id(long faculty_id);
}
