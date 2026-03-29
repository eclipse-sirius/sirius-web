/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web;

import org.eclipse.emf.ecore.EObject;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.module.SimpleSerializers;

/**
 * Used to customize the serialization of EObject.
 *
 * @author mcharfadi
 */
@Configuration
public class TestsConfigMapper {

    @Bean
    public JsonMapperBuilderCustomizer configureEobjects() {
        SimpleModule module = new SimpleModule();
        SimpleSerializers simpleDeserializers = new SimpleSerializers();
        simpleDeserializers.addSerializer(new EObjectJsonSerializer(EObject.class));
        module.setSerializers(simpleDeserializers);

        return builder -> builder.addModule(module);
    }
}
