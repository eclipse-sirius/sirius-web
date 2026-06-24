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
 * Retrieves layout configurations and provides assertions on the query result.
 *
 * @author frouene
 * @since v2026.12.0
 */
@Service
public class GetLayoutConfigurationExecutor {

    private final GetLayoutConfigurationQueryRunner queryRunner;

    public GetLayoutConfigurationExecutor(GetLayoutConfigurationQueryRunner queryRunner) {
        this.queryRunner = Objects.requireNonNull(queryRunner);
    }

    public GetLayoutConfigurationAssert execute(String editingContextId, String diagramId) {
        Map<String, Object> variables = Map.of(
                "editingContextId", editingContextId,
                "diagramId", diagramId
        );
        return new GetLayoutConfigurationAssert(this.queryRunner.run(variables).data());
    }
}
