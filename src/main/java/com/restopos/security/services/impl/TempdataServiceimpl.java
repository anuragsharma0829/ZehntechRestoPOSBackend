package com.restopos.security.services.impl;

import com.restopos.models.Temptable;
import com.restopos.repository.TempDatarepository;
import com.restopos.security.services.TempdataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TempdataServiceimpl implements TempdataService {
    @Autowired
    private TempDatarepository tempDatarepository;

    @Override
    public Temptable savetempData(Temptable temptable) {
        return tempDatarepository.save(temptable);
    }


}
