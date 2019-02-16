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

import java.io.IOException;
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
        System.out.println("test");
        List<CartoonConfig> cartoonList = cartoonProperties.getCartoonList();

        // check if cartoon is in cartoonList
        boolean found = false;
        String url = null;
        for(CartoonConfig cartoon : cartoonList){
            if(cartoon.getName().equals(cartoonName)){
                found = true;
                url = cartoon.getUrl();
            }
        }
        if(!found){
            throw new IllegalArgumentException("Cartoon not found");
        }

        // check CartoonRepository for latest stored episode
        Optional<CartoonInfo> cartoonInfoOptional = cartoonRepository.findFirstByCartoonNameOrderByChapterDesc(cartoonName);
        int latestEpisodeStored = cartoonInfoOptional.map(CartoonInfo::getChapter).orElse(0);

        // get latest episode number from web
        int latestEpisodeOnWeb = webService.getLatestEpisodeNumber(url);

        // loop over getting specific episodes from web and storing them
        for(int episodeNumber = latestEpisodeStored + 1; episodeNumber <= latestEpisodeOnWeb; episodeNumber++){
            // create cartoon in database
            CartoonInfo newCartoonInfo = new CartoonInfo();
            newCartoonInfo.setChapter(episodeNumber);
            newCartoonInfo.setCartoonName(cartoonName);
            newCartoonInfo.setEndpoint(url + "/" + episodeNumber);
            newCartoonInfo = cartoonRepository.save(newCartoonInfo);
            Long cartoonId = newCartoonInfo.getId();

            //add pages to database
            List<PageInfoDTO> pages = webService.getEpisode(url, episodeNumber, cartoonId);
            for(PageInfoDTO page : pages){
                PageInfo pageInfo = pageService.toEntity(page);
                //TODO
//                newCartoonInfo.setPageInfoList();
            }
        }
    }
}
