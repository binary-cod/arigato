package com.binarycod.arigato.repository;

import com.binarycod.arigato.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int count(){
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM products;", Integer.class);
    }

    public void insertProduct(Product product){
        String query = "INSERT INTO products(id,p_name,price,size) VALUES (?,?,?,?);";
        jdbcTemplate.update(query, product.getId(), product.getName(), product.getPrice(), product.getSize());
    }

    public List<Product> getListOfProducts(){
        List<Product> productList = jdbcTemplate.query("SELECT * FROM products;", productRowMapper);
        return productList;
    }

    private RowMapper<Product> productRowMapper = (rs, rowNum) -> {
      Product p = new Product();
      p.setId(rs.getLong("id"));
      p.setName(rs.getString("p_name"));
      p.setPrice(rs.getDouble("price"));
      p.setSize(rs.getInt("size"));
      return p;
    };
}
