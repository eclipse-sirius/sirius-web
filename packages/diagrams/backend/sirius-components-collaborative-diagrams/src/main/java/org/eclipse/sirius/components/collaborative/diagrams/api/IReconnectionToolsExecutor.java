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
package org.eclipse.sirius.components.collaborative.diagrams.api;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeKind;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * Provide the list of reconnection tools for a given diagram element.
 *
 * @author gcoutable
 */
public interface IReconnectionToolsExecutor {

    boolean canExecute(DiagramDescription diagramDescription);

    IStatus execute(IEditingContext editingContext, ReconnectionToolInterpreterData toolInterpreterData, Edge edge, EdgeDescription edgeDescription, ReconnectEdgeKind reconnectEdgeKind,
            DiagramDescription diagramDescription);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author gcoutable
     */
    class NoOp implements IReconnectionToolsExecutor {

        @Override
        public boolean canExecute(DiagramDescription diagramDescription) {
            return false;
        }

        @Override
        public IStatus execute(IEditingContext editingContext, ReconnectionToolInterpreterData toolInterpreterData, Edge edge, EdgeDescription edgeDescription, ReconnectEdgeKind reconnectEdgeKind,
                DiagramDescription diagramDescription) {
            return null;
        }

    }
}
