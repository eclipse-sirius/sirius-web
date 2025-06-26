/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.components.diagrams.components;

import java.util.Objects;

import org.eclipse.sirius.components.diagrams.description.OutsideLabelDescription;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The properties of the outside label component.
 *
 * @author frouene
 */
public class OutsideLabelComponentProps implements IProps {

    private final VariableManager variableManager;

    private final OutsideLabelDescription outsideLabelDescription;

    private final String parentNodeId;

    public OutsideLabelComponentProps(VariableManager variableManager, OutsideLabelDescription labelDescription, String parentNodeId) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.outsideLabelDescription = Objects.requireNonNull(labelDescription);
        this.parentNodeId = Objects.requireNonNull(parentNodeId);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public OutsideLabelDescription getOutsideLabelDescription() {
        return this.outsideLabelDescription;
    }

    public String getParentNodeId() {
        return this.parentNodeId;
    }
}
