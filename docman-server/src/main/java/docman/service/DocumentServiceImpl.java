package docman.service;

import docman.exception.*;
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
        Date dateStart = simpleDateFormat.parse(createDate+" 00:00:00.000");
        Date dateEnd = simpleDateFormat.parse(createDate+" 23:59:59.999");

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
        Date dateStart = simpleDateFormat.parse(updateDate+" 00:00:00.000");
        Date dateEnd = simpleDateFormat.parse(updateDate+" 23:59:59.999");

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
    public List<Document> findDocumentByDocumentName(String documentName) {
        List<Document> documentsByName = documentRepository.findAllByDocumentName(documentName);
        if (documentsByName.isEmpty()) {
            throw new NoDocumentException("Документа с именем: " + documentsByName + " не существует");
        } else {
            return documentsByName;
        }
    }

    @Override
    public Optional<Document> findDocumentByDocumentCode(int documentCode) {
        Optional<Document> documentByCode = documentRepository.findDocumentByDocumentCode(documentCode);
        if (documentByCode.isPresent()) {
            return documentByCode;
        } else {
            throw new NoDocumentException("Документа с кодом: " + documentCode + " не существует");
        }
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
            }
        }
    }

    @Override
    @Transactional
    @SneakyThrows
    public void updateDocument(MultipartFile file, int documentCode) {
        Optional<Document> document = documentRepository.findDocumentByDocumentCode(documentCode);
        if (document.isPresent() && file != null) {

            String filename = file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "\\" + filename));
            document.get().setUpdateDate(new Date());
            document.get().setDocumentName(document.get().getDocumentName());

            documentRepository.save(document.get());
        }
    }

    @Override
    @Transactional
    public void deleteDocument(int documentCode) {
        Optional<Document> document = documentRepository.findDocumentByDocumentCode(documentCode);
        if (document.isPresent()) {
            File file = new File(uploadPath+ "\\" + documentCode + "." + document.get().getDocumentName());
            file.delete();
            documentRepository.deleteDocumentByDocumentCode(documentCode);
        } else {
            throw new NoDocumentException("Документа с кодом: " + documentCode + " не существует");
        }
    }

    private void enrichDocumentData(Document document) {
        document.setCreateDate(new Date());
        document.setUpdateDate(new Date());
    }
}