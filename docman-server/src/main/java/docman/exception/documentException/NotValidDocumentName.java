package docman.exception.documentException;

public class NotValidDocumentName extends RuntimeException{
    public NotValidDocumentName(String message) {
        super(message);
    }
}
