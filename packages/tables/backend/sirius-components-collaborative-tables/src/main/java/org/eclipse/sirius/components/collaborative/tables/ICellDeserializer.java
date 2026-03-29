/*******************************************************************************
 * Copyright (c) 2024, 2026 CEA LIST.
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
package org.eclipse.sirius.components.collaborative.tables;

import org.eclipse.sirius.components.tables.ICell;
import org.eclipse.sirius.components.tables.MultiSelectCell;
import org.eclipse.sirius.components.tables.SelectCell;
import org.eclipse.sirius.components.tables.TextfieldCell;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.deser.std.StdDeserializer;
import tools.jackson.databind.node.ObjectNode;

/**
 * Custom deserializer for cell since Jackson need to know how to find the concrete class matching the JSON data.
 *
 * @author frouene
 */
public class ICellDeserializer extends StdDeserializer<ICell> {

    public ICellDeserializer() {
        this(ICell.class);
    }

    public ICellDeserializer(Class<?> valueClass) {
        super(valueClass);
    }

    @Override
    public ICell deserialize(JsonParser jsonParser, DeserializationContext context) {
        JsonNode rootNode = context.readTree(jsonParser);
        ObjectNode root = (ObjectNode) rootNode;

        return switch (root.get("type").asText()) {
            case TextfieldCell.TYPE -> context.readTreeAsValue(root, TextfieldCell.class);
            case MultiSelectCell.TYPE -> context.readTreeAsValue(root, MultiSelectCell.class);
            case SelectCell.TYPE -> context.readTreeAsValue(root, SelectCell.class);
            default -> context.readTreeAsValue(root, TextfieldCell.class);
        };
    }

}
