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
package org.eclipse.sirius.web.papaya.omnibox;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.dto.EditingContextRepresentationDescriptionsInput;
import org.eclipse.sirius.components.collaborative.dto.EditingContextRepresentationDescriptionsPayload;
import org.eclipse.sirius.components.collaborative.omnibox.api.IWorkbenchOmniboxCommandProvider;
import org.eclipse.sirius.components.collaborative.omnibox.dto.OmniboxCommand;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.springframework.stereotype.Service;

/**
 * Used to allow the creation of representations.
 *
 * @author sbegaudeau
 */
@Service
public class CreateRepresentationCommandProvider implements IWorkbenchOmniboxCommandProvider {

    public static final String ACTION_PREFIX = "create-representation#";

    private final IEditingContextDispatcher editingContextDispatcher;

    public CreateRepresentationCommandProvider(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public List<OmniboxCommand> getCommands(String editingContextId, List<String> selectedObjectIds, String query) {
        if (!selectedObjectIds.isEmpty()) {
            var input = new EditingContextRepresentationDescriptionsInput(UUID.randomUUID(), editingContextId, selectedObjectIds.get(0));
            return this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input)
                    .map(payload -> {
                        if (!(payload instanceof  EditingContextRepresentationDescriptionsPayload)) {
                            return new EditingContextRepresentationDescriptionsPayload(input.id(), List.of());
                        }
                        return payload;
                    })
                    .filter(EditingContextRepresentationDescriptionsPayload.class::isInstance)
                    .map(EditingContextRepresentationDescriptionsPayload.class::cast)
                    .map(this::toCommands)
                    .block();
        }
        return List.of();
    }

    private List<OmniboxCommand> toCommands(EditingContextRepresentationDescriptionsPayload payload) {
        return payload.representationDescriptions().stream()
                .map(representationDescriptionMetadata -> new OmniboxCommand(ACTION_PREFIX + representationDescriptionMetadata.getId(), "Create a new " + representationDescriptionMetadata.getLabel(), List.of("/omnibox/create.svg"), ""))
                .toList();
    }
}
