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
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.dto.toolbar.tools.InvokeFilterSelectionInput;
import org.springframework.stereotype.Service;

/**
 * Used to invoke a FilterSelectionMenuItems & perform assertions on its result.
 *
 * @author mcharfadi
 */
@Service
public class InvokeFilterSelectionMenuItemsExecutor {

    private final InvokeFilterSelectionActionMutationRunner invokeFilterSelectionActionMutationRunner;

    public InvokeFilterSelectionMenuItemsExecutor(InvokeFilterSelectionActionMutationRunner invokeFilterSelectionActionMutationRunner) {
        this.invokeFilterSelectionActionMutationRunner = Objects.requireNonNull(invokeFilterSelectionActionMutationRunner);
    }

    public InvokeFilterSelectionMenuItemsAssert execute(String editingContextId, String representationId, List<String> diagramElementIds, String filterSelectionId) {
        InvokeFilterSelectionInput input = new InvokeFilterSelectionInput(UUID.randomUUID(), editingContextId, representationId, diagramElementIds, filterSelectionId);
        String result = this.invokeFilterSelectionActionMutationRunner.run(input).data();
        return new InvokeFilterSelectionMenuItemsAssert(result);
    }
}
