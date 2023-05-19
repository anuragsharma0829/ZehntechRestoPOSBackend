package com.restopos.repository;

import com.restopos.models.Tables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TableRepository extends JpaRepository<Tables,Integer> {

//    @Query("select count(u.cId) from Contect u where u.user.id=:userId")
        @Query("select  count(t.Area)  from Tables t where t.Area= 'Terrace' ")
        public String terraceDtl();

        @Query("select  count(t.Area)  from Tables t where t.Area= 'Lawn' ")
        public String lawnDtl();

        @Query("select  count(t.Area)  from Tables t where t.Area= 'Garden' ")
        public String gaedenDtl();

        @Query("select  count(t.Area)  from Tables t where t.Table_status= true")
        public String occupied();

        @Query("select  count(t.Area)  from Tables t where t.Table_status= false ")
        public String Empty();

        @Query("select  count(t.Area)  from Tables t ")
        public String all();



        public Optional<Tables> findById (Integer id);


}

