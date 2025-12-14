package com.formatec.gestionabsences.core.repository;

import com.formatec.gestionabsences.core.entity.Role;
import com.formatec.gestionabsences.core.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
    List<UserEntity> findByDepartementId(Long departementId);
    List<UserEntity> findByRole(Role role);
    Optional <UserEntity> findById(@NotNull Long aLong);
}
