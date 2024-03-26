/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import org.eclipse.sirius.components.diagrams.description.InsideLabelDescription;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The properties of the inside label component.
 *
 * @author gcoutable
 */
public class InsideLabelComponentProps implements IProps {

    private final VariableManager variableManager;

    private final InsideLabelDescription insideLabelDescription;


    public InsideLabelComponentProps(VariableManager variableManager, InsideLabelDescription labelDescription) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.insideLabelDescription = Objects.requireNonNull(labelDescription);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public InsideLabelDescription getInsideLabelDescription() {
        return this.insideLabelDescription;
    }

}
