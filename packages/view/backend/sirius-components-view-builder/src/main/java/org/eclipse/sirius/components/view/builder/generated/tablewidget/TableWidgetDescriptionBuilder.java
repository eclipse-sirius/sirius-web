/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
package org.eclipse.sirius.components.view.builder.generated.tablewidget;

/**
 * Builder for TableWidgetDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class TableWidgetDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.widget.tablewidget.TableWidgetDescription.
     *
     * @generated
     */
    private org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription tableWidgetDescription =
            org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetFactory.eINSTANCE.createTableWidgetDescription();

    /**
     * Return instance org.eclipse.sirius.components.widget.tablewidget.TableWidgetDescription.
     *
     * @generated
     */
    protected org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription getTableWidgetDescription() {
        return this.tableWidgetDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.widget.tablewidget.TableWidgetDescription.
     *
     * @generated
     */
    public org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetDescription build() {
        return this.getTableWidgetDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public TableWidgetDescriptionBuilder name(java.lang.String value) {
        this.getTableWidgetDescription().setName(value);
        return this;
    }

    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public TableWidgetDescriptionBuilder labelExpression(java.lang.String value) {
        this.getTableWidgetDescription().setLabelExpression(value);
        return this;
    }

    /**
     * Setter for HelpExpression.
     *
     * @generated
     */
    public TableWidgetDescriptionBuilder helpExpression(java.lang.String value) {
        this.getTableWidgetDescription().setHelpExpression(value);
        return this;
    }

    /**
     * Setter for DiagnosticsExpression.
     *
     * @generated
     */
    public TableWidgetDescriptionBuilder diagnosticsExpression(java.lang.String value) {
        this.getTableWidgetDescription().setDiagnosticsExpression(value);
        return this;
    }

    /**
     * Setter for ColumnDescriptions.
     *
     * @generated
     */
    public TableWidgetDescriptionBuilder columnDescriptions(org.eclipse.sirius.components.view.table.ColumnDescription ... values) {
        for (org.eclipse.sirius.components.view.table.ColumnDescription value : values) {
            this.getTableWidgetDescription().getColumnDescriptions().add(value);
        }
        return this;
    }

    /**
     * Setter for RowDescription.
     *
     * @generated
     */
    public TableWidgetDescriptionBuilder rowDescription(org.eclipse.sirius.components.view.table.RowDescription value) {
        this.getTableWidgetDescription().setRowDescription(value);
        return this;
    }

    /**
     * Setter for CellDescriptions.
     *
     * @generated
     */
    public TableWidgetDescriptionBuilder cellDescriptions(org.eclipse.sirius.components.view.table.CellDescription ... values) {
        for (org.eclipse.sirius.components.view.table.CellDescription value : values) {
            this.getTableWidgetDescription().getCellDescriptions().add(value);
        }
        return this;
    }

    /**
     * Setter for UseStripedRowsExpression.
     *
     * @generated
     */
    public TableWidgetDescriptionBuilder useStripedRowsExpression(java.lang.String value) {
        this.getTableWidgetDescription().setUseStripedRowsExpression(value);
        return this;
    }

    /**
     * Setter for IsEnabledExpression.
     *
     * @generated
     */
    public TableWidgetDescriptionBuilder isEnabledExpression(java.lang.String value) {
        this.getTableWidgetDescription().setIsEnabledExpression(value);
        return this;
    }

}

