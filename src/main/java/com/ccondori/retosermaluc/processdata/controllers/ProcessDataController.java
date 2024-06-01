package com.ccondori.retosermaluc.processdata.controllers;

import com.ccondori.retosermaluc.commons.models.ResponseApi;
import com.ccondori.retosermaluc.processdata.services.ProcessDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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

    @PostMapping(PROCESAR_FORMULAS)
    public ResponseApi<String> procesarFormulas() {

        return ResponseApi.build(processDataService.procesarFormulas());
    }

    @PostMapping(DESCARGAR_EXCEL)
    public ResponseEntity<Resource> descargarExcel() {

        Resource reporte = processDataService.generarExcel();

        return ResponseEntity.ok()
                .headers(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "Reporte_Formulas_Procesadas.xlsx");
                    httpHeaders.add("ms-filename", "Reporte_Formulas_Procesadas.xlsx");
                    List<String> exposeHeaders = (new LinkedList<>());
                    exposeHeaders.add("ms-filename");
                    httpHeaders.setAccessControlExposeHeaders(exposeHeaders);
                })
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(reporte);
    }
}
