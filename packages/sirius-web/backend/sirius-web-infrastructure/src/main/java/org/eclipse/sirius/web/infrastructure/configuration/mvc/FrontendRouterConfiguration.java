/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.infrastructure.configuration.mvc;

import static org.springframework.web.servlet.function.RequestPredicates.path;
import static org.springframework.web.servlet.function.RequestPredicates.pathExtension;
import static org.springframework.web.servlet.function.RouterFunctions.route;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

/**
 * Used to redirect request to frontend paths to the proper static resources.
 *
 * @author sbegaudeau
 */
@Configuration
public class FrontendRouterConfiguration {
    @Bean
    public RouterFunction<ServerResponse> redirectToIndex() {
        var extensionsToIgnore = List.of("css", "html", "js", "js.map", "chunk.js", "json", "ico", "ttf", "jpg", "jpeg", "png", "svg");

        var singlePageApplicationPredicate = path("/api/**")
                .or(path("/v3/api-docs/**"))
                .or(path("/subscriptions"))
                .or(pathExtension(extensionsToIgnore::contains))
                .negate();

        var index = new ClassPathResource("static/index.html");
        if (!index.exists()) {
            return route()
                    .GET(singlePageApplicationPredicate, request -> ServerResponse.notFound().build())
                    .build();
        }
        return route()
                .resource(singlePageApplicationPredicate, index)
                .build();
    }
}
