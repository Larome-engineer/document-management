package docman.service.adminService.interfaces;

import docman.model.userEntities.User;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    void deleteUser(String username);
    List<User> findAllUsers();
    List<User> findAllUsersOnConfirm();
    void updateOnConfirmStatus(String username);
    Optional<User> findUserById(int id);
    List<User> findUserByFirstname(String firstname);
    List<User> findUserByLastname(String lastname);
    List<User> findUserByMiddleName(String middleName);
    Optional<User> findUserByPhone(String phone);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);
    List<User> findUserByLFM(String firstname, String lastname, String middleName);
    List<User> findUserByLF(String firstname, String lastname);
    List<User> findUsersByFM(String firstname, String middleName);

}
