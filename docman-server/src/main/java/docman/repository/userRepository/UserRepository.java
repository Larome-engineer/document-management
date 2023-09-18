package docman.repository.userRepository;

import docman.model.userEntities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findUserByPhone(String phone);

    Optional<User> findUserByEmail(String email);

    List<User> findAllByFirstnameIgnoreCase(String firstname);

    List<User> findAllByLastnameIgnoreCase(String lastname);

    List<User> findAllByMiddleNameIgnoreCase(String middleName);

    List<User> findAllByLastnameIgnoreCaseAndFirstnameIgnoreCaseAndMiddleNameIgnoreCase(
            String lastname, String firstname, String middleName
    );

    List<User> findAllByLastnameIgnoreCaseAndFirstnameIgnoreCase(String lastname, String firstname);

    List<User> findAllByFirstnameIgnoreCaseAndMiddleNameIgnoreCase(String firstname, String middleName);

    List<User> findAllByOnConfirmEquals(boolean onConfirmStatus);

    void deleteUserByUsername(String username);


    @Query(nativeQuery = true, value = "select user_on_confirm from users where user_username=:username")
    boolean getUserConfirmStatusByUsername(@Param("username") String username);
}
