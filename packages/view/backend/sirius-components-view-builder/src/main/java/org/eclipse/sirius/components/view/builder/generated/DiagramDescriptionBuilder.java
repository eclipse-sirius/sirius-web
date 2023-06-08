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
 * Builder for DiagramDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class DiagramDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.DiagramDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.DiagramDescription diagramDescription = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createDiagramDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.DiagramDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.DiagramDescription getDiagramDescription() {
        return this.diagramDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.DiagramDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.DiagramDescription build() {
        return this.getDiagramDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public DiagramDescriptionBuilder name(java.lang.String value) {
        this.getDiagramDescription().setName(value);
        return this;
    }
    /**
     * Setter for DomainType.
     *
     * @generated
     */
    public DiagramDescriptionBuilder domainType(java.lang.String value) {
        this.getDiagramDescription().setDomainType(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public DiagramDescriptionBuilder preconditionExpression(java.lang.String value) {
        this.getDiagramDescription().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for TitleExpression.
     *
     * @generated
     */
    public DiagramDescriptionBuilder titleExpression(java.lang.String value) {
        this.getDiagramDescription().setTitleExpression(value);
        return this;
    }
    /**
     * Setter for AutoLayout.
     *
     * @generated
     */
    public DiagramDescriptionBuilder autoLayout(java.lang.Boolean value) {
        this.getDiagramDescription().setAutoLayout(value);
        return this;
    }
    /**
     * Setter for Palette.
     *
     * @generated
     */
    public DiagramDescriptionBuilder palette(org.eclipse.sirius.components.view.DiagramPalette value) {
        this.getDiagramDescription().setPalette(value);
        return this;
    }
    /**
     * Setter for NodeDescriptions.
     *
     * @generated
     */
    public DiagramDescriptionBuilder nodeDescriptions(org.eclipse.sirius.components.view.NodeDescription ... values) {
        for (org.eclipse.sirius.components.view.NodeDescription value : values) {
            this.getDiagramDescription().getNodeDescriptions().add(value);
        }
        return this;
    }

    /**
     * Setter for EdgeDescriptions.
     *
     * @generated
     */
    public DiagramDescriptionBuilder edgeDescriptions(org.eclipse.sirius.components.view.EdgeDescription ... values) {
        for (org.eclipse.sirius.components.view.EdgeDescription value : values) {
            this.getDiagramDescription().getEdgeDescriptions().add(value);
        }
        return this;
    }


}

