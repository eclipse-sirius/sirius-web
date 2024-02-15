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
     * Setter for ChildrenExpression.
     *
     * @generated
     */
    public TreeDescriptionBuilder childrenExpression(java.lang.String value) {
        this.getTreeDescription().setChildrenExpression(value);
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
    /**
     * Setter for TreeItemBeginIconExpression.
     *
     * @generated
     */
    public TreeDescriptionBuilder treeItemBeginIconExpression(java.lang.String value) {
        this.getTreeDescription().setTreeItemBeginIconExpression(value);
        return this;
    }
    /**
     * Setter for TreeItemEndIconsExpression.
     *
     * @generated
     */
    public TreeDescriptionBuilder treeItemEndIconsExpression(java.lang.String value) {
        this.getTreeDescription().setTreeItemEndIconsExpression(value);
        return this;
    }
    /**
     * Setter for IsCheckableExpression.
     *
     * @generated
     */
    public TreeDescriptionBuilder isCheckableExpression(java.lang.String value) {
        this.getTreeDescription().setIsCheckableExpression(value);
        return this;
    }
    /**
     * Setter for CheckedValueExpression.
     *
     * @generated
     */
    public TreeDescriptionBuilder checkedValueExpression(java.lang.String value) {
        this.getTreeDescription().setCheckedValueExpression(value);
        return this;
    }
    /**
     * Setter for IsEnabledExpression.
     *
     * @generated
     */
    public TreeDescriptionBuilder isEnabledExpression(java.lang.String value) {
        this.getTreeDescription().setIsEnabledExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public TreeDescriptionBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getTreeDescription().getBody().add(value);
        }
        return this;
    }


}

