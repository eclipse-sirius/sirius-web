/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.collaborative.representations.api;

import reactor.core.scheduler.Scheduler;

/**
 * Used to provide a different scheduler for subscriptions.
 *
 * <p>
 * Thanks to this API, it will be easier to customize the thread management strategy of subscriptions.
 * </p>
 *
 * @author gcoutable
 */
public interface IEventProcessorSubscriptionSchedulerProvider {
    Scheduler getScheduler(String editingContextId);
}
