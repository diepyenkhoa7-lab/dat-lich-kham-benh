package com.datlichkhambenh.backend.repository;

import com.datlichkhambenh.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository cho bảng USERS.
 * Logic nghiệp vụ sẽ được bổ sung ở bước tiếp theo.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Tìm user theo email (dùng cho authentication)
    Optional<User> findByEmail(String email);

    // Kiểm tra email đã tồn tại chưa (dùng khi đăng ký)
    boolean existsByEmail(String email);
}
