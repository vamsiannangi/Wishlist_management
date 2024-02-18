package com.Wishlist.Management.TestServices;

import com.Wishlist.Management.models.Item;
import com.Wishlist.Management.models.User;
import com.Wishlist.Management.models.Wishlist;
import com.Wishlist.Management.repository.ItemRepository;
import com.Wishlist.Management.repository.WishlistRepository;
import com.Wishlist.Management.services.UserService;
import com.Wishlist.Management.services.WishlistService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class WishlistServiceTests {


    @Mock
    private WishlistRepository wishlistRepository;
    @Mock
    private ItemRepository itemRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private Item item  ;
    @InjectMocks
    private WishlistService wishlistService;

    @Test
    public void testGetItemsByUserId() {
        User user = new User();
        user.setId(1L);

        Item item = new Item();
        item.setId(1L);
        item.setPrice(2000d);
        item.setName("headset");

        Wishlist wishlist = new Wishlist();
        wishlist.setId(1L);
        wishlist.setItem_id(1L);
        wishlist.setUser_id(1L);

        // Mocking the findAllByUserId method to return a list containing the item_id.
        when(wishlistRepository.findAllByUserId(user.getId())).thenReturn(Collections.singletonList((wishlist.getItem_id())));

        // Mocking the findById method to return the item when called with the specified itemId.
        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));

        // Call the service method
        List<Item> resultItems = wishlistService.getItemsByUserId(user.getId());

        // Assertions
        assertThat(resultItems).isNotNull();
        assertThat(resultItems).hasSize(1);

        Item resultItem = resultItems.get(0);

        assertThat(resultItem).isNotNull();
        assertThat(resultItem.getId()).isEqualTo(item.getId());
        assertThat(resultItem.getName()).isEqualTo(item.getName());
        assertThat(resultItem.getPrice()).isEqualTo(item.getPrice());

    }

    @Test
    public void testAddItemForUserWishlist() {
        // Mocking data
        Long itemId = 1L;
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        Item item = new Item();
        item.setId(itemId);

        Wishlist existingWishlist = new Wishlist();
        existingWishlist.setId(1L);
        existingWishlist.setUser_id(userId);
        existingWishlist.setItem_id(itemId);

        // Mocking repository behaviors
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        when(wishlistRepository.findByUserIdItemId(userId, itemId)).thenReturn(existingWishlist);

        // Call the service method
        String resultMessage = wishlistService.addItemForUserWishlist(itemId, user);

        // Assertions
        assertThat(resultMessage).isEqualTo("item already exist in wishlist");

        // Verify that relevant repository methods were called
        verify(itemRepository, times(1)).findById(itemId);
        verify(wishlistRepository, times(1)).findByUserIdItemId(userId, itemId);
        verify(wishlistRepository, never()).save(any(Wishlist.class));
    }
    @Test
    public void testDeleteItem() {
        // Mocking data
        Long itemId = 1L;
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        Wishlist existingWishlist = new Wishlist();
        existingWishlist.setId(1L);
        existingWishlist.setUser_id(userId);
        existingWishlist.setItem_id(itemId);

        // Mocking repository behaviors
        when(wishlistRepository.findByUserIdItemId(userId, itemId)).thenReturn(existingWishlist);

        // Call the service method
        String resultMessage = wishlistService.deleteItem(itemId, user);

        // Assertions
        assertThat(resultMessage).isEqualTo("Item removed from wishlist successfully");

        // Verify that relevant repository methods were called
        verify(wishlistRepository, times(1)).findByUserIdItemId(userId, itemId);
        verify(wishlistRepository, times(1)).delete(existingWishlist);
    }

}
