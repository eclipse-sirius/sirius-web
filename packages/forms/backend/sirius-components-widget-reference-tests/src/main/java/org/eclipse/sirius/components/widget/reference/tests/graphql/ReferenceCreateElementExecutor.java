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

import org.eclipse.sirius.components.collaborative.widget.reference.dto.CreateElementInput;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TestTransaction;

/**
 * Used to execute element creation in references and perform assertions on its result.
 *
 * @author tgiraudet
 */
@Service
public class ReferenceCreateElementExecutor {

    private final ReferenceCreateElementMutationRunner referenceCreateElementMutationRunner;

    public ReferenceCreateElementExecutor(ReferenceCreateElementMutationRunner referenceCreateElementMutationRunner) {
        this.referenceCreateElementMutationRunner = Objects.requireNonNull(referenceCreateElementMutationRunner);
    }

    public ReferenceCreateElementAssert execute(CreateElementInput input) {
        var result = this.referenceCreateElementMutationRunner.run(input);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        return new ReferenceCreateElementAssert(result);
    }
}
