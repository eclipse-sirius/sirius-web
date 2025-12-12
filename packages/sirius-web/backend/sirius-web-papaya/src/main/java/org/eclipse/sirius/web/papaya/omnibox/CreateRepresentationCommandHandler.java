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

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationSuccessPayload;
import org.eclipse.sirius.components.collaborative.omnibox.dto.ExecuteWorkbenchOmniboxCommandInput;
import org.eclipse.sirius.components.collaborative.omnibox.api.IWorkbenchOmniboxCommandHandler;
import org.eclipse.sirius.components.collaborative.omnibox.dto.ExecuteWorkbenchOmniboxCommandSuccessPayload;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.representations.WorkbenchSelection;
import org.eclipse.sirius.components.representations.WorkbenchSelectionEntry;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

/**
 * Used to perform the creation of a new representation.
 *
 * @author sbegaudeau
 */
@Service
public class CreateRepresentationCommandHandler implements IWorkbenchOmniboxCommandHandler {

    private final IMessageService messageService;

    private final IEditingContextDispatcher editingContextDispatcher;

    public CreateRepresentationCommandHandler(IMessageService messageService, IEditingContextDispatcher editingContextDispatcher) {
        this.messageService = Objects.requireNonNull(messageService);
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public boolean canHandle(ExecuteWorkbenchOmniboxCommandInput input) {
        return input.commandId().startsWith(CreateRepresentationCommandProvider.ACTION_PREFIX) && !input.selectedObjectIds().isEmpty();
    }

    @Override
    public IPayload handle(ExecuteWorkbenchOmniboxCommandInput input) {
        if (input.commandId().length() > CreateRepresentationCommandProvider.ACTION_PREFIX.length() && !input.selectedObjectIds().isEmpty()) {
            var representationDescriptionId = input.commandId().substring(CreateRepresentationCommandProvider.ACTION_PREFIX.length());
            var objectId = input.selectedObjectIds().get(0);

            var createRepresentationInput = new CreateRepresentationInput(input.id(), input.editingContextId(), representationDescriptionId, objectId, "New representation");
            return this.editingContextDispatcher.dispatchMutation(input.editingContextId(), createRepresentationInput)
                    .map(payload -> {
                        if (payload instanceof CreateRepresentationSuccessPayload createRepresentationSuccessPayload) {
                            List<WorkbenchSelectionEntry> entries = List.of(
                                    new WorkbenchSelectionEntry(createRepresentationSuccessPayload.representation().id())
                            );
                            var workbenchSelection = new WorkbenchSelection(entries);
                            return new ExecuteWorkbenchOmniboxCommandSuccessPayload(createRepresentationSuccessPayload.id(), workbenchSelection, List.of());
                        }
                        return new ErrorPayload(payload.id(), this.messageService.unexpectedError());
                    }).block();
        }

        return new ErrorPayload(input.id(), this.messageService.unexpectedError());
    }
}
