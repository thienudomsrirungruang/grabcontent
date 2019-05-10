package com.thien.grabcontent.service;

import com.thien.grabcontent.config.CartoonProperties;
import com.thien.grabcontent.dto.CartoonInfoDTO;
import com.thien.grabcontent.entity.CartoonInfo;
import com.thien.grabcontent.repository.CartoonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartoonServiceTest {

    @Mock
    private CartoonRepository cartoonRepositoryMock;

    @Mock
    private CartoonProperties cartoonPropertiesMock;

    @InjectMocks
    private CartoonService cartoonService;

    @Test
    public void createCartoon() throws Exception {
        cartoonService.createCartoon(new CartoonInfoDTO());
        verify(cartoonRepositoryMock, times(1)).save(any(CartoonInfo.class));
    }

    @Test
    public void getCartoonByIdNotExist() {
        when(cartoonRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        CartoonInfoDTO result = cartoonService.getCartoonById(0L);
        assertNull(result);
        verify(cartoonRepositoryMock, times(1)).findById(anyLong());
    }

    @Test
    public void getCartoonByIdExists() {
        CartoonInfo cartoonInfo = buildCartoonInfo();
        when(cartoonRepositoryMock.findById(anyLong())).thenReturn(Optional.of(cartoonInfo));
        CartoonInfoDTO result = cartoonService.getCartoonById(0L);
        assertCorrectCartoonInfoDTO(result);
        verify(cartoonRepositoryMock, times(1)).findById(anyLong());

        assertEquals(result.getViews(), 1);
    }

    @Test
    public void getAllCartoons(){
        when(cartoonPropertiesMock.getPageSize()).thenReturn(10);
        CartoonInfo cartoonInfo = buildCartoonInfo();
        ArrayList<CartoonInfo> cartoonInfoList = new ArrayList<>();
        cartoonInfoList.add(cartoonInfo);
        when(cartoonRepositoryMock.findAllByOrderByCreateDateDesc(any(PageRequest.class))).thenReturn(cartoonInfoList);
        List<CartoonInfoDTO> output = cartoonService.getAllCartoons(1);
        assertEquals(output.size(), 1);
        CartoonInfoDTO outputCartoonInfoDTO = output.get(0);
        assertCorrectCartoonInfoDTO(outputCartoonInfoDTO);
        verify(cartoonRepositoryMock, times(1)).findAllByOrderByCreateDateDesc(any(PageRequest.class));

        assertEquals(outputCartoonInfoDTO.getViews(), 0);
    }

    @Test
    public void updateCartoon(){
        when(cartoonRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());
        cartoonService.updateCartoon(buildCartoonInfoDTO());
        verify(cartoonRepositoryMock, times(1)).findById(anyLong());
    }

    @Test
    public void deleteCartoon(){
        cartoonService.deleteCartoon(1L);
        verify(cartoonRepositoryMock, times(1)).deleteById(anyLong());
    }

    @Test
    public void getCartoonNames(){
        cartoonService.getCartoonNames();
        verify(cartoonRepositoryMock, times(1)).findDistinctCartoonName();
    }

    @Test
    public void getCartoonsByName(){
        CartoonInfo cartoonInfo = buildCartoonInfo();
        ArrayList<CartoonInfo> cartoonInfoList = new ArrayList<>();
        cartoonInfoList.add(cartoonInfo);
        when(cartoonRepositoryMock.findByCartoonNameOrderByChapterDesc(anyString())).thenReturn(cartoonInfoList);
        List<CartoonInfoDTO> output = cartoonService.getCartoonsByName("Name test");
        assertEquals(output.size(), 1);
        CartoonInfoDTO outputCartoonInfoDTO = output.get(0);
        assertCorrectCartoonInfoDTO(outputCartoonInfoDTO);
        verify(cartoonRepositoryMock, times(1)).findByCartoonNameOrderByChapterDesc(anyString());

        assertEquals(outputCartoonInfoDTO.getViews(), 0);
    }

    private CartoonInfo buildCartoonInfo(){
        CartoonInfo cartoonInfo = new CartoonInfo();
        cartoonInfo.setId(1L);
        cartoonInfo.setViews(0);
        cartoonInfo.setCartoonName("Name test");
        cartoonInfo.setChapter(1);
        cartoonInfo.setEndpoint("Endpoint test");
        cartoonInfo.setPageInfoList(new ArrayList<>());
        return cartoonInfo;
    }

    private CartoonInfoDTO buildCartoonInfoDTO(){
        CartoonInfoDTO cartoonInfoDTO = new CartoonInfoDTO();
        cartoonInfoDTO.setId(1L);
        cartoonInfoDTO.setViews(0);
        cartoonInfoDTO.setCartoonName("Name test");
        cartoonInfoDTO.setChapter(1);
        return cartoonInfoDTO;
    }

    private void assertCorrectCartoonInfoDTO(CartoonInfoDTO result){
        assertNotNull(result);
        assertEquals(result.getId().longValue(), 1);
        assertEquals(result.getCartoonName(), "Name test");
        assertEquals(result.getChapter(), 1);
    }
}