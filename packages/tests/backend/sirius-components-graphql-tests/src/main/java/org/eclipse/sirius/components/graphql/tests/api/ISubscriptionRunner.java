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

import org.eclipse.sirius.components.core.api.IInput;

import reactor.core.publisher.Flux;

/**
 * Interface used to run GraphQL subscriptions.
 *
 * @param <T> the input of the subscription
 *
 * @author gdaniel
 */
public interface ISubscriptionRunner<T extends IInput> {

    Flux<Object> run(T input);

}
