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
 * Builder for LocalActionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class LocalActionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.LocalAction.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.LocalAction localAction = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createLocalAction();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.LocalAction.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.LocalAction getLocalAction() {
        return this.localAction;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.LocalAction.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.LocalAction build() {
        return this.getLocalAction();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public LocalActionBuilder name(java.lang.String value) {
        this.getLocalAction().setName(value);
        return this;
    }
    /**
     * Setter for TooltipExpression.
     *
     * @generated
     */
    public LocalActionBuilder tooltipExpression(java.lang.String value) {
        this.getLocalAction().setTooltipExpression(value);
        return this;
    }
    /**
     * Setter for IconURLsExpression.
     *
     * @generated
     */
    public LocalActionBuilder iconURLsExpression(java.lang.String value) {
        this.getLocalAction().setIconURLsExpression(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public LocalActionBuilder preconditionExpression(java.lang.String value) {
        this.getLocalAction().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public LocalActionBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getLocalAction().getBody().add(value);
        }
        return this;
    }

    /**
     * Setter for ReadOnlyVisible.
     *
     * @generated
     */
    public LocalActionBuilder readOnlyVisible(java.lang.Boolean value) {
        this.getLocalAction().setReadOnlyVisible(value);
        return this;
    }

}

