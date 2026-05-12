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

import org.eclipse.sirius.web.application.project.dto.CreateProjectInput;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TestTransaction;

/**
 * Used to execute the creation of the project and perform assertions on its result.
 *
 * @author sbegaudeau
 */
@Service
public class CreateProjectExecutor {

    private final CreateProjectMutationRunner createProjectMutationRunner;

    private final IMessageService messageService;

    public CreateProjectExecutor(CreateProjectMutationRunner createProjectMutationRunner, IMessageService messageService) {
        this.createProjectMutationRunner = Objects.requireNonNull(createProjectMutationRunner);
        this.messageService = Objects.requireNonNull(messageService);
    }

    public CreateProjectAssert execute(CreateProjectInput input, CapturedOutput capturedOutput) {
        var result = this.createProjectMutationRunner.run(input);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        return new CreateProjectAssert(this.messageService, input, result, capturedOutput);
    }
}
