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
 * Builder for LetBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class LetBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.Let.
     * @generated
     */
    private org.eclipse.sirius.components.view.Let let = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createLet();

    /**
     * Return instance org.eclipse.sirius.components.view.Let.
     * @generated
     */
    protected org.eclipse.sirius.components.view.Let getLet() {
        return this.let;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.Let.
     * @generated
     */
    public org.eclipse.sirius.components.view.Let build() {
        return this.getLet();
    }

    /**
     * Setter for Children.
     *
     * @generated
     */
    public LetBuilder children(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getLet().getChildren().add(value);
        }
        return this;
    }

    /**
     * Setter for VariableName.
     *
     * @generated
     */
    public LetBuilder variableName(java.lang.String value) {
        this.getLet().setVariableName(value);
        return this;
    }
    /**
     * Setter for ValueExpression.
     *
     * @generated
     */
    public LetBuilder valueExpression(java.lang.String value) {
        this.getLet().setValueExpression(value);
        return this;
    }

}

