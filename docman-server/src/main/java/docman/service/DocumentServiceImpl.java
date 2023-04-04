package docman.service;

import docman.model.Document;
import docman.repository.DocumentRepository;
import docman.service.interfaces.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Value(value = "${path}") // Путь к серверу, где лежат документы.
    private String serverPath;

    @Override
    public List<Document> findAllDocuments() {
        return documentRepository.findAll();
    }

    @Override
    public List<Document> findDocumentsByCreateDate(Date createDate) {
        return documentRepository.findAllByCreateDate(createDate);
    }

    @Override
    public Optional<Document> findDocumentById(int documentId) {
        return documentRepository.findById(documentId);
    }

    @Override
    public Optional<Document> findDocumentByDocumentName(String documentName) {
        return documentRepository.findDocumentByDocumentName(documentName);
    }

    @Override
    public Optional<Document> findDocumentByDocumentCode(int documentCode) {
        return documentRepository.findDocumentByDocumentCode(documentCode);
    }

    @Override
    @Transactional
    // Тестовый пример сохранения документа
    // В последсвии разработки, в название документа, в базе, будет записываться путь документу, где он лнжит на сервере
    public void createDocument(Document document) {
        documentRepository.save(document);
    }

    @Override
    @Transactional
    public void updateDocument(Document updatedDocument, String documentName) {
        updatedDocument.setDocumentId(
                documentRepository.findDocumentByDocumentName(documentName).get().getDocumentId()
        );
        documentRepository.save(updatedDocument);
    }

    @Override
    @Transactional
    public void deleteDocument(String documentName) {
        int id = documentRepository.findDocumentByDocumentName(documentName).get().getDocumentId();
        documentRepository.deleteById(id);
    }
}