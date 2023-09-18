package docman.repository.companyRepositories;

import docman.model.companyEntities.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Set;

@Repository
public interface CriteriaRepository extends JpaRepository<Criteria, Integer> {

    @Query(nativeQuery = true, value =" select * from criteria left join company_criteria cc " +
            "on criteria.criteria_id = cc.cc_criteria_id left join company c on c.c_id = cc.cc_comp_id where c_tin=:tin")
    Set<Criteria> findAllByTin(@Param("tin") String tin);
}
