package com.stubserver.backend.service;

import com.stubserver.backend.database.entity.User;
import com.stubserver.backend.exception.BadRequestException;
import com.stubserver.backend.exception.NotFoundException;
import com.stubserver.backend.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.security.SecureRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final MailService mailService;

    public List<User> getUsersList() {
        return userRepository.findAll().stream()
                .filter(u -> u.getUsername() != null && !u.getUsername().equalsIgnoreCase("admin"))
                .sorted((a, b) -> a.getUsername().compareToIgnoreCase(b.getUsername()))
                .toList();
    }

    @Transactional
    public Map<String, String> signUp(String username, String email, String firstname,
                                       String lastname, String requestedBy, String userrole) {
        if (userRepository.existsByUsernameOrEmail(username, email)) {
            log.warn("SignUp attempt with already existing username or email: {}", username);
            throw new BadRequestException("Username or Email already exists");
        }

        String autoPass = randomPassword();
        User user = new User();
        user.setUsername(username);
        user.setPassword(tokenService.hashPassword(autoPass));
        user.setEmail(email);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setCreatedBy(requestedBy);
        user.setUserrole(userrole != null ? userrole : "Guest");
        userRepository.save(user);

        mailService.sendEmail(email, "StubServer Account Created",
                "templates/accountCreationEmailTemplate.html",
                Map.of("username", username, "firstname", nvl(firstname),
                        "lastname", nvl(lastname), "autoPass", autoPass));

        return Map.of("message", "User created and password sent to email.");
    }

    @Transactional
    public Map<String, String> modifyUser(String username, String firstname, String lastname,
                                           String email, String updatedBy, String userrole) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (eq(user.getFirstname(), firstname) && eq(user.getLastname(), lastname)
                && eq(user.getEmail(), email) && eq(user.getUserrole(), userrole)) {
            return Map.of("message", "No changes were made.");
        }

        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setUserrole(userrole);
        user.setUpdatedBy(updatedBy);
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);
        return Map.of("message", "User updated successfully");
    }

    @Transactional
    public Map<String, String> deleteUser(String username, String requestedBy) {
        if (!userRepository.existsById(username)) {
            throw new NotFoundException("User Not Found");
        }
        userRepository.deleteById(username);
        return Map.of("message", "User deleted successfully");
    }

    private String randomPassword() {
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom rng = new SecureRandom();
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) sb.append(chars.charAt(rng.nextInt(chars.length())));
        return sb.toString();
    }

    private boolean eq(String a, String b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.equals(b);
    }

    private String nvl(String s) { return s != null ? s : ""; }
}
