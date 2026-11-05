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
package org.eclipse.sirius.web.tests.services.tables;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.tables.dto.EditTextfieldCellInput;
import org.eclipse.sirius.components.tables.tests.graphql.EditTextfieldCellMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Executes textfield cell edits and provides assertions on their result.
 *
 * @author frouene
 */
@Service
public class EditTextfieldCellExecutor {

    private final EditTextfieldCellMutationRunner editTextfieldCellMutationRunner;

    public EditTextfieldCellExecutor(EditTextfieldCellMutationRunner editTextfieldCellMutationRunner) {
        this.editTextfieldCellMutationRunner = Objects.requireNonNull(editTextfieldCellMutationRunner);
    }

    public EditTextfieldCellAssert execute(EditTextfieldCellInput input) {
        return new EditTextfieldCellAssert(this.editTextfieldCellMutationRunner.run(input));
    }
}

