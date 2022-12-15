/*******************************************************************************
 * Copyright (c) 2019, 2020, 2022 Obeo.
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
package org.eclipse.sirius.components.forms.tests;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.Checkbox;
import org.eclipse.sirius.components.forms.List;
import org.eclipse.sirius.components.forms.Radio;
import org.eclipse.sirius.components.forms.Select;
import org.eclipse.sirius.components.forms.Textarea;
import org.eclipse.sirius.components.forms.Textfield;
import org.eclipse.sirius.components.forms.TreeWidget;

/**
 * Custom deserializer for AbstractWidget since Jackson need to know how to find the concrete class matching the JSON
 * data.
 *
 * @author lfasani
 */
public class WidgetDeserializer extends StdDeserializer<AbstractWidget> {

    private static final long serialVersionUID = 5722074461929397488L;

    public WidgetDeserializer() {
        this(null);
    }

    public WidgetDeserializer(Class<?> valueClass) {
        super(valueClass);
    }

    @Override
    public AbstractWidget deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
        AbstractWidget nodeStyle = null;
        ObjectCodec objectCodec = jsonParser.getCodec();
        if (objectCodec instanceof ObjectMapper) {
            ObjectMapper mapper = (ObjectMapper) objectCodec;
            ObjectNode root = mapper.readTree(jsonParser);

            JsonNode typeName = root.get("__typename");
            if (Radio.class.getSimpleName().equals(typeName.asText())) {
                nodeStyle = mapper.readValue(root.toString(), Radio.class);
            } else if (Select.class.getSimpleName().equals(typeName.asText())) {
                nodeStyle = mapper.readValue(root.toString(), Select.class);
            } else if (Checkbox.class.getSimpleName().equals(typeName.asText())) {
                nodeStyle = mapper.readValue(root.toString(), Checkbox.class);
            } else if (Textarea.class.getSimpleName().equals(typeName.asText())) {
                nodeStyle = mapper.readValue(root.toString(), Textarea.class);
            } else if (Textfield.class.getSimpleName().equals(typeName.asText())) {
                nodeStyle = mapper.readValue(root.toString(), Textfield.class);
            } else if (List.class.getSimpleName().equals(typeName.asText())) {
                nodeStyle = mapper.readValue(root.toString(), List.class);
            } else if (TreeWidget.class.getSimpleName().equals(typeName.asText())) {
                nodeStyle = mapper.readValue(root.toString(), TreeWidget.class);
            }
        }
        return nodeStyle;
    }

}
