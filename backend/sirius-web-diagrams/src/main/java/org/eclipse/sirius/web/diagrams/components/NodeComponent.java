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
package org.eclipse.sirius.web.diagrams.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.components.Fragment;
import org.eclipse.sirius.web.components.FragmentProps;
import org.eclipse.sirius.web.components.IComponent;
import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.description.LabelDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.web.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * The component used to render a node.
 *
 * @author sbegaudeau
 */
public class NodeComponent implements IComponent {

    private NodeComponentProps props;

    public NodeComponent(NodeComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        NodeDescription nodeDescription = this.props.getNodeDescription();
        List<Element> children = new ArrayList<>();
        if (nodeDescription.isSynchronised()) {
            // @formatter:off
            nodeDescription.getSemanticElementsProvider().apply(variableManager)
                .forEach(semanticElement -> children.add(this.renderNodeElementProps(semanticElement, Optional.empty())));
            // @formatter:on
        } else {
            Optional<Map> optionalTargetSemanticObjects = variableManager.get(DiagramDescription.TARGET_SEMANTIC_OBJECTS, Map.class);
            if (optionalTargetSemanticObjects.isPresent()) {
                Map<String, Object> targetSemanticObjects = optionalTargetSemanticObjects.get();
               // @formatter:off
               this.props.getPrevNodes()
                   .forEach(prevNode -> {
                       String targetObjectId = prevNode.getTargetObjectId();
                       if (targetSemanticObjects.containsKey(targetObjectId)) {
                           children.add(this.renderNodeElementProps(targetSemanticObjects.get(targetObjectId), Optional.of(prevNode)));
                       }
                   });
               // @formatter:on
            }
        }
        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }

    private Element renderNodeElementProps(Object semanticElement, Optional<Node> prevNode) {
        VariableManager variableManager = this.props.getVariableManager();
        NodeDescription nodeDescription = this.props.getNodeDescription();
        DiagramRenderingCache cache = this.props.getCache();

        VariableManager nodeVariableManager = variableManager.createChild();
        nodeVariableManager.put(VariableManager.SELF, semanticElement);

        String nodeId = nodeDescription.getIdProvider().apply(nodeVariableManager);
        String type = nodeDescription.getTypeProvider().apply(nodeVariableManager);
        String targetObjectId = nodeDescription.getTargetObjectIdProvider().apply(nodeVariableManager);
        String targetObjectKind = nodeDescription.getTargetObjectKindProvider().apply(nodeVariableManager);
        String targetObjectLabel = nodeDescription.getTargetObjectLabelProvider().apply(nodeVariableManager);

        LabelDescription labelDescription = nodeDescription.getLabelDescription();
        nodeVariableManager.put(LabelDescription.OWNER_ID, nodeId);
        LabelComponentProps labelComponentProps = new LabelComponentProps(nodeVariableManager, labelDescription);
        Element labelElement = new Element(LabelComponent.class, labelComponentProps);

        INodeStyle style = nodeDescription.getStyleProvider().apply(nodeVariableManager);

        // @formatter:off
        var borderNodes = nodeDescription.getBorderNodeDescriptions().stream()
                .map(borderNodeDescription -> {
                    List<Node> prevBorderNodes = List.of();
                    if (!nodeDescription.isSynchronised()) {
                         prevBorderNodes = prevNode.map(Node::getBorderNodes).orElse(List.of())
                                .stream()
                                .filter(node -> Objects.equals(node.getDescriptionId(), borderNodeDescription.getId()))
                                .collect(Collectors.toList());
                    }
                    var nodeComponentProps = new NodeComponentProps(nodeVariableManager, borderNodeDescription, true, cache, prevBorderNodes);
                    return new Element(NodeComponent.class, nodeComponentProps);
                })
                .collect(Collectors.toList());

        var childNodes = nodeDescription.getChildNodeDescriptions().stream()
                .map(childNodeDescription -> {
                    List<Node> prevChildNodes = List.of();
                    if (!nodeDescription.isSynchronised()) {
                        prevChildNodes = prevNode.map(Node::getChildNodes).orElse(List.of())
                                .stream()
                                .filter(node -> Objects.equals(node.getDescriptionId(), childNodeDescription.getId()))
                                .collect(Collectors.toList());
                    }
                    var nodeComponentProps = new NodeComponentProps(nodeVariableManager, childNodeDescription, false, cache, prevChildNodes);
                    return new Element(NodeComponent.class, nodeComponentProps);
                })
                .collect(Collectors.toList());
        // @formatter:on

        List<Element> nodeChildren = new ArrayList<>();
        nodeChildren.add(labelElement);
        nodeChildren.addAll(borderNodes);
        nodeChildren.addAll(childNodes);

        // @formatter:off
        NodeElementProps nodeElementProps = NodeElementProps.newNodeElementProps(nodeId)
                .type(type)
                .targetObjectId(targetObjectId)
                .targetObjectKind(targetObjectKind)
                .targetObjectLabel(targetObjectLabel)
                .descriptionId(nodeDescription.getId())
                .borderNode(this.props.isBorderNode())
                .style(style)
                .position(Position.UNDEFINED)
                .size(Size.UNDEFINED)
                .children(nodeChildren)
                .build();
        // @formatter:on
        Element element = new Element(NodeElementProps.TYPE, nodeElementProps);
        cache.put(nodeDescription.getId(), element);
        cache.put(semanticElement, element);
        return element;
    }
}
