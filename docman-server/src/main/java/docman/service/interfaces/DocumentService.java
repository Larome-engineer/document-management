package docman.service.interfaces;

import docman.model.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface DocumentService {
    List<Document> findAllDocuments();
    List<Document> findDocumentsByCreateDate(String createDate);
    List<Document> findDocumentsByUpdateDate(String updateDate);
    Optional<Document> findDocumentById(int documentId);
    Optional<Document> findDocumentByDocumentName(String documentName);
    List<Document> findDocumentsByDocumentCode(String documentCode);
    void createDocument(MultipartFile file);
    void updateDocument(MultipartFile file, String documentName);
    void deleteDocument(String documentName);
}
