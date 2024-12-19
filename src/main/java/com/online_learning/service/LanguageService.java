package com.online_learning.service;

import com.online_learning.dao.LanguageDao;
import com.online_learning.dto.LanguageRequest;
import com.online_learning.entity.Language;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageDao languageDao;

    public void addLanguage(LanguageRequest languageRequest) {

        Language language = Language.builder()
                .code(languageRequest.getCode())
                .nameInternational(languageRequest.getNameInternational())
                .nameVietnamese(languageRequest.getNameVietnamese())
                .build();

        languageDao.save(language);
    }

    public void initLanguages() {

        // khởi tạo cái này. Tiếng anh, pháp, tiếng việt
        List<Language> languages = languageDao.findAll();
        if (!languages.isEmpty())
            return;

        List<Language> initLanguages = new ArrayList<>();

        initLanguages.add(new Language("en-us", "English", "Tiếng Anh"));
        initLanguages.add(new Language("vi-vn", "Vietnamese", "Tiếng Việt"));
        initLanguages.add(new Language("fr-fr", "French", "Tiếng Pháp"));

        languageDao.saveAll(initLanguages);
    }
}
