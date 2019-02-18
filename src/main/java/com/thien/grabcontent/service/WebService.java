package com.thien.grabcontent.service;

import com.thien.grabcontent.dto.PageInfoDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebService {

    public int getLatestEpisodeNumber(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements episodes = document.select("a.lst");
        for(Element element : episodes){
            String link = element.attr("href");
            String[] splitLink = link.split("[/]+");
            String lastPathName = splitLink[splitLink.length - 1];
            try {
                int latestEpisodeNumber = Integer.parseInt(lastPathName);
                return latestEpisodeNumber;
            }catch(NumberFormatException e){}
        }
        return 0;
    }

    /**
     * gets image data from an episode
     * @param url url to cartoon
     */
    public List<PageInfoDTO> getEpisode(String url, int episodeNumber, Long cartoonId) throws IOException {
        String urlToEpisode = url + episodeNumber + "/?all";
        Document document = Jsoup.connect(urlToEpisode).get();
        Elements pictures = document.select("img");
        int pageNumber = 0;
        ArrayList<PageInfoDTO> pages = new ArrayList<>();
        for(Element pictureElement : pictures){
            PageInfoDTO pageInfoDTO = new PageInfoDTO();
            pageInfoDTO.setCartoonId(cartoonId);
            pageInfoDTO.setPageNumber(pageNumber);
            pageInfoDTO.setPageUrl(pictureElement.attr("src"));
            pages.add(pageInfoDTO);
            pageNumber++;
        }
        return pages;
    }
}
