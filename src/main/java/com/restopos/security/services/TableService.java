package com.restopos.security.services;

import com.restopos.models.Tables;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;


public interface TableService {

//    add table

  Tables createTable(Tables tables);

//    Show Table;
   List<Tables> fetchTables();

//   Get Table Data using Id
Optional<Tables> findById(Integer id);




//Api To get count terrace total

 public String terraceDtls();

    public String lawnDtls();

    public String gardenDtls();

    public String occupieDTLS();

    public String EmptyDTLS();

    public String alltables();


//    Update Waiter name in cards

    Tables updateWaiter(Tables tables, Integer id);


}
