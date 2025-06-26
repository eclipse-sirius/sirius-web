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
package org.eclipse.sirius.components.collaborative.representations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.representations.api.IRepresentationEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.editingcontext.RepresentationEventProcessorEntry;
import org.springframework.stereotype.Service;

/**
 * Used as a registry of all RepresentationEventProcessor.
 *
 * @author mcharfadi
 */
@Service
public class RepresentationEventProcessorRegistry implements IRepresentationEventProcessorRegistry {

    private final Map<RepresentationEventProcessorKey, RepresentationEventProcessorEntry> representationEventProcessors = new ConcurrentHashMap<>();

    @Override
    public void put(String editingContextId, String representationId, RepresentationEventProcessorEntry entry) {
        var key = new RepresentationEventProcessorKey(editingContextId, representationId);
        this.representationEventProcessors.put(key, entry);
    }

    @Override
    public RepresentationEventProcessorEntry get(String editingContextId, String representationId) {
        var key = new RepresentationEventProcessorKey(editingContextId, representationId);
        return this.representationEventProcessors.get(key);
    }

    @Override
    public List<IRepresentationEventProcessor> values(String editingContextId) {
        return this.representationEventProcessors.entrySet()
                .stream().filter(representationEventProcessorEntry -> representationEventProcessorEntry.getKey().editingContextId().equals(editingContextId))
                .map(Map.Entry::getValue)
                .map(RepresentationEventProcessorEntry::getRepresentationEventProcessor)
                .toList();
    }

    @Override
    public void disposeRepresentation(String editingContextId, String representationId) {
        var key = new RepresentationEventProcessorKey(editingContextId, representationId);
        Optional.ofNullable(this.representationEventProcessors.remove(key)).ifPresent(RepresentationEventProcessorEntry::dispose);
    }

    @Override
    public void dispose(String editingContextId) {
        List<RepresentationEventProcessorKey> keysToRemove = new ArrayList<>();
        this.representationEventProcessors.entrySet()
                .stream().filter(representationEventProcessorEntry -> representationEventProcessorEntry.getKey().editingContextId().equals(editingContextId))
                .forEach(representationEventProcessorEntry -> {
                    keysToRemove.add(representationEventProcessorEntry.getKey());
                    representationEventProcessorEntry.getValue().dispose();
                });

        keysToRemove.forEach(this.representationEventProcessors::remove);
    }

}
