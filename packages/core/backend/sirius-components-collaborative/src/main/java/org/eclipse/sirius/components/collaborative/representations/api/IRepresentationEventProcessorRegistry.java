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

import java.util.List;

import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.editingcontext.RepresentationEventProcessorEntry;

/**
 * Used as a registry of all RepresentationEventProcessor.
 *
 * @author mcharfadi
 */
public interface IRepresentationEventProcessorRegistry {

    void put(String editingContextId, String representationId, RepresentationEventProcessorEntry entry);

    RepresentationEventProcessorEntry get(String editingContextId, String representationId);

    List<IRepresentationEventProcessor> values(String editingContextId);

    void disposeRepresentation(String editingContextId, String representationId);

    void dispose(String editingContextId);

}
