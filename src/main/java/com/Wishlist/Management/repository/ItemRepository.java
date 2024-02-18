package com.Wishlist.Management.repository;

import com.Wishlist.Management.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {

}
