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
package org.eclipse.sirius.components.view.builder.generated;

/**
 * Builder for SelectionDialogDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class SelectionDialogDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.SelectionDialogDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.SelectionDialogDescription selectionDialogDescription = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createSelectionDialogDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.SelectionDialogDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.SelectionDialogDescription getSelectionDialogDescription() {
        return this.selectionDialogDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.SelectionDialogDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.SelectionDialogDescription build() {
        return this.getSelectionDialogDescription();
    }

    /**
     * Setter for SelectionCandidatesExpression.
     *
     * @generated
     */
    public SelectionDialogDescriptionBuilder selectionCandidatesExpression(java.lang.String value) {
        this.getSelectionDialogDescription().setSelectionCandidatesExpression(value);
        return this;
    }
    /**
     * Setter for SelectionMessage.
     *
     * @generated
     */
    public SelectionDialogDescriptionBuilder selectionMessage(java.lang.String value) {
        this.getSelectionDialogDescription().setSelectionMessage(value);
        return this;
    }

}

