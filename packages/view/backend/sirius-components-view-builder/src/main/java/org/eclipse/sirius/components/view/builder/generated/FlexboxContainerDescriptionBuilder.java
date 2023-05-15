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
 * Builder for FlexboxContainerDescriptionBuilder.
 * @generated
 */
@SuppressWarnings("checkstyle:JavadocType")
public class FlexboxContainerDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.FlexboxContainerDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.FlexboxContainerDescription flexboxContainerDescription = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createFlexboxContainerDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.FlexboxContainerDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.FlexboxContainerDescription getFlexboxContainerDescription() {
        return this.flexboxContainerDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.FlexboxContainerDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.FlexboxContainerDescription build() {
        return this.getFlexboxContainerDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public FlexboxContainerDescriptionBuilder name(java.lang.String value) {
        this.getFlexboxContainerDescription().setName(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public FlexboxContainerDescriptionBuilder labelExpression(java.lang.String value) {
        this.getFlexboxContainerDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for Children.
     *
     * @generated
     */
    public FlexboxContainerDescriptionBuilder children(org.eclipse.sirius.components.view.WidgetDescription ... values) {
        for (org.eclipse.sirius.components.view.WidgetDescription value : values) {
            this.getFlexboxContainerDescription().getChildren().add(value);
        }
        return this;
    }

    /**
     * Setter for FlexDirection.
     *
     * @generated
     */
    public FlexboxContainerDescriptionBuilder flexDirection(org.eclipse.sirius.components.view.FlexDirection value) {
        this.getFlexboxContainerDescription().setFlexDirection(value);
        return this;
    }

}

