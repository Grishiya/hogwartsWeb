package sky.pro.hogwartsWeb.service;

import nonapi.io.github.classgraph.utils.FileUtils;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import sky.pro.hogwartsWeb.exception.AvatarNotFoundException;
import sky.pro.hogwartsWeb.model.Avatar;
import sky.pro.hogwartsWeb.model.Faculty;
import sky.pro.hogwartsWeb.model.Student;
import sky.pro.hogwartsWeb.repository.AvatarRepository;
import sky.pro.hogwartsWeb.service.StudentService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.postgresql.hostchooser.HostRequirement.any;

@ExtendWith(MockitoExtension.class)
class AvatarServiceImplTest {

    StudentService studentService = mock(StudentService.class);
    AvatarRepository avatarRepository = mock(AvatarRepository.class);
    String avatarsDir = "./src/test/resources";

    AvatarServiceImpl avatarService = new AvatarServiceImpl(studentService, avatarRepository, avatarsDir);
    Student student = new Student(1L, "Harry", 13);

    @Test
    void uploadAvatar__avatarSavedToDbAndDirectory() throws IOException {
        MultipartFile file = new MockMultipartFile("1.jpg",
                "1.jpg"
                , "jpg"
                , new byte[]{});

        when(studentService.read(student.getId())).thenReturn(student);
        when(avatarRepository.findByStudent_id(student.getId()))
                .thenReturn(Optional.empty());

        avatarService.uploadAvatar(student.getId(), file);

        verify(avatarRepository, times(1)).save(any());
        assertTrue(FileUtils.canRead(new File(
                avatarsDir + "/"
                        + student.getId()
                        + "." + file.getContentType())));
    }

    @Test
    void readFromDB() {
Avatar avatar=new Avatar(
        1L
        ,avatarsDir
        ,300L
        ,".jpg"
        ,new byte[8]
        ,student);
        when(avatarRepository.findByStudent_id(student.getId()))
                .thenReturn(Optional.of(avatar));
        Avatar result = avatarService.readFromDB(student.getId());
        assertEquals(avatar,result);

    }

    @Test
    void readFromDB_avatarIsNotInDB_() {
        when(avatarRepository.findByStudent_id(student.getId())).thenReturn(Optional.empty());

        AvatarNotFoundException result = assertThrows(AvatarNotFoundException.class, () -> avatarService.readFromDB(student.getId()));
        assertThrows(AvatarNotFoundException.class, () -> avatarService.readFromDB(student.getId()));
        assertEquals("Аватар не найден", result.getMessage());

    }

    @Test
    void getPage__returnListOfAvatar() {
        Avatar avatar = new Avatar();
        when(avatarRepository.findAll((PageRequest) any()))
                .thenReturn(new PageImpl<Avatar>(List.of(avatar)));
        List<Avatar> result = avatarService.getPage(1, 1);
        assertEquals(List.of(avatar),result);
    }
}