package com.sogeti.menu.app.persistence.repositories;

import com.sogeti.menu.app.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UserEntity, Long> {
}
