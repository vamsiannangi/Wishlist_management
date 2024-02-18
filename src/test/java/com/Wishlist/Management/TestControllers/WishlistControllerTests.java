package com.Wishlist.Management.TestControllers;

import com.Wishlist.Management.controllers.WishlistController;
import com.Wishlist.Management.models.Item;
import com.Wishlist.Management.models.User;
import com.Wishlist.Management.models.Wishlist;
import com.Wishlist.Management.services.ItemService;
import com.Wishlist.Management.services.UserService;
import com.Wishlist.Management.services.WishlistService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class WishlistControllerTests {

  @MockBean
   private WishlistService wishlistService;
  @MockBean
   private ItemService itemService;
  @MockBean
  private UserService userService;
  @Autowired
    private WishlistController wishlistController;


    @Test
    public void getUserWishlistTest(){
    User user=new User();
        user.setId(1l);
        user.setEnabled(true);
        user.setRole("USER");
        user.setPassword("password1");
        user.setUsername("vamsi");
        Item item=new Item();
        item.setPrice(2000d);
        item.setName("headset");

        List<Item> itemsOfUser=new ArrayList<>();
        itemsOfUser.add(item);
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurity = mock(SecurityContext.class,RETURNS_DEEP_STUBS);
        SecurityContextHolder.setContext(mockSecurity);
        when(mockSecurity.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("vamsi");
        when(userService.findByName("vamsi")).thenReturn(user);
        when(wishlistService.getItemsByUserId(user.getId())).thenReturn(itemsOfUser);

        ResponseEntity<List<Item>> resultWishlist=wishlistController.getAuthenticatedUserWishlists();

        verify(wishlistService, times(1)).getItemsByUserId(user.getId());
        verify(userService,times(1)).findByName("vamsi");
        verify(mockSecurity,times(1)).getAuthentication();
        verify(mockAuthentication,times(1)).getName();
        assertNotNull("Result object should not be null",resultWishlist);
        assertEquals(HttpStatus.OK, resultWishlist.getStatusCode());
        assertEquals(itemsOfUser, resultWishlist.getBody());
        // Reset SecurityContextHolder after the test
        SecurityContextHolder.clearContext();
    }


    @Test
    public void creatWishlistItemTest(){
                User user=new User();
        user.setId(1l);
        user.setEnabled(true);
        user.setRole("USER");
        user.setPassword("password1");
        user.setUsername("vamsi");
        Item item=new Item();
        item.setId(1l);
        item.setPrice(2000d);
        item.setName("headset");
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurity = mock(SecurityContext.class,RETURNS_DEEP_STUBS);

        //SecurityContextHolder mockSecurityContextHolder = mock(SecurityContextHolder.class);
        SecurityContextHolder.setContext(mockSecurity);
        when(mockSecurity.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("vamsi");
        when(userService.findByName("vamsi")).thenReturn(user);
        String wishlistStatus = "Item added successfully";
        when(wishlistService.addItemForUserWishlist(item.getId(),user)).thenReturn(wishlistStatus);

        ResponseEntity<String> resultWishlist=wishlistController.addItemToWishlist(item.getId());

        Assertions.assertNotNull(resultWishlist, "Result object should not be null");
        Assertions.assertEquals(HttpStatus.OK, resultWishlist.getStatusCode(),"expected status code was not returned");
        Assertions.assertEquals(wishlistStatus, resultWishlist.getBody(),"item not added successfully");

        // Reset SecurityContextHolder after the test
        SecurityContextHolder.clearContext();
    }

    @Test
    public void deleteWishlistTest(){
        User user=new User();
        user.setId(1l);
        user.setEnabled(true);
        user.setRole("USER");
        user.setPassword("password1");
        user.setUsername("vamsi");
        Item item=new Item();
        item.setId(1l);
        item.setPrice(2000d);
        item.setName("headset");
        Wishlist wishlist=new Wishlist();
        wishlist.setId(1l);
        wishlist.setUser_id(user.getId());
        wishlist.setItem_id(item.getId());
        Authentication mockAuthentication = mock(Authentication.class);
        SecurityContext mockSecurity = mock(SecurityContext.class,RETURNS_DEEP_STUBS);
        SecurityContextHolder.setContext(mockSecurity);
        when(mockSecurity.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.getName()).thenReturn("vamsi");
        when(userService.findByName("vamsi")).thenReturn(user);
        when(wishlistService.deleteItem(item.getId(),user)).thenReturn("Deleted Successfully");

        String resultWishlist=wishlistController.deleteItemFromUserWishlist(item.getId());

        verify(mockSecurity,times(1)).getAuthentication();
        verify(mockAuthentication,times(1)).getName();
        verify(userService,times(1)).findByName("vamsi");
        verify(wishlistService,times(1)).deleteItem(item.getId(),user);
        Assertions.assertNotNull(resultWishlist, "Result object should not be null");
        Assertions.assertEquals("Deleted Successfully", resultWishlist,"not deleted");
    }

    @Test
    public void saveTest()throws Exception{
        Item item = new Item();
        item.setName("laptop");
        item.setPrice(200d);
        when(itemService.saveItem("myName",1.2)).thenReturn(item);
        Item resultItem = wishlistController.saveItem("myName",1.2);
        verify(itemService,times(1)).saveItem("myName",1.2);
        assertNotNull("Result object should not be null",resultItem);
        assertEquals("Expected name was not return","laptop", resultItem.getName());
        assertEquals("Expected price was not return",200d, resultItem.getPrice(),0.0);

    }

}
