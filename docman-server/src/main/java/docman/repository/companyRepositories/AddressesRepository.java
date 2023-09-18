package docman.repository.companyRepositories;

import docman.model.companyEntities.Addresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AddressesRepository extends JpaRepository<Addresses, Integer> {

    @Query(nativeQuery = true, value = "select a_address from addresses left join company_addresses ca " +
            "on addresses.a_id = ca.ca_ad_id left join company c on c.c_id = ca.ca_comp_id where c_tin=:tin")
    Set<Addresses> findAllAddressesByCompanyTIN(@Param("tin") String tin);

}
