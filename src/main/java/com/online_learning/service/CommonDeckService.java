package com.online_learning.service;

import com.online_learning.dao.CommonDeckDao;
import com.online_learning.dao.GroupDao;
import com.online_learning.dto.deck.CommonDeckRequest;
import com.online_learning.dto.deck.CommonDeckResponse;
import com.online_learning.dto.common_deckv2.QueryCommonDeck;
import com.online_learning.entity.CommonDeck;
import com.online_learning.entity.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonDeckService {

    private final CommonDeckDao commonDeckDao;
    private final GroupDao groupDao;

    public boolean createCommonDeck(CommonDeckRequest commonDeckRequest) {
        Group group = groupDao.findById(commonDeckRequest.getIdGroup()).orElseThrow();

        CommonDeck commonDeck = CommonDeck.builder()
                .name(commonDeckRequest.getName())
                .description(commonDeckRequest.getDescription())
                .configLanguage(commonDeckRequest.getConfigLanguage())
                .group(group)
                .build();
        try {
            commonDeckDao.save(commonDeck);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<CommonDeckResponse> getCommonDecksByIdGroup(long idGroup) {
        return commonDeckDao.getCommonDeckByIdGroup(idGroup).stream()
                .map(CommonDeckResponse::new).toList();
    }

    public QueryCommonDeck getCommonDeck(Long idCommonDeck) {
        return new QueryCommonDeck(commonDeckDao.findById(idCommonDeck).orElseThrow());
    }

    public boolean editCommonDeck(Long idCommonDeck, CommonDeckRequest commonDeckRequest) {

        CommonDeck commonDeck = commonDeckDao.findById(idCommonDeck).orElseThrow();
        commonDeck.setName(commonDeckRequest.getName());
        commonDeck.setDescription(commonDeckRequest.getDescription());
        try {
            commonDeckDao.save(commonDeck);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteCommonDeck(Long id) {
        try {
            commonDeckDao.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
