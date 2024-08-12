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
 * Builder for SelectionDialogTreeDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class SelectionDialogTreeDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription selectionDialogTreeDescription = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createSelectionDialogTreeDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription getSelectionDialogTreeDescription() {
        return this.selectionDialogTreeDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription build() {
        return this.getSelectionDialogTreeDescription();
    }

    /**
     * Setter for ElementsExpression.
     *
     * @generated
     */
    public SelectionDialogTreeDescriptionBuilder elementsExpression(java.lang.String value) {
        this.getSelectionDialogTreeDescription().setElementsExpression(value);
        return this;
    }

    /**
     * Setter for ChildrenExpression.
     *
     * @generated
     */
    public SelectionDialogTreeDescriptionBuilder childrenExpression(java.lang.String value) {
        this.getSelectionDialogTreeDescription().setChildrenExpression(value);
        return this;
    }

    /**
     * Setter for IsSelectableExpression.
     *
     * @generated
     */
    public SelectionDialogTreeDescriptionBuilder isSelectableExpression(java.lang.String value) {
        this.getSelectionDialogTreeDescription().setIsSelectableExpression(value);
        return this;
    }

}

