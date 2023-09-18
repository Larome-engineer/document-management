package docman.repository.companyRepositories;

import docman.model.companyEntities.BusinessProcess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessProcessRepository extends
        JpaRepository<BusinessProcess, Integer> {
}
