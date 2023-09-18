package docman.controller;

import docman.model.userEntities.User;
import docman.service.adminService.interfaces.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @DeleteMapping("/delete/{username}")
    public void deleteUser(@PathVariable("username") String username) {
        adminService.deleteUser(username);
    }

    @GetMapping("/users")
    public List<User> findAllUsers() {
        return adminService.findAllUsers();
    }

    @GetMapping("/users/on-confirm")
    public List<User> findAllUsersOnConfirm() {
        return adminService.findAllUsersOnConfirm();
    }

    @PatchMapping("/update-confirm/{username}")
    public void updateOnConfirmStatus(@PathVariable("username") String username) {
        adminService.updateOnConfirmStatus(username);
    }

    @GetMapping("/byId/{id}")
    public Optional<User> findUserById(@PathVariable("id") int id) {
        return adminService.findUserById(id);
    }


    @GetMapping("/byFirstname/{firstname}")
    public List<User> findUserByFirstname(@PathVariable("firstname") String firstname) {
        return adminService.findUserByFirstname(firstname);
    }

    @GetMapping("/byLastname/{lastname}")
    public List<User> findUserByLastname(@PathVariable("lastname") String lastname) {
        return adminService.findUserByLastname(lastname);
    }

    @GetMapping("/byMiddleName/{middleName}")
    public List<User> findUserByMiddleName(@PathVariable("middleName") String middleName) {
        return adminService.findUserByMiddleName(middleName);
    }

    @GetMapping("/byPhone/{phone}")
    public Optional<User> findUserByPhone(@PathVariable("phone") String phone) {
        return adminService.findUserByPhone(phone);
    }


    @GetMapping("/byEmail/{email}")
    public Optional<User> findUserByEmail(@PathVariable("email") String email) {
        return adminService.findUserByEmail(email);
    }

    @GetMapping("/byUsername/{username}")
    public Optional<User> findUserByUsername(@PathVariable("username") String username) {
        return adminService.findUserByUsername(username);
    }


    @GetMapping("/byLFM")
    public List<User> findUserByLFM(@RequestParam("lastname") String lastname,
                                    @RequestParam("firstname")String firstname,
                                    @RequestParam("middleName")String middleName) {
        return adminService.findUserByLFM(lastname, firstname, middleName);
    }


    @GetMapping("/byLF")
    public List<User> findUserByLF(@RequestParam("lastname") String lastname,
                                   @RequestParam("firstname") String firstname) {
        return adminService.findUserByLF(lastname, firstname);
    }


    @GetMapping("/byFM")
    public List<User> findUsersByFM(@RequestParam("firstname") String firstname,
                                    @RequestParam("middleName") String middleName) {
        return adminService.findUsersByFM(firstname, middleName);
    }
}
