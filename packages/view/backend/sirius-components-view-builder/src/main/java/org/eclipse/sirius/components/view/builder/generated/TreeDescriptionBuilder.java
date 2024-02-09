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
package org.eclipse.sirius.components.view.builder.generated;

/**
 * Builder for TreeDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class TreeDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.TreeDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.TreeDescription treeDescription = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createTreeDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.form.TreeDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.TreeDescription getTreeDescription() {
        return this.treeDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.TreeDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.TreeDescription build() {
        return this.getTreeDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public TreeDescriptionBuilder name(java.lang.String value) {
        this.getTreeDescription().setName(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public TreeDescriptionBuilder labelExpression(java.lang.String value) {
        this.getTreeDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for HelpExpression.
     *
     * @generated
     */
    public TreeDescriptionBuilder helpExpression(java.lang.String value) {
        this.getTreeDescription().setHelpExpression(value);
        return this;
    }
    /**
     * Setter for ChildExpression.
     *
     * @generated
     */
    public TreeDescriptionBuilder childExpression(java.lang.String value) {
        this.getTreeDescription().setChildExpression(value);
        return this;
    }
    /**
     * Setter for TreeItemLabelExpression.
     *
     * @generated
     */
    public TreeDescriptionBuilder treeItemLabelExpression(java.lang.String value) {
        this.getTreeDescription().setTreeItemLabelExpression(value);
        return this;
    }
    /**
     * Setter for IsTreeItemSelectableExpression.
     *
     * @generated
     */
    public TreeDescriptionBuilder isTreeItemSelectableExpression(java.lang.String value) {
        this.getTreeDescription().setIsTreeItemSelectableExpression(value);
        return this;
    }

}

