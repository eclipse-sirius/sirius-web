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
package org.eclipse.sirius.web;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.components.emf.services.EObjectIDManager;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

/**
 * Custom JSON Serializer for EObjects, only for tests purpose.
 *
 * @author arichard
 */
public class EObjectJsonSerializer extends StdSerializer<EObject> {

    private static final String ID = "@id";

    private static final String TYPE = "@type";

    private EObjectIDManager eObjectIDManager = new EObjectIDManager();

    public EObjectJsonSerializer(Class<?> t) {
        super(EObject.class);
    }

    private void writeArray(JsonGenerator gen, String arrayName, Object objectValue) {
        gen.writeArrayPropertyStart(arrayName);
        if (objectValue instanceof List<?> listValue && !listValue.isEmpty()) {
            for (Object listElementValue : listValue) {
                if (listElementValue instanceof EObject eObject) {
                    var listElementId = this.eObjectIDManager.findId(eObject);
                    if (listElementId.isPresent()) {
                        gen.writeStartObject();
                        gen.writeStringProperty(ID, listElementId.get());
                        gen.writeEndObject();
                    }
                }
            }
        }
        gen.writeEndArray();
    }

    @Override
    public void serialize(EObject value, tools.jackson.core.JsonGenerator gen, SerializationContext provider) throws JacksonException {
        gen.writeStartObject();
        var id = this.eObjectIDManager.findId(value);
        if (id.isPresent()) {
            gen.writeStringProperty(ID, id.get());
        }
        gen.writeStringProperty(TYPE, value.eClass().getName());
        EList<EAttribute> eAllAttributes = value.eClass().getEAllAttributes();
        for (EAttribute eAttribute : eAllAttributes) {
            Object objectValue = value.eGet(eAttribute);
            if (objectValue != null) {
                gen.writeStringProperty(eAttribute.getName(), objectValue.toString());
            } else {
                gen.writeStringProperty(eAttribute.getName(), null);
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
                        gen.writeObjectPropertyStart(eReference.getName());
                        gen.writeStringProperty(ID, refElementId.get());
                        gen.writeEndObject();
                    }
                }
            } else {
                gen.writeStringProperty(eReference.getName(), null);
            }
        }
        gen.writeEndObject();
    }
}