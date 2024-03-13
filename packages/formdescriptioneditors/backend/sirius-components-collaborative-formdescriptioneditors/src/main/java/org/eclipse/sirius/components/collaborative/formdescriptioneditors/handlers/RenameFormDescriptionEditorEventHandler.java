/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.formdescriptioneditors.handlers;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.dto.RenameRepresentationSuccessPayload;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorContext;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorEventHandler;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.RenameFormDescriptionEditorInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.messages.ICollaborativeFormDescriptionEditorMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to rename a form.
 *
 * @author arichard
 */
@Service
public class RenameFormDescriptionEditorEventHandler implements IFormDescriptionEditorEventHandler {

    private final IRepresentationPersistenceService representationPersistenceService;

    private final ICollaborativeFormDescriptionEditorMessageService messageService;

    private final Counter counter;

    public RenameFormDescriptionEditorEventHandler(IRepresentationPersistenceService representationPersistenceService, ICollaborativeFormDescriptionEditorMessageService messageService,
            MeterRegistry meterRegistry) {
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IFormDescriptionEditorInput formDescriptionEditorInput) {
        return formDescriptionEditorInput instanceof RenameFormDescriptionEditorInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IFormDescriptionEditorContext formDescriptionEditorContext,
            IFormDescriptionEditorInput formDescriptionEditorInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(formDescriptionEditorInput.getClass().getSimpleName(), RenameFormDescriptionEditorInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(formDescriptionEditorInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, formDescriptionEditorInput.representationId(), formDescriptionEditorInput);

        if (formDescriptionEditorInput instanceof RenameFormDescriptionEditorInput) {
            RenameFormDescriptionEditorInput renameRepresentationInput = (RenameFormDescriptionEditorInput) formDescriptionEditorInput;
            String newLabel = renameRepresentationInput.newLabel();

            FormDescriptionEditor renamedFormDescriptionEditor = FormDescriptionEditor.newFormDescriptionEditor(formDescriptionEditorContext.getFormDescriptionEditor())
                    .label(newLabel)
                    .pages(List.of()) // We don't store form description editor pages, it will be re-render by
                    // the FormDescriptionEditorProcessor.
                    .build();
            this.representationPersistenceService.save(renameRepresentationInput, editingContext, renamedFormDescriptionEditor);

            payload = new RenameRepresentationSuccessPayload(formDescriptionEditorInput.id(), renamedFormDescriptionEditor);
            changeDescription = new ChangeDescription(ChangeKind.REPRESENTATION_RENAMING, renameRepresentationInput.representationId(), formDescriptionEditorInput);
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
