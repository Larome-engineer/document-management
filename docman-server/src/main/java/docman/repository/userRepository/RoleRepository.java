package docman.repository.userRepository;

import docman.model.userEntities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByRoleValue(String value);
}
