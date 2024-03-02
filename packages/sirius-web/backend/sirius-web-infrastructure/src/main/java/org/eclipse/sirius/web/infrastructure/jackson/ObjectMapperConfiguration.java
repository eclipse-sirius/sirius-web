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
package org.eclipse.sirius.web.infrastructure.jackson;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.collaborative.api.IStdDeserializerProvider;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of the object mapper used to serialize and deserialize in JSON.
 *
 * @author sbegaudeau
 */
@Configuration
public class ObjectMapperConfiguration {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer(List<IStdDeserializerProvider<?>> deserializerProviders) {
        Map<Class<?>, JsonDeserializer<?>> deserializers = deserializerProviders.stream()
                .collect(Collectors.toMap(IStdDeserializerProvider::getType, IStdDeserializerProvider::getDeserializer));

        SimpleDeserializers simpleDeserializers = new SimpleDeserializers();
        simpleDeserializers.addDeserializers(deserializers);

        SimpleModule module = new SimpleModule();
        module.setDeserializers(simpleDeserializers);

        return builder -> builder.modulesToInstall(module);
    }
}
