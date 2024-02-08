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
 * Builder for org.eclipse.sirius.components.view.diagram.DiagramElementDescription.
 *
 * @author BuilderGenerator
 * @generated
 */
public abstract class DiagramElementDescriptionBuilder {

    /**
     * Builder for org.eclipse.sirius.components.view.diagram.DiagramElementDescription.
     *
     * @generated
     */
    protected abstract org.eclipse.sirius.components.view.diagram.DiagramElementDescription getDiagramElementDescription();

    /**
     * Setter for Name.
     *
     * @generated
     */
    public DiagramElementDescriptionBuilder name(java.lang.String value) {
        this.getDiagramElementDescription().setName(value);
        return this;
    }

    /**
     * Setter for DomainType.
     *
     * @generated
     */
    public DiagramElementDescriptionBuilder domainType(java.lang.String value) {
        this.getDiagramElementDescription().setDomainType(value);
        return this;
    }

    /**
     * Setter for SemanticCandidatesExpression.
     *
     * @generated
     */
    public DiagramElementDescriptionBuilder semanticCandidatesExpression(java.lang.String value) {
        this.getDiagramElementDescription().setSemanticCandidatesExpression(value);
        return this;
    }

    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public DiagramElementDescriptionBuilder preconditionExpression(java.lang.String value) {
        this.getDiagramElementDescription().setPreconditionExpression(value);
        return this;
    }

    /**
     * Setter for SynchronizationPolicy.
     *
     * @generated
     */
    public DiagramElementDescriptionBuilder synchronizationPolicy(org.eclipse.sirius.components.view.diagram.SynchronizationPolicy value) {
        this.getDiagramElementDescription().setSynchronizationPolicy(value);
        return this;
    }

}

