package sky.pro.hogwartsWeb.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sky.pro.hogwartsWeb.model.Faculty;

import java.util.Arrays;

@ControllerAdvice
public class ControllerExceptionHandler {
    Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);
    @ExceptionHandler({StudentException.class, FacultyException.class, AvatarNotFoundException.class})
    public ResponseEntity<String> handleStudentException(RuntimeException exception) {
        logger.warn(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception) {
        logger.error(String.valueOf(exception));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Хьюстон у нас проблемы");
    }
}
