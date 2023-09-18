package docman.dto.companyDto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddInfoDto {

    private String description;
    private String contactPerson;

    private String email;

    private String phoneNumber;

    private Date dateSheetWasCreated;

    private String site;
}
