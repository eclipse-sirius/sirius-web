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
 * Builder for org.eclipse.sirius.components.view.form.WidgetFlexboxLayout.
 *
 * @author BuilderGenerator
 * @generated
 */
public abstract class WidgetFlexboxLayoutBuilder {

    /**
     * Builder for org.eclipse.sirius.components.view.form.WidgetFlexboxLayout.
     * @generated
     */
    protected abstract org.eclipse.sirius.components.view.form.WidgetFlexboxLayout getWidgetFlexboxLayout();

    /**
     * Setter for FlexDirection.
     *
     * @generated
     */
    public WidgetFlexboxLayoutBuilder flexDirection(java.lang.String value) {
        this.getWidgetFlexboxLayout().setFlexDirection(value);
        return this;
    }
    /**
     * Setter for Gap.
     *
     * @generated
     */
    public WidgetFlexboxLayoutBuilder gap(java.lang.String value) {
        this.getWidgetFlexboxLayout().setGap(value);
        return this;
    }
    /**
     * Setter for LabelFlex.
     *
     * @generated
     */
    public WidgetFlexboxLayoutBuilder labelFlex(java.lang.String value) {
        this.getWidgetFlexboxLayout().setLabelFlex(value);
        return this;
    }
    /**
     * Setter for ValueFlex.
     *
     * @generated
     */
    public WidgetFlexboxLayoutBuilder valueFlex(java.lang.String value) {
        this.getWidgetFlexboxLayout().setValueFlex(value);
        return this;
    }

}

