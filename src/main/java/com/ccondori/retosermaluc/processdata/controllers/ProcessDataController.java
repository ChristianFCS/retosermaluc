package com.ccondori.retosermaluc.processdata.controllers;

import com.ccondori.retosermaluc.commons.config.PathsGlobal;
import com.ccondori.retosermaluc.processdata.config.Paths;
import com.ccondori.retosermaluc.processdata.services.ProcessDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(PathsGlobal.ROOT_API+ Paths.PROCESS_DATA_CONTROLLER+PathsGlobal.VERSION)
public class ProcessDataController {

    @Autowired
    private ProcessDataService processDataService;


}
