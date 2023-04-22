package docman.controller;

import docman.dto.DocumentResponse;
import docman.model.Document;
import docman.service.interfaces.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService service;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<DocumentResponse> findAllDocuments() {
        return service
                .findAllDocuments()
                .stream()
                .map(this::mapDocumentToResponse)
                .toList();
    }
    @GetMapping("/byCreate/{createDate}")
    @SneakyThrows
    public List<DocumentResponse> findAllDocumentsByCreateDate(@PathVariable("createDate") String createDate) {
        return service
                .findDocumentsByCreateDate(createDate)
                .stream()
                .map(this::mapDocumentToResponse)
                .toList();
    }

    @GetMapping("/byUpdate/{updateDate}")
    public List<DocumentResponse> findAllDocumentsByUpdateDate(@PathVariable("updateDate") String updateDate) {
        return service
                .findDocumentsByUpdateDate(updateDate)
                .stream()
                .map(this::mapDocumentToResponse)
                .toList();
    }

    @GetMapping("/byId/{documentId}")
    public Optional<DocumentResponse> findDocumentById(@PathVariable("documentId") int id) {
        return service
                .findDocumentById(id)
                .map(this::mapDocumentToResponse);
    }

    @GetMapping("/byName/{documentName}")
    public Optional<DocumentResponse> findDocumentByDocumentName(@PathVariable("documentName") String documentName) {
        return service
                .findDocumentByDocumentName(documentName)
                .map(this::mapDocumentToResponse);
    }

    @GetMapping("/byCode/{documentCode}")
    public List<DocumentResponse> findDocumentsByDocumentCode(@PathVariable("documentCode") String documentCode) {
        return service
                .findDocumentsByDocumentCode(documentCode)
                .stream()
                .map(this::mapDocumentToResponse)
                .toList();
    }

    @PostMapping("/addDocument")
    public void addDocument(@RequestPart("file") MultipartFile file) {
        service.createDocument(file);
    }

    @PatchMapping("/updateDocument/{name}")
    public void updateDocument(@RequestParam("file") MultipartFile file, @PathVariable("name") String documentName) {
        service.updateDocument(file, documentName);
    }

    @DeleteMapping("/deleteDocument/{name}")
    public void deleteDocument(@PathVariable("name") String documentName) {
        service.deleteDocument(documentName);
    }

    private DocumentResponse mapDocumentToResponse(Document document) {
        return modelMapper.map(document, DocumentResponse.class);
    }
}
