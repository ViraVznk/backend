package com.krasnovozBek.krasnovozBek.domain;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    private Integer id;
    private String username;
    private String password;
    private String role;
}