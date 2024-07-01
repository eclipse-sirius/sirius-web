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
 * Builder for ConditionalDiagramStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ConditionalDiagramStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.ConditionalDiagramStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.ConditionalDiagramStyle conditionalDiagramStyle = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createConditionalDiagramStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.ConditionalDiagramStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.ConditionalDiagramStyle getConditionalDiagramStyle() {
        return this.conditionalDiagramStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.ConditionalDiagramStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.ConditionalDiagramStyle build() {
        return this.getConditionalDiagramStyle();
    }

    /**
     * Setter for Condition.
     *
     * @generated
     */
    public ConditionalDiagramStyleBuilder condition(java.lang.String value) {
        this.getConditionalDiagramStyle().setCondition(value);
        return this;
    }
    /**
     * Setter for Style.
     *
     * @generated
     */
    public ConditionalDiagramStyleBuilder style(org.eclipse.sirius.components.view.diagram.DiagramStyleDescription value) {
        this.getConditionalDiagramStyle().setStyle(value);
        return this;
    }

}

