package com.ccondori.retosermaluc.commons.config;

import com.ccondori.retosermaluc.commons.models.Variable;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.util.List;

public class TypeVariable extends BaseTypeHandler<List<Variable>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<Variable> parameter, JdbcType jdbcType) throws SQLException {
        SQLServerDataTable dataTable= new SQLServerDataTable();
        try {
            dataTable.addColumnMetadata("aco_id_asociacion_comuna", Types.INTEGER);
            dataTable.addColumnMetadata("componente", Types.VARCHAR);
            dataTable.addColumnMetadata("valor", Types.DOUBLE);
        }catch (SQLServerException e){
            e.printStackTrace();
        }
        parameter.forEach(variable -> {
            try {
                dataTable.addRow(variable.aco_id_asociacion_comuna(), variable.componente(), variable.valor());
            } catch (SQLServerException e) {
                e.printStackTrace();
            }
        });
        ps.unwrap(SQLServerPreparedStatement.class)
                .setStructured(i, "SLUC_type_table_variable", dataTable);
    }

    @Override
    public List<Variable> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public List<Variable> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public List<Variable> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
