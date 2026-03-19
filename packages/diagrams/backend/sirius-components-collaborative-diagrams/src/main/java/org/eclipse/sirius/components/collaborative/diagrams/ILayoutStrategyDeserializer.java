/*******************************************************************************
 * Copyright (c) 2022, 2026 Obeo.
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

import java.io.Serializable;

import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.ILayoutStrategy;
import org.eclipse.sirius.components.diagrams.ListLayoutStrategy;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.deser.std.StdDeserializer;
import tools.jackson.databind.node.ObjectNode;

/**
 * Custom deserializer for layout strategy since jackson need to know how to find the concrete class matching the JSON
 * data.
 *
 * @author gcoutable
 */
public class ILayoutStrategyDeserializer extends StdDeserializer<ILayoutStrategy> implements Serializable {

    public ILayoutStrategyDeserializer() {
        this(ILayoutStrategy.class);
    }

    public ILayoutStrategyDeserializer(Class<?> valueClass) {
        super(valueClass);
    }

    @Override
    public ILayoutStrategy deserialize(JsonParser jsonParser, DeserializationContext context) throws JacksonException {
        ILayoutStrategy layoutStrategy = null;
        JsonNode rootNode = context.readTree(jsonParser);
        ObjectNode root = (ObjectNode) rootNode;

        switch (root.get("kind").asText()) {
            case ListLayoutStrategy.KIND:
                layoutStrategy = context.readTreeAsValue(root, ListLayoutStrategy.class);
                break;
            case FreeFormLayoutStrategy.KIND:
                layoutStrategy = context.readTreeAsValue(root, FreeFormLayoutStrategy.class);
                break;
            default:
                break;

        }
        return layoutStrategy;
    }

}
