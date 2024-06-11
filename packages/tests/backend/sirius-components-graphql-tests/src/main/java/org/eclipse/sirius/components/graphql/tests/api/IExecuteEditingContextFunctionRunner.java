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
package org.eclipse.sirius.components.graphql.tests.api;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionInput;

import reactor.core.publisher.Mono;

/**
 * Used to execute some function using the editing context.
 *
 * @author sbegaudeau
 */
public interface IExecuteEditingContextFunctionRunner {
    Mono<IPayload> execute(ExecuteEditingContextFunctionInput input);
}
