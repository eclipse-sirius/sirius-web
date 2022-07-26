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
 * Properties of the hierarchy element.
 *
 * @author sbegaudeau
 */
public class HierarchyElementProps implements IProps {

    public static final String TYPE = "Hierarchy"; //$NON-NLS-1$

    private final String id;

    private final String descriptionId;

    private final String targetObjectId;

    private final String label;

    private final String kind;

    private final List<Element> children;

    public HierarchyElementProps(String id, String descriptionId, String targetObjectId, String label, String kind, List<Element> children) {
        this.id = Objects.requireNonNull(id);
        this.descriptionId = Objects.requireNonNull(descriptionId);
        this.targetObjectId = Objects.requireNonNull(targetObjectId);
        this.label = Objects.requireNonNull(label);
        this.kind = Objects.requireNonNull(kind);
        this.children = Objects.requireNonNull(children);
    }

    public String getId() {
        return this.id;
    }

    public String getDescriptionId() {
        return this.descriptionId;
    }

    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    public String getLabel() {
        return this.label;
    }

    public String getKind() {
        return this.kind;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

}
