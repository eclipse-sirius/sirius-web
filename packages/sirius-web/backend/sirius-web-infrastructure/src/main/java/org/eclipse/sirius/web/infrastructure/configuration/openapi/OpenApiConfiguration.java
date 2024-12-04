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
package org.eclipse.sirius.web.infrastructure.configuration.openapi;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of the openapi generator.
 *
 * @author gescande
 */
@Configuration
public class OpenApiConfiguration {

    @Bean
    public GroupedOpenApi selectedApiGroup() {
        return GroupedOpenApi.builder()
                .group("rest-apis")
                .pathsToMatch("/api/rest/**")
                .build();
    }
}