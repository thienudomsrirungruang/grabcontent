package com.thien.grabcontent.service;

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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PageServiceTest {

    @Mock
    private PageRepository pageRepositoryMock;

    @Mock
    private CartoonRepository cartoonRepositoryMock;

    @InjectMocks
    private PageService pageService;

    @Test
    public void createPage() throws Exception {
        when(cartoonRepositoryMock.findById(anyLong())).thenReturn(Optional.of(buildCartoonInfo()));
        pageService.createPage(buildPageInfoDTO());
        verify(pageRepositoryMock, times(1)).save(any(PageInfo.class));
    }

    @Test
    public void getPageByIdExists() {
        when(pageRepositoryMock.findById(anyLong())).thenReturn(Optional.of(buildPageInfo()));
        PageInfoDTO result = pageService.getPageById(1L);
        verify(pageRepositoryMock, times(1)).findById(anyLong());
        assertNotNull(result);
        assertEquals((long) result.getId(), 1L);
    }

    @Test
    public void getPageByIdNotExist() {
        when(pageRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        PageInfoDTO result = pageService.getPageById(1L);
        verify(pageRepositoryMock, times(1)).findById(anyLong());
        assertNull(result);
    }

    @Test
    public void getAllPages() {
        when(pageRepositoryMock.findAll()).thenReturn(Arrays.asList(buildPageInfo(), buildPageInfo()));
        List<PageInfoDTO> result = pageService.getAllPages();
        verify(pageRepositoryMock, times(1)).findAll();
        assertEquals(result.size(), 2);
    }

    @Test
    public void updatePage() {
        when(pageRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        when(cartoonRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        pageService.updatePage(buildPageInfoDTO());
        verify(pageRepositoryMock, times(1)).findById(anyLong());
        verify(cartoonRepositoryMock, times(1)).findById(anyLong());
    }

    @Test
    public void deletePage() {
        pageService.deletePage(1L);
        verify(pageRepositoryMock, times(1)).deleteById(anyLong());
    }


    private PageInfoDTO buildPageInfoDTO() {
        PageInfoDTO pageInfoDTO = new PageInfoDTO();
        pageInfoDTO.setPageNumber(1);
        pageInfoDTO.setCartoonId(1L);
        pageInfoDTO.setPageUrl("url test");
        pageInfoDTO.setId(1L);
        return pageInfoDTO;
    }

    private PageInfo buildPageInfo() {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageUrl("url test");
        pageInfo.setPageNumber(1);
        pageInfo.setId(1L);
        pageInfo.setCartoonInfo(buildCartoonInfo());
        return pageInfo;
    }

    private CartoonInfo buildCartoonInfo() {
        CartoonInfo cartoonInfo = new CartoonInfo();
        cartoonInfo.setId(1L);
        return cartoonInfo;
    }

}
