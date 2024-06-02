package com.ccondori.retosermaluc.processdata.mappers;

import com.ccondori.retosermaluc.commons.models.Formula;
import com.ccondori.retosermaluc.commons.models.Variable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface ProcessDataMapper {
    Integer cargarDatosExcel(@Param("variables") List<Variable> variables,@Param("formulas") List<Formula> formulas);
    Integer procesarFormulas();
    List<HashMap<String, Object>> generarExcel();
}
