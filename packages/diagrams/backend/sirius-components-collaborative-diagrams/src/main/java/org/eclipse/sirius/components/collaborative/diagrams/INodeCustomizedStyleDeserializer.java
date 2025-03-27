/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.appearancedata.INodeCustomizedStyle;
import org.eclipse.sirius.components.diagrams.appearancedata.IconLabelNodeCustomizedStyle;
import org.eclipse.sirius.components.diagrams.appearancedata.ImageNodeCustomizedStyle;
import org.eclipse.sirius.components.diagrams.appearancedata.RectangularNodeCustomizedStyle;

/**
 * Deserializer for customized node styles.
 *
 * @author nvannier
 */
public class INodeCustomizedStyleDeserializer extends StdDeserializer<INodeCustomizedStyle> {

    public INodeCustomizedStyleDeserializer() {
        this(null);
    }

    public INodeCustomizedStyleDeserializer(Class<?> valueClass) {
        super(valueClass);
    }

    @Override
    public INodeCustomizedStyle deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
        INodeCustomizedStyle nodeCustomizedStyle = null;
        ObjectCodec objectCodec = jsonParser.getCodec();
        if (objectCodec instanceof ObjectMapper mapper) {
            ObjectNode root = mapper.readTree(jsonParser);
            Object parent = jsonParser.getParsingContext().getParent().getCurrentValue();
            if (parent instanceof Node parentNode) {
                switch (parentNode.getType()) {
                    case NodeType.NODE_RECTANGLE:
                        nodeCustomizedStyle = mapper.readValue(root.toString(), RectangularNodeCustomizedStyle.class);
                        break;
                    case NodeType.NODE_IMAGE:
                        nodeCustomizedStyle = mapper.readValue(root.toString(), ImageNodeCustomizedStyle.class);
                        break;
                    case NodeType.NODE_ICON_LABEL:
                        nodeCustomizedStyle = mapper.readValue(root.toString(), IconLabelNodeCustomizedStyle.class);
                        break;
                    default:
                        nodeCustomizedStyle = mapper.readValue(root.toString(), RectangularNodeCustomizedStyle.class);
                        break;
                }
            }
        }
        return nodeCustomizedStyle;
    }

}
