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
package org.eclipse.sirius.components.diagrams.components;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.description.LabelDescription;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The properties of the inside label component.
 *
 * @author gcoutable
 */
public class InsideLabelComponentProps implements IProps {

    private final VariableManager variableManager;

    private final LabelDescription labelDescription;

    private final Optional<InsideLabel> optionalPreviousInsideLabel;

    private final String type;

    public InsideLabelComponentProps(VariableManager variableManager, LabelDescription labelDescription, Optional<InsideLabel> optionalPreviousInsideLabel, String type) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.labelDescription = Objects.requireNonNull(labelDescription);
        this.optionalPreviousInsideLabel = Objects.requireNonNull(optionalPreviousInsideLabel);
        this.type = Objects.requireNonNull(type);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public LabelDescription getLabelDescription() {
        return this.labelDescription;
    }

    public Optional<InsideLabel> getPreviousInsideLabel() {
        return this.optionalPreviousInsideLabel;
    }

    public String getType() {
        return this.type;
    }

}
