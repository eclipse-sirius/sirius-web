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
package org.eclipse.sirius.web.tests.undoredo;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.application.undo.dto.UndoInput;
import org.springframework.stereotype.Service;

/**
 * Used to execute an undo and perform assertions on its result.
 *
 * @author tgiraudet
 */
@Service
public class UndoExecutor {

    private final UndoMutationRunner undoMutationRunner;

    public UndoExecutor(UndoMutationRunner undoMutationRunner) {
        this.undoMutationRunner = Objects.requireNonNull(undoMutationRunner);
    }

    public UndoAssert execute(String editingContextId, UUID mutationId) {
        var input = new UndoInput(UUID.randomUUID(), editingContextId, mutationId);
        String result = this.undoMutationRunner.run(input).data();
        return new UndoAssert(result);
    }
}
