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
package org.eclipse.sirius.web.spring.collaborative.diagrams.handlers;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.collaborative.diagrams.api.DiagramCreationParameters;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramRefreshManager;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Node;

/**
 * Implementation of the diagram service which does nothing.
 *
 * @author sbegaudeau
 */
public class NoOpDiagramService implements IDiagramService {

    @Override
    public Diagram create(DiagramCreationParameters parameters) {
        return null;
    }

    @Override
    public Diagram refresh(DiagramCreationParameters parameters, IDiagramRefreshManager refreshManager) {
        return null;
    }

    @Override
    public Optional<Diagram> findById(UUID diagramId) {
        return Optional.empty();
    }

    @Override
    public Optional<Node> findNodeById(Diagram diagram, String nodeId) {
        return Optional.empty();
    }
}
