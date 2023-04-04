package docman.repository;

import docman.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {

    List<Document> findAllByCreateDate(Date createDate);
    Optional<Document> findDocumentByDocumentCode(int documentCode);
    Optional<Document> findDocumentByDocumentName(String documentName);

}
