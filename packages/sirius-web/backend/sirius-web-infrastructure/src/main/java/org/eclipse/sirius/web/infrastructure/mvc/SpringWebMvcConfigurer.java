/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.infrastructure.mvc;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Used to configure the server side routing.
 *
 * @author sbegaudeau
 */
@Configuration
public class SpringWebMvcConfigurer implements WebMvcConfigurer {
    private final String[] allowedOriginPatterns;

    private final String[] allowedHeaders;

    private final String[] allowedMethods;

    private final boolean allowedCredentials;

    public SpringWebMvcConfigurer(
            @Value("${sirius.components.cors.allowedOriginPatterns:}") String[] allowedOriginPatterns,
            @Value("${sirius.components.cors.allowedHeaders:}") String[] allowedHeaders,
            @Value("${sirius.components.cors.allowedMethods:}") String[] allowedMethods,
            @Value("${sirius.components.cors.allowedCredentials:false}") boolean allowedCredentials
    ) {
        this.allowedOriginPatterns = Objects.requireNonNull(allowedOriginPatterns);
        this.allowedHeaders = Objects.requireNonNull(allowedHeaders);
        this.allowedMethods = Objects.requireNonNull(allowedMethods);
        this.allowedCredentials = allowedCredentials;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Make sure that all static assets are redirected properly to the proper path
        registry.addResourceHandler(
                SpringWebMvcConfigurerConstants.CSS_PATTERN,
                SpringWebMvcConfigurerConstants.HTML_PATTERN,
                SpringWebMvcConfigurerConstants.JS_PATTERN,
                SpringWebMvcConfigurerConstants.JS_CHUNK_PATTERN,
                SpringWebMvcConfigurerConstants.JS_MAP_PATTERN,
                SpringWebMvcConfigurerConstants.JSON_PATTERN,
                SpringWebMvcConfigurerConstants.ICO_PATTERN,
                SpringWebMvcConfigurerConstants.TTF_PATTERN,
                SpringWebMvcConfigurerConstants.MEDIA_PATTERN
        ).addResourceLocations(SpringWebMvcConfigurerConstants.STATIC_ASSETS_PATH);

        // Make sure that all other requests are redirected to index.html, the React router will handle it
        registry.addResourceHandler(SpringWebMvcConfigurerConstants.EMPTY_PATTERN, SpringWebMvcConfigurerConstants.HOMEPAGE_PATTERN, SpringWebMvcConfigurerConstants.ANY_PATTERN)
                .addResourceLocations(SpringWebMvcConfigurerConstants.INDEX_HTML_PATH)
                .resourceChain(true)
                .addResolver(new SpringPathResourceResolver(SpringWebMvcConfigurerConstants.API_BASE_PATH));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        CorsRegistration corsRegistration = registry.addMapping(SpringWebMvcConfigurerConstants.ANY_PATTERN);
        if (this.allowedOriginPatterns.length > 0) {
            corsRegistration.allowedOriginPatterns(this.allowedOriginPatterns);
        }
        if (this.allowedHeaders.length > 0) {
            corsRegistration.allowedHeaders(this.allowedHeaders);
        }
        if (this.allowedMethods.length > 0) {
            corsRegistration.allowedMethods(this.allowedMethods);
        }
        corsRegistration.allowCredentials(this.allowedCredentials);
    }
}
