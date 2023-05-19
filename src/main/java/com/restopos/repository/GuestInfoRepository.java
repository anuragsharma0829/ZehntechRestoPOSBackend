package com.restopos.repository;

import com.restopos.models.GuestInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GuestInfoRepository  extends JpaRepository<GuestInfo,Integer> {


}
