package docman.service.adminService;

import docman.model.userEntities.User;
import docman.repository.userRepository.UserRepository;
import docman.service.adminService.interfaces.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    @Override
    @Transactional
    public void deleteUser(String username) {
        userRepository.deleteUserByUsername(username);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findAllUsersOnConfirm() {
        return userRepository.findAllByOnConfirmEquals(true);
    }

    @Override
    @Transactional
    public void updateOnConfirmStatus(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            user.get().setOnConfirm(false);
            userRepository.save(user.get());
        }
        else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public Optional<User> findUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findUserByFirstname(String firstname) {
        return userRepository.findAllByFirstnameIgnoreCase(firstname);
    }

    @Override
    public List<User> findUserByLastname(String lastname) {
        return userRepository.findAllByLastnameIgnoreCase(lastname);
    }

    @Override
    public List<User> findUserByMiddleName(String middleName) {
        return userRepository.findAllByMiddleNameIgnoreCase(middleName);
    }


    @Override
    public Optional<User> findUserByPhone(String phone) {
        return userRepository.findUserByPhone(phone);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findUserByLFM(String lastname, String firstname, String middleName) {
        return userRepository.findAllByLastnameIgnoreCaseAndFirstnameIgnoreCaseAndMiddleNameIgnoreCase(
                lastname, firstname, middleName
        );
    }

    @Override
    public List<User> findUserByLF(String lastname, String firstname) {
        return userRepository.findAllByLastnameIgnoreCaseAndFirstnameIgnoreCase(lastname, firstname);
    }

    @Override
    public List<User> findUsersByFM(String firstname, String middleName) {
        return userRepository.findAllByFirstnameIgnoreCaseAndMiddleNameIgnoreCase(firstname, middleName);
    }
}
