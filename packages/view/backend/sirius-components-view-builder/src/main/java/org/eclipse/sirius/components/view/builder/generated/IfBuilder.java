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
 * Builder for IfBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class IfBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.If.
     * @generated
     */
    private org.eclipse.sirius.components.view.If if_ = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createIf();

    /**
     * Return instance org.eclipse.sirius.components.view.If.
     * @generated
     */
    protected org.eclipse.sirius.components.view.If getIf() {
        return this.if_;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.If.
     * @generated
     */
    public org.eclipse.sirius.components.view.If build() {
        return this.getIf();
    }

    /**
     * Setter for Children.
     *
     * @generated
     */
    public IfBuilder children(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getIf().getChildren().add(value);
        }
        return this;
    }

    /**
     * Setter for ConditionExpression.
     *
     * @generated
     */
    public IfBuilder conditionExpression(java.lang.String value) {
        this.getIf().setConditionExpression(value);
        return this;
    }

}

