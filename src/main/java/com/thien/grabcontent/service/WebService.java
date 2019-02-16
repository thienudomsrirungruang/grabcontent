package com.thien.grabcontent.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
}
