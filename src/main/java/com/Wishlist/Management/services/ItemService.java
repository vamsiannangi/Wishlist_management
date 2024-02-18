package com.Wishlist.Management.services;

import com.Wishlist.Management.models.Item;
import com.Wishlist.Management.models.User;
import com.Wishlist.Management.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    public Item getItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("item not found with id: " + itemId));
      if(item!=null){
          return item;
      }
      else return null;
    }

    public Item saveItem(String name, Double price) {
        Item item=new Item();
        item.setName(name);
        item.setPrice(price);
       return itemRepository.save(item);
    }
}
