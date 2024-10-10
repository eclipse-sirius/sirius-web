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
package org.eclipse.sirius.web;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.components.emf.services.EObjectIDManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Custom JSON ResponseBodyAdvice for EObjects, only for tests purpose.
 *
 * @author arichard
 */
@ControllerAdvice("org.eclipse.sirius.web.application.object.controllers")
public class EObjectSerializerTestConfig implements ResponseBodyAdvice<Object> {

    @Autowired
    private MappingJackson2HttpMessageConverter converter;

    private ObjectMapper customObjectMapper;

    public EObjectSerializerTestConfig() {
        this.customObjectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(EObject.class, new EObjectJsonSerializer());
        this.customObjectMapper.registerModule(module);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {
        this.converter.setObjectMapper(this.customObjectMapper);
        return body;
    }

    /**
     * Custom JSON Serializer for EObjects, only for tests purpose.
     *
     * @author arichard
     */
    private final class EObjectJsonSerializer extends JsonSerializer<EObject> {

        private static final String ID = "@id";

        private EObjectIDManager eObjectIDManager = new EObjectIDManager();

        @Override
        public void serialize(EObject value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            var id = this.eObjectIDManager.findId(value);
            if (id.isPresent()) {
                gen.writeStringField(ID, id.get());
            }
            gen.writeStringField("@type", value.eClass().getName());
            EList<EAttribute> eAllAttributes = value.eClass().getEAllAttributes();
            for (EAttribute eAttribute : eAllAttributes) {
                Object objectValue = value.eGet(eAttribute);
                if (objectValue != null) {
                    gen.writeStringField(eAttribute.getName(), objectValue.toString());
                } else {
                    gen.writeStringField(eAttribute.getName(), null);
                }
            }
            EList<EReference> eAllReferences = value.eClass().getEAllReferences();
            for (EReference eReference : eAllReferences) {
                Object objectValue = value.eGet(eReference);
                if (objectValue != null) {
                    if (eReference.isMany()) {
                        this.writeArray(gen, eReference.getName(), objectValue);
                    } else if (objectValue instanceof EObject eObject) {
                        var refElementId = this.eObjectIDManager.findId(eObject);
                        if (refElementId.isPresent()) {
                            gen.writeObjectFieldStart(eReference.getName());
                            gen.writeStringField(ID, refElementId.get());
                            gen.writeEndObject();
                        }
                    }
                } else {
                    gen.writeStringField(eReference.getName(), null);
                }
            }
            gen.writeEndObject();
        }

        private void writeArray(JsonGenerator gen, String arrayName, Object objectValue) throws IOException {
            gen.writeArrayFieldStart(arrayName);
            if (objectValue instanceof List<?> listValue && !listValue.isEmpty()) {
                for (Object listElementValue : listValue) {
                    if (listElementValue instanceof EObject eObject) {
                        var listElementId = this.eObjectIDManager.findId(eObject);
                        if (listElementId.isPresent()) {
                            gen.writeStartObject();
                            gen.writeStringField(ID, listElementId.get());
                            gen.writeEndObject();
                        }
                    }
                }
            }
            gen.writeEndArray();
        }
    }
}
