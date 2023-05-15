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
 * @generated
 */
@SuppressWarnings("checkstyle:JavadocType")
public class GroupDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.GroupDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.GroupDescription groupDescription = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createGroupDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.GroupDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.GroupDescription getGroupDescription() {
        return this.groupDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.GroupDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.GroupDescription build() {
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
    public GroupDescriptionBuilder displayMode(org.eclipse.sirius.components.view.GroupDisplayMode value) {
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
    public GroupDescriptionBuilder toolbarActions(org.eclipse.sirius.components.view.ButtonDescription ... values) {
        for (org.eclipse.sirius.components.view.ButtonDescription value : values) {
            this.getGroupDescription().getToolbarActions().add(value);
        }
        return this;
    }

    /**
     * Setter for Widgets.
     *
     * @generated
     */
    public GroupDescriptionBuilder widgets(org.eclipse.sirius.components.view.WidgetDescription ... values) {
        for (org.eclipse.sirius.components.view.WidgetDescription value : values) {
            this.getGroupDescription().getWidgets().add(value);
        }
        return this;
    }


}

