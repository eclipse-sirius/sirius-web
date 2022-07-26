/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.view.emf.diagram;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;

/**
 * Represents the context used while converting a View DiagramDescription.
 *
 * @author fbarbin
 */
public class ViewDiagramDescriptionConverterContext {

    private final Map<org.eclipse.sirius.components.view.NodeDescription, NodeDescription> convertedNodes;

    private final Map<org.eclipse.sirius.components.view.EdgeDescription, EdgeDescription> convertedEdges;

    private final AQLInterpreter interpreter;

    public ViewDiagramDescriptionConverterContext(AQLInterpreter interpreter) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.convertedNodes = new HashMap<>();
        this.convertedEdges = new HashMap<>();
    }

    public Map<org.eclipse.sirius.components.view.EdgeDescription, EdgeDescription> getConvertedEdges() {
        return this.convertedEdges;
    }

    public Map<org.eclipse.sirius.components.view.NodeDescription, NodeDescription> getConvertedNodes() {
        return this.convertedNodes;
    }

    public AQLInterpreter getInterpreter() {
        return this.interpreter;
    }

}
