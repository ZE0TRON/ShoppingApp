package com.babob.sporcantam;

import com.babob.sporcantam.Seller.SellerRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SellerTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SellerRepository sellerRepository;


    // write test cases here

}