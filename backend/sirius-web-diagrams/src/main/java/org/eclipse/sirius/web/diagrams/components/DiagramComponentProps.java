/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.diagrams.components;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.components.IProps;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * The properties of a diagram component.
 *
 * @author sbegaudeau
 */
public class DiagramComponentProps implements IProps {
    private final VariableManager variableManager;

    private final DiagramDescription diagramDescription;

    private final Optional<Diagram> prevDiagram;

    public DiagramComponentProps(VariableManager variableManager, DiagramDescription diagramDescription, Optional<Diagram> prevDiagram) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.diagramDescription = Objects.requireNonNull(diagramDescription);
        this.prevDiagram = Objects.requireNonNull(prevDiagram);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public DiagramDescription getDiagramDescription() {
        return this.diagramDescription;
    }

    public Optional<Diagram> getPrevDiagram() {
        return this.prevDiagram;
    }
}
