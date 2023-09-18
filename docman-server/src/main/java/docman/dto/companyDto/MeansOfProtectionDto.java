package docman.dto.companyDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeansOfProtectionDto {

    private String antivirus;

    private String againstUnauthorizedAccess;

    private String interNetworkShielding;

    private String siem;

    private String dlp;

    private String crypto;

    private String securityMonitoringTool;

}
