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
 * Builder for NodeDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class NodeDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.NodeDescription.
     *
     * @generated
     */
    private final org.eclipse.sirius.components.view.diagram.NodeDescription nodeDescription = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createNodeDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.NodeDescription.
     *
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.NodeDescription getNodeDescription() {
        return this.nodeDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.NodeDescription.
     *
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.NodeDescription build() {
        return this.getNodeDescription();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public NodeDescriptionBuilder name(java.lang.String value) {
        this.getNodeDescription().setName(value);
        return this;
    }

    /**
     * Setter for DomainType.
     *
     * @generated
     */
    public NodeDescriptionBuilder domainType(java.lang.String value) {
        this.getNodeDescription().setDomainType(value);
        return this;
    }

    /**
     * Setter for SemanticCandidatesExpression.
     *
     * @generated
     */
    public NodeDescriptionBuilder semanticCandidatesExpression(java.lang.String value) {
        this.getNodeDescription().setSemanticCandidatesExpression(value);
        return this;
    }

    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public NodeDescriptionBuilder preconditionExpression(java.lang.String value) {
        this.getNodeDescription().setPreconditionExpression(value);
        return this;
    }

    /**
     * Setter for SynchronizationPolicy.
     *
     * @generated
     */
    public NodeDescriptionBuilder synchronizationPolicy(org.eclipse.sirius.components.view.diagram.SynchronizationPolicy value) {
        this.getNodeDescription().setSynchronizationPolicy(value);
        return this;
    }

    /**
     * Setter for Collapsible.
     *
     * @generated
     */
    public NodeDescriptionBuilder collapsible(java.lang.Boolean value) {
        this.getNodeDescription().setCollapsible(value);
        return this;
    }

    /**
     * Setter for Palette.
     *
     * @generated
     */
    public NodeDescriptionBuilder palette(org.eclipse.sirius.components.view.diagram.NodePalette value) {
        this.getNodeDescription().setPalette(value);
        return this;
    }

    /**
     * Setter for ChildrenLayoutStrategy.
     *
     * @generated
     */
    public NodeDescriptionBuilder childrenLayoutStrategy(org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription value) {
        this.getNodeDescription().setChildrenLayoutStrategy(value);
        return this;
    }

    /**
     * Setter for Style.
     *
     * @generated
     */
    public NodeDescriptionBuilder style(org.eclipse.sirius.components.view.diagram.NodeStyleDescription value) {
        this.getNodeDescription().setStyle(value);
        return this;
    }

    /**
     * Setter for ConditionalStyles.
     *
     * @generated
     */
    public NodeDescriptionBuilder conditionalStyles(org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle... values) {
        for (org.eclipse.sirius.components.view.diagram.ConditionalNodeStyle value : values) {
            this.getNodeDescription().getConditionalStyles().add(value);
        }
        return this;
    }

    /**
     * Setter for ChildrenDescriptions.
     *
     * @generated
     */
    public NodeDescriptionBuilder childrenDescriptions(org.eclipse.sirius.components.view.diagram.NodeDescription... values) {
        for (org.eclipse.sirius.components.view.diagram.NodeDescription value : values) {
            this.getNodeDescription().getChildrenDescriptions().add(value);
        }
        return this;
    }

    /**
     * Setter for BorderNodesDescriptions.
     *
     * @generated
     */
    public NodeDescriptionBuilder borderNodesDescriptions(org.eclipse.sirius.components.view.diagram.NodeDescription... values) {
        for (org.eclipse.sirius.components.view.diagram.NodeDescription value : values) {
            this.getNodeDescription().getBorderNodesDescriptions().add(value);
        }
        return this;
    }

    /**
     * Setter for ReusedChildNodeDescriptions.
     *
     * @generated
     */
    public NodeDescriptionBuilder reusedChildNodeDescriptions(org.eclipse.sirius.components.view.diagram.NodeDescription... values) {
        for (org.eclipse.sirius.components.view.diagram.NodeDescription value : values) {
            this.getNodeDescription().getReusedChildNodeDescriptions().add(value);
        }
        return this;
    }

    /**
     * Setter for ReusedBorderNodeDescriptions.
     *
     * @generated
     */
    public NodeDescriptionBuilder reusedBorderNodeDescriptions(org.eclipse.sirius.components.view.diagram.NodeDescription... values) {
        for (org.eclipse.sirius.components.view.diagram.NodeDescription value : values) {
            this.getNodeDescription().getReusedBorderNodeDescriptions().add(value);
        }
        return this;
    }

    /**
     * Setter for UserResizable.
     *
     * @generated
     */
    public NodeDescriptionBuilder userResizable(java.lang.Boolean value) {
        this.getNodeDescription().setUserResizable(value);
        return this;
    }

    /**
     * Setter for DefaultWidthExpression.
     *
     * @generated
     */
    public NodeDescriptionBuilder defaultWidthExpression(java.lang.String value) {
        this.getNodeDescription().setDefaultWidthExpression(value);
        return this;
    }

    /**
     * Setter for DefaultHeightExpression.
     *
     * @generated
     */
    public NodeDescriptionBuilder defaultHeightExpression(java.lang.String value) {
        this.getNodeDescription().setDefaultHeightExpression(value);
        return this;
    }

    /**
     * Setter for KeepAspectRatio.
     *
     * @generated
     */
    public NodeDescriptionBuilder keepAspectRatio(java.lang.Boolean value) {
        this.getNodeDescription().setKeepAspectRatio(value);
        return this;
    }

    /**
     * Setter for InsideLabel.
     *
     * @generated
     */
    public NodeDescriptionBuilder insideLabel(org.eclipse.sirius.components.view.diagram.InsideLabelDescription value) {
        this.getNodeDescription().setInsideLabel(value);
        return this;
    }

    /**
     * Setter for OutsideLabels.
     *
     * @generated
     */
    public NodeDescriptionBuilder outsideLabels(org.eclipse.sirius.components.view.diagram.OutsideLabelDescription... values) {
        for (org.eclipse.sirius.components.view.diagram.OutsideLabelDescription value : values) {
            this.getNodeDescription().getOutsideLabels().add(value);
        }
        return this;
    }


}

