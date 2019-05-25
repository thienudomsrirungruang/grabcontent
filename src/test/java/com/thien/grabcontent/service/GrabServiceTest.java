package com.thien.grabcontent.service;

import com.thien.grabcontent.config.CartoonConfig;
import com.thien.grabcontent.config.CartoonProperties;
import com.thien.grabcontent.dto.PageInfoDTO;
import com.thien.grabcontent.entity.CartoonInfo;
import com.thien.grabcontent.entity.PageInfo;
import com.thien.grabcontent.repository.CartoonRepository;
import com.thien.grabcontent.repository.PageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GrabServiceTest {

    @Mock
    private CartoonProperties cartoonPropertiesMock;

    @Mock
    private CartoonRepository cartoonRepositoryMock;

    @Mock
    private PageRepository pageRepositoryMock;

    @Mock
    private WebService webServiceMock;

    @Mock
    private PageService pageServiceMock;

    @InjectMocks
    private GrabService grabService;

    @Test(expected = IllegalArgumentException.class)
    public void getLatestCartoonCartoonNotFound() throws Exception {
        when(cartoonPropertiesMock.getCartoonList()).thenReturn(buildCartoonList());
        grabService.getLatestCartoon("other name");
    }

    @Test
    public void getLatestCartoonNotStored() throws Exception {
        when(cartoonPropertiesMock.getCartoonList()).thenReturn(buildCartoonList());
        when(cartoonRepositoryMock.findFirstByCartoonNameOrderByChapterDesc(anyString())).thenReturn(Optional.empty());
        when(webServiceMock.getLatestEpisodeNumber(anyString())).thenReturn(3);
        when(webServiceMock.getEpisode(anyString(), anyInt(), anyLong())).thenReturn(new ArrayList<PageInfoDTO>());
        when(cartoonRepositoryMock.save(any(CartoonInfo.class))).thenReturn(buildCartoonInfo(1, 1, "name", 1));
        grabService.getLatestCartoon("name2");
        verify(cartoonRepositoryMock, times(3)).save(any(CartoonInfo.class));
    }

    @Test
    public void getLatestCartoonNotUpToDate() throws Exception {
        when(cartoonPropertiesMock.getCartoonList()).thenReturn(buildCartoonList());
        when(cartoonRepositoryMock.findFirstByCartoonNameOrderByChapterDesc(anyString())).thenReturn(Optional.of(buildCartoonInfo(1, 5, "name", 1)));
        when(webServiceMock.getLatestEpisodeNumber(anyString())).thenReturn(10);
        when(webServiceMock.getEpisode(anyString(), anyInt(), anyLong())).thenReturn(new ArrayList<PageInfoDTO>());
        when(cartoonRepositoryMock.save(any(CartoonInfo.class))).thenReturn(buildCartoonInfo(1, 1, "name", 1));
        grabService.getLatestCartoon("name2");
        verify(cartoonRepositoryMock, times(5)).save(any(CartoonInfo.class));
    }

    @Test
    public void getLatestCartoonUpToDate() throws Exception {
        when(cartoonPropertiesMock.getCartoonList()).thenReturn(buildCartoonList());
        when(cartoonRepositoryMock.findFirstByCartoonNameOrderByChapterDesc(anyString())).thenReturn(Optional.of(buildCartoonInfo(1, 5, "name", 1)));
        when(webServiceMock.getLatestEpisodeNumber(anyString())).thenReturn(5);
        grabService.getLatestCartoon("name2");
        verify(cartoonRepositoryMock, times(0)).save(any(CartoonInfo.class));
    }

    @Test
    public void getLatestCartoonPageSaving() throws Exception {
        when(cartoonPropertiesMock.getCartoonList()).thenReturn(buildCartoonList());
        when(cartoonRepositoryMock.findFirstByCartoonNameOrderByChapterDesc(anyString())).thenReturn(Optional.of(buildCartoonInfo(1, 5, "name", 1)));
        when(webServiceMock.getLatestEpisodeNumber(anyString())).thenReturn(10);
        when(webServiceMock.getEpisode(anyString(), anyInt(), anyLong())).thenReturn(new ArrayList<PageInfoDTO>(Arrays.asList(buildPageInfoDTO(1, 1, "url"), buildPageInfoDTO(2, 2, "url"))));
        when(cartoonRepositoryMock.save(any(CartoonInfo.class))).thenReturn(buildCartoonInfo(1, 1, "name", 1));
        when(pageServiceMock.toEntity(any(PageInfoDTO.class))).thenReturn(buildPageInfo(1, 1, "url"));
        grabService.getLatestCartoon("name2");
        verify(pageServiceMock, times(10)).toEntity(any(PageInfoDTO.class));
        verify(pageRepositoryMock, times(5)).saveAll(anyList());
    }

    private List<CartoonConfig> buildCartoonList() {
        List<CartoonConfig> cartoonList = new ArrayList<>();
        CartoonConfig cc1 = new CartoonConfig();
        cc1.setName("name1");
        cc1.setUrl("test url1");
        cartoonList.add(cc1);
        CartoonConfig cc2 = new CartoonConfig();
        cc2.setName("name2");
        cc2.setUrl("test url2");
        cartoonList.add(cc2);
        return cartoonList;
    }

    private CartoonInfo buildCartoonInfo(long id, int chapter, String name, int views) {
        CartoonInfo cartoonInfo = new CartoonInfo();
        cartoonInfo.setId(id);
        cartoonInfo.setChapter(chapter);
        cartoonInfo.setCartoonName(name);
        cartoonInfo.setViews(views);
        return cartoonInfo;
    }

    private PageInfoDTO buildPageInfoDTO(long id, int pageNumber, String pageUrl) {
        PageInfoDTO pageInfoDTO = new PageInfoDTO();
        pageInfoDTO.setId(id);
        pageInfoDTO.setPageNumber(pageNumber);
        pageInfoDTO.setPageUrl(pageUrl);
        return pageInfoDTO;
    }

    private PageInfo buildPageInfo(long id, int pageNumber, String pageUrl) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setId(id);
        pageInfo.setPageNumber(pageNumber);
        pageInfo.setPageUrl(pageUrl);
        return pageInfo;
    }
}
