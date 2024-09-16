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
 * Builder for org.eclipse.sirius.components.view.tree.TreeItemLabelDescription.
 *
 * @author BuilderGenerator
 * @generated
 */
public class TreeItemLabelDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.tree.TreeItemLabelDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.tree.TreeItemLabelDescription treeItemLabelDescription = org.eclipse.sirius.components.view.tree.TreeFactory.eINSTANCE.createTreeItemLabelDescription();

    /**
     * Builder for org.eclipse.sirius.components.view.tree.TreeItemLabelDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.tree.TreeItemLabelDescription getTreeItemLabelDescription() {
        return this.treeItemLabelDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.tree.TreeItemLabelDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.tree.TreeItemLabelDescription build() {
        return this.getTreeItemLabelDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public TreeItemLabelDescriptionBuilder name(java.lang.String value) {
        this.getTreeItemLabelDescription().setName(value);
        return this;
    }

    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public TreeItemLabelDescriptionBuilder preconditionExpression(java.lang.String value) {
        this.getTreeItemLabelDescription().setPreconditionExpression(value);
        return this;
    }

    /**
     * Setter for Children.
     *
     * @generated
     */
    public TreeItemLabelDescriptionBuilder children(org.eclipse.sirius.components.view.tree.TreeItemLabelElementDescription ... values) {
        for (org.eclipse.sirius.components.view.tree.TreeItemLabelElementDescription value : values) {
            this.getTreeItemLabelDescription().getChildren().add(value);
        }
        return this;
    }


}

