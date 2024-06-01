package com.ccondori.retosermaluc.auth.mappers;

import com.ccondori.retosermaluc.auth.model.Usuario;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthMapper {

    Usuario obtUsuPerfPorUsername(@Param("username") String username);

}
