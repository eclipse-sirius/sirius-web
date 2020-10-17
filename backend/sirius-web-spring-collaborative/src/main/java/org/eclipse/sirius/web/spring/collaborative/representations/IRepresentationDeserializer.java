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
package org.eclipse.sirius.web.spring.collaborative.representations;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.representations.IRepresentation;

/**
 * Custom deserializer for IRepresentation since Jackson need to now how to find the concrete class matching the JSON
 * data.
 *
 * @author gcoutable
 */
public class IRepresentationDeserializer extends StdDeserializer<IRepresentation> {

    private static final long serialVersionUID = -1759653601179599083L;

    public IRepresentationDeserializer() {
        this(null);
    }

    public IRepresentationDeserializer(Class<?> valueClass) {
        super(valueClass);
    }

    @Override
    public IRepresentation deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
        IRepresentation representation = null;
        ObjectCodec objectCodec = jsonParser.getCodec();
        if (objectCodec instanceof ObjectMapper) {
            ObjectMapper mapper = (ObjectMapper) objectCodec;
            ObjectNode root = mapper.readTree(jsonParser);
            representation = mapper.readValue(root.toString(), Diagram.class);
        }
        return representation;
    }

}
