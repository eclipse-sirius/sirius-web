/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.List;

import org.eclipse.sirius.components.collaborative.diagrams.api.ICustomNodeStyleDeserializer;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.IconLabelNodeStyle;
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;

/**
 * Custom deserializer for node style since Jackson need to know how to find the concrete class matching the JSON data.
 *
 * @author sbegaudeau
 */
public class INodeStyleDeserializer extends StdDeserializer<INodeStyle> {

    private static final long serialVersionUID = 5722074461929397488L;
    private final List<ICustomNodeStyleDeserializer> customNodeStyleDeserializers;

    public INodeStyleDeserializer(List<ICustomNodeStyleDeserializer> customNodeStyleDeserializers) {
        this(null, customNodeStyleDeserializers);
    }

    public INodeStyleDeserializer(Class<?> valueClass, List<ICustomNodeStyleDeserializer> customNodeStyleDeserializers) {
        super(valueClass);
        this.customNodeStyleDeserializers = customNodeStyleDeserializers;
    }

    @Override
    public INodeStyle deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        INodeStyle nodeStyle = null;
        ObjectCodec objectCodec = jsonParser.getCodec();
        if (objectCodec instanceof ObjectMapper mapper) {
            ObjectNode root = mapper.readTree(jsonParser);
            Object parent = jsonParser.getParsingContext().getCurrentValue();
            if (parent instanceof Node parentNode) {
                nodeStyle = switch (parentNode.getType()) {
                    case NodeType.NODE_RECTANGLE -> mapper.readValue(root.toString(), RectangularNodeStyle.class);
                    case NodeType.NODE_IMAGE -> mapper.readValue(root.toString(), ImageNodeStyle.class);
                    case NodeType.NODE_ICON_LABEL -> mapper.readValue(root.toString(), IconLabelNodeStyle.class);
                    default -> {
                        var customDeserialize = this.customNodeStyleDeserializers.stream()
                                .filter(iCustomNodeStyleDeserializer -> iCustomNodeStyleDeserializer.canHandle(parentNode.getType())).findFirst();
                        if (customDeserialize.isPresent()) {
                            yield customDeserialize.get().handle(mapper, root.toString());
                        } else {
                            yield mapper.readValue(root.toString(), RectangularNodeStyle.class);
                        }
                    }
                };
            }
        }
        return nodeStyle;
    }
}
