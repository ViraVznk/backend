package com.krasnovozBek.krasnovozBek.dao;
import com.krasnovozBek.krasnovozBek.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}