package com.krasnovozBek.krasnovozBek.dao;

import com.krasnovozBek.krasnovozBek.domain.User;

import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbc;

    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<User> userMapper = (rs, rowNum) -> User.builder()
            .id(rs.getInt("id"))
            .username(rs.getString("username"))
            .password(rs.getString("password"))
            .role(rs.getString("role"))
            .build();

    public Optional<User> findByUsername(String username) {
        String sql = "SELECT id, username, password, role FROM users WHERE username = ?";
        return jdbc.query(sql, userMapper, username)
                .stream()
                .findFirst();
    }
}