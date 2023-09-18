package docman.model.documentEntities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
@Data
@Entity
@Builder
@Table(name = "document_details")
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDetails {

    @Id
    private int ddId;

    @Column(name = "dd_type")
    private String ddType;

    @Column(name = "dd_number")
    private String ddNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    @MapsId
    private Document document;

}
