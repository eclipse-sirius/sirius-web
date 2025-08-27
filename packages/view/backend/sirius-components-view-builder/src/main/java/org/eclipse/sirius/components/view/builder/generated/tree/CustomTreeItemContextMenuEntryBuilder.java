/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
 * Builder for CustomTreeItemContextMenuEntryBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class CustomTreeItemContextMenuEntryBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.tree.CustomTreeItemContextMenuEntry.
     * @generated
     */
    private org.eclipse.sirius.components.view.tree.CustomTreeItemContextMenuEntry customTreeItemContextMenuEntry = org.eclipse.sirius.components.view.tree.TreeFactory.eINSTANCE.createCustomTreeItemContextMenuEntry();

    /**
     * Return instance org.eclipse.sirius.components.view.tree.CustomTreeItemContextMenuEntry.
     * @generated
     */
    protected org.eclipse.sirius.components.view.tree.CustomTreeItemContextMenuEntry getCustomTreeItemContextMenuEntry() {
        return this.customTreeItemContextMenuEntry;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.tree.CustomTreeItemContextMenuEntry.
     * @generated
     */
    public org.eclipse.sirius.components.view.tree.CustomTreeItemContextMenuEntry build() {
        return this.getCustomTreeItemContextMenuEntry();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public CustomTreeItemContextMenuEntryBuilder name(java.lang.String value) {
        this.getCustomTreeItemContextMenuEntry().setName(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public CustomTreeItemContextMenuEntryBuilder preconditionExpression(java.lang.String value) {
        this.getCustomTreeItemContextMenuEntry().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for ContributionId.
     *
     * @generated
     */
    public CustomTreeItemContextMenuEntryBuilder contributionId(java.lang.String value) {
        this.getCustomTreeItemContextMenuEntry().setContributionId(value);
        return this;
    }
    /**
     * Setter for WithImpactAnalysis.
     *
     * @generated
     */
    public CustomTreeItemContextMenuEntryBuilder withImpactAnalysis(java.lang.Boolean value) {
        this.getCustomTreeItemContextMenuEntry().setWithImpactAnalysis(value);
        return this;
    }

}

