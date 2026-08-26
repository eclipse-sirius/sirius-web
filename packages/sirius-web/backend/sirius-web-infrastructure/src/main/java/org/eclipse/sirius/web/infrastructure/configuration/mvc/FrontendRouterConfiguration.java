/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

/**
 * Used to redirect request to frontend paths to the proper static resources.
 *
 * @author sbegaudeau
 */
@Configuration
public class FrontendRouterConfiguration {

    private final ResourceLoader resourceLoader;

    public FrontendRouterConfiguration(ResourceLoader resourceLoader) {
        this.resourceLoader = Objects.requireNonNull(resourceLoader);
    }

    @Bean
    public RouterFunction<ServerResponse> redirectToIndex(List<IBackendPathPredicate> backendResourcePredicates, List<IIndexProcessor> indexProcessors, WebProperties webProperties) {
        var extensionsToIgnore = List.of("css", "html", "js", "map", "chunk.js", "json", "ico", "ttf", "woff", "woff2", "jpg", "jpeg", "png", "svg");

        var singlePageApplicationPredicate = path("/api/**")
                .or(path("/v3/api-docs/**"))
                .or(path("/subscriptions"))
                .or(pathExtension(extension -> extension != null && extensionsToIgnore.contains(extension)))
                .or(request -> backendResourcePredicates.stream().anyMatch(backendResourcePredicate -> backendResourcePredicate.isBackendPath(request.path())))
                .negate();

        Optional<Resource> optionalIndex = Arrays.stream(webProperties.getResources().getStaticLocations())
                .map(location -> this.resourceLoader.getResource(location + "index.html"))
                .filter(Resource::exists)
                .findFirst();

        if (optionalIndex.isEmpty()) {
            return route()
                    .GET(singlePageApplicationPredicate, request -> ServerResponse.notFound().build())
                    .build();
        } else {
            return route()
                    .GET(singlePageApplicationPredicate, request -> this.computeServerResponse(optionalIndex.get(), request, indexProcessors))
                    .build();
        }
    }

    private ServerResponse computeServerResponse(Resource index, ServerRequest request, List<IIndexProcessor> indexProcessors) {
        String content;
        try (var inputStream = index.getInputStream()) {
            content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            return ServerResponse.notFound().build();
        }
        for (var processor : indexProcessors) {
            content = processor.process(request, content);
        }
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_HTML)
                .cacheControl(CacheControl.noStore().mustRevalidate())
                .body(content);
    }
}
