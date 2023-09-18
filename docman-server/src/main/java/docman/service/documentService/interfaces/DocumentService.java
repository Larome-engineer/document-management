package docman.service.documentService.interfaces;

import docman.model.documentEntities.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface DocumentService {
    List<Document> findAllDocuments();
    List<Document> findDocumentsByCreateDate(String createDate);

    Optional<Document> findDocumentByCodeAndType(String docType, String docNumber);
    List<Document> findDocumentsByUpdateDate(String updateDate);
    Optional<Document> findDocumentById(int documentId);
    Optional<Document> findDocumentByDocumentName(String documentName);
    void createDocument(MultipartFile file);
    void updateDocument(MultipartFile file, String documentName);
    void deleteDocument(String documentName);


}
