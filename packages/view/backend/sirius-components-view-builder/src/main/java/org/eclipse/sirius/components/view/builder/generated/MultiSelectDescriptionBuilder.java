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
 * Builder for MultiSelectDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class MultiSelectDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.MultiSelectDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.MultiSelectDescription multiSelectDescription = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createMultiSelectDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.MultiSelectDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.MultiSelectDescription getMultiSelectDescription() {
        return this.multiSelectDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.MultiSelectDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.MultiSelectDescription build() {
        return this.getMultiSelectDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public MultiSelectDescriptionBuilder name(java.lang.String value) {
        this.getMultiSelectDescription().setName(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public MultiSelectDescriptionBuilder labelExpression(java.lang.String value) {
        this.getMultiSelectDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for ValueExpression.
     *
     * @generated
     */
    public MultiSelectDescriptionBuilder valueExpression(java.lang.String value) {
        this.getMultiSelectDescription().setValueExpression(value);
        return this;
    }
    /**
     * Setter for CandidatesExpression.
     *
     * @generated
     */
    public MultiSelectDescriptionBuilder candidatesExpression(java.lang.String value) {
        this.getMultiSelectDescription().setCandidatesExpression(value);
        return this;
    }
    /**
     * Setter for CandidateLabelExpression.
     *
     * @generated
     */
    public MultiSelectDescriptionBuilder candidateLabelExpression(java.lang.String value) {
        this.getMultiSelectDescription().setCandidateLabelExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public MultiSelectDescriptionBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getMultiSelectDescription().getBody().add(value);
        }
        return this;
    }

    /**
     * Setter for Style.
     *
     * @generated
     */
    public MultiSelectDescriptionBuilder style(org.eclipse.sirius.components.view.MultiSelectDescriptionStyle value) {
        this.getMultiSelectDescription().setStyle(value);
        return this;
    }
    /**
     * Setter for ConditionalStyles.
     *
     * @generated
     */
    public MultiSelectDescriptionBuilder conditionalStyles(org.eclipse.sirius.components.view.ConditionalMultiSelectDescriptionStyle ... values) {
        for (org.eclipse.sirius.components.view.ConditionalMultiSelectDescriptionStyle value : values) {
            this.getMultiSelectDescription().getConditionalStyles().add(value);
        }
        return this;
    }


}

