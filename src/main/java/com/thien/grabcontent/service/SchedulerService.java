package com.thien.grabcontent.service;

import com.thien.grabcontent.controller.GrabController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class SchedulerService {
    @Autowired
    private GrabController grabController;


    @Scheduled(cron="0 0 0 1/1 * ?")
    public void scheduleUpdateDrStone() throws Exception {
        grabController.getCartoon("Dr-Stone");
    }

    @Scheduled(cron="0 0 0 1/1 * ?")
    public void scheduleUpdateThePromisedNeverland() throws Exception{
        grabController.getCartoon("The-Promised-Neverland");
    }
}
