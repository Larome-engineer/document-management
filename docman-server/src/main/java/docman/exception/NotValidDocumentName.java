package docman.exception;

public class NotValidDocumentName extends RuntimeException{
    public NotValidDocumentName(String message) {
        super(message);
    }
}
