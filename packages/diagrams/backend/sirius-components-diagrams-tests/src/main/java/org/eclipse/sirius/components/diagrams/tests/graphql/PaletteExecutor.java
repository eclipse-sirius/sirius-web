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

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;

/**
 * Used to retrieve a palette and perform assertions on its result.
 *
 * @author tgiraudet
 */
@Service
public class PaletteExecutor {

    private final PaletteQueryRunner paletteQueryRunner;

    public PaletteExecutor(PaletteQueryRunner paletteQueryRunner) {
        this.paletteQueryRunner = Objects.requireNonNull(paletteQueryRunner);
    }

    public PaletteAssert execute(String editingContextId, String representationId, List<String> diagramElementIds) {
        Map<String, Object> variables = Map.of(
                "editingContextId", editingContextId,
                "representationId", representationId,
                "diagramElementIds", diagramElementIds
        );
        String result = this.paletteQueryRunner.run(variables).data();
        return new PaletteAssert(result);
    }
}
