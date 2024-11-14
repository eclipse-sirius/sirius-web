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
package org.eclipse.sirius.components.collaborative.api;

import java.util.concurrent.ExecutorService;

import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;

import reactor.core.publisher.Mono;

/**
 * Used to execute editing context event handlers in an executor service.
 *
 * @author gcoutable
 */
public interface IEditingContextExecutor {

    String INPUT = "INPUT";

    ExecutorService getExecutorService();

    Mono<IPayload> handle(IInput input);

    void dispose();
}
