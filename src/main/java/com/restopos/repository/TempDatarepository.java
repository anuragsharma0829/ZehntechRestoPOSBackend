package com.restopos.repository;

import com.restopos.models.Temptable;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface TempDatarepository extends JpaRepository<Temptable,Integer> {

    @Query(value="select * from tempdata where t_teble_id=:t_teble_id ", nativeQuery = true)
    List<Temptable> findAllByT_teble_id(@Param("t_teble_id")Integer t_teble_id);

    Optional<Temptable>findById(Integer temp_id);

    @Query( value = "select  * from tempdata t order by temp_id desc limit 1",nativeQuery = true)
    public Temptable lastrow();



@Modifying
@Transactional
    @Query( value = "delete from tempdata where order_id=:order_id",nativeQuery = true)
    Integer deletedata(@Param("order_id")Integer order_id);

}


