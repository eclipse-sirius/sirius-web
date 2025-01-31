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
 * Builder for org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription.
 *
 * @author BuilderGenerator
 * @generated
 */
public abstract class LayoutStrategyDescriptionBuilder {

    /**
     * Builder for org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription.
     * @generated
     */
    protected abstract org.eclipse.sirius.components.view.diagram.LayoutStrategyDescription getLayoutStrategyDescription();

    /**
     * Setter for OnWestAtCreationBorderNodes.
     *
     * @generated
     */
    public LayoutStrategyDescriptionBuilder onWestAtCreationBorderNodes(org.eclipse.sirius.components.view.diagram.NodeDescription ... values) {
        for (org.eclipse.sirius.components.view.diagram.NodeDescription value : values) {
            this.getLayoutStrategyDescription().getOnWestAtCreationBorderNodes().add(value);
        }
        return this;
    }

    /**
     * Setter for OnEastAtCreationBorderNodes.
     *
     * @generated
     */
    public LayoutStrategyDescriptionBuilder onEastAtCreationBorderNodes(org.eclipse.sirius.components.view.diagram.NodeDescription ... values) {
        for (org.eclipse.sirius.components.view.diagram.NodeDescription value : values) {
            this.getLayoutStrategyDescription().getOnEastAtCreationBorderNodes().add(value);
        }
        return this;
    }

    /**
     * Setter for OnSouthAtCreationBorderNodes.
     *
     * @generated
     */
    public LayoutStrategyDescriptionBuilder onSouthAtCreationBorderNodes(org.eclipse.sirius.components.view.diagram.NodeDescription ... values) {
        for (org.eclipse.sirius.components.view.diagram.NodeDescription value : values) {
            this.getLayoutStrategyDescription().getOnSouthAtCreationBorderNodes().add(value);
        }
        return this;
    }

    /**
     * Setter for OnNorthAtCreationBorderNodes.
     *
     * @generated
     */
    public LayoutStrategyDescriptionBuilder onNorthAtCreationBorderNodes(org.eclipse.sirius.components.view.diagram.NodeDescription ... values) {
        for (org.eclipse.sirius.components.view.diagram.NodeDescription value : values) {
            this.getLayoutStrategyDescription().getOnNorthAtCreationBorderNodes().add(value);
        }
        return this;
    }


}

