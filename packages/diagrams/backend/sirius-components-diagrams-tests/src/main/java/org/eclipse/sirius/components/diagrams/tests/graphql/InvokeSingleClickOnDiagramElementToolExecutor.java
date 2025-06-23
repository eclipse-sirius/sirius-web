/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolVariable;
import org.springframework.stereotype.Service;

/**
 * Used to execute a single click on diagram element tool and perform assertions on its result.
 *
 * @author sbegaudeau
 */
@Service
public class InvokeSingleClickOnDiagramElementToolExecutor {

    private final InvokeSingleClickOnDiagramElementToolMutationRunner invokeSingleClickOnDiagramElementToolMutationRunner;

    public InvokeSingleClickOnDiagramElementToolExecutor(InvokeSingleClickOnDiagramElementToolMutationRunner invokeSingleClickOnDiagramElementToolMutationRunner) {
        this.invokeSingleClickOnDiagramElementToolMutationRunner = Objects.requireNonNull(invokeSingleClickOnDiagramElementToolMutationRunner);
    }

    public InvokeSingleClickOnDiagramElementToolAssert execute(String editingContextId, String representationId, String diagramElementId, String toolId, double startingPositionX, double startingPositionY, List<ToolVariable> variables) {
        var input = new InvokeSingleClickOnDiagramElementToolInput(
                UUID.randomUUID(),
                editingContextId,
                representationId,
                diagramElementId,
                toolId,
                startingPositionX,
                startingPositionY,
                variables);
        String result = this.invokeSingleClickOnDiagramElementToolMutationRunner.run(input);
        return new InvokeSingleClickOnDiagramElementToolAssert(result);
    }
}
