/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

package org.eclipse.sirius.components.emf.handlers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.dto.CreateChildInput;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.collaborative.omnibox.dto.OmniboxCommand;
import org.eclipse.sirius.components.collaborative.omnibox.dto.WorkbenchOmniboxSearchInput;
import org.eclipse.sirius.components.collaborative.omnibox.dto.WorkbenchOmniboxSearchPayload;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * The event handler to perform the omnibox search.
 *
 * @author gdaniel
 */
@Service
public class WorkbenchOmniboxSearchEventHandler implements IEditingContextEventHandler {

    private final ICollaborativeMessageService messageService;

    private final IIdentityService identityService;

    private final ILabelService labelService;

    private final Counter counter;

    public WorkbenchOmniboxSearchEventHandler(ICollaborativeMessageService messageService, IIdentityService identityService, ILabelService labelService, MeterRegistry meterRegistry) {
        this.messageService = Objects.requireNonNull(messageService);
        this.identityService = Objects.requireNonNull(identityService);
        this.labelService = Objects.requireNonNull(labelService);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return editingContext instanceof IEMFEditingContext && input instanceof WorkbenchOmniboxSearchInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        List<Message> messages = List.of(new Message(this.messageService.invalidInput(input.getClass().getSimpleName(), CreateChildInput.class.getSimpleName()),
                MessageLevel.ERROR));
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);
        IPayload payload = null;

        if (input instanceof WorkbenchOmniboxSearchInput workbenchOmniboxSearchInput) {
            List<OmniboxCommand> commands = this.getAllEditingContextContentByLabel(editingContext, workbenchOmniboxSearchInput.query()).stream()
                    .map(this::toOmniboxCommand)
                    .sorted(Comparator.comparingInt(command -> command.label().length()))
                    .toList();

            payload = new WorkbenchOmniboxSearchPayload(input.id(), commands);
        }

        if (payload == null) {
            payload = new ErrorPayload(input.id(), messages);
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private OmniboxCommand toOmniboxCommand(Object object) {
        var id = this.identityService.getId(object);
        var label = this.labelService.getStyledLabel(object).toString();
        var iconURL = this.labelService.getImagePaths(object);
        return new OmniboxCommand(id, label, iconURL, "Click to select the element");
    }

    private List<Object> getAllEditingContextContentByLabel(IEditingContext editingContext, String query) {
        var results = new ArrayList<>();
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            var editingDomain = emfEditingContext.getDomain();
            var iterator = editingDomain.getResourceSet().getAllContents();
            while (iterator.hasNext()) {
                Notifier notifier = iterator.next();
                var adapter = editingDomain.getAdapterFactory().adapt(notifier, IItemLabelProvider.class);
                if (adapter instanceof IItemLabelProvider itemLabelProvider && itemLabelProvider.getText(notifier).toLowerCase().contains(query.toLowerCase())) {
                    results.add(notifier);
                }
            }
        }
        return results;
    }

}
