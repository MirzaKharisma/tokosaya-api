package com.enigma.TokoSayaApi.model.entity;

import com.enigma.TokoSayaApi.constant.ERole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<User> users;
}
