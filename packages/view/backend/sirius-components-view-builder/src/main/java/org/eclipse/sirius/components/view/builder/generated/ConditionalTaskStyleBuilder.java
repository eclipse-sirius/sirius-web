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
 * Builder for ConditionalTaskStyleBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ConditionalTaskStyleBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.gantt.ConditionalTaskStyle.
     * @generated
     */
    private org.eclipse.sirius.components.view.gantt.ConditionalTaskStyle conditionalTaskStyle = org.eclipse.sirius.components.view.gantt.GanttFactory.eINSTANCE.createConditionalTaskStyle();

    /**
     * Return instance org.eclipse.sirius.components.view.gantt.ConditionalTaskStyle.
     * @generated
     */
    protected org.eclipse.sirius.components.view.gantt.ConditionalTaskStyle getConditionalTaskStyle() {
        return this.conditionalTaskStyle;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.gantt.ConditionalTaskStyle.
     * @generated
     */
    public org.eclipse.sirius.components.view.gantt.ConditionalTaskStyle build() {
        return this.getConditionalTaskStyle();
    }

    /**
     * Setter for Condition.
     *
     * @generated
     */
    public ConditionalTaskStyleBuilder condition(java.lang.String value) {
        this.getConditionalTaskStyle().setCondition(value);
        return this;
    }
    /**
     * Setter for Style.
     *
     * @generated
     */
    public ConditionalTaskStyleBuilder style(org.eclipse.sirius.components.view.gantt.TaskStyleDescription value) {
        this.getConditionalTaskStyle().setStyle(value);
        return this;
    }

}

