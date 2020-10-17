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
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.components.Fragment;
import org.eclipse.sirius.web.components.FragmentProps;
import org.eclipse.sirius.web.components.IComponent;
import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
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
        boolean isBorderNode = this.props.isBorderNode();
        DiagramRenderingCache cache = this.props.getCache();

        List<Element> children = new ArrayList<>();
        List<Object> semanticElements = nodeDescription.getSemanticElementsProvider().apply(variableManager);
        for (Object semanticElement : semanticElements) {
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
                        var nodeComponentProps = new NodeComponentProps(nodeVariableManager, borderNodeDescription, true, cache);
                        return new Element(NodeComponent.class, nodeComponentProps);
                    })
                    .collect(Collectors.toList());

            var childNodes = nodeDescription.getChildNodeDescriptions().stream()
                    .map(childNodeDescription -> {
                        var nodeComponentProps = new NodeComponentProps(nodeVariableManager, childNodeDescription, false, cache);
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
                    .borderNode(isBorderNode)
                    .style(style)
                    .position(Position.UNDEFINED)
                    .size(Size.UNDEFINED)
                    .children(nodeChildren)
                    .build();
            // @formatter:on
            Element nodeElement = new Element(NodeElementProps.TYPE, nodeElementProps);
            children.add(nodeElement);

            cache.put(nodeDescription.getId(), nodeElement);
            cache.put(semanticElement, nodeElement);
        }

        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }

}
