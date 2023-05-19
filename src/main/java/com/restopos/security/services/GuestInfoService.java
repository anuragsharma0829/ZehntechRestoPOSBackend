package com.restopos.security.services;

import com.restopos.models.Food;
import com.restopos.models.GuestInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestInfoService {

//    Save GuestInfo
GuestInfo saveGuest(GuestInfo guestInfo);
}
