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
package org.eclipse.sirius.components.charts.hierarchy.elements;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Properties of the node element.
 *
 * @author sbegaudeau
 */
public class HierarchyNodeElementProps implements IProps {

    public static final String TYPE = "HierarchyNode";

    private final String id;

    private final String targetObjectId;

    private final String label;

    private final List<Element> children;

    public HierarchyNodeElementProps(String id, String targetObjectId, String label, List<Element> children) {
        this.id = Objects.requireNonNull(id);
        this.targetObjectId = Objects.requireNonNull(targetObjectId);
        this.label = Objects.requireNonNull(label);
        this.children = Objects.requireNonNull(children);
    }

    public String getId() {
        return this.id;
    }

    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    public String getLabel() {
        return this.label;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

}
