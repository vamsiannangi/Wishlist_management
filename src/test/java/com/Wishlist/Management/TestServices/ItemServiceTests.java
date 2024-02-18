package com.Wishlist.Management.TestServices;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.Wishlist.Management.models.Item;
import com.Wishlist.Management.models.User;
import com.Wishlist.Management.repository.ItemRepository;
import com.Wishlist.Management.services.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class ItemServiceTests {

    @MockBean
    private ItemRepository itemRepository;
    @Autowired
    private ItemService itemService;

@Test
    public void testGetItem(){
        Item item=new Item();
      item.setId(1l);
      item.setPrice(2345d);
      item.setName("headset");
      long itemId=item.getId();
      when(itemRepository.findById(1l)).thenReturn(Optional.of(item));
     Item result= itemService.getItem(1l);
    verify(itemRepository,times(1)).findById(1l);

    assertNotNull(result,"item is null");
      assertEquals(2345d,result.getPrice(),"expected result was not returned");

}

    @Test
    public void TestSaveItem(){
        Item item=new Item();
        item.setId(1l);
        item.setPrice(2345d);
        item.setName("headset");
        when(itemRepository.save(any(Item.class))).thenReturn(item);
        Item result=itemService.saveItem("headset",2345d);
        verify(itemRepository,times(1)).save(any(Item.class));

        assertNotNull(result,"result is null");
        assertEquals(2345d,result.getPrice(),"Item was not saved properly");

    }

}
