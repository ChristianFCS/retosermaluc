package com.ccondori.retosermaluc.processdata.services.impl;

import com.ccondori.retosermaluc.processdata.mappers.ProcessDataMapper;
import com.ccondori.retosermaluc.processdata.services.ProcessDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessDataServiceImpl implements ProcessDataService {

    @Autowired
    private ProcessDataMapper processDataMapper;

}
