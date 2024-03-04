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
 * Builder for SplitButtonDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class SplitButtonDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.form.SplitButtonDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.form.SplitButtonDescription splitButtonDescription = org.eclipse.sirius.components.view.form.FormFactory.eINSTANCE.createSplitButtonDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.form.SplitButtonDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.form.SplitButtonDescription getSplitButtonDescription() {
        return this.splitButtonDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.form.SplitButtonDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.form.SplitButtonDescription build() {
        return this.getSplitButtonDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public SplitButtonDescriptionBuilder name(java.lang.String value) {
        this.getSplitButtonDescription().setName(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public SplitButtonDescriptionBuilder labelExpression(java.lang.String value) {
        this.getSplitButtonDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for HelpExpression.
     *
     * @generated
     */
    public SplitButtonDescriptionBuilder helpExpression(java.lang.String value) {
        this.getSplitButtonDescription().setHelpExpression(value);
        return this;
    }
    /**
     * Setter for Actions.
     *
     * @generated
     */
    public SplitButtonDescriptionBuilder actions(org.eclipse.sirius.components.view.form.ButtonDescription ... values) {
        for (org.eclipse.sirius.components.view.form.ButtonDescription value : values) {
            this.getSplitButtonDescription().getActions().add(value);
        }
        return this;
    }

    /**
     * Setter for IsEnabledExpression.
     *
     * @generated
     */
    public SplitButtonDescriptionBuilder isEnabledExpression(java.lang.String value) {
        this.getSplitButtonDescription().setIsEnabledExpression(value);
        return this;
    }

}

