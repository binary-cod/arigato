package com.binarycod.arigato;


import com.binarycod.arigato.domain.Cart;
import com.binarycod.arigato.domain.CartItem;
import com.binarycod.arigato.domain.Product;
import com.binarycod.arigato.domain.Store;
import com.binarycod.arigato.services.InventoryService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingCartScenarioTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    InventoryService inventoryService;

    @Test
    public void loadProductsOnHome() throws Exception {
        Product product1 = new Product(3l, "Jeans", 100.0, 32);
        Product product2 = new Product(8l, "T-Shirt", 25.0, 46);

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        Mockito.when(inventoryService.getProductsListOfGivenStore(Store.TYPE.ONLINE)).thenReturn(productList);

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("products", Matchers.<List<Product>>allOf(Matchers.hasSize(2))))
                .andExpect(model().attribute("products", Matchers.hasItem(product1)));
    }


    @Test
    @DisplayName("call to cart details on empty cart should redirect us to /")
    public void showEmptyShoppingCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cart/details"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("after adding product into shopping cart, we should see exactly the item we added in cart")
    public void addProductToShoppingCart() throws Exception {

        Product product1 = new Product(3l, "Jeans", 100.0, 32);
        Product product2 = new Product(8l, "T-Shirt", 25.0, 46);

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        //add item to the cart and check if the item quantity and total price of cart is correct
        CartItem cartItem1 = new CartItem(product1, 1, product1.getPrice());
        Cart shoppingCart = new Cart();
        //shoppingCart.getCartItemList().add(cartItem1);

        HashMap<String, Object> sessionAttr = new HashMap<>();
        sessionAttr.put("cart", shoppingCart);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/cart/add/{id}",
                        product1.getId()).sessionAttrs(sessionAttr))
                .andDo(print())
                .andExpect(view().name("redirect:/"))
                .andExpect(status().is3xxRedirection())
                .andDo(print());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/cart/add/{id}",
                        product2.getId()).sessionAttrs(sessionAttr))
                .andExpect(view().name("redirect:/"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/cart/add/{id}",
                        product1.getId()).sessionAttrs(sessionAttr))
                .andExpect(view().name("redirect:/"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/cart/add/{id}",
                        product2.getId()).sessionAttrs(sessionAttr))
                .andExpect(view().name("redirect:/"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(MockMvcRequestBuilders.get("/cart/details").sessionAttrs(sessionAttr))
                .andDo(print())
                .andExpect(view().name("cart_details"))
                .andExpect(model().attribute("cartItems", Matchers.<List<CartItem>>allOf(Matchers.hasSize(2))))
                .andExpect(model().attribute("totalPrice", Matchers.equalTo(250.0)));

    }

    @Test
    @DisplayName("after removing product from shopping cart, we should see correct items and totalPrice")
    public void deleteItemFromShoppingCart() throws Exception {


        Product product1 = new Product(3l, "Jeans", 100.0, 32);
        Product product2 = new Product(8l, "T-shirt", 25.0, 46);

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        CartItem cartItem1 = new CartItem(product1, 2, product1.getPrice() * 2);

        CartItem cartItem2 = new CartItem(product2, 2, product2.getPrice() * 2);

        Cart shoppingCart = new Cart();

        shoppingCart.getCartItemList().add(cartItem1);
        shoppingCart.getCartItemList().add(cartItem2);

        HashMap<String, Object> sessionAttr = new HashMap<>();
        sessionAttr.put("cart", shoppingCart);


        mockMvc.perform(
                MockMvcRequestBuilders.get("/cart/items/delete/{id}",
                        cartItem1.getUuid().toString()).sessionAttrs(sessionAttr))
                .andExpect(view().name("redirect:/cart/details"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(MockMvcRequestBuilders.get("/cart/details").sessionAttrs(sessionAttr))
                .andDo(print())
                .andExpect(view().name("cart_details"))
                .andExpect(model().attribute("cartItems", Matchers.<List<CartItem>>allOf(Matchers.hasSize(2))))
                .andExpect(model().attribute("totalPrice", Matchers.equalTo(150.0)));

    }
}
