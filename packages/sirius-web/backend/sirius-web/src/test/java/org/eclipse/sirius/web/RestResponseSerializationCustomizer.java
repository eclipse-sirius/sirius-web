/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Used to customize the serialization of JSON responses for the REST API.
 *
 * @author arichard
 */
@ControllerAdvice("org.eclipse.sirius.web.application.object.controllers")
public class RestResponseSerializationCustomizer implements ResponseBodyAdvice<Object> {

    private final MappingJackson2HttpMessageConverter converter;

    private final ObjectMapper customObjectMapper;

    public RestResponseSerializationCustomizer(MappingJackson2HttpMessageConverter converter) {
        this.converter = Objects.requireNonNull(converter);
        this.customObjectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(EObject.class, new EObjectJsonSerializer());
        this.customObjectMapper.registerModule(module);
        this.customObjectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.customObjectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        this.converter.setObjectMapper(this.customObjectMapper);
        return body;
    }
}
