/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.web.infrastructure.configuration.jackson;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.collaborative.api.IStdDeserializerProvider;
import org.eclipse.sirius.components.graphql.api.UploadFile;
import org.eclipse.sirius.web.application.project.api.ICreateProjectInput;
import org.eclipse.sirius.web.application.project.api.IUploadProjectInput;
import org.eclipse.sirius.web.application.project.dto.CreateProjectInput;
import org.eclipse.sirius.web.application.project.dto.UploadProjectInput;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;


import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.deser.std.StdDeserializer;
import tools.jackson.databind.module.SimpleDeserializers;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.ser.std.StdSerializer;

/**
 * Configuration of the object mapper used to serialize and deserialize in JSON.
 *
 * @author sbegaudeau
 */
@Configuration
public class ObjectMapperConfiguration {

    @Bean
    public JsonMapperBuilderCustomizer jsonCustomizer(List<IStdDeserializerProvider<?>> deserializerProviders) {
        Map<Class<?>, ValueDeserializer<?>> deserializers = deserializerProviders.stream()
                .collect(Collectors.toMap(IStdDeserializerProvider::getType, IStdDeserializerProvider::getDeserializer));

        SimpleDeserializers simpleDeserializers = new SimpleDeserializers();
        simpleDeserializers.addDeserializers(deserializers);

        SimpleModule module = new SimpleModule();
        module.setDeserializers(simpleDeserializers);

        return builder -> builder.addModule(module);
    }

    @Bean
    public JsonMapperBuilderCustomizer configureAsJackson2() {
        return builder -> builder.disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
                .disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
    }

    @Bean
    @ConditionalOnMissingBean(name = "createProjectInputModuleJsonCustomizer")
    public JsonMapperBuilderCustomizer createProjectInputModuleJsonCustomizer() {
        SimpleModule module = new SimpleModule();
        module.addAbstractTypeMapping(ICreateProjectInput.class, CreateProjectInput.class);
        return builder -> builder.addModule(module);
    }

    @Bean
    @ConditionalOnMissingBean(name = "uploadProjectInputModuleJsonCustomizer")
    public JsonMapperBuilderCustomizer uploadProjectInputModuleJsonCustomizer() {
        SimpleModule module = new SimpleModule();
        module.addAbstractTypeMapping(IUploadProjectInput.class, UploadProjectInput.class);
        return builder -> builder.addModule(module);
    }

    @Bean
    @ConditionalOnMissingBean(name = "uploadFileModuleJsonCustomizer")
    public JsonMapperBuilderCustomizer uploadFileModuleJsonCustomizer() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(new StdSerializer<>(UploadFile.class) {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializationContext provider) throws JacksonException {
                gen.writeEmbeddedObject(value);
            }
        });
        module.addDeserializer(UploadFile.class, new StdDeserializer<>(UploadFile.class) {
            @Override
            public UploadFile deserialize(JsonParser p, DeserializationContext ctx) throws JacksonException {
                return (UploadFile) p.getEmbeddedObject();
            }
        });
        return builder -> builder.addModule(module);
    }
}