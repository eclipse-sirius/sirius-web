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
 * Builder for CellLabelWidgetDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class CellLabelWidgetDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.table.CellLabelWidgetDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.table.CellLabelWidgetDescription cellLabelWidgetDescription = org.eclipse.sirius.components.view.table.TableFactory.eINSTANCE.createCellLabelWidgetDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.table.CellLabelWidgetDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.table.CellLabelWidgetDescription getCellLabelWidgetDescription() {
        return this.cellLabelWidgetDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.table.CellLabelWidgetDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.table.CellLabelWidgetDescription build() {
        return this.getCellLabelWidgetDescription();
    }

    /**
     * Setter for IconExpression.
     *
     * @generated
     */
    public CellLabelWidgetDescriptionBuilder iconExpression(java.lang.String value) {
        this.getCellLabelWidgetDescription().setIconExpression(value);
        return this;
    }

}

