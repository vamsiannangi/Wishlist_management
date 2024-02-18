package com.Wishlist.Management.repository;

import com.Wishlist.Management.models.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist,Long> {


    @Query("SELECT w.Item_id FROM Wishlist w WHERE w.user_id = :userId")
    List<Long> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT w FROM Wishlist w WHERE w.user_id = :userId AND w.Item_id = :itemId")
    Wishlist findByUserIdItemId(@Param("userId") Long userId, @Param("itemId") Long itemId);

}
