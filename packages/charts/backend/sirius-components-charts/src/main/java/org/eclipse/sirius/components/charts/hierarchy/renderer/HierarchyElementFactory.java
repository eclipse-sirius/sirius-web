/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
package org.eclipse.sirius.components.charts.hierarchy.renderer;

import java.util.List;

import org.eclipse.sirius.components.charts.hierarchy.Hierarchy;
import org.eclipse.sirius.components.charts.hierarchy.HierarchyNode;
import org.eclipse.sirius.components.charts.hierarchy.elements.HierarchyElementProps;
import org.eclipse.sirius.components.charts.hierarchy.elements.HierarchyNodeElementProps;
import org.eclipse.sirius.components.representations.IElementFactory;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Used to instantiate the elements of the hierarchy representation.
 *
 * @author sbegaudeau
 */
public class HierarchyElementFactory implements IElementFactory {

    @Override
    public Object instantiateElement(String type, IProps props, List<Object> children) {
        Object object = null;
        if (HierarchyElementProps.TYPE.equals(type) && props instanceof HierarchyElementProps hierarchyElementProps) {
            object = this.instantiateHierarchy(hierarchyElementProps, children);
        } else if (HierarchyNodeElementProps.TYPE.equals(type) && props instanceof HierarchyNodeElementProps hierarchyNodeElementProps) {
            object = this.instantiateNode(hierarchyNodeElementProps, children);
        }
        return object;
    }

    private Hierarchy instantiateHierarchy(HierarchyElementProps props, List<Object> children) {
        List<HierarchyNode> nodes = children.stream()
                .filter(HierarchyNode.class::isInstance)
                .map(HierarchyNode.class::cast)
                .toList();

        return new Hierarchy(props.getId(), props.getDescriptionId(), props.getTargetObjectId(), props.getKind(), nodes);
    }

    private HierarchyNode instantiateNode(HierarchyNodeElementProps props, List<Object> children) {
        List<HierarchyNode> nodes = children.stream()
                .filter(HierarchyNode.class::isInstance)
                .map(HierarchyNode.class::cast)
                .toList();

        return new HierarchyNode(props.getId(), props.getTargetObjectId(), props.getLabel(), nodes);
    }

}
