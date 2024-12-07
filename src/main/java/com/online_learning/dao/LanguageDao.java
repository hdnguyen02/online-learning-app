package com.online_learning.dao;

import com.online_learning.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LanguageDao extends JpaRepository<Language, Long> {

    Optional<Language> findByCode(String code);

}
