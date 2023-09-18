package docman.service.authenticationService;

import docman.config.jwt.JwtService;
import docman.dto.authenticationDto.AuthenticationRequest;
import docman.dto.authenticationDto.AuthenticationResponse;
import docman.dto.userDto.RegisterRequest;
import docman.model.userEntities.User;
import docman.repository.userRepository.RoleRepository;
import docman.repository.userRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public void register(RegisterRequest registerRequest) {
        var user = User.builder()
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .middleName(registerRequest.getMiddleName())
                .email(registerRequest.getEmail())
                .phone(registerRequest.getPhone())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(Set.of(roleRepository.findRoleByRoleValue("ROLE_USER")))
                .onConfirm(true)
                .build();

        userRepository.save(user);

    }

    public AuthenticationResponse authenticate(AuthenticationRequest registerRequest) {
//        if (userRepository.getUserConfirmStatusByUsername(registerRequest.getUsername())) {
//            return null;
//        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerRequest.getUsername(),
                        registerRequest.getPassword()
                )
        );

        var user = userRepository.findByUsername(registerRequest.getUsername()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
