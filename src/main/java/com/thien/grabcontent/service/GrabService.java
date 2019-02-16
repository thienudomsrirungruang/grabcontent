package com.thien.grabcontent.service;

import com.thien.grabcontent.config.CartoonConfig;
import com.thien.grabcontent.config.CartoonProperties;
import com.thien.grabcontent.entity.CartoonInfo;
import com.thien.grabcontent.repository.CartoonRepository;
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
    private WebService webService;

    public void getLatestCartoon(String cartoonName) throws IOException {
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
        Optional<CartoonInfo> cartoonInfo = cartoonRepository.findFirstByCartoonNameOrderByChapterDesc(cartoonName);
        int latestEpisodeStored = cartoonInfo.map(CartoonInfo::getChapter).orElse(0);

        // get latest episode number from web
        int latestEpisodeOnWeb = webService.getLatestEpisodeNumber(url);
    }
}
