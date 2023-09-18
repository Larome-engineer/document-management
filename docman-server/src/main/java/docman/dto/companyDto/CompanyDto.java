package docman.dto.companyDto;

import docman.model.companyEntities.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    private String tin;
    private AddInfoDto addInfo;

    private Set<CriteriaDto> criteria;

    private Set<ReportsDto> reports;

    private Set<MeansOfProtectionDto> meansOfProtections;

    private Set<InformationSystemDto> informationSystems;

    private Set<AddressDto> addresses;

    private Set<BusinessProcessDto> businessProcesses;
}
