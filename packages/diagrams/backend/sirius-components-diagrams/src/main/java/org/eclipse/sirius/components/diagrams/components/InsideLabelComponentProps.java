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
package org.eclipse.sirius.components.diagrams.components;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.description.InsideLabelDescription;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
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

    private final Optional<InsideLabel> previousLabel;

    private List<IDiagramEvent> diagramEvents;

    public InsideLabelComponentProps(VariableManager variableManager, InsideLabelDescription labelDescription, Optional<InsideLabel> previousLabel, List<IDiagramEvent> diagramEvents) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.insideLabelDescription = Objects.requireNonNull(labelDescription);
        this.previousLabel = Objects.requireNonNull(previousLabel);
        this.diagramEvents = Objects.requireNonNull(diagramEvents);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public InsideLabelDescription getInsideLabelDescription() {
        return this.insideLabelDescription;
    }

    public Optional<InsideLabel> getPreviousLabel() {
        return this.previousLabel;
    }

    public List<IDiagramEvent> getDiagramEvents() {
        return this.diagramEvents;
    }
}
