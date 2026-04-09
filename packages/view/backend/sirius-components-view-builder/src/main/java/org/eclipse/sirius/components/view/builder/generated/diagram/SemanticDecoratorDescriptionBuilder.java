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
 * Builder for SemanticDecoratorDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class SemanticDecoratorDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.SemanticDecoratorDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.SemanticDecoratorDescription semanticDecoratorDescription = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createSemanticDecoratorDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.SemanticDecoratorDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.SemanticDecoratorDescription getSemanticDecoratorDescription() {
        return this.semanticDecoratorDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.SemanticDecoratorDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.SemanticDecoratorDescription build() {
        return this.getSemanticDecoratorDescription();
    }

    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public SemanticDecoratorDescriptionBuilder labelExpression(java.lang.String value) {
        this.getSemanticDecoratorDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public SemanticDecoratorDescriptionBuilder preconditionExpression(java.lang.String value) {
        this.getSemanticDecoratorDescription().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for IconURLExpression.
     *
     * @generated
     */
    public SemanticDecoratorDescriptionBuilder iconURLExpression(java.lang.String value) {
        this.getSemanticDecoratorDescription().setIconURLExpression(value);
        return this;
    }
    /**
     * Setter for Position.
     *
     * @generated
     */
    public SemanticDecoratorDescriptionBuilder position(org.eclipse.sirius.components.view.diagram.DecoratorPosition value) {
        this.getSemanticDecoratorDescription().setPosition(value);
        return this;
    }
    /**
     * Setter for Name.
     *
     * @generated
     */
    public SemanticDecoratorDescriptionBuilder name(java.lang.String value) {
        this.getSemanticDecoratorDescription().setName(value);
        return this;
    }
    /**
     * Setter for DomainType.
     *
     * @generated
     */
    public SemanticDecoratorDescriptionBuilder domainType(java.lang.String value) {
        this.getSemanticDecoratorDescription().setDomainType(value);
        return this;
    }

}

