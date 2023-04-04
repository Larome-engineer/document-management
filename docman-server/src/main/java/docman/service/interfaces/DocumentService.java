package docman.service.interfaces;

import docman.model.Document;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DocumentService {

    List<Document> findAllDocuments();
    List<Document> findDocumentsByCreateDate(Date createDate);
    Optional<Document> findDocumentById(int documentId);
    Optional<Document> findDocumentByDocumentName(String documentName);
    Optional<Document> findDocumentByDocumentCode(int documentCode);
    void createDocument(Document document);
    void updateDocument(Document document, String documentName);
    void deleteDocument(String documentName);

}
