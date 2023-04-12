package docman.controller;

import docman.dto.DocumentResponse;
import docman.model.Document;
import docman.service.DocumentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentServiceImpl documentService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<DocumentResponse> findAllDocuments() {
        return documentService
                .findAllDocuments()
                .stream()
                .map(this::mapDocumentToResponse)
                .toList();
    }

    @GetMapping("/byCreate/{createDate}")
    @SneakyThrows
    public List<DocumentResponse> findAllDocumentsByCreateDate(@PathVariable("createDate") String createDate) {
        return documentService
                .findDocumentsByCreateDate(createDate)
                .stream()
                .map(this::mapDocumentToResponse)
                .toList();
    }

    @GetMapping("/byUpdate/{updateDate}")
    public List<DocumentResponse> findAllDocumentsByUpdateDate(@PathVariable("updateDate") String updateDate) {
        return documentService
                .findDocumentsByUpdateDate(updateDate)
                .stream()
                .map(this::mapDocumentToResponse)
                .toList();
    }

    @GetMapping("/byId/{documentId}")
    public Optional<DocumentResponse> findDocumentById(@PathVariable("documentId") int id) {
        return documentService
                .findDocumentById(id)
                .map(this::mapDocumentToResponse);
    }

    @GetMapping("/byName/{documentName}")
    public List<DocumentResponse> findDocumentByDocumentName(@PathVariable("documentName") String documentName) {
        return documentService
                .findDocumentByDocumentName(documentName)
                .stream()
                .map(this::mapDocumentToResponse)
                .toList();
    }

    @GetMapping("/byCode/{documentCode}")
    public Optional<DocumentResponse> findDocumentByDocumentCode(@PathVariable("documentCode") int documentCode) {
        return documentService
                .findDocumentByDocumentCode(documentCode)
                .map(this::mapDocumentToResponse);
    }

    @PostMapping("/addDocument")
    public void addDocument(@RequestParam("file") MultipartFile file) {
        documentService.createDocument(file);
    }

    @PatchMapping("/updateDocument/{code}")
    public void updateDocument(@RequestParam("file") MultipartFile file, @PathVariable("code") int documentCode) {
        documentService.updateDocument(file, documentCode);
    }

    @DeleteMapping("/deleteDocument/{code}")
    public void deleteDocument(@PathVariable("code") int documentCode) {
        documentService.deleteDocument(documentCode);
    }

    private DocumentResponse mapDocumentToResponse(Document document) {
        return modelMapper.map(document, DocumentResponse.class);
    }
}

