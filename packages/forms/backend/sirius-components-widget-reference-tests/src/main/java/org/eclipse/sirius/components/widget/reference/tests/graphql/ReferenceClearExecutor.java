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
package org.eclipse.sirius.components.widget.reference.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.widget.reference.dto.ClearReferenceInput;
import org.springframework.stereotype.Service;

/**
 * Executes reference clearing mutations.
 *
 * @author tgiraudet
 */
@Service
public class ReferenceClearExecutor {

    private final ReferenceClearMutationRunner runner;

    public ReferenceClearExecutor(ReferenceClearMutationRunner runner) {
        this.runner = Objects.requireNonNull(runner);
    }

    public ReferenceClearAssert execute(ClearReferenceInput input) {
        return new ReferenceClearAssert(this.runner.run(input));
    }
}

