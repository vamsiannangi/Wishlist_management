package com.Wishlist.Management.services;//package com.Wishlist.Management.services;

import com.Wishlist.Management.models.Item;
import com.Wishlist.Management.models.User;
import com.Wishlist.Management.models.Wishlist;
import com.Wishlist.Management.repository.ItemRepository;
import com.Wishlist.Management.repository.UserRepository;
import com.Wishlist.Management.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistService{

    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;


    public List<Item> getItemsByUserId(Long userId){
         List<Long> item_ids=wishlistRepository.findAllByUserId(userId); //get all item_id from wishlist
        List<Item> ansItem=new ArrayList<>();
        for(Long itemId:item_ids){
           Item item=itemRepository.findById(itemId).orElseThrow();
           ansItem.add(item);
        }
         return ansItem;
    }


    public String addItemForUserWishlist(Long itemId, User user) { //it should return appropriate messege if wishhlist and already exist
    //check item in items table
     Optional<Item> item=itemRepository.findById(itemId);
     if(!item.isPresent()){
         return "Item does not exist";
     }
     Long userId= user.getId();
        Wishlist wishlist=wishlistRepository.findByUserIdItemId(userId,itemId); //get all item_id from wishlist
        Wishlist newWIshlist=new Wishlist();
        if(wishlist==null){
            newWIshlist.setItem_id(itemId);
            newWIshlist.setUser_id(userId);
            Wishlist savedWishlist= wishlistRepository.save(newWIshlist);
            if(savedWishlist!=null){
              return "item added to wishlist";
            }
        }
        return "item already exist in wishlist";
    }

    public String deleteItem(Long itemId, User user) {
        Long userId=user.getId();
        Wishlist wishlist=wishlistRepository.findByUserIdItemId(userId,itemId);

        if(wishlist!=null){
            wishlistRepository.delete(wishlist);
            return "Item removed from wishlist successfully";
        }
        else {
          return "item does not exist in the wishlist";
        }
    }

}