package com.ccondori.retosermaluc.processdata.services.impl;

import com.ccondori.retosermaluc.commons.exceptions.BusinessException;
import com.ccondori.retosermaluc.commons.models.Formula;
import com.ccondori.retosermaluc.commons.models.Variable;
import com.ccondori.retosermaluc.processdata.mappers.ProcessDataMapper;
import com.ccondori.retosermaluc.processdata.services.ProcessDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProcessDataServiceImpl implements ProcessDataService {

    @Autowired
    private ProcessDataMapper processDataMapper;

    @Override
    public String cargarDatosExcel(MultipartFile file) throws IOException {
        //CARGAMOS LAS VARIABLES
        List<Variable> variables = new LinkedList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheetVariable = workbook.getSheet("variables");
        variables = StreamSupport.stream(worksheetVariable.spliterator(),false)
                .skip(1)
                .map(row -> {
                    return new Variable(
                            (int) row.getCell(0).getNumericCellValue()
                            ,row.getCell(1).getStringCellValue()
                            , row.getCell(2).getNumericCellValue()
                    );
                })
                .collect(Collectors.toList());
        //CARGAMOS LAS FORMULAS
        List<Formula> formulas = new LinkedList<>();
        XSSFSheet worksheetFormula = workbook.getSheet("Formula");
        formulas = StreamSupport.stream(worksheetFormula.spliterator(),false)
                .skip(1)
                .map(row -> {
                    return new Formula(
                            (int) row.getCell(0).getNumericCellValue()
                            ,row.getCell(1).getStringCellValue()
                            , row.getCell(2).getStringCellValue()
                            , row.getCell(3).getStringCellValue()
                            , (int) row.getCell(4).getNumericCellValue()
                            , row.getCell(5).getStringCellValue()
                            , row.getCell(6).getStringCellValue()
                            , row.getCell(7).getStringCellValue()
                    );
                })
                .collect(Collectors.toList());
        Integer cantRegistros = processDataMapper.cargarDatosExcel(variables,formulas);
        if(cantRegistros>0)return ("Se grabaron "+cantRegistros+" registros.");
        else throw new BusinessException("No se ha grabado ningun dato.");

    }

    @Override
    public String procesarFormulas() {
        Integer cantRegistros = processDataMapper.procesarFormulas();
        if(cantRegistros>0) return ("Se procesaron "+cantRegistros+" registros.");
        else throw new BusinessException("No se procesó ningún dato.");
    }
}
