/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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

import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;

/**
 * Interface used to create a specific representation event processor.
 *
 * @author sbegaudeau
 */
public interface IRepresentationEventProcessorFactory {
    boolean canHandle(IEditingContext editingContext, String representationId);

    Optional<IRepresentationEventProcessor> createRepresentationEventProcessor(IEditingContext editingContext, String representationId);
}
