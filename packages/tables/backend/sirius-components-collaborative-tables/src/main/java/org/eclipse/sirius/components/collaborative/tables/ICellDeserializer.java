/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

import org.eclipse.sirius.components.tables.ICell;
import org.eclipse.sirius.components.tables.MultiSelectCell;
import org.eclipse.sirius.components.tables.SelectCell;
import org.eclipse.sirius.components.tables.TextfieldCell;

/**
 * Custom deserializer for cell since Jackson need to know how to find the concrete class matching the JSON data.
 *
 * @author frouene
 */
public class ICellDeserializer extends StdDeserializer<ICell> {

    public ICellDeserializer() {
        this(null);
    }

    public ICellDeserializer(Class<?> valueClass) {
        super(valueClass);
    }

    @Override
    public ICell deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        ICell cell = null;
        ObjectCodec objectCodec = jsonParser.getCodec();
        if (objectCodec instanceof ObjectMapper mapper) {
            ObjectNode root = mapper.readTree(jsonParser);
            cell = switch (root.get("type").asText()) {
                case TextfieldCell.TYPE -> mapper.readValue(root.toString(), TextfieldCell.class);
                case MultiSelectCell.TYPE -> mapper.readValue(root.toString(), MultiSelectCell.class);
                case SelectCell.TYPE -> mapper.readValue(root.toString(), SelectCell.class);
                default -> mapper.readValue(root.toString(), TextfieldCell.class);
            };
        }
        return cell;
    }
}
