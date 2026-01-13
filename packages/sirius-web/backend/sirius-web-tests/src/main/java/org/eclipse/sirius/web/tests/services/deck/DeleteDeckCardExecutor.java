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
package org.eclipse.sirius.web.tests.services.deck;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.deck.dto.input.DeleteDeckCardInput;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TestTransaction;

/**
 * Used to execute the deletion of a deck card and perform assertions on its result.
 *
 * @author sbegaudeau
 * @since v2026.9.0
 */
@Service
public class DeleteDeckCardExecutor {

    private final DeleteDeckCardMutationRunner deleteDeckCardMutationRunner;

    public DeleteDeckCardExecutor(DeleteDeckCardMutationRunner deleteDeckCardMutationRunner) {
        this.deleteDeckCardMutationRunner = Objects.requireNonNull(deleteDeckCardMutationRunner);
    }

    public DeleteDeckCardAssert execute(DeleteDeckCardInput input) {
        var result = this.deleteDeckCardMutationRunner.run(input);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        return new DeleteDeckCardAssert(result);
    }
}
