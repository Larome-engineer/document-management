package docman.controller;

import docman.dto.companyDto.*;
import docman.service.companyService.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping
    public List<CompanyDto> getAllCompanies(){
        return companyService.getAllCompanies();
    }
    @GetMapping("/{tin}")
    public Optional<CompanyDto> getCompanyByTin(@PathVariable("tin") String tin) {
        return companyService.getCompanyByTIN(tin);
    }

    @GetMapping("/by-email/{email}")
    public Optional<CompanyDto> getCompanyByEmail(@PathVariable("email") String email) {
        return companyService.getCompanyByEmail(email);
    }

    @GetMapping("/by-phone/{phone}")
    public Optional<CompanyDto> getCompanyByPhone(@PathVariable("phone") String phone) {
        return companyService.getCompanyByPhone(phone);
    }

    @GetMapping("/by-contact-person/{contact-person}")
    public Optional<CompanyDto> getCompanyByContactPerson(@PathVariable("contact-person") String contactPerson) {
        return companyService.getCompanyByContactPerson(contactPerson);
    }

    @GetMapping("/by-description/{description}")
    public List<CompanyDto> getCompanyByDescriptionContainingSomebody(@PathVariable("description") String description) {
        return companyService.getCompanyByDescriptionContainingSomebody(description);
    }

    @GetMapping("/addresses/{tin}")
    public List<AddressDto> findAllAddressesByTIN(@PathVariable("tin") String tin){
        return companyService.findAllCompanyAddressesByTIN(tin);
    }

    @GetMapping("/by-address/{address}")
    public Optional<CompanyDto> findCompanyByAddress(@PathVariable("address") String address) {
        return companyService.findCompanyByAddress(address);
    }

    @GetMapping("/criteria/{tin}")
    public List<CriteriaDto> getAllCriteriaByTin(@PathVariable("tin") String tin) {
        return companyService.getAllCriteria(tin);
    }

    @PostMapping
    public void saveInfo(@RequestBody CompanyDto company) {
        companyService.saveInfo(company);
    }

}

