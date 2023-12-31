package sky.pro.hogwartsWeb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sky.pro.hogwartsWeb.exception.AvatarNotFoundException;
import sky.pro.hogwartsWeb.model.Avatar;
import sky.pro.hogwartsWeb.model.Student;
import sky.pro.hogwartsWeb.repository.AvatarRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarServiceImpl implements AvatarService {
    private final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);
    private final String avatarsDir;
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    public AvatarServiceImpl(StudentService studentService
            , AvatarRepository avatarRepository
            , @Value("${path.to.avatars.folder}") String avatarsDir) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
        this.avatarsDir = avatarsDir;
    }

    @Override
    public void uploadAvatar(Long studentId
            , MultipartFile avatarFile) throws IOException {
        logger.info("Был вызван метод uploadAvatar c параметрами" + studentId + avatarFile);
        Student student = studentService.read(studentId);
        Path filePath = Path.of(avatarsDir
                , student.getId()
                        + "."
                        + getExtensions(Objects.requireNonNull(avatarFile.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = avatarRepository.findByStudent_id(studentId).orElse(new Avatar());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
        logger.info("Метод загрузил автар студенту");
    }

    @Override
    public Avatar readFromDB(long id) {
        logger.info("Вызван метод readFromDb с параметром" + id);
        Avatar avatar=avatarRepository.findByStudent_id(id)
                .orElseThrow(() -> new AvatarNotFoundException("Аватар не найден"));
        logger.info("Метод вернул автар"+ avatar.getId());
        return avatar;
    }

    @Override
    public List<Avatar> getPage(int pageNo, int size) {
        logger.info("Вызван метод getPage с параметрами"+pageNo+size);

        PageRequest pageRequest = PageRequest.of(pageNo, size);
        List<Avatar> avatars=avatarRepository.findAll(pageRequest).getContent();
        logger.info("Метод вернул список "+ avatars);
        return avatars;
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
