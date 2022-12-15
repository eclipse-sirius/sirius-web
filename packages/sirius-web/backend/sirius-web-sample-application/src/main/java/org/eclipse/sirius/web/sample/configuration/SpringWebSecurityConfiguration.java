/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.web.sample.configuration;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.persistence.repositories.IAccountRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Security configuration of Sirius Web.
 *
 * @author sbegaudeau
 */
@Configuration
@EnableWebSecurity
public class SpringWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final IAccountRepository accountRepository;

    public SpringWebSecurityConfiguration(IAccountRepository accountRepository) {
        this.accountRepository = Objects.requireNonNull(accountRepository);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable();
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/api/graphql").authenticated();
        http.authorizeRequests().antMatchers("/**").permitAll();

        http.httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserDetailsService accountBasedUserDetailsService = username -> {
            // @formatter:off
            return this.accountRepository.findByUsername(username)
                       .map(account -> new User(account.getUsername(), account.getPassword(), List.of()))
                       .orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("User ''{0}'' not found", username)));
            // @formatter:on
        };
        auth.userDetailsService(accountBasedUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
