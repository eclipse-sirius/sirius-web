/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.graphql.api;

import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;

import reactor.core.publisher.Mono;

/**
 * Used to wrap the editing context event processor registry dispatch mechanism.
 *
 * @author sbegaudeau
 */
public interface IEditingContextDispatcher {

    Mono<IPayload> dispatchQuery(String editingContextId, IInput input);

    Mono<IPayload> dispatchMutation(String editingContextId, IInput input);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author sbegaudeau
     */
    class NoOp implements IEditingContextDispatcher {

        @Override
        public Mono<IPayload> dispatchQuery(String editingContextId, IInput input) {
            return Mono.empty();
        }

        @Override
        public Mono<IPayload> dispatchMutation(String editingContextId, IInput input) {
            return Mono.empty();
        }

    }
}
