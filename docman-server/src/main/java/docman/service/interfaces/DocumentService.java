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
    List<Document> findDocumentByDocumentName(String documentName);
    Optional<Document> findDocumentByDocumentCode(int documentCode);
    void createDocument(MultipartFile file);
    void updateDocument(MultipartFile file, int documentCode);
    void deleteDocument(int documentCode);

}
