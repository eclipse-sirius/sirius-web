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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
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

        return builder -> builder.modulesToInstall(existing -> existing.add(module));
    }

    @Bean
    @ConditionalOnMissingBean(name = "createProjectInputModuleJsonCustomizer")
    public Jackson2ObjectMapperBuilderCustomizer createProjectInputModuleJsonCustomizer() {
        SimpleModule module = new SimpleModule();
        module.addAbstractTypeMapping(ICreateProjectInput.class, CreateProjectInput.class);
        return builder -> builder.modulesToInstall(existing -> existing.add(module));
    }

    @Bean
    @ConditionalOnMissingBean(name = "uploadProjectInputModuleJsonCustomizer")
    public Jackson2ObjectMapperBuilderCustomizer uploadProjectInputModuleJsonCustomizer() {
        SimpleModule module = new SimpleModule();
        module.addAbstractTypeMapping(IUploadProjectInput.class, UploadProjectInput.class);
        return builder -> builder.modulesToInstall(existing -> existing.add(module));
    }

    @Bean
    @ConditionalOnMissingBean(name = "uploadFileModuleJsonCustomizer")
    public Jackson2ObjectMapperBuilderCustomizer uploadFileModuleJsonCustomizer() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(new StdSerializer<>(UploadFile.class) {
            @Override
            public void serialize(UploadFile value, JsonGenerator gen, SerializerProvider provider) throws IOException {
                gen.writeEmbeddedObject(value);
            }
        });
        module.addDeserializer(UploadFile.class, new StdDeserializer<>(UploadFile.class) {
            @Override
            public UploadFile deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
                return (UploadFile) p.getEmbeddedObject();
            }
        });
        return builder -> builder.modulesToInstall(existing -> existing.add(module));
    }
}
