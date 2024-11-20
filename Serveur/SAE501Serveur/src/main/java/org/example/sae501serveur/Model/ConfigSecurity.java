package org.example.sae501serveur.Model;

import org.example.sae501serveur.Model.Service.JoueurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfigSecurity  {
    @Autowired
    private JoueurService joueurService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configure l'authentification globale avec un service de détails d'utilisateur et un encodeur de mot de passe.
     *
     * @param auth le gestionnaire d'authentification à configurer
     * @throws Exception si une erreur se produit lors de la configuration
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(joueurService).passwordEncoder(passwordEncoder());
    }

    /**
     * Définit la chaîne de filtres de sécurité pour l'application.
     *
     * @param http l'objet HttpSecurity à configurer
     * @return le SecurityFilterChain configuré
     * @throws Exception si une erreur se produit lors de la configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/public/**").permitAll()// Autorise l'accès à "/public/**"
                        .requestMatchers("/creationCompte").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/ajoutUtilisateurBDD").permitAll()
                        .requestMatchers("/testConnexion","/joueurSession","/joueurId","/connexionPartie","/game/{idPartie}").hasAnyRole("JOUEUR","ADMIN")
                        .anyRequest().hasRole("ADMIN")  // Exige une authentification pour toutes les autres URL
                )
                .formLogin((form) -> form
                        .loginPage("/login").defaultSuccessUrl("/testConnexion").permitAll()
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

}