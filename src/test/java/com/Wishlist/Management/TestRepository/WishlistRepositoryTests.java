package com.Wishlist.Management.TestRepository;

import com.Wishlist.Management.models.Wishlist;
import com.Wishlist.Management.repository.WishlistRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.apache.coyote.http11.Constants.a;
import static org.junit.Assert.*;



@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WishlistRepositoryTests {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setup() {
        Wishlist wishlistEntity = new Wishlist();
        wishlistEntity.setId(7L);
        wishlistEntity.setUser_id(2L);
        wishlistEntity.setItem_id(2L);

        entityManager.merge(wishlistEntity);

    }

    @Test
    public void testFindAllByUserId() {
        Long userId = 2L;
        entityManager.clear();

        List<Long> result = wishlistRepository.findAllByUserId(userId);
        Assertions.assertNotNull(result, "Result object should not be null");
        Assertions.assertEquals(1, result.size(), "The result list should have one element");
        Assertions.assertTrue(result.contains(2L), "Result does not contain expected item_id ");
    }

    @Test
    public void testFindByUserIdItemId(){
        Long itemId=2L;
        Long userId=2L;
        entityManager.clear();
        Wishlist wishlist=wishlistRepository.findByUserIdItemId(itemId,userId);
        Long item_id=wishlist.getItem_id();
        Assertions.assertNotNull(wishlist, "Result object should not be null");
        Assertions.assertEquals(0, Objects.compare(2L,item_id, Long::compare), "the itemId in the wishlist was incorrect");
    }

}
