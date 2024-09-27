/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
 * Builder for SingleClickTreeItemContextMenuEntryBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class SingleClickTreeItemContextMenuEntryBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry.
     * @generated
     */
    private org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry singleClickTreeItemContextMenuEntry = org.eclipse.sirius.components.view.tree.TreeFactory.eINSTANCE.createSingleClickTreeItemContextMenuEntry();

    /**
     * Return instance org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry.
     * @generated
     */
    protected org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry getSingleClickTreeItemContextMenuEntry() {
        return this.singleClickTreeItemContextMenuEntry;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry.
     * @generated
     */
    public org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry build() {
        return this.getSingleClickTreeItemContextMenuEntry();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public SingleClickTreeItemContextMenuEntryBuilder name(java.lang.String value) {
        this.getSingleClickTreeItemContextMenuEntry().setName(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public SingleClickTreeItemContextMenuEntryBuilder labelExpression(java.lang.String value) {
        this.getSingleClickTreeItemContextMenuEntry().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for IconURLExpression.
     *
     * @generated
     */
    public SingleClickTreeItemContextMenuEntryBuilder iconURLExpression(java.lang.String value) {
        this.getSingleClickTreeItemContextMenuEntry().setIconURLExpression(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public SingleClickTreeItemContextMenuEntryBuilder preconditionExpression(java.lang.String value) {
        this.getSingleClickTreeItemContextMenuEntry().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public SingleClickTreeItemContextMenuEntryBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getSingleClickTreeItemContextMenuEntry().getBody().add(value);
        }
        return this;
    }


}

