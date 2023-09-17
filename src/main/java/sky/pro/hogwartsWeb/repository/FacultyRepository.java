package sky.pro.hogwartsWeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sky.pro.hogwartsWeb.model.Faculty;
import sky.pro.hogwartsWeb.model.Student;

import javax.swing.text.Position;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

}
