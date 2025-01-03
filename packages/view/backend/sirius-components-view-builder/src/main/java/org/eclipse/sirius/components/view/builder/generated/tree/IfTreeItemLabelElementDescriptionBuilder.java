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
 * Builder for IfTreeItemLabelElementDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class IfTreeItemLabelElementDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.tree.IfTreeItemLabelElementDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.tree.IfTreeItemLabelElementDescription ifTreeItemLabelElementDescription = org.eclipse.sirius.components.view.tree.TreeFactory.eINSTANCE.createIfTreeItemLabelElementDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.tree.IfTreeItemLabelElementDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.tree.IfTreeItemLabelElementDescription getIfTreeItemLabelElementDescription() {
        return this.ifTreeItemLabelElementDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.tree.IfTreeItemLabelElementDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.tree.IfTreeItemLabelElementDescription build() {
        return this.getIfTreeItemLabelElementDescription();
    }

    /**
     * Setter for PredicateExpression.
     *
     * @generated
     */
    public IfTreeItemLabelElementDescriptionBuilder predicateExpression(java.lang.String value) {
        this.getIfTreeItemLabelElementDescription().setPredicateExpression(value);
        return this;
    }
    /**
     * Setter for Children.
     *
     * @generated
     */
    public IfTreeItemLabelElementDescriptionBuilder children(org.eclipse.sirius.components.view.tree.TreeItemLabelElementDescription ... values) {
        for (org.eclipse.sirius.components.view.tree.TreeItemLabelElementDescription value : values) {
            this.getIfTreeItemLabelElementDescription().getChildren().add(value);
        }
        return this;
    }


}

