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

import org.eclipse.sirius.components.view.widget.reference.ConditionalReferenceWidgetDescriptionStyle;
import org.eclipse.sirius.components.view.widget.reference.ReferenceFactory;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescriptionStyle;

/**
 * Builder for ReferenceWidgetDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ReferenceWidgetDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription referenceWidgetDescription = org.eclipse.sirius.components.view.widget.reference.ReferenceFactory.eINSTANCE.createReferenceWidgetDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription getReferenceWidgetDescription() {
        return this.referenceWidgetDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription build() {
        return this.getReferenceWidgetDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public ReferenceWidgetDescriptionBuilder name(java.lang.String value) {
        this.getReferenceWidgetDescription().setName(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public ReferenceWidgetDescriptionBuilder labelExpression(java.lang.String value) {
        this.getReferenceWidgetDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for HelpExpression.
     *
     * @generated
     */
    public ReferenceWidgetDescriptionBuilder helpExpression(java.lang.String value) {
        this.getReferenceWidgetDescription().setHelpExpression(value);
        return this;
    }
    /**
     * Setter for IsEnabledExpression.
     *
     * @generated
     */
    public ReferenceWidgetDescriptionBuilder isEnabledExpression(java.lang.String value) {
        this.getReferenceWidgetDescription().setIsEnabledExpression(value);
        return this;
    }
    /**
     * Setter for ReferenceOwnerExpression.
     *
     * @generated
     */
    public ReferenceWidgetDescriptionBuilder referenceOwnerExpression(java.lang.String value) {
        this.getReferenceWidgetDescription().setReferenceOwnerExpression(value);
        return this;
    }
    /**
     * Setter for ReferenceNameExpression.
     *
     * @generated
     */
    public ReferenceWidgetDescriptionBuilder referenceNameExpression(java.lang.String value) {
        this.getReferenceWidgetDescription().setReferenceNameExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public ReferenceWidgetDescriptionBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getReferenceWidgetDescription().getBody().add(value);
        }
        return this;
    }

    /**
     * Setter for Style.
     *
     * @generated
     */
    public ReferenceWidgetDescriptionBuilder style(org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescriptionStyle value) {
        this.getReferenceWidgetDescription().setStyle(value);
        return this;
    }

    /**
     * Setter for ConditionalStyles.
     *
     * @generated
     */
    public ReferenceWidgetDescriptionBuilder conditionalStyles(org.eclipse.sirius.components.view.widget.reference.ConditionalReferenceWidgetDescriptionStyle ... values) {
        for (org.eclipse.sirius.components.view.widget.reference.ConditionalReferenceWidgetDescriptionStyle value : values) {
            this.getReferenceWidgetDescription().getConditionalStyles().add(value);
        }
        return this;
    }


}

