package docman.repository.companyRepositories;

import docman.model.companyEntities.InformationSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformationSystemRepository extends JpaRepository<InformationSystem, Integer> {
}
