/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sro.web.config;

import java.util.Hashtable;
import java.util.Map;
import javax.naming.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.web.SecurityFilterChain;

/**
 *
 * @author a1024408
 */
@Configuration
public class WebSecurityConfigLogin {
    
    @Value("${app.config.ldap.url}")
    private String ldapURL;
    
    @Value("${app.config.ldap.context.factory}")
    private String ldapFactory;
    
    @Value("${app.config.ldap.security.authentication}")
    private String authenticationMethod;
    
    @Value("${app.config.ldap.security.principal}")
    private String authenticationUser;
    
    @Value("${app.config.ldap.security.credentials}")
    private String authenticationPass;
    
    @Value("${app.config.ldap.security.protocol}")
    private String authenticationProtocol;
    
    
    @Bean()
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {        
        http
            .csrf().disable()
            .authorizeRequests()                
                .antMatchers("/**")
                .fullyAuthenticated()
                .and().formLogin()
                    .defaultSuccessUrl("/home");
        
        /*
        http
            .csrf().disable()
            .authorizeRequests()                
                .antMatchers("/**")
                .fullyAuthenticated()
                .and().formLogin()
                .and()
             .authorizeRequests()
                .antMatchers("/**")
                .fullyAuthenticated()
                .and().httpBasic();
                */
        
        return http.build();
    }
    
    @Bean
    LdapAuthenticationProvider ldapAuthenticationProvider() {
       return new LdapAuthenticationProvider(authenticator());
    }   
    
    @Bean
    BindAuthenticator authenticator() {

       FilterBasedLdapUserSearch search = new FilterBasedLdapUserSearch("dc=sso,dc=sensata,dc=ad", "(employeeID={0})", contextSource());
       BindAuthenticator authenticator = new BindAuthenticator(contextSource());
       authenticator.setUserSearch(search);
       return authenticator;
    }    
    
    @Bean
    public DefaultSpringSecurityContextSource contextSource() {
       DefaultSpringSecurityContextSource dsCtx = new DefaultSpringSecurityContextSource(ldapURL);
       Map<String, Object> ldapEnv = new Hashtable<String, Object>();
       
       ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, ldapFactory);
       ldapEnv.put(Context.SECURITY_AUTHENTICATION, authenticationMethod);
       ldapEnv.put(Context.SECURITY_PRINCIPAL,  authenticationUser);
       ldapEnv.put(Context.SECURITY_CREDENTIALS, authenticationPass);
       //ldapEnv.put(Context.PROVIDER_URL, "ldaps2://ldaps.corp.sensata.com:636/");
       ldapEnv.put(Context.SECURITY_PROTOCOL, authenticationProtocol);        
       
       dsCtx.setBaseEnvironmentProperties(ldapEnv);
       return dsCtx;
    }    

    public String getLdapURL() {
        return ldapURL;
    }

    public void setLdapURL(String ldapURL) {
        this.ldapURL = ldapURL;
    }

    public String getLdapFactory() {
        return ldapFactory;
    }

    public void setLdapFactory(String ldapFactory) {
        this.ldapFactory = ldapFactory;
    }

    public String getAuthenticationMethod() {
        return authenticationMethod;
    }

    public void setAuthenticationMethod(String authenticationMethod) {
        this.authenticationMethod = authenticationMethod;
    }

    public String getAuthenticationUser() {
        return authenticationUser;
    }

    public void setAuthenticationUser(String authenticationUser) {
        this.authenticationUser = authenticationUser;
    }

    public String getAuthenticationPass() {
        return authenticationPass;
    }

    public void setAuthenticationPass(String authenticationPass) {
        this.authenticationPass = authenticationPass;
    }

    public String getAuthenticationProtocol() {
        return authenticationProtocol;
    }

    public void setAuthenticationProtocol(String authenticationProtocol) {
        this.authenticationProtocol = authenticationProtocol;
    }


    
    
    
}
