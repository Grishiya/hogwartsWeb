package sky.pro.hogwartsWeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sky.pro.hogwartsWeb.model.Faculty;
import sky.pro.hogwartsWeb.model.Student;

import javax.swing.text.Position;
import java.util.List;
import java.util.Optional;
@Repository

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Optional<Faculty> findByNameAndColor(String name, String color);

    Optional<Faculty> findByColor(String color);
    Optional<Faculty> findByColorOrNameIgnoreCase(String name,String color);

}
