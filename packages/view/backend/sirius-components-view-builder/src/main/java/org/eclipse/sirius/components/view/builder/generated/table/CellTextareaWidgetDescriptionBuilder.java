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
 * Builder for CellTextareaWidgetDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class CellTextareaWidgetDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.table.CellTextareaWidgetDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.table.CellTextareaWidgetDescription cellTextareaWidgetDescription = org.eclipse.sirius.components.view.table.TableFactory.eINSTANCE.createCellTextareaWidgetDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.table.CellTextareaWidgetDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.table.CellTextareaWidgetDescription getCellTextareaWidgetDescription() {
        return this.cellTextareaWidgetDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.table.CellTextareaWidgetDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.table.CellTextareaWidgetDescription build() {
        return this.getCellTextareaWidgetDescription();
    }

    /**
     * Setter for Body.
     *
     * @generated
     */
    public CellTextareaWidgetDescriptionBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getCellTextareaWidgetDescription().getBody().add(value);
        }
        return this;
    }


}

