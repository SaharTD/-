package com.example.final_project;


import com.example.final_project.Model.ItemSale;
import com.example.final_project.Repository.ItemSaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ItemSaleRepositoryTest {

    @Autowired
    ItemSaleRepository itemSaleRepository;

    ItemSale item1, item2 ;

    @BeforeEach
    void setUp() {
        item1 = new ItemSale();
        item1.setProductName("Sony headphone");
        item1.setQuantity(1);
        item1.setUnitPrice(999.0);
        item1.setTotalPrice(999.0);

        item2 = new ItemSale();
        item2.setProductName("Bose wireless headphones");
        item2.setQuantity(3);
        item2.setUnitPrice(375.0);
        item2.setTotalPrice(1125.0);


        itemSaleRepository.save(item1);
        itemSaleRepository.save(item2);

    }

    @Test
    public void testFindItemSaleById() {
        ItemSale found = itemSaleRepository.findItemSaleById(item2.getId());

        assertThat(found).isNotNull();
        assertThat(found.getProductName()).isEqualTo("Bose wireless headphones");
        assertThat(found.getQuantity()).isEqualTo(3);
        assertThat(found.getUnitPrice()).isEqualTo(375.0);
        assertThat(found.getTotalPrice()).isEqualTo(1125.0);
    }
}
