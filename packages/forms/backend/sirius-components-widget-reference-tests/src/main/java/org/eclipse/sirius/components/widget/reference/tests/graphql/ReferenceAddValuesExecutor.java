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

import org.eclipse.sirius.components.collaborative.widget.reference.dto.AddReferenceValuesInput;
import org.springframework.stereotype.Service;

/**
 * Executes reference value addition mutations.
 *
 * @author tgiraudet
 */
@Service
public class ReferenceAddValuesExecutor {

    private final ReferenceAddValuesMutationRunner runner;

    public ReferenceAddValuesExecutor(ReferenceAddValuesMutationRunner runner) {
        this.runner = Objects.requireNonNull(runner);
    }

    public ReferenceAddValuesAssert execute(AddReferenceValuesInput input) {
        return new ReferenceAddValuesAssert(this.runner.run(input));
    }
}
