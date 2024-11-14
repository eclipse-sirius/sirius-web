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
package org.eclipse.sirius.components.view.builder.generated.table;

/**
 * Builder for CellTextfieldWidgetDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class CellTextfieldWidgetDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.table.CellTextfieldWidgetDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.table.CellTextfieldWidgetDescription cellTextfieldWidgetDescription = org.eclipse.sirius.components.view.table.TableFactory.eINSTANCE.createCellTextfieldWidgetDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.table.CellTextfieldWidgetDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.table.CellTextfieldWidgetDescription getCellTextfieldWidgetDescription() {
        return this.cellTextfieldWidgetDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.table.CellTextfieldWidgetDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.table.CellTextfieldWidgetDescription build() {
        return this.getCellTextfieldWidgetDescription();
    }

    /**
     * Setter for Body.
     *
     * @generated
     */
    public CellTextfieldWidgetDescriptionBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getCellTextfieldWidgetDescription().getBody().add(value);
        }
        return this;
    }


}

