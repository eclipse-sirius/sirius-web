/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo and others.
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

import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.components.representations.IOperationValidator;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Properties of the edge component.
 *
 * @author sbegaudeau
 */
public class EdgeComponentProps implements IProps {

    private final VariableManager variableManager;

    private final EdgeDescription edgeDescription;

    private final IEdgesRequestor edgesRequestor;

    private final DiagramRenderingCache cache;

    private final IOperationValidator operationValidator;

    private final List<IDiagramEvent> diagramEvents;

    public EdgeComponentProps(VariableManager variableManager, EdgeDescription edgeDescription, IEdgesRequestor edgesRequestor, DiagramRenderingCache cache, IOperationValidator operationValidator, List<IDiagramEvent> diagramEvents) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.edgeDescription = Objects.requireNonNull(edgeDescription);
        this.edgesRequestor = Objects.requireNonNull(edgesRequestor);
        this.cache = Objects.requireNonNull(cache);
        this.operationValidator = Objects.requireNonNull(operationValidator);
        this.diagramEvents = Objects.requireNonNull(diagramEvents);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public EdgeDescription getEdgeDescription() {
        return this.edgeDescription;
    }

    public IEdgesRequestor getEdgesRequestor() {
        return this.edgesRequestor;
    }

    public DiagramRenderingCache getCache() {
        return this.cache;
    }

    public IOperationValidator getOperationValidator() {
        return this.operationValidator;
    }

    public List<IDiagramEvent> getDiagramEvents() {
        return this.diagramEvents;
    }
}
