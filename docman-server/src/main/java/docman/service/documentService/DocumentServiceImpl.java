package docman.service.documentService;

import docman.exception.documentException.DocumentAlreadyExist;
import docman.exception.documentException.EmptyDataException;
import docman.exception.documentException.NoDocumentException;
import docman.exception.documentException.NotValidDocumentName;
import docman.model.documentEntities.Document;
import docman.model.documentEntities.DocumentDetails;
import docman.repository.documentRepositories.DocumentDetailsRepository;
import docman.repository.documentRepositories.DocumentRepository;
import docman.service.documentService.interfaces.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentDetailsRepository documentDetailsRepository;

    @Value(value = "${upload.path}") // Путь к серверу, где лежат документы.
    private String uploadPath;

    @Override
    public List<Document> findAllDocuments() {
        List<Document> documents = documentRepository.findAll();
        if (documents.isEmpty()) {
            throw new EmptyDataException("Нет загруженных документов");
        } else {
            return documents;
        }
    }

    @Override
    public Optional<Document> findDocumentByCodeAndType(String docType, String docNumber) {
        Optional<Document> document = documentRepository.findDocumentByCodeAndType(docType, docNumber);
        if (document.isPresent()) {
            return document;
        } else {
            throw new NoDocumentException("Такого документа не существует");
        }
    }

    @Override
    @SneakyThrows
    public List<Document> findDocumentsByCreateDate(String createDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date dateStart = simpleDateFormat.parse(createDate + " 00:00:00.000");
        Date dateEnd = simpleDateFormat.parse(createDate + " 23:59:59.999");

        List<Document> documentsByCDate = documentRepository.findAllByCreateDateBetween(dateStart, dateEnd);
        if (documentsByCDate.isEmpty()) {
            throw new EmptyDataException("Нет загруженных документов по дате: " + createDate);
        } else {

            return documentsByCDate;
        }
    }

    @Override
    @SneakyThrows
    public List<Document> findDocumentsByUpdateDate(String updateDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date dateStart = simpleDateFormat.parse(updateDate + " 00:00:00.000");
        Date dateEnd = simpleDateFormat.parse(updateDate + " 23:59:59.999");

        List<Document> documentsByUDate = documentRepository.findAllByUpdateDateBetween(dateStart, dateEnd);
        if (documentsByUDate.isEmpty()) {
            throw new EmptyDataException("Нет загруженных документов по дате: " + updateDate);
        } else {
            return documentsByUDate;
        }
    }

    @Override
    public Optional<Document> findDocumentById(int documentId) {
        Optional<Document> documentById = documentRepository.findById(documentId);
        if (documentById.isPresent()) {
            return documentById;
        } else {
            throw new NoDocumentException("Документа с id: " + documentId + " не существует");
        }
    }

    @Override
    public Optional<Document> findDocumentByDocumentName(String documentName) {
        String docType = documentName.substring(0, 4);
        Optional<Document> documentsByName = documentRepository
                .findDocumentByDocumentName("\\ИБ\\" + docType + "\\" + documentName);

        if (documentsByName.isEmpty()) {
            throw new NoDocumentException("Документа с именем: " + documentsByName + " не существует");
        } else {
            return documentsByName;
        }
    }

    @Override
    @SneakyThrows
    @Transactional
    public void createDocument(MultipartFile file) {
        createDir(file);

        String filename = file.getOriginalFilename();
        assert filename != null;
        String documentCode = filename.substring(5, 11);
        String docType = filename.substring(0, 4);

        if (documentRepository.findDocumentByDocumentName("\\ИБ\\" + docType + "\\" + filename).isPresent()) {
            throw new DocumentAlreadyExist("Такой документ уже существует");
        }

        if (!filename.matches("[ИБ]+\\.*[0-9]*\\.[0-9]+\\.[xlsdocpf]+")) {
            throw new NotValidDocumentName("Неккоректное имя документа");
        } else if (docType.equals("ИБ.0")) {
            file.transferTo(new File(uploadPath + "\\ИБ\\" + filename + " (реестр)"));
        } else {
            Document document = enrichDocument("\\ИБ\\" + docType + "\\" + filename);
            file.transferTo(new File(uploadPath + "\\ИБ\\" + docType + "\\" + filename));

            documentRepository.save(document);
            documentDetailsRepository.save(enrichDocumentDetails(docType, documentCode, document));
        }
    }

    @Override
    @SneakyThrows
    @Transactional
    public void updateDocument(MultipartFile file, String documentName) {
        String docType = documentName.substring(0, 4);
        Optional<Document> document = documentRepository
                .findDocumentByDocumentName("\\ИБ\\" + docType + "\\" + documentName);

        if (document.isPresent() && file != null) {
            String filename = file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "\\ИБ\\" + docType + "\\" + filename));
            document.get().setUpdateDate(new Date());
            document.get().setDocumentName(document.get().getDocumentName());

            documentRepository.save(document.get());
        }
    }

    @Override
    @Transactional
    public void deleteDocument(String documentName) {
        String docType = documentName.substring(0, 4);
        Optional<Document> document = documentRepository
                .findDocumentByDocumentName("\\ИБ\\" + docType + "\\" + documentName);

        if (document.isPresent()) {
            File file = new File(uploadPath + "\\ИБ\\" + docType + "\\" + documentName);
            file.delete();
            documentRepository.deleteDocumentByDocumentName("\\ИБ\\" + docType + "\\" + documentName);
        } else {
            throw new NoDocumentException("Документа с именем: " + documentName + " не существует");
        }
    }

    Document enrichDocument(String docName) {
        return Document.builder()
                .documentName(docName)
                .createDate(new Date())
                .updateDate(new Date())
                .build();
    }

    DocumentDetails enrichDocumentDetails(String docType, String docCode, Document document) {
        return DocumentDetails.builder()
                .ddType(docType)
                .ddNumber(docCode)
                .document(document)
                .build();
    }

    private void createDir(MultipartFile file) {
        if (file != null) {
            File uploadDir = new File(uploadPath + "\\ИБ\\ИБ.1");
            File uploadDir2 = new File(uploadPath + "\\ИБ\\ИБ.2");

            if (!uploadDir.exists() || !uploadDir2.exists()) {
                uploadDir.mkdirs();
                uploadDir2.mkdirs();
            }
        }
    }
}



