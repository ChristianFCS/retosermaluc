package com.ccondori.retosermaluc.processdata.reports;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ProcessDataReport {
    private List<HashMap<String, Object>> cuerpo;
    private String[] cabecera;

    public ProcessDataReport(List<HashMap<String, Object>> cuerpo, String[] cabecera) {
        this.cuerpo = cuerpo;
        this.cabecera = cabecera;
    }

    public Resource generarReporte(){
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("REPORTE CALCULO FORMULAS");

        //CABECERA
        Row cabeceraRow = sheet.createRow(0);
        IntStream.range(0,this.cabecera.length).forEach(i -> {
                    cabeceraRow.createCell(i).setCellValue(this.cabecera[i]);
                }
        );
        //CUERPO
        IntStream.range(0, this.cuerpo.size()).forEach(i -> {
            Row row = sheet.createRow(i + 1);
            Map<String, Object> registro = this.cuerpo.get(i);
            row.createCell(0).setCellValue(registro.get("aco_id_asociacion_comuna").toString());
            row.createCell(1).setCellValue(registro.get("emp_nom_empresa").toString());
            row.createCell(2).setCellValue(registro.get("com_nom_comuna").toString());
            row.createCell(3).setCellValue(registro.get("sub_nombre_sec").toString());
            row.createCell(4).setCellValue(registro.get("opc_tarifaria_id").toString());
            row.createCell(5).setCellValue(registro.get("opc_tarifaria_nombre").toString());
            row.createCell(6).setCellValue(registro.get("aca_for_formula_descompuesta").toString());
            row.createCell(7).setCellValue(registro.get("car_desc_cargo").toString());
            row.createCell(8).setCellValue(registro.get("resultado").toString());
            row.createCell(9).setCellValue(registro.get("resultado_msg").toString());
            row.createCell(10).setCellValue(registro.get("process_date").toString());
        });
        IntStream.range(0, this.cabecera.length).forEach(sheet::autoSizeColumn);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            wb.write(byteArrayOutputStream);
            return new ByteArrayResource(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error generando el excel.");
        }

    }
}
