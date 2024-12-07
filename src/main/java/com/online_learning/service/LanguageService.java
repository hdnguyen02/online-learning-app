package com.online_learning.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.online_learning.dao.LanguageDao;
import com.online_learning.dto.LanguageRequest;
import com.online_learning.entity.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageDao languageDao;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void addLanguage(LanguageRequest languageRequest) {


        Language language = Language.builder()
                .code(languageRequest.getCode())
                .nameInternational(languageRequest.getNameInternational())
                .nameVietnamese(languageRequest.getNameVietnamese())
                .build();

        languageDao.save(language);
    }


//    List<String> getVoicesSupportedByLanguage(String code) {
//        Language language = languageDao.findByCode(code).orElseThrow(() -> new RuntimeException("Language not found!"));
//        try {
//            return objectMapper.readValue(language.getVoicesSupported(), List.class);
//        }
//        catch (IOException e) {
//            throw new RuntimeException("Error parsing voices JSON", e);
//        }
//    }
}
