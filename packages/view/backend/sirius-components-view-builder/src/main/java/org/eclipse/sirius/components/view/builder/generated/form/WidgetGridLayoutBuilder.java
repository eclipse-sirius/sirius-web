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
 * Builder for org.eclipse.sirius.components.view.form.WidgetGridLayout.
 *
 * @author BuilderGenerator
 * @generated
 */
public abstract class WidgetGridLayoutBuilder {

    /**
     * Builder for org.eclipse.sirius.components.view.form.WidgetGridLayout.
     * @generated
     */
    protected abstract org.eclipse.sirius.components.view.form.WidgetGridLayout getWidgetGridLayout();

    /**
     * Setter for GridTemplateColumns.
     *
     * @generated
     */
    public WidgetGridLayoutBuilder gridTemplateColumns(java.lang.String value) {
        this.getWidgetGridLayout().setGridTemplateColumns(value);
        return this;
    }

    /**
     * Setter for GridTemplateRows.
     *
     * @generated
     */
    public WidgetGridLayoutBuilder gridTemplateRows(java.lang.String value) {
        this.getWidgetGridLayout().setGridTemplateRows(value);
        return this;
    }
    /**
     * Setter for LabelGridRow.
     *
     * @generated
     */
    public WidgetGridLayoutBuilder labelGridRow(java.lang.String value) {
        this.getWidgetGridLayout().setLabelGridRow(value);
        return this;
    }
    /**
     * Setter for LabelGridColumn.
     *
     * @generated
     */
    public WidgetGridLayoutBuilder labelGridColumn(java.lang.String value) {
        this.getWidgetGridLayout().setLabelGridColumn(value);
        return this;
    }
    /**
     * Setter for WidgetGridRow.
     *
     * @generated
     */
    public WidgetGridLayoutBuilder widgetGridRow(java.lang.String value) {
        this.getWidgetGridLayout().setWidgetGridRow(value);
        return this;
    }
    /**
     * Setter for WidgetGridColumn.
     *
     * @generated
     */
    public WidgetGridLayoutBuilder widgetGridColumn(java.lang.String value) {
        this.getWidgetGridLayout().setWidgetGridColumn(value);
        return this;
    }

    /**
     * Setter for Gap.
     *
     * @generated
     */
    public WidgetGridLayoutBuilder gap(java.lang.String value) {
        this.getWidgetGridLayout().setGap(value);
        return this;
    }

}

