package sky.pro.hogwartsWeb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sky.pro.hogwartsWeb.model.Faculty;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler({StudentException.class, FacultyException.class})
    public ResponseEntity<String> handleStudentException(RuntimeException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}
