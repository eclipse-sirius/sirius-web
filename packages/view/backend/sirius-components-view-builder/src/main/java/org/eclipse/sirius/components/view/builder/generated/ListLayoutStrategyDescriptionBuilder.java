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
 * Builder for ListLayoutStrategyDescriptionBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class ListLayoutStrategyDescriptionBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription.
     * @generated
     */
    private org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription listLayoutStrategyDescription = org.eclipse.sirius.components.view.diagram.DiagramFactory.eINSTANCE.createListLayoutStrategyDescription();

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription.
     * @generated
     */
    protected org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription getListLayoutStrategyDescription() {
        return this.listLayoutStrategyDescription;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription.
     * @generated
     */
    public org.eclipse.sirius.components.view.diagram.ListLayoutStrategyDescription build() {
        return this.getListLayoutStrategyDescription();
    }

    /**
     * Setter for AreChildNodesDraggableExpression.
     *
     * @generated
     */
    public ListLayoutStrategyDescriptionBuilder areChildNodesDraggableExpression(java.lang.String value) {
        this.getListLayoutStrategyDescription().setAreChildNodesDraggableExpression(value);
        return this;
    }


}

