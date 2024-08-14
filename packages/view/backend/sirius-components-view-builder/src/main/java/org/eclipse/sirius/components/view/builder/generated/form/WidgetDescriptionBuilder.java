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
package org.eclipse.sirius.components.view.builder.generated.form;

/**
 * Builder for org.eclipse.sirius.components.view.form.WidgetDescription.
 *
 * @author BuilderGenerator
 * @generated
 */
public abstract class WidgetDescriptionBuilder {

    /**
     * Builder for org.eclipse.sirius.components.view.form.WidgetDescription.
     * @generated
     */
    protected abstract org.eclipse.sirius.components.view.form.WidgetDescription getWidgetDescription();

    /**
     * Setter for Name.
     *
     * @generated
     */
    public WidgetDescriptionBuilder name(java.lang.String value) {
        this.getWidgetDescription().setName(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public WidgetDescriptionBuilder labelExpression(java.lang.String value) {
        this.getWidgetDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for HelpExpression.
     *
     * @generated
     */
    public WidgetDescriptionBuilder helpExpression(java.lang.String value) {
        this.getWidgetDescription().setHelpExpression(value);
        return this;
    }

}

