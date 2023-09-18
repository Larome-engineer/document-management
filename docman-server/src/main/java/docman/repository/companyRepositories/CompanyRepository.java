package docman.repository.companyRepositories;

import docman.model.companyEntities.Addresses;
import docman.model.companyEntities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Optional<Company> findCompanyByTin(String tin);
    Optional<Company> findCompanyByAddInfo_Email(String email);
    Optional<Company> findCompanyByAddInfo_PhoneNumber(String phone);
    Optional<Company> findCompanyByAddInfo_ContactPerson(String contactPerson);
    List<Company> findAllByAddInfoDescriptionContainingIgnoreCase(String description);

    @Query(nativeQuery = true, value = "select * from company left join company_addresses ca on company.c_id = ca.ca_comp_id\n" +
            "                      left join addresses a on a.a_id = ca.ca_ad_id where a_address=:address")
    Optional<Company> findCompanyByAddress(@Param("address") String address);
}
