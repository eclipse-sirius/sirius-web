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
 * Builder for TreeItemLabelFragmentDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class TreeItemLabelFragmentDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription treeItemLabelFragmentDescription = org.eclipse.sirius.components.view.tree.TreeFactory.eINSTANCE.createTreeItemLabelFragmentDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription getTreeItemLabelFragmentDescription() {
        return this.treeItemLabelFragmentDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription build() {
        return this.getTreeItemLabelFragmentDescription();
    }

    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public TreeItemLabelFragmentDescriptionBuilder labelExpression(java.lang.String value) {
        this.getTreeItemLabelFragmentDescription().setLabelExpression(value);
        return this;
    }

    /**
     * Setter for Style.
     *
     * @generated
     */
    public TreeItemLabelFragmentDescriptionBuilder style(org.eclipse.sirius.components.view.TextStyleDescription value) {
        this.getTreeItemLabelFragmentDescription().setStyle(value);
        return this;
    }



}

