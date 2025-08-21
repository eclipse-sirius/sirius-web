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
package org.eclipse.sirius.components.view.builder.generated.diagram;

/**
 * Builder for EdgeDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class EdgeDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.EdgeDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.EdgeDescription edgeDescription = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createEdgeDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.EdgeDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.EdgeDescription getEdgeDescription() {
        return this.edgeDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.EdgeDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.EdgeDescription build() {
        return this.getEdgeDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public EdgeDescriptionBuilder name(java.lang.String value) {
        this.getEdgeDescription().setName(value);
        return this;
    }
    /**
     * Setter for DomainType.
     *
     * @generated
     */
    public EdgeDescriptionBuilder domainType(java.lang.String value) {
        this.getEdgeDescription().setDomainType(value);
        return this;
    }
    /**
     * Setter for SemanticCandidatesExpression.
     *
     * @generated
     */
    public EdgeDescriptionBuilder semanticCandidatesExpression(java.lang.String value) {
        this.getEdgeDescription().setSemanticCandidatesExpression(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public EdgeDescriptionBuilder preconditionExpression(java.lang.String value) {
        this.getEdgeDescription().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for SynchronizationPolicy.
     *
     * @generated
     */
    public EdgeDescriptionBuilder synchronizationPolicy(org.eclipse.sirius.components.view.diagram.SynchronizationPolicy value) {
        this.getEdgeDescription().setSynchronizationPolicy(value);
        return this;
    }
    /**
     * Setter for BeginLabelExpression.
     *
     * @generated
     */
    public EdgeDescriptionBuilder beginLabelExpression(java.lang.String value) {
        this.getEdgeDescription().setBeginLabelExpression(value);
        return this;
    }
    /**
     * Setter for CenterLabelExpression.
     *
     * @generated
     */
    public EdgeDescriptionBuilder centerLabelExpression(java.lang.String value) {
        this.getEdgeDescription().setCenterLabelExpression(value);
        return this;
    }
    /**
     * Setter for EndLabelExpression.
     *
     * @generated
     */
    public EdgeDescriptionBuilder endLabelExpression(java.lang.String value) {
        this.getEdgeDescription().setEndLabelExpression(value);
        return this;
    }
    /**
     * Setter for IsDomainBasedEdge.
     *
     * @generated
     */
    public EdgeDescriptionBuilder isDomainBasedEdge(java.lang.Boolean value) {
        this.getEdgeDescription().setIsDomainBasedEdge(value);
        return this;
    }
    /**
     * Setter for Palette.
     *
     * @generated
     */
    public EdgeDescriptionBuilder palette(org.eclipse.sirius.components.view.diagram.EdgePalette value) {
        this.getEdgeDescription().setPalette(value);
        return this;
    }
    /**
     * Setter for SourceDescriptions.
     *
     * @generated
     */
    public EdgeDescriptionBuilder sourceDescriptions(org.eclipse.sirius.components.view.diagram.DiagramElementDescription ... values) {
        for (org.eclipse.sirius.components.view.diagram.DiagramElementDescription value : values) {
            this.getEdgeDescription().getSourceDescriptions().add(value);
        }
        return this;
    }

    /**
     * Setter for TargetDescriptions.
     *
     * @generated
     */
    public EdgeDescriptionBuilder targetDescriptions(org.eclipse.sirius.components.view.diagram.DiagramElementDescription ... values) {
        for (org.eclipse.sirius.components.view.diagram.DiagramElementDescription value : values) {
            this.getEdgeDescription().getTargetDescriptions().add(value);
        }
        return this;
    }

    /**
     * Setter for SourceExpression.
     *
     * @generated
     */
    public EdgeDescriptionBuilder sourceExpression(java.lang.String value) {
        this.getEdgeDescription().setSourceExpression(value);
        return this;
    }
    /**
     * Setter for TargetExpression.
     *
     * @generated
     */
    public EdgeDescriptionBuilder targetExpression(java.lang.String value) {
        this.getEdgeDescription().setTargetExpression(value);
        return this;
    }
    /**
     * Setter for Style.
     *
     * @generated
     */
    public EdgeDescriptionBuilder style(org.eclipse.sirius.components.view.diagram.EdgeStyle value) {
        this.getEdgeDescription().setStyle(value);
        return this;
    }
    /**
     * Setter for ConditionalStyles.
     *
     * @generated
     */
    public EdgeDescriptionBuilder conditionalStyles(org.eclipse.sirius.components.view.diagram.ConditionalEdgeStyle ... values) {
        for (org.eclipse.sirius.components.view.diagram.ConditionalEdgeStyle value : values) {
            this.getEdgeDescription().getConditionalStyles().add(value);
        }
        return this;
    }


}

