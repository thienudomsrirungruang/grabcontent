package com.thien.grabcontent.service;

import com.thien.grabcontent.config.CartoonConfig;
import com.thien.grabcontent.config.CartoonProperties;
import com.thien.grabcontent.dto.PageInfoDTO;
import com.thien.grabcontent.entity.CartoonInfo;
import com.thien.grabcontent.entity.PageInfo;
import com.thien.grabcontent.repository.CartoonRepository;
import com.thien.grabcontent.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GrabService {

    @Autowired
    private CartoonProperties cartoonProperties;

    @Autowired
    private CartoonRepository cartoonRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private PageService pageService;

    @Autowired
    private WebService webService;


    public void getLatestCartoon(String cartoonName) throws Exception {
        String url = getUrlFromCartoon(cartoonName);

        int latestEpisodeStored = getLatestEpisodeNumberStored(cartoonName);

        int latestEpisodeOnWeb = webService.getLatestEpisodeNumber(url);

        for (int episodeNumber = latestEpisodeStored + 1; episodeNumber <= latestEpisodeOnWeb; episodeNumber++) {
            System.out.println("Episode number " + episodeNumber);

            CartoonInfo newCartoonInfo = toEntity(episodeNumber, cartoonName, url + "/" + episodeNumber);
            newCartoonInfo = cartoonRepository.save(newCartoonInfo);
            Long cartoonId = newCartoonInfo.getId();

            addPages(url, episodeNumber, cartoonId);
        }
    }

    private String getUrlFromCartoon(String cartoonName) {
        List<CartoonConfig> cartoonList = cartoonProperties.getCartoonList();

        for (CartoonConfig cartoon : cartoonList) {
            if (cartoon.getName().equals(cartoonName)) {
                return cartoon.getUrl();
            }
        }
        throw new IllegalArgumentException("Cartoon not found");
    }

    private int getLatestEpisodeNumberStored(String cartoonName) {
        Optional<CartoonInfo> cartoonInfoOptional = cartoonRepository.findFirstByCartoonNameOrderByChapterDesc(cartoonName);
        return cartoonInfoOptional.map(CartoonInfo::getChapter).orElse(0);
    }

    private CartoonInfo toEntity(int chapter, String cartoonName, String endpoint) {
        CartoonInfo newCartoonInfo = new CartoonInfo();
        newCartoonInfo.setChapter(chapter);
        newCartoonInfo.setCartoonName(cartoonName);
        newCartoonInfo.setEndpoint(endpoint);
        return newCartoonInfo;
    }

    private void addPages(String url, int episodeNumber, long cartoonId) throws Exception {
        List<PageInfoDTO> pages = webService.getEpisode(url, episodeNumber, cartoonId);
        List<PageInfo> pageInfoList = new ArrayList<>();
        for (PageInfoDTO page : pages) {
            pageInfoList.add(pageService.toEntity(page));
        }
        pageRepository.saveAll(pageInfoList);
    }
}
