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
 * Builder for RowFilterDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class RowFilterDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.table.RowFilterDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.table.RowFilterDescription rowFilterDescription = org.eclipse.sirius.components.view.table.TableFactory.eINSTANCE.createRowFilterDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.table.RowFilterDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.table.RowFilterDescription getRowFilterDescription() {
        return this.rowFilterDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.table.RowFilterDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.table.RowFilterDescription build() {
        return this.getRowFilterDescription();
    }

    /**
     * Setter for Id.
     *
     * @generated
     */
    public RowFilterDescriptionBuilder id(java.lang.String value) {
        this.getRowFilterDescription().setId(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public RowFilterDescriptionBuilder labelExpression(java.lang.String value) {
        this.getRowFilterDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for InitialStateExpression.
     *
     * @generated
     */
    public RowFilterDescriptionBuilder initialStateExpression(java.lang.String value) {
        this.getRowFilterDescription().setInitialStateExpression(value);
        return this;
    }

}

