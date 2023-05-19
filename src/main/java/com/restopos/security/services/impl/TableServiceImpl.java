package com.restopos.security.services.impl;

import com.restopos.models.Tables;
import com.restopos.repository.TableRepository;
import com.restopos.security.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class TableServiceImpl implements TableService {
    @Autowired
    private TableRepository tableRepository;

    @Override
    public Tables createTable(Tables tables) {
         return tableRepository.save(tables);
    }

    @Override
    public List<Tables> fetchTables() {
        return (List<Tables>) tableRepository.findAll();
    }

    @Override
    public Optional<Tables> findById(Integer id) {
        return tableRepository.findById(id);
    }

    @Override
    public String terraceDtls() {
       return  tableRepository.terraceDtl();
    }

    @Override
    public String lawnDtls() {
        return  tableRepository.lawnDtl();
    }

    @Override
    public String gardenDtls() {
        return  tableRepository.gaedenDtl();
    }

    @Override
    public String occupieDTLS() {
        return  tableRepository.occupied();
    }

    @Override
    public String EmptyDTLS() {
        return  tableRepository.Empty();
    }

    @Override
    public String alltables() {
        return tableRepository.all();
    }


    @Override
    public Tables updateWaiter(Tables tables, Integer id) {
        Tables tables1= tableRepository.findById(id).get();
        Tables tables2= tableRepository.save(tables1);
        return tables2;
    }

}
