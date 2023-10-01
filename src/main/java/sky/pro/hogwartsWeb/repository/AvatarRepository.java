package sky.pro.hogwartsWeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sky.pro.hogwartsWeb.model.Avatar;

import java.util.Optional;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar,Long> {
    Optional<Avatar> findByStudent_id(long studentId);
}
