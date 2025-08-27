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
 * Builder for org.eclipse.sirius.components.view.diagram.LabelDescription.
 *
 * @author BuilderGenerator
 * @generated
 */
public abstract class LabelDescriptionBuilder {

    /**
     * Builder for org.eclipse.sirius.components.view.diagram.LabelDescription.
     * @generated
     */
    protected abstract org.eclipse.sirius.components.view.diagram.LabelDescription getLabelDescription();

    /**
     * Setter for LabelExpression.
     *
     * @generated
     */
    public LabelDescriptionBuilder labelExpression(java.lang.String value) {
        this.getLabelDescription().setLabelExpression(value);
        return this;
    }
    /**
     * Setter for OverflowStrategy.
     *
     * @generated
     */
    public LabelDescriptionBuilder overflowStrategy(org.eclipse.sirius.components.view.diagram.LabelOverflowStrategy value) {
        this.getLabelDescription().setOverflowStrategy(value);
        return this;
    }
    /**
     * Setter for TextAlign.
     *
     * @generated
     */
    public LabelDescriptionBuilder textAlign(org.eclipse.sirius.components.view.diagram.LabelTextAlign value) {
        this.getLabelDescription().setTextAlign(value);
        return this;
    }

}

