package com.ccondori.retosermaluc.commons.models;

public record Formula(
    Integer aco_id_asociacion_comuna
    ,String emp_nom_empresa
    ,String com_nom_comuna
    ,String sub_nombre_sec
    ,Integer opc_tarifaria_id
    ,String opc_tarifaria_nombre
    ,String aca_for_formula_descompuesta
    ,String car_desc_cargo
) {
}
