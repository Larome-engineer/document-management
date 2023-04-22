package docman.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Builder
@Table(name = "documents")
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    @Id
    @Column(name = "document_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int documentId;

    @Column(name = "document_code")
    private String documentCode;

    @Column(name = "document_name")
    private String documentName;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;
}
