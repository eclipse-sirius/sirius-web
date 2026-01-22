/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
package org.eclipse.sirius.components.view.builder.generated.table;

/**
 * Builder for TableDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class TableDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.table.TableDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.table.TableDescription tableDescription = org.eclipse.sirius.components.view.table.TableFactory.eINSTANCE.createTableDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.table.TableDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.table.TableDescription getTableDescription() {
        return this.tableDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.table.TableDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.table.TableDescription build() {
        return this.getTableDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public TableDescriptionBuilder name(java.lang.String value) {
        this.getTableDescription().setName(value);
        return this;
    }
    /**
     * Setter for DomainType.
     *
     * @generated
     */
    public TableDescriptionBuilder domainType(java.lang.String value) {
        this.getTableDescription().setDomainType(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public TableDescriptionBuilder preconditionExpression(java.lang.String value) {
        this.getTableDescription().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for TitleExpression.
     *
     * @generated
     */
    public TableDescriptionBuilder titleExpression(java.lang.String value) {
        this.getTableDescription().setTitleExpression(value);
        return this;
    }
    /**
     * Setter for IconExpression.
     *
     * @generated
     */
    public TableDescriptionBuilder iconExpression(java.lang.String value) {
        this.getTableDescription().setIconExpression(value);
        return this;
    }
    /**
     * Setter for Description.
     *
     * @generated
     */
    public TableDescriptionBuilder description(java.lang.String value) {
        this.getTableDescription().setDescription(value);
        return this;
    }

    /**
     * Setter for UseStripedRowsExpression.
     *
     * @generated
     */
    public TableDescriptionBuilder useStripedRowsExpression(java.lang.String value) {
        this.getTableDescription().setUseStripedRowsExpression(value);
        return this;
    }
    /**
     * Setter for ColumnDescriptions.
     *
     * @generated
     */
    public TableDescriptionBuilder columnDescriptions(org.eclipse.sirius.components.view.table.ColumnDescription ... values) {
        for (org.eclipse.sirius.components.view.table.ColumnDescription value : values) {
            this.getTableDescription().getColumnDescriptions().add(value);
        }
        return this;
    }

    /**
     * Setter for RowDescription.
     *
     * @generated
     */
    public TableDescriptionBuilder rowDescription(org.eclipse.sirius.components.view.table.RowDescription value) {
        this.getTableDescription().setRowDescription(value);
        return this;
    }
    /**
     * Setter for CellDescriptions.
     *
     * @generated
     */
    public TableDescriptionBuilder cellDescriptions(org.eclipse.sirius.components.view.table.CellDescription ... values) {
        for (org.eclipse.sirius.components.view.table.CellDescription value : values) {
            this.getTableDescription().getCellDescriptions().add(value);
        }
        return this;
    }

    /**
     * Setter for EnableSubRows.
     *
     * @generated
     */
    public TableDescriptionBuilder enableSubRows(java.lang.Boolean value) {
        this.getTableDescription().setEnableSubRows(value);
        return this;
    }
    /**
     * Setter for RowFilters.
     *
     * @generated
     */
    public TableDescriptionBuilder rowFilters(org.eclipse.sirius.components.view.table.RowFilterDescription ... values) {
        for (org.eclipse.sirius.components.view.table.RowFilterDescription value : values) {
            this.getTableDescription().getRowFilters().add(value);
        }
        return this;
    }

    /**
     * Setter for PageSizeOptionsExpression.
     *
     * @generated
     */
    public TableDescriptionBuilder pageSizeOptionsExpression(java.lang.String value) {
        this.getTableDescription().setPageSizeOptionsExpression(value);
        return this;
    }
    /**
     * Setter for DefaultPageSizeIndexExpression.
     *
     * @generated
     */
    public TableDescriptionBuilder defaultPageSizeIndexExpression(java.lang.String value) {
        this.getTableDescription().setDefaultPageSizeIndexExpression(value);
        return this;
    }

}

