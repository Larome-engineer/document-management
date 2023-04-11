package docman.service;

import docman.exception.DocumentAlreadyExist;
import docman.exception.NotValidCodeValue;
import docman.exception.NotValidDocumentName;
import docman.model.Document;
import docman.repository.DocumentRepository;
import docman.service.interfaces.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Value(value = "${upload.path}") // Путь к серверу, где лежат документы.
    private String uploadPath;

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
    public List<Document> findDocumentByDocumentName(String documentName) {
        return documentRepository.findDocumentByDocumentName(documentName);
    }

    @Override
    public Optional<Document> findDocumentByDocumentCode(int documentCode) {
        return documentRepository.findDocumentByDocumentCode(documentCode);
    }


    @Override
    @Transactional
    @SneakyThrows
    public void createDocument(MultipartFile file) {
        Document document = new Document();
        if (file != null) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String filename = file.getOriginalFilename();
            assert filename != null;
            String documentName = filename.substring(7);
            String documentCode = filename.substring(0, 6);

            try {
                document.setDocumentCode(Integer.parseInt(documentCode));
            } catch (Exception e) {
                throw new NotValidCodeValue("Неккоректный формат кода документа");
            }

            if (!documentName.matches("[a-zA-Zа-яА-Я]+\\s[0-9]+\\.xls")) {
                throw new NotValidDocumentName("Неккоректное имя документа");
            } else if (documentRepository.findDocumentByDocumentCode(Integer.parseInt(documentCode)).isPresent()) {
                throw new DocumentAlreadyExist("Такой документ уже существует");
            } else {
                document.setDocumentName(documentName);
                enrichDocumentData(document);
                file.transferTo(new File(uploadPath + "\\" + filename));
                documentRepository.save(document);

                System.out.println("Документ был успешно сохранен!");
            }
        }

    }

    @Override
    @Transactional
    public void updateDocument(Document updatedDocument, int documentCode) {
        Optional<Document> document = documentRepository.findDocumentByDocumentCode(documentCode);
        document.ifPresent(value -> updatedDocument.setDocumentId(value.getDocumentId()));
        // ...
        // Метод находится в доработке
        documentRepository.save(updatedDocument);
    }

    @Override
    @Transactional
    public void deleteDocument(int documentCode) {
        documentRepository.deleteDocumentByDocumentCode(documentCode);

    }

    private void enrichDocumentData(Document document) {
        document.setCreateDate(new Date());
        document.setUpdateDate(new Date());
    }
}