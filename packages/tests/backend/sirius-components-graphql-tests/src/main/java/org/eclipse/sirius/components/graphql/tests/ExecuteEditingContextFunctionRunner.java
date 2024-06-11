/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.graphql.tests;

import java.util.Objects;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.tests.api.IExecuteEditingContextFunctionRunner;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

/**
 * Used to run a function using the editing context.
 *
 * @author sbegaudeau
 */
@Service
public class ExecuteEditingContextFunctionRunner implements IExecuteEditingContextFunctionRunner {

    private final IEditingContextDispatcher editingContextDispatcher;

    public ExecuteEditingContextFunctionRunner(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public Mono<IPayload> execute(ExecuteEditingContextFunctionInput input) {
        return this.editingContextDispatcher.dispatchMutation(input.editingContextId(), input);
    }
}
