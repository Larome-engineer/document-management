package docman.service.interfaces;

import docman.model.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DocumentService {

    List<Document> findAllDocuments();
    List<Document> findDocumentsByCreateDate(Date createDate);
    Optional<Document> findDocumentById(int documentId);

    List<Document> findDocumentByDocumentName(String documentName);
    Optional<Document> findDocumentByDocumentCode(int documentCode);
    void createDocument(MultipartFile file);
    void updateDocument(Document document, int documentName);
    void deleteDocument(int documentCode);

}
