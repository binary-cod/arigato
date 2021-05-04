package com.binarycod.arigato.repository;

import com.binarycod.arigato.domain.Product;
import com.binarycod.arigato.domain.Store;
import com.binarycod.arigato.domain.StoreProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StoreManagementRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void putProductToStore(Integer storeId, Long productId, Integer quantity){
        String sql = "INSERT INTO store_product(store_id, product_id, quantity) VALUES (?,?,?)";
        jdbcTemplate.update(sql, storeId, productId, quantity);
    }


    public List<StoreProduct> getAllProductsOnStore(){
        String sql= "select st.id as store_id, st.name as store_name,\n" +
                "pr.id as product_id, pr.name as product_name, pr.price, pr.size,\n" +
                "st_pr.id as store_product_id, st_pr.quantity\n" +
                "from store_product st_pr\n" +
                "inner join store st on st_pr.store_id = st.id\n" +
                "inner join products pr on st_pr.product_id = pr.id\n" +
                "where st.id = 1;";

        return jdbcTemplate.query(sql, storeProductRowMapper);
    }

    private RowMapper<StoreProduct> storeProductRowMapper = (rs, rowNum) -> {
        Store store = new Store();
        store.setId(rs.getInt("store_id"));
        store.setName(rs.getString("store_name"));

        Product product = new Product();
        product.setId(rs.getLong("product_id"));
        product.setName(rs.getString("product_name"));
        product.setPrice(rs.getDouble("price"));
        product.setSize(rs.getInt("size"));

        StoreProduct storeProduct = new StoreProduct();
        storeProduct.setStore(store);
        storeProduct.setProduct(product);
        storeProduct.setId(rs.getInt("store_product_id"));
        storeProduct.setQuantity(rs.getInt("quantity"));

        return storeProduct;
    };

}
