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
 * Builder for ActionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ActionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.Action.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.Action action = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createAction();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.Action.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.Action getAction() {
        return this.action;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.Action.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.Action build() {
        return this.getAction();
    }

    /**
     * Setter for Name.
     *
     * @generated
     */
    public ActionBuilder name(java.lang.String value) {
        this.getAction().setName(value);
        return this;
    }
    /**
     * Setter for TooltipExpression.
     *
     * @generated
     */
    public ActionBuilder tooltipExpression(java.lang.String value) {
        this.getAction().setTooltipExpression(value);
        return this;
    }
    /**
     * Setter for IconURLsExpression.
     *
     * @generated
     */
    public ActionBuilder iconURLsExpression(java.lang.String value) {
        this.getAction().setIconURLsExpression(value);
        return this;
    }
    /**
     * Setter for PreconditionExpression.
     *
     * @generated
     */
    public ActionBuilder preconditionExpression(java.lang.String value) {
        this.getAction().setPreconditionExpression(value);
        return this;
    }
    /**
     * Setter for Body.
     *
     * @generated
     */
    public ActionBuilder body(org.eclipse.sirius.components.view.Operation ... values) {
        for (org.eclipse.sirius.components.view.Operation value : values) {
            this.getAction().getBody().add(value);
        }
        return this;
    }


}

