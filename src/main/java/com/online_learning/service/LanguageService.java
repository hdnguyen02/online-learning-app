package com.online_learning.service;

import com.online_learning.dao.LanguageDao;
import com.online_learning.dto.LanguageRequest;
import com.online_learning.dto.LanguageResponse;
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

    public List<LanguageResponse> getLanguages() {

        List<Language> languages = languageDao.findAll();
        List<LanguageResponse> languageResponses = languages.stream().map(
                language -> {
                    LanguageResponse languageResponse = new LanguageResponse();
                    languageResponse.setId(language.getId());
                    languageResponse.setCode(language.getCode());
                    languageResponse.setNameInternational(language.getNameInternational());
                    languageResponse.setNameVietnamese(language.getNameVietnamese());
                    return languageResponse;

                }).toList();
        return languageResponses;
    }

    public void initLanguages() {
        List<Language> languages = languageDao.findAll();
        if (!languages.isEmpty())
            return;

        List<Language> initLanguages = new ArrayList<>();

        initLanguages.add(Language.builder()
                .code("en-us")
                .nameInternational("English")
                .nameVietnamese("Tiếng Anh")
                .build());

        initLanguages.add(Language.builder()
                .code("vi-vn")
                .nameInternational("Vietnamese")
                .nameVietnamese("Tiếng Việt")
                .build());
        initLanguages.add(Language.builder()
                .code("fr-fr")
                .nameInternational("French")
                .nameVietnamese("Tiếng Pháp")
                .build());

        languageDao.saveAll(initLanguages);
    }
}
