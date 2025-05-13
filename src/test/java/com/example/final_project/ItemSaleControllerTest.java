package com.example.final_project;

import com.example.final_project.Controller.ItemSaleController;
import com.example.final_project.Model.ItemSale;
import com.example.final_project.Service.ItemSaleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ItemSaleController.class)
@Import(ItemSaleControllerTest.MockedConfig.class)
@AutoConfigureMockMvc
public class ItemSaleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemSaleService itemSaleService;

    @Test
    @WithMockUser(username = "accountant", roles = {"ACCOUNTANT"})
    public void testGetItemSalesBySalesId() throws Exception {
        ItemSale item = new ItemSale();
        item.setProductName("Test Product");

        when(itemSaleService.getItemSalesBySalesId(Mockito.anyInt(), Mockito.eq(10)))
                .thenReturn(List.of(item));

        mockMvc.perform(get("/api/v1/item-sales/sales/10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value("Test Product"));
    }

    @TestConfiguration
    static class MockedConfig {
        @Bean
        public ItemSaleService itemSaleService() {
            return mock(ItemSaleService.class);
        }
    }
}
