package sky.pro.hogwartsWeb.service;

import org.springframework.web.multipart.MultipartFile;
import sky.pro.hogwartsWeb.model.Avatar;

import java.io.IOException;

public interface AvatarService {

    void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;

    Avatar readFromDB(long id);
}
