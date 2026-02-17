/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
package org.eclipse.sirius.components.view.builder.generated.diagram;

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
     * Setter for SelectionMessage.
     *
     * @generated
     */
    public SelectionDialogDescriptionBuilder selectionMessage(java.lang.String value) {
        this.getSelectionDialogDescription().setSelectionMessage(value);
        return this;
    }
    /**
     * Setter for SelectionDialogTreeDescription.
     *
     * @generated
     */
    public SelectionDialogDescriptionBuilder selectionDialogTreeDescription(org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription value) {
        this.getSelectionDialogDescription().setSelectionDialogTreeDescription(value);
        return this;
    }
    /**
     * Setter for Multiple.
     *
     * @generated
     */
    public SelectionDialogDescriptionBuilder multiple(java.lang.Boolean value) {
        this.getSelectionDialogDescription().setMultiple(value);
        return this;
    }

    /**
     * Setter for Optional.
     *
     * @generated
     */
    public SelectionDialogDescriptionBuilder optional(java.lang.Boolean value) {
        this.getSelectionDialogDescription().setOptional(value);
        return this;
    }

    /**
     * Setter for NoSelectionLabel.
     *
     * @generated
     */
    public SelectionDialogDescriptionBuilder noSelectionLabel(java.lang.String value) {
        this.getSelectionDialogDescription().setNoSelectionLabel(value);
        return this;
    }

}

