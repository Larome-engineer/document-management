package docman.service.companyService;

import docman.dto.companyDto.*;
import docman.model.companyEntities.*;
import docman.repository.companyRepositories.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {
    private final ModelMapper modelMapper;
    private final CompanyRepository companyRepository;
    private final ReportsRepository companyReportsRepository;
    private final CompanyAddInfoRepository companyAddInfoRepository;
    private final CriteriaRepository companyCriteriaRepository;
    private final AddressesRepository companyAddressesRepository;
    private final MeansOfProtectionRepository meansOfProtectionRepository;
    private final InformationSystemRepository informationSystemRepository;
    private final BusinessProcessRepository personalDataBusinessProcessRepository;

    public List<CompanyDto> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(this::mapToCompanyDTO)
                .toList();
    }
    public Optional<CompanyDto> getCompanyByTIN(String TIN) {
        return companyRepository.findCompanyByTin(TIN).map(this::mapToCompanyDTO);
    }
    public Optional<CompanyDto> getCompanyByEmail(String email) {
        return companyRepository.findCompanyByAddInfo_Email(email).map(this::mapToCompanyDTO);
    }

    public Optional<CompanyDto> getCompanyByPhone(String phone) {
        return companyRepository.findCompanyByAddInfo_PhoneNumber(phone).map(this::mapToCompanyDTO);
    }

    public Optional<CompanyDto> getCompanyByContactPerson(String contactPerson) {
        return companyRepository.findCompanyByAddInfo_ContactPerson(contactPerson).map(this::mapToCompanyDTO);
    }

    public List<CompanyDto> getCompanyByDescriptionContainingSomebody(String text) {
        return companyRepository.findAllByAddInfoDescriptionContainingIgnoreCase(text)
                .stream()
                .map(this::mapToCompanyDTO)
                .toList();
    }

    public List<AddressDto> findAllCompanyAddressesByTIN(String tin) {
        return mapToAddressesDto(companyAddressesRepository.findAllAddressesByCompanyTIN(tin));
    }

    public Optional<CompanyDto> findCompanyByAddress(String address) {
        return companyRepository.findCompanyByAddress(address).map(this::mapToCompanyDTO);
    }

    public List<CriteriaDto> getAllCriteria(String tin) {
        return mapToCriteriaDto(companyCriteriaRepository.findAllByTin(tin));
    }

    @Transactional
    public void saveInfo(CompanyDto companyDto) {
        Set<Criteria> criteriaSet = mapToCompanyCriteria(companyDto.getCriteria());
        Set<Addresses> addressesSet = mapToCompanyAddresses(companyDto.getAddresses());
        Set<Reports> reportsSet = mapToCompanyReports(companyDto.getReports());
        Set<InformationSystem> informationSystemSet = mapToCompanyInfoSystem(companyDto.getInformationSystems());
        Set<MeansOfProtection> meansOfProtectionSet = mapToMeansOfProtection(companyDto.getMeansOfProtections());
        Set<BusinessProcess> businessProcessSet = mapToCompanyPDBP(companyDto.getBusinessProcesses());

        AddInfo addCompanyInfo = mapToCompanyAddInfo(companyDto.getAddInfo());

        companyAddInfoRepository.save(addCompanyInfo);
        companyCriteriaRepository.saveAll(criteriaSet);
        companyAddressesRepository.saveAll(addressesSet);
        companyReportsRepository.saveAll(reportsSet);
        informationSystemRepository.saveAll(informationSystemSet);
        meansOfProtectionRepository.saveAll(meansOfProtectionSet);
        personalDataBusinessProcessRepository.saveAll(businessProcessSet);

        var company = Company.builder()
                .tin(companyDto.getTin())
                .criteria(criteriaSet)
                .addInfo(addCompanyInfo)
                .addresses(addressesSet)
                .reports(reportsSet)
                .informationSystems(informationSystemSet)
                .meansOfProtections(meansOfProtectionSet)
                .businessProcesses(businessProcessSet)
                .build();

        companyRepository.save(company);
    }


    // ENTITY-MAPPING

    private CompanyDto mapToCompanyDTO(Company company) {
        return modelMapper.map(company, CompanyDto.class);
    }
    private AddInfo mapToCompanyAddInfo(AddInfoDto companyDto) {
        return modelMapper.map(companyDto, AddInfo.class);
    }

    private Set<Addresses> mapToCompanyAddresses(Set<AddressDto> companyDto) {
        return companyDto
                .stream()
                .map(companyAddressDto -> modelMapper.map(companyAddressDto, Addresses.class))
                .collect(Collectors.toSet());
    }

    private Set<Criteria> mapToCompanyCriteria(Set<CriteriaDto> companyDto) {
        return companyDto.stream()
                .map(companyDto1 -> modelMapper.map(companyDto1, Criteria.class))
                .collect(Collectors.toSet());
    }

    private Set<InformationSystem> mapToCompanyInfoSystem(Set<InformationSystemDto> companyDto) {
        return companyDto
                .stream()
                .map(companyInformationSystemDto -> modelMapper
                        .map(companyInformationSystemDto, InformationSystem.class))
                .collect(Collectors.toSet());
    }

    private Set<BusinessProcess> mapToCompanyPDBP(Set<BusinessProcessDto> companyDto) {
        return companyDto
                .stream()
                .map(companyPersonalDataBusinessProcessDto -> modelMapper
                        .map(companyPersonalDataBusinessProcessDto, BusinessProcess.class))
                .collect(Collectors.toSet());
    }

    private Set<Reports> mapToCompanyReports(Set<ReportsDto> companyDto) {
        return companyDto.stream()
                .map(companyReportsDto -> modelMapper.map(companyReportsDto, Reports.class))
                .collect(Collectors.toSet());
    }

    private Set<MeansOfProtection> mapToMeansOfProtection(Set<MeansOfProtectionDto> meansOfProtectionDto) {
        return meansOfProtectionDto.stream()
                .map(meansOfProtectionDto1 -> modelMapper.map(meansOfProtectionDto1, MeansOfProtection.class))
                .collect(Collectors.toSet());
    }

    private AddInfoDto mapToAddInfoDTO(AddInfo addInfo){
        return modelMapper.map(addInfo, AddInfoDto.class);
    }
    private List<AddressDto> mapToAddressesDto(Set<Addresses> addresses){
        return addresses.stream()
                .map(address -> modelMapper.map(address, AddressDto.class))
                .toList();
    }
    private List<CriteriaDto> mapToCriteriaDto(Set<Criteria> criteria){
        return criteria.stream()
                .map(criteria1 -> modelMapper.map(criteria1, CriteriaDto.class))
                .toList();
    }
    private List<InformationSystemDto> mapToInfoSystemDto(Set<InformationSystem> informationSystems){
        return informationSystems.stream()
                .map(informationSystem -> modelMapper.map(informationSystem, InformationSystemDto.class))
                .toList();
    }
    private List<BusinessProcessDto> mapToBusinessProcessDto(Set<BusinessProcess> businessProcesses){
        return businessProcesses.stream()
                .map(businessProcess -> modelMapper.map(businessProcess, BusinessProcessDto.class))
                .toList();
    }
    private List<ReportsDto> mapToReportsDto(Set<Reports> reports){
        return reports.stream()
                .map(report -> modelMapper.map(reports, ReportsDto.class))
                .toList();
    }
    private List<MeansOfProtectionDto> mapToMeansOfProtectionDto(Set<MeansOfProtection> meansOfProtections){
        return meansOfProtections.stream()
                .map(mean -> modelMapper.map(mean, MeansOfProtectionDto.class))
                .toList();
    }
}


