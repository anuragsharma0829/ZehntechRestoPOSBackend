package com.restopos.repository;

import com.restopos.models.OrderTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderTable,Integer> {

    public Optional<OrderTable> findById(Integer order_id);


    @Query(value="select * from ordertable;", nativeQuery = true)
    public List<OrderTable> gethistory();

    @Query(value="select order_id,created_date, total_payment, wiater_name,table_id ,table_no from ordertable where table_no=?;", nativeQuery = true)
    public List<OrderTable> getbytableno(Integer table_no);
}
