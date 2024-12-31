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
 * Builder for CellDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class CellDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.table.CellDescription.
     *
     * @generated
     */
    private org.eclipse.sirius.components.view.table.CellDescription cellDescription = org.eclipse.sirius.components.view.table.TableFactory.eINSTANCE.createCellDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.table.CellDescription.
     *
     * @generated
     */
    protected org.eclipse.sirius.components.view.table.CellDescription getCellDescription() {
        return this.cellDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.table.CellDescription.
     *
     * @generated
     */
    public org.eclipse.sirius.components.view.table.CellDescription build() {
        return this.getCellDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public CellDescriptionBuilder name(java.lang.String value) {
        this.getCellDescription().setName(value);
        return this;
    }

    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public CellDescriptionBuilder preconditionExpression(java.lang.String value) {
        this.getCellDescription().setPreconditionExpression(value);
        return this;
    }

    /**
     * Setter for ValueExpression.
     *
     * @generated
     */
    public CellDescriptionBuilder valueExpression(java.lang.String value) {
        this.getCellDescription().setValueExpression(value);
        return this;
    }

    /**
     * Setter for TooltipExpression.
     *
     * @generated
     */
    public CellDescriptionBuilder tooltipExpression(java.lang.String value) {
        this.getCellDescription().setTooltipExpression(value);
        return this;
    }

    /**
     * Setter for CellWidgetDescription.
     *
     * @generated
     */
    public CellDescriptionBuilder cellWidgetDescription(org.eclipse.sirius.components.view.table.CellWidgetDescription value) {
        this.getCellDescription().setCellWidgetDescription(value);
        return this;
    }

}

