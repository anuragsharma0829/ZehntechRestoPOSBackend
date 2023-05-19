package com.restopos.security.services.impl;

import com.restopos.models.GuestInfo;
import com.restopos.repository.GuestInfoRepository;
import com.restopos.security.services.GuestInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuestInfoServiceimpl implements GuestInfoService {

    @Autowired
    private GuestInfoRepository guestInfoRepository;
//Save Info
    @Override
    public GuestInfo saveGuest(GuestInfo guestInfo) {
        return guestInfoRepository.save(guestInfo);
    }
}
