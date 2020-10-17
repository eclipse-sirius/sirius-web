/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.spring.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.services.api.mapper.IDeserializerProvider;
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
    public ObjectMapper objectMapper(List<IDeserializerProvider<?>> deserializerProviders) {
        ObjectMapper objectMapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        SimpleDeserializers simpleDeserializers = new SimpleDeserializers();
        simpleDeserializers.addDeserializers(deserializerProviders.stream().collect(Collectors.toMap(dp -> dp.getType(), dp -> dp.getDeserializer())));
        module.setDeserializers(simpleDeserializers);
        objectMapper.registerModule(module);

        return objectMapper;
    }
}
