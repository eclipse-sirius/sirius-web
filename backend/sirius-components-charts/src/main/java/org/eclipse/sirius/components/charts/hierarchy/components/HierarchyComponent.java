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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.charts.hierarchy.Hierarchy;
import org.eclipse.sirius.components.charts.hierarchy.HierarchyNode;
import org.eclipse.sirius.components.charts.hierarchy.descriptions.HierarchyDescription;
import org.eclipse.sirius.components.charts.hierarchy.elements.HierarchyElementProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render the hierarchy representation.
 *
 * @author sbegaudeau
 */
public class HierarchyComponent implements IComponent {

    private final HierarchyComponentProps props;

    public HierarchyComponent(HierarchyComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        HierarchyDescription hierarchyDescription = this.props.getHierarchyDescription();
        Optional<Hierarchy> optionalPreviousHierarchy = this.props.getPreviousHierarchy();

        String id = optionalPreviousHierarchy.map(Hierarchy::getId).orElseGet(() -> UUID.randomUUID().toString());
        String targetObjectId = hierarchyDescription.getTargetObjectIdProvider().apply(variableManager);
        String label = variableManager.get(HierarchyDescription.LABEL, String.class).orElse(""); //$NON-NLS-1$
        String kind = hierarchyDescription.getKind();

        List<HierarchyNode> previousNodes = optionalPreviousHierarchy.map(Hierarchy::getChildren).orElse(List.of());

        HierarchyNodeComponentProps nodeComponentProps = new HierarchyNodeComponentProps(variableManager, hierarchyDescription, previousNodes, id);
        List<Element> children = List.of(new Element(HierarchyNodeComponent.class, nodeComponentProps));

        HierarchyElementProps hierarchyElementProps = new HierarchyElementProps(id, hierarchyDescription.getId(), targetObjectId, label, kind, children);
        return new Element(HierarchyElementProps.TYPE, hierarchyElementProps);
    }

}
