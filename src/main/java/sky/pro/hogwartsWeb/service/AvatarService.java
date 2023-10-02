package sky.pro.hogwartsWeb.service;

import org.springframework.web.multipart.MultipartFile;
import sky.pro.hogwartsWeb.model.Avatar;

import java.io.IOException;
import java.util.List;

public interface AvatarService {

    void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;

    Avatar readFromDB(long id);

    List<Avatar> getPage(int pageNo, int size);
}
