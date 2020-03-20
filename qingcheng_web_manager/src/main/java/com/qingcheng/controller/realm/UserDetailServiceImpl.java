package com.qingcheng.controller.realm;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qingcheng.pojo.system.Admin;
import com.qingcheng.service.system.AdminService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDetailServiceImpl implements UserDetailsService {

    @Reference
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("经过了userDetailServiceImpl...");
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("loginName", s);
        searchMap.put("status", 1);
        List<Admin> list = adminService.findList(searchMap);
        if (list == null || list.size() == 0) {
            return null;
        }
        // 构建角色集合
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new User(s, list.get(0).getPassword(), grantedAuthorities);
    }
}
