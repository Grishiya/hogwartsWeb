package sky.pro.hogwartsWeb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sky.pro.hogwartsWeb.model.Faculty;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(StudentException.class)
    public ResponseEntity<String> handleStudentException(StudentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(FacultyException.class)
    public ResponseEntity<String> handleFacultyException(FacultyException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}
