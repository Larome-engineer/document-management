package docman.controller;

import docman.model.Document;
import docman.service.DocumentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentServiceImpl documentService;

    public List<Document> findAllDocuments() {
        return documentService.findAllDocuments();
    }

    public List<Document> findAllDocumentsByCreateDate(Date createDate) {
        return documentService.findDocumentsByCreateDate(createDate);
    }

    public Optional<Document> findDocumentById(int id) {
        return documentService.findDocumentById(id);
    }

    public Optional<Document> findDocumentByDocumentName(String documentName) {
        return documentService.findDocumentByDocumentName(documentName);
    }

    public Optional<Document> findDocumentByDocumentCode(int documentCode) {
        return documentService.findDocumentByDocumentCode(documentCode);
    }

    public void createDocument(Document document) {
        documentService.createDocument(document);
    }
    public void updateDocument(Document updatedDocument, String documentName){
        documentService.updateDocument(updatedDocument, documentName);
    }
    public void deleteDocument(String documentName){
        documentService.deleteDocument(documentName);
    }
}
