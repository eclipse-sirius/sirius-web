/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.charts.hierarchy.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.charts.hierarchy.HierarchyNode;
import org.eclipse.sirius.components.charts.hierarchy.descriptions.HierarchyDescription;
import org.eclipse.sirius.components.charts.hierarchy.elements.HierarchyNodeElementProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Fragment;
import org.eclipse.sirius.components.representations.FragmentProps;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render nodes.
 *
 * @author sbegaudeau
 */
public class HierarchyNodeComponent implements IComponent {

    private final HierarchyNodeComponentProps props;

    public HierarchyNodeComponent(HierarchyNodeComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        HierarchyDescription hierarchyDescription = this.props.getHierarchyDescription();
        String parentElementId = this.props.getParentElementId();
        List<HierarchyNode> previousNodes = this.props.getPreviousNodes();

        Map<String, HierarchyNode> targetObjectId2Nodes = previousNodes.stream().collect(Collectors.toMap(HierarchyNode::getTargetObjectId, Function.identity()));

        List<Element> children = new ArrayList<>();

        List<Object> semanticElements = hierarchyDescription.getChildSemanticElementsProvider().apply(variableManager);
        for (Object semanticElement : semanticElements) {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VariableManager.SELF, semanticElement);

            String targetObjectId = hierarchyDescription.getTargetObjectIdProvider().apply(childVariableManager);
            Optional<HierarchyNode> optionalPreviousNode = Optional.ofNullable(targetObjectId2Nodes.get(targetObjectId));
            String label = hierarchyDescription.getLabelProvider().apply(childVariableManager);

            String id = optionalPreviousNode.map(HierarchyNode::getId).orElseGet(() -> {
                String rawIdentifier = parentElementId.toString() + targetObjectId;
                return UUID.nameUUIDFromBytes(rawIdentifier.getBytes()).toString();
            });

            List<HierarchyNode> childPreviousNodes = optionalPreviousNode.map(HierarchyNode::getChildren).orElse(List.of());
            HierarchyNodeComponentProps nodeComponentProps = new HierarchyNodeComponentProps(childVariableManager, hierarchyDescription, childPreviousNodes, id);
            HierarchyNodeElementProps nodeElementProps = new HierarchyNodeElementProps(id, targetObjectId, label, List.of(new Element(HierarchyNodeComponent.class, nodeComponentProps)));
            children.add(new Element(HierarchyNodeElementProps.TYPE, nodeElementProps));

        }
        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }

}
