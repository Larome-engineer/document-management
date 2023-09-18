package docman.repository.documentRepositories;

import docman.model.documentEntities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {

    @Query(nativeQuery = true, value =
            "select documents.document_id, documents.document_name, documents.create_date, documents.update_date from documents " +
                    "left join document_details dd on documents.document_id = dd.document_id " +
                    "where dd_type=:type and dd_number=:number")
    Optional<Document> findDocumentByCodeAndType(@Param("type") String docType, @Param("number") String docNumber);
    List<Document> findAllByCreateDateBetween(Date createDateStart, Date createDateEnd);
    List<Document> findAllByUpdateDateBetween(Date updateDateStart, Date updateDateEnd);
    Optional<Document> findDocumentByDocumentName(String documentName);
    void deleteDocumentByDocumentName(String documentName);
}
