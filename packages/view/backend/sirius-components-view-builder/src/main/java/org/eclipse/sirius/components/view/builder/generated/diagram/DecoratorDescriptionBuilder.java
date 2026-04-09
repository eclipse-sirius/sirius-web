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
 * Builder for org.eclipse.sirius.components.view.diagram.DecoratorDescription.
 *
 * @author BuilderGenerator
 * @generated
 */
public abstract class DecoratorDescriptionBuilder {

    /**
     * Builder for org.eclipse.sirius.components.view.diagram.DecoratorDescription.
     * @generated
     */
    protected abstract org.eclipse.sirius.components.view.diagram.DecoratorDescription getDecoratorDescription();

    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public DecoratorDescriptionBuilder labelExpression(java.lang.String value) {
        this.getDecoratorDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public DecoratorDescriptionBuilder preconditionExpression(java.lang.String value) {
        this.getDecoratorDescription().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for IconURLExpression.
     *
     * @generated
     */
    public DecoratorDescriptionBuilder iconURLExpression(java.lang.String value) {
        this.getDecoratorDescription().setIconURLExpression(value);
        return this;
    }
    /**
     * Setter for Position.
     *
     * @generated
     */
    public DecoratorDescriptionBuilder position(org.eclipse.sirius.components.view.diagram.DecoratorPosition value) {
        this.getDecoratorDescription().setPosition(value);
        return this;
    }
    /**
     * Setter for Name.
     *
     * @generated
     */
    public DecoratorDescriptionBuilder name(java.lang.String value) {
        this.getDecoratorDescription().setName(value);
        return this;
    }

}

