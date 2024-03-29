package com.example.demo.security;

import com.example.demo.model.userInfo.Role;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //userName = "tao";
        //String password = "1234";

        try {

            com.example.demo.model.userInfo.User userDb = userService.getUserByUserCode(userName);

            if (userDb == null)
                throw new UsernameNotFoundException("用户名或密码不正确");

            String password = userDb.getPassword();

            String encodePassword = new BCryptPasswordEncoder().encode(password);
            List<GrantedAuthority> authorities = getAuthorities(userName);

            MyUser user = new MyUser(userName, encodePassword, authorities);
            user.setRealName(userDb.getUserName());

            return user;

        } catch (Exception e) {
            throw new UsernameNotFoundException("用户名或密码不正确");
        }
    }

    private List<GrantedAuthority> getAuthorities(String userName) throws Exception {

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        //authorities.add(new SimpleGrantedAuthority("role_user"));
        //data from database
        List<Role> roles = roleService.getRolesByUserCode(userName);
        if (roles != null) {
            roles.forEach(r -> authorities.add(new SimpleGrantedAuthority(r.getRoleName())));
        }

        return authorities;
    }
}
