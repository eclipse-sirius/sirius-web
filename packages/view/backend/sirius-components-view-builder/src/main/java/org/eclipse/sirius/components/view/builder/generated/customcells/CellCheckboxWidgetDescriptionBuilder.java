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
package org.eclipse.sirius.components.view.builder.generated.customcells;

/**
 * Builder for CellCheckboxWidgetDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class CellCheckboxWidgetDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.table.customcells.CellCheckboxWidgetDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.table.customcells.CellCheckboxWidgetDescription cellCheckboxWidgetDescription = org.eclipse.sirius.components.view.table.customcells.CustomcellsFactory.eINSTANCE.createCellCheckboxWidgetDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.table.customcells.CellCheckboxWidgetDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.table.customcells.CellCheckboxWidgetDescription getCellCheckboxWidgetDescription() {
        return this.cellCheckboxWidgetDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.table.customcells.CellCheckboxWidgetDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.table.customcells.CellCheckboxWidgetDescription build() {
        return this.getCellCheckboxWidgetDescription();
    }

    /**
     * Setter for Body.
     *
     * @generated
     */
    public CellCheckboxWidgetDescriptionBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getCellCheckboxWidgetDescription().getBody().add(value);
        }
        return this;
    }


}

