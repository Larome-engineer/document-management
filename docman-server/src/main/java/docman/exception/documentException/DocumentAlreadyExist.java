package docman.exception.documentException;

public class DocumentAlreadyExist extends RuntimeException{
    public DocumentAlreadyExist(String message) {
        super(message);
    }
}
