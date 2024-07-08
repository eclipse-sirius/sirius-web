/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
 * Builder for DiagramStyleDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class DiagramStyleDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.DiagramStyleDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.DiagramStyleDescription diagramStyleDescription = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createDiagramStyleDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.DiagramStyleDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.DiagramStyleDescription getDiagramStyleDescription() {
        return this.diagramStyleDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.DiagramStyleDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.DiagramStyleDescription build() {
        return this.getDiagramStyleDescription();
    }

    /**
     * Setter for Background.
     *
     * @generated
     */
    public DiagramStyleDescriptionBuilder background(org.eclipse.sirius.components.view.UserColor value) {
        this.getDiagramStyleDescription().setBackground(value);
        return this;
    }

}

