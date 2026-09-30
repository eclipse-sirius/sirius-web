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

import org.eclipse.sirius.components.collaborative.diagrams.dto.EditRectangularNodeAppearanceInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.RectangularNodeAppearanceInput;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TestTransaction;

/**
 * Used to edit the rectangular node appearance and perform assertions on its result.
 *
 * @author gcoutable
 */
@Service
public class EditRectangularNodeAppearanceExecutor {

    private final EditRectangularNodeAppearanceMutationRunner editRectangularNodeAppearanceMutationRunner;

    public EditRectangularNodeAppearanceExecutor(EditRectangularNodeAppearanceMutationRunner editRectangularNodeAppearanceMutationRunner) {
        this.editRectangularNodeAppearanceMutationRunner = Objects.requireNonNull(editRectangularNodeAppearanceMutationRunner);
    }

    public EditRectangularNodeAppearanceAssert execute(String editingContextId, String representationId, List<String> nodeIds, RectangularNodeAppearanceInput appearanceInput) {
        var input = new EditRectangularNodeAppearanceInput(
                UUID.randomUUID(),
                editingContextId,
                representationId,
                nodeIds,
                appearanceInput
        );

        String result = this.editRectangularNodeAppearanceMutationRunner.run(input).data();

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
        
        return new EditRectangularNodeAppearanceAssert(result);
    }

}
