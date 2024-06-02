package com.ccondori.retosermaluc.auth.services.impl;

import com.ccondori.retosermaluc.auth.mappers.AuthMapper;
import com.ccondori.retosermaluc.auth.model.*;
import com.ccondori.retosermaluc.commons.exceptions.BusinessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("MyBatisUserDetailService")
public class MyBatisUserDetailService implements UserDetailsService {


    private AuthMapper authMapper;

    public MyBatisUserDetailService(AuthMapper usuariolMapper) {
        this.authMapper = usuariolMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = authMapper.obtUsuPerfPorUsername(username);
        if (usuario == null) {
            throw new BusinessException("Usuario no encontrado");
        }
        return new UserDetailsCustom(usuario);
    }


}

