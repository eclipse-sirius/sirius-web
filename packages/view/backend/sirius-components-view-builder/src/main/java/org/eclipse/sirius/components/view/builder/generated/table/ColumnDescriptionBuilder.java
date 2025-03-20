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
package org.eclipse.sirius.components.view.builder.generated.table;

/**
 * Builder for ColumnDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ColumnDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.table.ColumnDescription.
     *
     * @generated
     */
    private org.eclipse.sirius.components.view.table.ColumnDescription columnDescription = org.eclipse.sirius.components.view.table.TableFactory.eINSTANCE.createColumnDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.table.ColumnDescription.
     *
     * @generated
     */
    protected org.eclipse.sirius.components.view.table.ColumnDescription getColumnDescription() {
        return this.columnDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.table.ColumnDescription.
     *
     * @generated
     */
    public org.eclipse.sirius.components.view.table.ColumnDescription build() {
        return this.getColumnDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public ColumnDescriptionBuilder name(java.lang.String value) {
        this.getColumnDescription().setName(value);
        return this;
    }

    /**
     * Setter for DomainType.
     *
     * @generated
     */
    public ColumnDescriptionBuilder domainType(java.lang.String value) {
        this.getColumnDescription().setDomainType(value);
        return this;
    }

    /**
     * Setter for SemanticCandidatesExpression.
     *
     * @generated
     */
    public ColumnDescriptionBuilder semanticCandidatesExpression(java.lang.String value) {
        this.getColumnDescription().setSemanticCandidatesExpression(value);
        return this;
    }

    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public ColumnDescriptionBuilder preconditionExpression(java.lang.String value) {
        this.getColumnDescription().setPreconditionExpression(value);
        return this;
    }

    /**
     * Setter for HeaderIndexLabelExpression.
     *
     * @generated
     */
    public ColumnDescriptionBuilder headerIndexLabelExpression(java.lang.String value) {
        this.getColumnDescription().setHeaderIndexLabelExpression(value);
        return this;
    }

    /**
     * Setter for HeaderLabelExpression.
     *
     * @generated
     */
    public ColumnDescriptionBuilder headerLabelExpression(java.lang.String value) {
        this.getColumnDescription().setHeaderLabelExpression(value);
        return this;
    }

    /**
     * Setter for HeaderIconExpression.
     *
     * @generated
     */
    public ColumnDescriptionBuilder headerIconExpression(java.lang.String value) {
        this.getColumnDescription().setHeaderIconExpression(value);
        return this;
    }

    /**
     * Setter for InitialWidthExpression.
     *
     * @generated
     */
    public ColumnDescriptionBuilder initialWidthExpression(java.lang.String value) {
        this.getColumnDescription().setInitialWidthExpression(value);
        return this;
    }

    /**
     * Setter for IsResizableExpression.
     *
     * @generated
     */
    public ColumnDescriptionBuilder isResizableExpression(java.lang.String value) {
        this.getColumnDescription().setIsResizableExpression(value);
        return this;
    }

    /**
     * Setter for FilterWidgetExpression.
     *
     * @generated
     */
    public ColumnDescriptionBuilder filterWidgetExpression(java.lang.String value) {
        this.getColumnDescription().setFilterWidgetExpression(value);
        return this;
    }

    /**
     * Setter for IsSortableExpression.
     *
     * @generated
     */
    public ColumnDescriptionBuilder isSortableExpression(java.lang.String value) {
        this.getColumnDescription().setIsSortableExpression(value);
        return this;
    }

}

