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
 * Builder for ChangeContextBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ChangeContextBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.ChangeContext.
     * @generated
     */
    private org.eclipse.sirius.components.view.ChangeContext changeContext = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createChangeContext();

    /**
     * Return instance org.eclipse.sirius.components.view.ChangeContext.
     * @generated
     */
    protected org.eclipse.sirius.components.view.ChangeContext getChangeContext() {
        return this.changeContext;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.ChangeContext.
     * @generated
     */
    public org.eclipse.sirius.components.view.ChangeContext build() {
        return this.getChangeContext();
    }

    /**
     * Setter for Children.
     *
     * @generated
     */
    public ChangeContextBuilder children(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getChangeContext().getChildren().add(value);
        }
        return this;
    }

    /**
     * Setter for Expression.
     *
     * @generated
     */
    public ChangeContextBuilder expression(java.lang.String value) {
        this.getChangeContext().setExpression(value);
        return this;
    }

}

