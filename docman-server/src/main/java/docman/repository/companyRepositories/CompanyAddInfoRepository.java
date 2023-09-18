package docman.repository.companyRepositories;

import docman.model.companyEntities.AddInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyAddInfoRepository extends JpaRepository<AddInfo, Integer> {

}
