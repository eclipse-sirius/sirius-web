/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
 * Builder for CellSelectWidgetDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class CellSelectWidgetDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.table.CellSelectWidgetDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.table.CellSelectWidgetDescription cellSelectWidgetDescription = org.eclipse.sirius.components.view.table.TableFactory.eINSTANCE.createCellSelectWidgetDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.table.CellSelectWidgetDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.table.CellSelectWidgetDescription getCellSelectWidgetDescription() {
        return this.cellSelectWidgetDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.table.CellSelectWidgetDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.table.CellSelectWidgetDescription build() {
        return this.getCellSelectWidgetDescription();
    }

    /**
     * Setter for CandidatesExpression.
     *
     * @generated
     */
    public CellSelectWidgetDescriptionBuilder candidatesExpression(java.lang.String value) {
        this.getCellSelectWidgetDescription().setCandidatesExpression(value);
        return this;
    }
    /**
     * Setter for CandidateLabelExpression.
     *
     * @generated
     */
    public CellSelectWidgetDescriptionBuilder candidateLabelExpression(java.lang.String value) {
        this.getCellSelectWidgetDescription().setCandidateLabelExpression(value);
        return this;
    }

    /**
     * Setter for Body.
     *
     * @generated
     */
    public CellSelectWidgetDescriptionBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getCellSelectWidgetDescription().getBody().add(value);
        }
        return this;
    }

}

