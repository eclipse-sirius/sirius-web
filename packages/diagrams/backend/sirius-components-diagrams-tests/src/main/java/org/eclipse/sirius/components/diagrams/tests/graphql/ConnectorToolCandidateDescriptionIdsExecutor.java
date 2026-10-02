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
 * Used to retrieve connector tool candidates and perform assertions on their result.
 *
 * @author mcharfadi
 */
@Service
public class ConnectorToolCandidateDescriptionIdsExecutor {

    private final ConnectorToolCandidateDescriptionIdsQueryRunner connectorToolCandidateDescriptionIdsQueryRunner;

    public ConnectorToolCandidateDescriptionIdsExecutor(ConnectorToolCandidateDescriptionIdsQueryRunner connectorToolCandidateDescriptionIdsQueryRunner) {
        this.connectorToolCandidateDescriptionIdsQueryRunner = Objects.requireNonNull(connectorToolCandidateDescriptionIdsQueryRunner);
    }

    public ConnectorToolCandidateDescriptionIdsAssert execute(String editingContextId, String diagramId, String diagramElementId) {
        Map<String, Object> variables = Map.of(
                "editingContextId", editingContextId,
                "diagramId", diagramId,
                "diagramElementId", diagramElementId
        );
        String result = this.connectorToolCandidateDescriptionIdsQueryRunner.run(variables).data();
        return new ConnectorToolCandidateDescriptionIdsAssert(result);
    }
}
