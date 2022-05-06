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
package org.eclipse.sirius.components.charts.hierarchy;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

/**
 * A node of the hierarchy representation.
 *
 * @author sbegaudeau
 */
public class HierarchyNode {
    private String id;

    private String targetObjectId;

    private String label;

    private List<HierarchyNode> children;

    public HierarchyNode() {
        // Used by Jackson
    }

    public HierarchyNode(String id, String targetObjectId, String label, List<HierarchyNode> children) {
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

    public List<HierarchyNode> getChildren() {
        return this.children;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, targetObjectId: {2}, label: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.targetObjectId, this.label);
    }
}
