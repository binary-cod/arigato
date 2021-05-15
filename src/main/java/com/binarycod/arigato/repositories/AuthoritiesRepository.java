package com.binarycod.arigato.repositories;

import com.binarycod.arigato.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthoritiesRepository extends JpaRepository<Authority, Long> {
    Authority findAuthorityByAuthority(String authority);
}
