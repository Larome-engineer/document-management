package docman.controller;

import docman.model.Document;
import docman.service.DocumentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentServiceImpl documentService;

    public List<Document> findAllDocuments() {
        return null; // pass...
    }

    public List<Document> findAllDocumentsByCreateDate(Date createDate) {
        return null; // pass...
    }

    public Optional<Document> findDocumentById(int id) {
        return null; // pass...
    }

    public List<Document> findDocumentByDocumentName(String documentName) {
        return null; // pass...
    }

    @GetMapping("/byCode/{documentCode}")
    public Optional<Document> findDocumentByDocumentCode(@PathVariable("documentCode") int documentCode) {
        return documentService.findDocumentByDocumentCode(documentCode);
    }

    @PostMapping("/addDocument")
    public void addDocument(@RequestParam("file") MultipartFile file) throws IOException {
        documentService.createDocument(file);
    }

    public void updateDocument(Document updatedDocument, int documentName) {
        // pass...
    }

    public void deleteDocument(int documentCode) {
        // pass...
    }
}
