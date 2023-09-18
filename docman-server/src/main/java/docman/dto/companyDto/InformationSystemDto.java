package docman.dto.companyDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformationSystemDto {

    private String system;

    private boolean isForeign;
}
