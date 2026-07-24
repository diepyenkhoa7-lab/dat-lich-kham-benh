package com.datlichkhambenh.backend.security;

import com.datlichkhambenh.backend.entity.User;
import com.datlichkhambenh.backend.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * CustomUserDetailsService -- Load UserDetails tu DB theo email.
 * Duoc Spring Security dung khi xac thuc.
 *
 * Email -> UserDetails (authorities = [ROLE_PATIENT / ROLE_DOCTOR / ROLE_ADMIN])
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Khong tim thay tai khoan voi email: " + email));

        // Boolean wrapper: Lombok @Getter tao getIsActive() (khong phai isIsActive())
        boolean isActive = Boolean.TRUE.equals(user.getIsActive());

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPasswordHash())
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())))
                .accountLocked(!isActive)
                .build();
    }
}
