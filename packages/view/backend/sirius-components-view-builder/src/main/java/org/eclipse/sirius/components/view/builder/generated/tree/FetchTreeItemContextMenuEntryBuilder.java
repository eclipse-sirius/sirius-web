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
 * Builder for FetchTreeItemContextMenuEntryBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class FetchTreeItemContextMenuEntryBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry.
     * @generated
     */
    private org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry fetchTreeItemContextMenuEntry = org.eclipse.sirius.components.view.tree.TreeFactory.eINSTANCE.createFetchTreeItemContextMenuEntry();

    /**
     * Return instance org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry.
     * @generated
     */
    protected org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry getFetchTreeItemContextMenuEntry() {
        return this.fetchTreeItemContextMenuEntry;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry.
     * @generated
     */
    public org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry build() {
        return this.getFetchTreeItemContextMenuEntry();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public FetchTreeItemContextMenuEntryBuilder name(java.lang.String value) {
        this.getFetchTreeItemContextMenuEntry().setName(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public FetchTreeItemContextMenuEntryBuilder preconditionExpression(java.lang.String value) {
        this.getFetchTreeItemContextMenuEntry().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for KeyBindings.
     *
     * @generated
     */
    public FetchTreeItemContextMenuEntryBuilder keyBindings(org.eclipse.sirius.components.view.KeyBinding ... values) {
        for (org.eclipse.sirius.components.view.KeyBinding value : values) {
            this.getFetchTreeItemContextMenuEntry().getKeyBindings().add(value);
        }
        return this;
    }

    /**
     * Setter for UrlExression.
     *
     * @generated
     */
    public FetchTreeItemContextMenuEntryBuilder urlExression(java.lang.String value) {
        this.getFetchTreeItemContextMenuEntry().setUrlExression(value);
        return this;
    }
    /**
     * Setter for Kind.
     *
     * @generated
     */
    public FetchTreeItemContextMenuEntryBuilder kind(org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntryKind value) {
        this.getFetchTreeItemContextMenuEntry().setKind(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public FetchTreeItemContextMenuEntryBuilder labelExpression(java.lang.String value) {
        this.getFetchTreeItemContextMenuEntry().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for IconURLExpression.
     *
     * @generated
     */
    public FetchTreeItemContextMenuEntryBuilder iconURLExpression(java.lang.String value) {
        this.getFetchTreeItemContextMenuEntry().setIconURLExpression(value);
        return this;
    }

}

