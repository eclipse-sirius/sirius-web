/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.diagrams;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.ImageNodeStyle;
import org.eclipse.sirius.web.diagrams.ListItemNodeStyle;
import org.eclipse.sirius.web.diagrams.ListNodeStyle;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.NodeType;
import org.eclipse.sirius.web.diagrams.RectangularNodeStyle;

/**
 * Custom deserializer for node style since Jackson need to know how to find the concrete class matching the JSON data.
 *
 * @author sbegaudeau
 */
public class INodeStyleDeserializer extends StdDeserializer<INodeStyle> {

    private static final long serialVersionUID = 5722074461929397488L;

    public INodeStyleDeserializer() {
        this(null);
    }

    public INodeStyleDeserializer(Class<?> valueClass) {
        super(valueClass);
    }

    @Override
    public INodeStyle deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
        INodeStyle nodeStyle = null;
        ObjectCodec objectCodec = jsonParser.getCodec();
        if (objectCodec instanceof ObjectMapper) {
            ObjectMapper mapper = (ObjectMapper) objectCodec;
            ObjectNode root = mapper.readTree(jsonParser);

            Object parent = jsonParser.getParsingContext().getCurrentValue();
            if (parent instanceof Node) {
                Node parentNode = (Node) parent;
                switch (parentNode.getType()) {
                case NodeType.NODE_RECTANGLE:
                    nodeStyle = mapper.readValue(root.toString(), RectangularNodeStyle.class);
                    break;
                case NodeType.NODE_IMAGE:
                    nodeStyle = mapper.readValue(root.toString(), ImageNodeStyle.class);
                    break;
                case NodeType.NODE_LIST:
                    nodeStyle = mapper.readValue(root.toString(), ListNodeStyle.class);
                    break;
                case NodeType.NODE_LIST_ITEM:
                    nodeStyle = mapper.readValue(root.toString(), ListItemNodeStyle.class);
                    break;
                default:
                    nodeStyle = mapper.readValue(root.toString(), RectangularNodeStyle.class);
                    break;
                }
            }
        }
        return nodeStyle;
    }

}
