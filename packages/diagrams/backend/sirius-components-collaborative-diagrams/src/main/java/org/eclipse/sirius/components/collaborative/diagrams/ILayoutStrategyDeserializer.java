/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.ILayoutStrategy;
import org.eclipse.sirius.components.diagrams.ListLayoutStrategy;

/**
 * Custom deserializer for layout strategy since jackson need to know how to find the concrete class matching the JSON
 * data.
 *
 * @author gcoutable
 */
public class ILayoutStrategyDeserializer extends StdDeserializer<ILayoutStrategy> {

    private static final long serialVersionUID = -4765219889537073762L;

    public ILayoutStrategyDeserializer() {
        this(null);
    }

    public ILayoutStrategyDeserializer(Class<?> valueClass) {
        super(valueClass);
    }

    @Override
    public ILayoutStrategy deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JacksonException {
        ILayoutStrategy layoutStrategy = null;
        ObjectCodec objectCodec = jsonParser.getCodec();
        if (objectCodec instanceof ObjectMapper) {
            ObjectMapper mapper = (ObjectMapper) objectCodec;
            ObjectNode root = mapper.readTree(jsonParser);
            switch (root.get("kind").asText()) { //$NON-NLS-1$
            case ListLayoutStrategy.KIND:
                layoutStrategy = mapper.readValue(root.toString(), ListLayoutStrategy.class);
                break;
            case FreeFormLayoutStrategy.KIND:
                layoutStrategy = mapper.readValue(root.toString(), FreeFormLayoutStrategy.class);
                break;
            default:
                break;
            }
        }
        return layoutStrategy;
    }

}
