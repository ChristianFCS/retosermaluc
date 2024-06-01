package com.ccondori.retosermaluc.processdata.controllers;

import com.ccondori.retosermaluc.commons.models.ResponseApi;
import com.ccondori.retosermaluc.processdata.services.ProcessDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.ccondori.retosermaluc.processdata.config.Paths.*;
import static com.ccondori.retosermaluc.commons.config.PathsGlobal.*;

@RestController
@RequestMapping(ROOT_API+ PROCESS_DATA_CONTROLLER+VERSION)
public class ProcessDataController {

    @Autowired
    private ProcessDataService processDataService;

    @PostMapping(CARGA_EXCEL)
    public ResponseApi<String> cargarDatosExcel(@RequestBody MultipartFile file) throws IOException {

        return ResponseApi.build(processDataService.cargarDatosExcel(file));
    }
}
