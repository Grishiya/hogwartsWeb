package sky.pro.hogwartsWeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sky.pro.hogwartsWeb.model.Student;

import javax.swing.text.Position;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByNameAndAge(String name, int age);

    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int min, int max);

    List<Student> findByFaculty_id(long faculty_id);

    @Query("select count(s) from Student s")
    Integer findAllStudentCount();

    @Query(value = "select avg(age) from Student age",nativeQuery = true)
    Integer findAvgAge();

    @Query(value = "select * from Student id order by id desc limit :size",nativeQuery = true)
    List<Student> findLastStudent(int size);
}
