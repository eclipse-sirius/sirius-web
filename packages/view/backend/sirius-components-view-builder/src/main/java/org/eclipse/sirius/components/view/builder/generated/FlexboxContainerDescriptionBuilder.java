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
 *
 * @author BuilderGenerator
 * @generated
 */
public class FlexboxContainerDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.FlexboxContainerDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.FlexboxContainerDescription flexboxContainerDescription = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createFlexboxContainerDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.form.FlexboxContainerDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.FlexboxContainerDescription getFlexboxContainerDescription() {
        return this.flexboxContainerDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.FlexboxContainerDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.FlexboxContainerDescription build() {
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
     * Setter for HelpExpression.
     *
     * @generated
     */
    public FlexboxContainerDescriptionBuilder helpExpression(java.lang.String value) {
        this.getFlexboxContainerDescription().setHelpExpression(value);
        return this;
    }
    /**
     * Setter for Children.
     *
     * @generated
     */
    public FlexboxContainerDescriptionBuilder children(org.eclipse.sirius.components.view.form.FormElementDescription ... values) {
        for (org.eclipse.sirius.components.view.form.FormElementDescription value : values) {
            this.getFlexboxContainerDescription().getChildren().add(value);
        }
        return this;
    }

    /**
     * Setter for FlexDirection.
     *
     * @generated
     */
    public FlexboxContainerDescriptionBuilder flexDirection(org.eclipse.sirius.components.view.form.FlexDirection value) {
        this.getFlexboxContainerDescription().setFlexDirection(value);
        return this;
    }
    /**
     * Setter for IsEnabledExpression.
     *
     * @generated
     */
    public FlexboxContainerDescriptionBuilder isEnabledExpression(java.lang.String value) {
        this.getFlexboxContainerDescription().setIsEnabledExpression(value);
        return this;
    }
    /**
     * Setter for BorderStyle.
     *
     * @generated
     */
    public FlexboxContainerDescriptionBuilder borderStyle(org.eclipse.sirius.components.view.form.ContainerBorderStyle value) {
        this.getFlexboxContainerDescription().setBorderStyle(value);
        return this;
    }
    /**
     * Setter for ConditionalBorderStyles.
     *
     * @generated
     */
    public FlexboxContainerDescriptionBuilder conditionalBorderStyles(org.eclipse.sirius.components.view.form.ConditionalContainerBorderStyle ... values) {
        for (org.eclipse.sirius.components.view.form.ConditionalContainerBorderStyle value : values) {
            this.getFlexboxContainerDescription().getConditionalBorderStyles().add(value);
        }
        return this;
    }


}

