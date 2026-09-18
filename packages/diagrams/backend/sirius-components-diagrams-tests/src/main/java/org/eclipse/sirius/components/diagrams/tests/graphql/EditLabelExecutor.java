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

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.dto.EditLabelInput;
import org.springframework.stereotype.Service;

/**
 * Used to execute the label edit tool and perform assertions on its result.
 *
 * @author gcoutable
 */
@Service
public class EditLabelExecutor {

    private final EditLabelMutationRunner editLabelMutationRunner;

    public EditLabelExecutor(EditLabelMutationRunner editLabelMutationRunner) {
        this.editLabelMutationRunner = Objects.requireNonNull(editLabelMutationRunner);
    }

    public EditLabelAssert execute(String editingContextId, String representationId, String labelId, String newText) {
        var input = new EditLabelInput(UUID.randomUUID(), editingContextId, representationId, labelId, newText);
        String result = this.editLabelMutationRunner.run(input).data();
        return new EditLabelAssert(result);
    }
}
