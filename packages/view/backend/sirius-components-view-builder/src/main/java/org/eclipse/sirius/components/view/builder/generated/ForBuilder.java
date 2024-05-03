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
 * Builder for ForBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ForBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.For.
     * @generated
     */
    private org.eclipse.sirius.components.view.For for_ = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createFor();

    /**
     * Return instance org.eclipse.sirius.components.view.For.
     * @generated
     */
    protected org.eclipse.sirius.components.view.For getFor() {
        return this.for_;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.For.
     * @generated
     */
    public org.eclipse.sirius.components.view.For build() {
        return this.getFor();
    }

    /**
     * Setter for Children.
     *
     * @generated
     */
    public ForBuilder children(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getFor().getChildren().add(value);
        }
        return this;
    }

    /**
     * Setter for Expression.
     *
     * @generated
     */
    public ForBuilder expression(java.lang.String value) {
        this.getFor().setExpression(value);
        return this;
    }
    /**
     * Setter for IteratorName.
     *
     * @generated
     */
    public ForBuilder iteratorName(java.lang.String value) {
        this.getFor().setIteratorName(value);
        return this;
    }

}

