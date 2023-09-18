package docman.controller;

import docman.dto.documentDto.DocumentResponse;
import docman.model.documentEntities.Document;
import docman.service.documentService.interfaces.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService service;

    @GetMapping
    public List<DocumentResponse> findAllDocuments() {
        return service
                .findAllDocuments()
                .stream()
                .map(this::mapDocumentToResponse)
                .toList();
    }

    @GetMapping("/byCode")
    public Optional<DocumentResponse> findDocumentByCodeAndType(@RequestParam("type") String type,
                                                                @RequestParam("number") String number) {
        return service
                .findDocumentByCodeAndType(type, number)
                .map(this::mapDocumentToResponse);
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
    @PreAuthorize("hasAnyRole('ADMIN', 'EXPERT')")
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

    @PostMapping("/addDocument")
    @PreAuthorize("hasAnyRole('EXPERT', 'ADMIN')")
    public void addDocument(@RequestPart("file") MultipartFile file) {
        service.createDocument(file);
    }

    @PatchMapping("/updateDocument/{name}")
    @PreAuthorize("hasAnyRole('EXPERT', 'ADMIN')")
    public void updateDocument(@RequestParam("file") MultipartFile file, @PathVariable("name") String documentName) {
        service.updateDocument(file, documentName);
    }

    private DocumentResponse mapDocumentToResponse(Document document) {
        return DocumentResponse.builder()
                .documentName(document.getDocumentName().substring(9))
                .createDate(document.getCreateDate())
                .updateDate(document.getUpdateDate())
                .build();
    }
}
