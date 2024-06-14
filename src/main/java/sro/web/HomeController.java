/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sro.web;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.stereotype.Component;

/**
 *
 * @author a1024408
 *
 */

@Scope(value = "session")
@Component(value = "homeController")
@ELBeanName(value = "homeController")
@Join(path = "/home", to = "/index.xhtml")
public class HomeController {
    
    
    public String getUsername(){
        return String.format("Hello, %s", getAuthentication().getName());
    }
    
    private Authentication getAuthentication() {
        return getSecurityContext().getAuthentication();
    }
    
    public SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }
    
    public String logout(){
        return "";
    }
}
