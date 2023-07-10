/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
 * Builder for GroupDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class GroupDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.GroupDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.GroupDescription groupDescription = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createGroupDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.form.GroupDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.GroupDescription getGroupDescription() {
        return this.groupDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.GroupDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.GroupDescription build() {
        return this.getGroupDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public GroupDescriptionBuilder name(java.lang.String value) {
        this.getGroupDescription().setName(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public GroupDescriptionBuilder labelExpression(java.lang.String value) {
        this.getGroupDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for DisplayMode.
     *
     * @generated
     */
    public GroupDescriptionBuilder displayMode(org.eclipse.sirius.components.view.form.GroupDisplayMode value) {
        this.getGroupDescription().setDisplayMode(value);
        return this;
    }
    /**
     * Setter for SemanticCandidatesExpression.
     *
     * @generated
     */
    public GroupDescriptionBuilder semanticCandidatesExpression(java.lang.String value) {
        this.getGroupDescription().setSemanticCandidatesExpression(value);
        return this;
    }
    /**
     * Setter for ToolbarActions.
     *
     * @generated
     */
    public GroupDescriptionBuilder toolbarActions(org.eclipse.sirius.components.view.form.ButtonDescription ... values) {
        for (org.eclipse.sirius.components.view.form.ButtonDescription value : values) {
            this.getGroupDescription().getToolbarActions().add(value);
        }
        return this;
    }

    /**
     * Setter for Widgets.
     *
     * @generated
     */
    public GroupDescriptionBuilder widgets(org.eclipse.sirius.components.view.form.WidgetDescription ... values) {
        for (org.eclipse.sirius.components.view.form.WidgetDescription value : values) {
            this.getGroupDescription().getWidgets().add(value);
        }
        return this;
    }

    /**
     * Setter for BorderStyle.
     *
     * @generated
     */
    public GroupDescriptionBuilder borderStyle(org.eclipse.sirius.components.view.form.ContainerBorderStyle value) {
        this.getGroupDescription().setBorderStyle(value);
        return this;
    }

    /**
     * Setter for ConditionalBorderStyles.
     *
     * @generated
     */
    public GroupDescriptionBuilder conditionalBorderStyles(org.eclipse.sirius.components.view.form.ConditionalContainerBorderStyle ... values) {
        for (org.eclipse.sirius.components.view.form.ConditionalContainerBorderStyle value : values) {
            this.getGroupDescription().getConditionalBorderStyles().add(value);
        }
        return this;
    }


}

