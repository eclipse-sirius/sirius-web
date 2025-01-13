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
 * Builder for ForTreeItemLabelElementDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ForTreeItemLabelElementDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.tree.ForTreeItemLabelElementDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.tree.ForTreeItemLabelElementDescription forTreeItemLabelElementDescription = org.eclipse.sirius.components.view.tree.TreeFactory.eINSTANCE.createForTreeItemLabelElementDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.tree.ForTreeItemLabelElementDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.tree.ForTreeItemLabelElementDescription getForTreeItemLabelElementDescription() {
        return this.forTreeItemLabelElementDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.tree.ForTreeItemLabelElementDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.tree.ForTreeItemLabelElementDescription build() {
        return this.getForTreeItemLabelElementDescription();
    }

    /**
     * Setter for Iterator.
     *
     * @generated
     */
    public ForTreeItemLabelElementDescriptionBuilder iterator(java.lang.String value) {
        this.getForTreeItemLabelElementDescription().setIterator(value);
        return this;
    }
    /**
     * Setter for IterableExpression.
     *
     * @generated
     */
    public ForTreeItemLabelElementDescriptionBuilder iterableExpression(java.lang.String value) {
        this.getForTreeItemLabelElementDescription().setIterableExpression(value);
        return this;
    }
    /**
     * Setter for Children.
     *
     * @generated
     */
    public ForTreeItemLabelElementDescriptionBuilder children(org.eclipse.sirius.components.view.tree.TreeItemLabelElementDescription ... values) {
        for (org.eclipse.sirius.components.view.tree.TreeItemLabelElementDescription value : values) {
            this.getForTreeItemLabelElementDescription().getChildren().add(value);
        }
        return this;
    }


}

