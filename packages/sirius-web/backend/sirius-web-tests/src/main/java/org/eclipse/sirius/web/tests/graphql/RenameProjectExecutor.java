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
package org.eclipse.sirius.web.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.web.application.project.dto.RenameProjectInput;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TestTransaction;

/**
 * Used to execute the rename of a project and perform assertions on its result.
 *
 * @author gcoutable
 */
@Service
public class RenameProjectExecutor {

    private final RenameProjectMutationRunner renameProjectMutationRunner;

    public RenameProjectExecutor(RenameProjectMutationRunner renameProjectMutationRunner) {
        this.renameProjectMutationRunner = Objects.requireNonNull(renameProjectMutationRunner);
    }

    public RenameProjectAssert execute(RenameProjectInput input, CapturedOutput capturedOutput) {
        var result = this.renameProjectMutationRunner.run(input);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        return new RenameProjectAssert(input, result, capturedOutput);
    }
}
