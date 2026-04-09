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
 * Builder for NodeDecoratorDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class NodeDecoratorDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.NodeDecoratorDescription.
     *
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.NodeDecoratorDescription nodeDecoratorDescription = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createNodeDecoratorDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.NodeDecoratorDescription.
     *
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.NodeDecoratorDescription getNodeDecoratorDescription() {
        return this.nodeDecoratorDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.NodeDecoratorDescription.
     *
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.NodeDecoratorDescription build() {
        return this.getNodeDecoratorDescription();
    }

    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public NodeDecoratorDescriptionBuilder labelExpression(java.lang.String value) {
        this.getNodeDecoratorDescription().setLabelExpression(value);
        return this;
    }

    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public NodeDecoratorDescriptionBuilder preconditionExpression(java.lang.String value) {
        this.getNodeDecoratorDescription().setPreconditionExpression(value);
        return this;
    }

    /**
     * Setter for IconURLExpression.
     *
     * @generated
     */
    public NodeDecoratorDescriptionBuilder iconURLExpression(java.lang.String value) {
        this.getNodeDecoratorDescription().setIconURLExpression(value);
        return this;
    }

    /**
     * Setter for Position.
     *
     * @generated
     */
    public NodeDecoratorDescriptionBuilder position(org.eclipse.sirius.components.view.diagram.DecoratorPosition value) {
        this.getNodeDecoratorDescription().setPosition(value);
        return this;
    }

    /**
     * Setter for NodeDescriptions.
     *
     * @generated
     */
    public NodeDecoratorDescriptionBuilder nodeDescriptions(org.eclipse.sirius.components.view.diagram.NodeDescription ... values) {
        for (org.eclipse.sirius.components.view.diagram.NodeDescription value : values) {
            this.getNodeDecoratorDescription().getNodeDescriptions().add(value);
        }
        return this;
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public NodeDecoratorDescriptionBuilder name(java.lang.String value) {
        this.getNodeDecoratorDescription().setName(value);
        return this;
    }

}

