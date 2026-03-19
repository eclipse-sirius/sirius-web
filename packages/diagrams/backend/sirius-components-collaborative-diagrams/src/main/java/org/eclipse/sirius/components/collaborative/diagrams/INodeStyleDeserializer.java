/*******************************************************************************
 * Copyright (c) 2019, 2026 Obeo.
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
import java.util.List;

import org.eclipse.sirius.components.collaborative.diagrams.api.ICustomNodeStyleDeserializer;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.IconLabelNodeStyle;
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.deser.std.StdDeserializer;
import tools.jackson.databind.node.ObjectNode;

/**
 * Custom deserializer for node style since Jackson need to know how to find the concrete class matching the JSON data.
 *
 * @author sbegaudeau
 */
public class INodeStyleDeserializer extends StdDeserializer<INodeStyle> implements Serializable {

    private final List<ICustomNodeStyleDeserializer> customNodeStyleDeserializers;

    public INodeStyleDeserializer(List<ICustomNodeStyleDeserializer> customNodeStyleDeserializers) {
        this(INodeStyle.class, customNodeStyleDeserializers);
    }

    public INodeStyleDeserializer(Class<?> valueClass, List<ICustomNodeStyleDeserializer> customNodeStyleDeserializers) {
        super(valueClass);
        this.customNodeStyleDeserializers = customNodeStyleDeserializers;
    }

    @Override
    public INodeStyle deserialize(JsonParser jsonParser, DeserializationContext context) {
        INodeStyle nodeStyle = null;
        var parentNodeStyle = jsonParser.streamReadContext().getParent();
        var parent = parentNodeStyle.currentValue();
        JsonNode rootNode = context.readTree(jsonParser);
        ObjectNode root = (ObjectNode) rootNode;

        if (parent instanceof Node parentNode) {
            nodeStyle = switch (parentNode.getType()) {
                case NodeType.NODE_RECTANGLE -> context.readTreeAsValue(root, RectangularNodeStyle.class);
                case NodeType.NODE_IMAGE -> context.readTreeAsValue(root, ImageNodeStyle.class);
                case NodeType.NODE_ICON_LABEL -> context.readTreeAsValue(root, IconLabelNodeStyle.class);
                default -> {
                    var customDeserialize = this.customNodeStyleDeserializers.stream()
                            .filter(iCustomNodeStyleDeserializer -> iCustomNodeStyleDeserializer.canHandle(parentNode.getType())).findFirst();
                    if (customDeserialize.isPresent()) {
                        yield customDeserialize.get().handle(root, jsonParser,  context);
                    } else {
                        yield context.readTreeAsValue(root, RectangularNodeStyle.class);
                    }
                }
            };
        }

        return nodeStyle;
    }
}
