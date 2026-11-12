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
package org.eclipse.sirius.components.tables.tests.graphql;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.tables.dto.ResizeTableRowsInput;
import org.eclipse.sirius.components.collaborative.tables.dto.TableRowHeightInput;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TestTransaction;

/**
 * Used to resize the table row heights.
 *
 * @author lfasani
 */
@Service
public class ResizeTableRowsExecutor {

    private final ResizeTableRowsMutationRunner resizeTableRowsMutationRunner;

    public ResizeTableRowsExecutor(ResizeTableRowsMutationRunner resizeTableRowsMutationRunner) {
        this.resizeTableRowsMutationRunner = Objects.requireNonNull(resizeTableRowsMutationRunner);
    }

    public ResizeTableRowsAssert execute(String editingContextId, String representationId, List<TableRowHeightInput> rowHeightInputs) {
        var input = new ResizeTableRowsInput(
                UUID.randomUUID(),
                editingContextId,
                representationId,
                representationId,
                rowHeightInputs
        );

        String result = this.resizeTableRowsMutationRunner.run(input).data();

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();
        
        return new ResizeTableRowsAssert(result);
    }
}
