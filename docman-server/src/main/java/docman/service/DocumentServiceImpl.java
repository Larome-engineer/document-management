package docman.service;

import docman.exception.DocumentAlreadyExist;
import docman.exception.EmptyDataException;
import docman.exception.NoDocumentException;
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
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;

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
                .findDocumentByDocumentName("\\ИБ\\"+docType+"\\"+documentName);

        if (documentsByName.isEmpty()) {
            throw new NoDocumentException("Документа с именем: " + documentsByName + " не существует");
        } else {
            return documentsByName;
        }
    }

    @Override
    public List<Document> findDocumentsByDocumentCode(String documentCode) {
        List<Document> documentByCode = documentRepository.findAllByDocumentCode(documentCode);
        if (!documentByCode.isEmpty()) {
            return documentByCode;
        } else {
            throw new NoDocumentException("Документа с кодом: " + documentCode + " не существует");
        }
    }

    @Override
    @SneakyThrows
    @Transactional
    public void createDocument(MultipartFile file) {
        createDir(file);

        String filename = file.getOriginalFilename();
        String documentCode = filename.substring(5, 11);
        String docType = filename.substring(0, 4);
        if (documentRepository.findDocumentByDocumentName("\\ИБ\\"+docType+"\\"+filename).isPresent()) {
            throw new DocumentAlreadyExist("Такой документ уже существует");
        }

        if (!filename.matches("[ИБ]+\\.*[0-9]*\\.[0-9]+\\.[xlsdocpf]+")) {
            throw new NotValidDocumentName("Неккоректное имя документа");
        } else if(docType.equals("ИБ.0")) {
            file.transferTo(new File(uploadPath + "\\ИБ\\" + filename+" (реестр)"));
        } else {
            Document document = new Document();
            enrichDocumentData(document, "\\ИБ\\" + docType + "\\" + filename, documentCode);
            file.transferTo(new File(uploadPath + "\\ИБ\\" + docType + "\\" + filename));
            documentRepository.save(document);
        }
    }

    @Override
    @SneakyThrows
    @Transactional
    public void updateDocument(MultipartFile file, String documentName) {
        String docType = documentName.substring(0, 4);
        Optional<Document> document = documentRepository
                .findDocumentByDocumentName("\\ИБ\\"+docType+"\\"+documentName);

        if (document.isPresent() && file != null) {
            String filename = file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "\\ИБ\\"+docType+"\\" + filename));
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
                .findDocumentByDocumentName("\\ИБ\\"+docType+"\\"+documentName);

        if (document.isPresent()) {
            File file = new File(uploadPath + "\\ИБ\\"+docType+"\\"+documentName);
            file.delete();
            documentRepository.deleteDocumentByDocumentName("\\ИБ\\"+docType+"\\"+documentName);
        } else {
            throw new NoDocumentException("Документа с именем: " + documentName + " не существует");
        }
    }

    private void enrichDocumentData(Document document, String docName, String docCode) {
        document.setDocumentName(docName);
        document.setDocumentCode(docCode);
        document.setCreateDate(new Date());
        document.setUpdateDate(new Date());
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



