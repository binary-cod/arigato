package com.binarycod.arigato.repository;

import com.binarycod.arigato.domain.Address;
import com.binarycod.arigato.domain.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class StoreRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createStore(Store store){
        String sql = "INSERT INTO store (id, name, address_id) VALUES(?,?,?)";
        Integer addressId = createAddress(store.getAddress());

        jdbcTemplate.update(sql, store.getId(), store.getName(), addressId);
    }

    public Integer createAddress(Address address) {
        String sql = "INSERT INTO address (address1, address2, city, state, country, zipCode) "+
                "VALUES (?,?,?,?,?,?)";

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                final PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, address.getAddress1());
                ps.setString(2, address.getAddress2());
                ps.setString(3, address.getCity());
                ps.setString(4, address.getState());
                ps.setString(5, address.getCountry());
                ps.setString(6, address.getZipCode());
                return ps;
            }
        };

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public Store getStore(Integer id) {
        String sql = "SELECT st.id, st.name, " +
                "ad.id as ad_id, ad.address1, " +
                "ad.address2, ad.city, ad.state, " +
                "ad.country,ad.zipCode " +
                "FROM store st, address ad WHERE st.address_id=ad.id AND st.id=?;";
        Store store = null;

        try {
            store = jdbcTemplate.queryForObject(sql,storeRowMapper,id);
        } catch (DataAccessException dae) {
            store =null;
        }
        return store;
    }

    public List<Store> getAllStores(){
        String sql = "SELECT st.id, st.name, " +
                "ad.id as ad_id, ad.address1, " +
                "ad.address2, ad.city, ad.state, " +
                "ad.country,ad.zipCode " +
                "FROM store st, address ad WHERE st.address_id=ad.id;";

        return jdbcTemplate.query(sql, storeRowMapper);
    }

    private RowMapper<Store> storeRowMapper = (rs, rowNum) -> {
        Address address = new Address();
        address.setId(rs.getInt("ad_id"));
        address.setAddress1(rs.getString("address1"));
        address.setAddress2(rs.getString("address2"));
        address.setCity(rs.getString("city"));
        address.setState(rs.getString("state"));
        address.setCountry(rs.getString("country"));
        address.setZipCode(rs.getString("zipCode"));

        Store store = new Store();
        store.setId(rs.getInt("id"));
        store.setName(rs.getString("name"));
        store.setAddress(address);
        return store;
    };

}
