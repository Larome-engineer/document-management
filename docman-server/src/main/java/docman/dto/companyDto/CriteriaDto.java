package docman.dto.companyDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaDto {

    private int responsibleIs;
    private Date createDate;
    private boolean responsibleForProtect;
    private int numOfTerritories;
    private boolean personalDataBusinessProcess;
    private int numOfEmployees;
    private int numOfClients;
    private boolean publiclyInfo;
    private boolean biometricInfo;
    private boolean specialInfo;
    private boolean employeeProcessing;
    private boolean clientsProcessing;
    private boolean otherProcessing;
    private int numOfServers;
    private int arm;
    private boolean skzi;
    private boolean threatModel;
    private boolean personalDataProtectPolicy;
    private boolean otherDocumentsOfPdProtect;
    private boolean securityLevel;
    private int degreeOfDocumentation;
}
