/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
package org.eclipse.sirius.components.view.builder.generated.tree;

/**
 * Builder for org.eclipse.sirius.components.view.tree.TreeItemContextMenuEntry.
 *
 * @author BuilderGenerator
 * @generated
 */
public abstract class TreeItemContextMenuEntryBuilder {

    /**
     * Builder for org.eclipse.sirius.components.view.tree.TreeItemContextMenuEntry.
     * @generated
     */
    protected abstract org.eclipse.sirius.components.view.tree.TreeItemContextMenuEntry getTreeItemContextMenuEntry();

    /**
     * Setter for Name.
     *
     * @generated
     */
    public TreeItemContextMenuEntryBuilder name(java.lang.String value) {
        this.getTreeItemContextMenuEntry().setName(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public TreeItemContextMenuEntryBuilder preconditionExpression(java.lang.String value) {
        this.getTreeItemContextMenuEntry().setPreconditionExpression(value);
        return this;
    }

    /**
     * Setter for KeyBindings.
     *
     * @generated
     */
    public TreeItemContextMenuEntryBuilder keyBindings(org.eclipse.sirius.components.view.KeyBinding ... values) {
        for (org.eclipse.sirius.components.view.KeyBinding value : values) {
            this.getTreeItemContextMenuEntry().getKeyBindings().add(value);
        }
        return this;
    }

}

