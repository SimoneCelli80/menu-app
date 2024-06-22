package com.sogeti.menu.app.persistence.repositories;

import com.sogeti.menu.app.persistence.entities.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenusRepository extends JpaRepository<MenuEntity, Long> {
}
