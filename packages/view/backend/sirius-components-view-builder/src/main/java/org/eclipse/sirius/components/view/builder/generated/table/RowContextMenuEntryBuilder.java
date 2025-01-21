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
 * Builder for RowContextMenuEntryBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class RowContextMenuEntryBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.table.RowContextMenuEntry.
     * @generated
     */
    private org.eclipse.sirius.components.view.table.RowContextMenuEntry rowContextMenuEntry = org.eclipse.sirius.components.view.table.TableFactory.eINSTANCE.createRowContextMenuEntry();

    /**
     * Return instance org.eclipse.sirius.components.view.table.RowContextMenuEntry.
     * @generated
     */
    protected org.eclipse.sirius.components.view.table.RowContextMenuEntry getRowContextMenuEntry() {
        return this.rowContextMenuEntry;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.table.RowContextMenuEntry.
     * @generated
     */
    public org.eclipse.sirius.components.view.table.RowContextMenuEntry build() {
        return this.getRowContextMenuEntry();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public RowContextMenuEntryBuilder name(java.lang.String value) {
        this.getRowContextMenuEntry().setName(value);
        return this;
    }
    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public RowContextMenuEntryBuilder labelExpression(java.lang.String value) {
        this.getRowContextMenuEntry().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for IconURLExpression.
     *
     * @generated
     */
    public RowContextMenuEntryBuilder iconURLExpression(java.lang.String value) {
        this.getRowContextMenuEntry().setIconURLExpression(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public RowContextMenuEntryBuilder preconditionExpression(java.lang.String value) {
        this.getRowContextMenuEntry().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public RowContextMenuEntryBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getRowContextMenuEntry().getBody().add(value);
        }
        return this;
    }


}

