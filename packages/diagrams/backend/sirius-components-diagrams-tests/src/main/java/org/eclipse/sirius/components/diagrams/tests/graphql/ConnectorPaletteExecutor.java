/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.diagrams.tests.graphql;

import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;

/**
 * Used to retrieve a connector palette and perform assertions on its result.
 *
 * @author mcharfadi
 */
@Service
public class ConnectorPaletteExecutor {

    private final ConnectorPaletteQueryRunner connectorPaletteQueryRunner;

    public ConnectorPaletteExecutor(ConnectorPaletteQueryRunner connectorPaletteQueryRunner) {
        this.connectorPaletteQueryRunner = Objects.requireNonNull(connectorPaletteQueryRunner);
    }

    public ConnectorPaletteAssert execute(String editingContextId, String representationId, String sourceDiagramElementId, String targetDiagramElementId) {
        Map<String, Object> variables = Map.of(
                "editingContextId", editingContextId,
                "representationId", representationId,
                "sourceDiagramElementId", sourceDiagramElementId,
                "targetDiagramElementId", targetDiagramElementId
        );
        String result = this.connectorPaletteQueryRunner.run(variables).data();
        return new ConnectorPaletteAssert(result);
    }
}
