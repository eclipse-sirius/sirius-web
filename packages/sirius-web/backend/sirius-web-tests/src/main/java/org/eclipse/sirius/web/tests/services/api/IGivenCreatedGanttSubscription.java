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
package org.eclipse.sirius.web.tests.services.api;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.gantt.dto.GanttRefreshedEventPayload;

import reactor.core.publisher.Flux;

/**
 * Used to create a gantt and subscribe to it.
 *
 * @author sbegaudeau
 */
public interface IGivenCreatedGanttSubscription {
    Flux<GanttRefreshedEventPayload> createAndSubscribe(CreateRepresentationInput input);
}
