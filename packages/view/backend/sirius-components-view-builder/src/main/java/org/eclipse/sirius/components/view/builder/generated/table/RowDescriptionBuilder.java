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
 * Builder for RowDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class RowDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.table.RowDescription.
     *
     * @generated
     */
    private org.eclipse.sirius.components.view.table.RowDescription rowDescription = org.eclipse.sirius.components.view.table.TableFactory.eINSTANCE.createRowDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.table.RowDescription.
     *
     * @generated
     */
    protected org.eclipse.sirius.components.view.table.RowDescription getRowDescription() {
        return this.rowDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.table.RowDescription.
     *
     * @generated
     */
    public org.eclipse.sirius.components.view.table.RowDescription build() {
        return this.getRowDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public RowDescriptionBuilder name(java.lang.String value) {
        this.getRowDescription().setName(value);
        return this;
    }

    /**
     * Setter for SemanticCandidatesExpression.
     *
     * @generated
     */
    public RowDescriptionBuilder semanticCandidatesExpression(java.lang.String value) {
        this.getRowDescription().setSemanticCandidatesExpression(value);
        return this;
    }

    /**
     * Setter for HeaderLabelExpression.
     *
     * @generated
     */
    public RowDescriptionBuilder headerLabelExpression(java.lang.String value) {
        this.getRowDescription().setHeaderLabelExpression(value);
        return this;
    }

    /**
     * Setter for HeaderIconExpression.
     *
     * @generated
     */
    public RowDescriptionBuilder headerIconExpression(java.lang.String value) {
        this.getRowDescription().setHeaderIconExpression(value);
        return this;
    }

    /**
     * Setter for HeaderIndexLabelExpression.
     *
     * @generated
     */
    public RowDescriptionBuilder headerIndexLabelExpression(java.lang.String value) {
        this.getRowDescription().setHeaderIndexLabelExpression(value);
        return this;
    }

    /**
     * Setter for InitialHeightExpression.
     *
     * @generated
     */
    public RowDescriptionBuilder initialHeightExpression(java.lang.String value) {
        this.getRowDescription().setInitialHeightExpression(value);
        return this;
    }

    /**
     * Setter for IsResizableExpression.
     *
     * @generated
     */
    public RowDescriptionBuilder isResizableExpression(java.lang.String value) {
        this.getRowDescription().setIsResizableExpression(value);
        return this;
    }

    /**
     * Setter for ContextMenuEntries.
     *
     * @generated
     */
    public RowDescriptionBuilder contextMenuEntries(org.eclipse.sirius.components.view.table.RowContextMenuEntry ... values) {
        for (org.eclipse.sirius.components.view.table.RowContextMenuEntry value : values) {
            this.getRowDescription().getContextMenuEntries().add(value);
        }
        return this;
    }

}

