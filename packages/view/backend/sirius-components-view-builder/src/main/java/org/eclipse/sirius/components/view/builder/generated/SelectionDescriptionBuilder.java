/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
 * Builder for SelectionDescriptionBuilder.
 * @generated
 */
@SuppressWarnings("checkstyle:JavadocType")
public class SelectionDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.SelectionDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.SelectionDescription selectionDescription = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createSelectionDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.SelectionDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.SelectionDescription getSelectionDescription() {
        return this.selectionDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.SelectionDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.SelectionDescription build() {
        return this.getSelectionDescription();
    }

    /**
     * Setter for SelectionCandidatesExpression.
     *
     * @generated
     */
    public SelectionDescriptionBuilder selectionCandidatesExpression(java.lang.String value) {
        this.getSelectionDescription().setSelectionCandidatesExpression(value);
        return this;
    }
    /**
     * Setter for SelectionMessage.
     *
     * @generated
     */
    public SelectionDescriptionBuilder selectionMessage(java.lang.String value) {
        this.getSelectionDescription().setSelectionMessage(value);
        return this;
    }

}

