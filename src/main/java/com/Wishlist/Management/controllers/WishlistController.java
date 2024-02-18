package com.Wishlist.Management.controllers;//package com.Wishlist.Management.controllers;
//
//import com.Wishlist.Management.models.User;
//import com.Wishlist.Management.models.Wishlist;
//import com.Wishlist.Management.models.Item;
//import com.Wishlist.Management.services.ItemService;
//import com.Wishlist.Management.services.UserService;
//import com.Wishlist.Management.services.WishlistService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
////@RestController
////@RequestMapping("/api")
////public class WishlistController {
////
////    private final WishlistService wishlistService;
//// private ItemService itemService;
////
//// @Autowired
////    public WishlistController(WishlistService wishlistService, ItemService itemService) {
////        this.wishlistService = wishlistService;
////        this.itemService = itemService;
////    }
////
////    @GetMapping("/wishlist/{userId}")
////    public ResponseEntity<List<Wishlist>> getAllWishlists(@PathVariable Long userId) {
////        List<Wishlist> wishlists=new ArrayList<>();
////
////        if (userId != null) {
////            wishlists = wishlistService.getAllWishlists(userId);
////        } else {
////            return ResponseEntity.ok(Collections.emptyList());
////        }
////        return ResponseEntity.ok(wishlists);
////    }
////
//    //using user_id and item_id create a wishlist
////    @PostMapping("/wishlist")
////    public ResponseEntity<Wishlist> createWishlistItem(
////            @RequestParam Long userId,@RequestParam Long itemId,
////            @RequestParam String wishlistName) {
////
////       Item item=itemService.getItem(itemId);
////
////        Wishlist wishlist = wishlistService.createWishlistItem(userId,item,wishlistName);
////        return ResponseEntity.ok(wishlist);
////    }
////
////    //delete wishlist item from user using wishlist id
////    @DeleteMapping("/wishlist/{id}")
////    public void deleteWishlistItem(@PathVariable Long id) {
////        wishlistService.deleteWishlistItem(id);
////    }
////
////    @PostMapping("/saveItem")
////    public Item saveItem(@RequestParam String name,@RequestParam Double price){
////     return itemService.saveItem(name,price);
////    }
////}
////
//
//
//
//@RestController
//@RequestMapping("/api/wishlists")
//public class WishlistController {
//
//    @Autowired
//    private WishlistService wishlistService;
//
//    @Autowired
//    private UserService userService;
//    @Autowired
// private ItemService itemService;
//
//    @GetMapping
//    public ResponseEntity<List<Item>> getAuthenticatedUserWishlists() {
//        // Retrieve authenticated user's username from the security context
//        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
//        String userName=authentication.getName();
//        User user=userService.findByName(userName);
//        // Retrieve wishlists for the authenticated user
//        List<Item> wishlist = wishlistService.getAllItems(user);
//
//        return ResponseEntity.ok(wishlists);
//    }
//    @PostMapping("/add")
//    public ResponseEntity<Wishlist> createWishlistItem(
//            @RequestParam Long itemId,
//            @RequestParam String wishlistName) {
//        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
//        String username=authentication.getName();
//        User user=userService.findByName(username);
//        Item item=itemService.getItem(itemId);
//        Wishlist wishlist = wishlistService.createWishlistItem(user,item,wishlistName);
//        return ResponseEntity.ok(wishlist);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteWishlistItem(@PathVariable Long id) {
//        // Retrieve authenticated user's username from the security context
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        User user=userService.findByName(username);
//        Long userId= user.getId();
//        // Delete wishlist item for the authenticated user
//       String messege= wishlistService.deleteWishlistItem(id,userId);
//
//        return ResponseEntity.ok(messege);
//    }
//    @PostMapping("/saveItem")
//    public Item saveItem(@RequestParam String name,@RequestParam Double price){
//     return itemService.saveItem(name,price);
//    }
//}
//

import com.Wishlist.Management.models.Item;
import com.Wishlist.Management.models.User;
import com.Wishlist.Management.services.ItemService;
import com.Wishlist.Management.services.UserService;
import com.Wishlist.Management.services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WishlistController{
    @Autowired
    private WishlistService wishlistService;
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @GetMapping("/wishlists")
    public ResponseEntity<List<Item>> getAuthenticatedUserWishlists() {
        // Initialize an empty list to store wishlist items
        List<Item> itemsOfUser=new ArrayList<>();
        // Get the authenticated user's information from the security context
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userService.findByName(userName);
        Long userId=user.getId();
        // Check if the user is found
        if (userId != null) {
            // Retrieve wishlist items for the user
            itemsOfUser=wishlistService.getItemsByUserId(userId);
        } else {
            // Return an empty list if the user is not found
            return ResponseEntity.ok(Collections.emptyList());
        }
        // Return the wishlist items in the ResponseEntity
        return ResponseEntity.ok(itemsOfUser);
    }

    @PostMapping("/wishlists")
    public ResponseEntity<String> addItemToWishlist(@RequestParam Long itemId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // The 'authentication' object contains details about the currently authenticated user.
        // It includes information such as username, roles, and authentication status.
        String username = authentication.getName();
        User user=userService.findByName(username);
        String wishlistStatus=wishlistService.addItemForUserWishlist(itemId,user);
        return ResponseEntity.ok(wishlistStatus);
    }

    @DeleteMapping("/wishlists/{id}")
    public String deleteItemFromUserWishlist(@PathVariable("id") Long itemId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user=userService.findByName(username);
       return wishlistService.deleteItem(itemId,user);
}

    @PostMapping("/saveItem")
    public Item saveItem(@RequestParam String name,@RequestParam Double price){
       return itemService.saveItem(name,price);
    }
}