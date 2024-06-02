package com.ccondori.retosermaluc.processdata.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProcessDataService {
    String cargarDatosExcel(MultipartFile file) throws IOException;
    String procesarFormulas();
    Resource generarExcel();
}
