package docman.repository.companyRepositories;

import docman.model.companyEntities.MeansOfProtection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeansOfProtectionRepository extends JpaRepository<MeansOfProtection, Integer> {
}
