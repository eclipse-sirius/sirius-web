/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.selection.renderer;

import java.util.Objects;

/**
 * Internal class to compute the tree representation.
 *
 * @author fbarbin
 */
public class SelectionNode {

    private Object object;

    private boolean isSelectable;

    private String parentId;

    public SelectionNode(Object object, boolean isSelectable) {
        this.object = Objects.requireNonNull(object);
        this.isSelectable = isSelectable;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Object getObject() {
        return this.object;
    }

    public String getParentId() {
        return this.parentId;
    }

    public boolean isSelectable() {
        return this.isSelectable;
    }

}
