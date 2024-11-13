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
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.components.emf.services.EObjectIDManager;

/**
 * Custom JSON Serializer for EObjects, only for tests purpose.
 *
 * @author arichard
 */
public class EObjectJsonSerializer extends JsonSerializer<EObject> {

    private static final String ID = "@id";

    private static final String TYPE = "@type";

    private EObjectIDManager eObjectIDManager = new EObjectIDManager();

    @Override
    public void serialize(EObject value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        var id = this.eObjectIDManager.findId(value);
        if (id.isPresent()) {
            gen.writeStringField(ID, id.get());
        }
        gen.writeStringField(TYPE, value.eClass().getName());
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