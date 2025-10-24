/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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

import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.description.LabelDescription;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The properties of the label component.
 *
 * @author sbegaudeau
 */
public class LabelComponentProps implements IProps {

    private final VariableManager variableManager;

    private final LabelDescription labelDescription;

    private final String type;

    private final String parentEdgeId;

    private final String position;

    private final Optional<Label> previousLabel;

    private List<IDiagramEvent> diagramEvents;

    public LabelComponentProps(VariableManager variableManager, LabelDescription labelDescription, String type, String parentEdgeId, String position, Optional<Label> previousLabel, List<IDiagramEvent> diagramEvents) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.labelDescription = Objects.requireNonNull(labelDescription);
        this.type = Objects.requireNonNull(type);
        this.parentEdgeId = Objects.requireNonNull(parentEdgeId);
        this.position = Objects.requireNonNull(position);
        this.previousLabel = Objects.requireNonNull(previousLabel);
        this.diagramEvents = Objects.requireNonNull(diagramEvents);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public LabelDescription getLabelDescription() {
        return this.labelDescription;
    }

    public String getType() {
        return this.type;
    }

    public String getParentEdgeId() {
        return this.parentEdgeId;
    }

    public String getPosition() {
        return this.position;
    }

    public Optional<Label> getPreviousLabel() {
        return this.previousLabel;
    }

    public List<IDiagramEvent> getDiagramEvents() {
        return this.diagramEvents;
    }
}
