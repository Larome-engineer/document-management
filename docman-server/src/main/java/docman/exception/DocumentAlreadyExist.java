package docman.exception;

public class DocumentAlreadyExist extends RuntimeException{
    public DocumentAlreadyExist(String message) {
        super(message);
    }
}
