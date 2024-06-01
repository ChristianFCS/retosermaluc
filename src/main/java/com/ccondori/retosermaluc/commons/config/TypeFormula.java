package com.ccondori.retosermaluc.commons.config;

import com.ccondori.retosermaluc.commons.models.Formula;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.util.List;

public class TypeFormula extends BaseTypeHandler<List<Formula>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<Formula> parameter, JdbcType jdbcType) throws SQLException {
        SQLServerDataTable dataTable= new SQLServerDataTable();
        try {
            dataTable.addColumnMetadata("aco_id_asociacion_comuna", Types.INTEGER);
            dataTable.addColumnMetadata("emp_nom_empresa", Types.VARCHAR);
            dataTable.addColumnMetadata("com_nom_comuna", Types.VARCHAR);
            dataTable.addColumnMetadata("sub_nombre_sec", Types.VARCHAR);
            dataTable.addColumnMetadata("opc_tarifaria_id", Types.INTEGER);
            dataTable.addColumnMetadata("opc_tarifaria_nombre", Types.VARCHAR);
            dataTable.addColumnMetadata("aca_for_formula_descompuesta", Types.VARCHAR);
            dataTable.addColumnMetadata("car_desc_cargo", Types.VARCHAR);
        }catch (SQLServerException e){
            e.printStackTrace();
        }
        parameter.forEach(formula -> {
            try {
                dataTable.addRow(formula.aco_id_asociacion_comuna()
                        ,formula.emp_nom_empresa()
                        ,formula.com_nom_comuna()
                        ,formula.sub_nombre_sec()
                        ,formula.opc_tarifaria_id()
                        ,formula.opc_tarifaria_nombre()
                        ,formula.aca_for_formula_descompuesta()
                        ,formula.car_desc_cargo()
                );
            } catch (SQLServerException e) {
                e.printStackTrace();
            }
        });
        ps.unwrap(SQLServerPreparedStatement.class)
                .setStructured(i, "SLUC_type_table_formula", dataTable);
    }

    @Override
    public List<Formula> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public List<Formula> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public List<Formula> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
