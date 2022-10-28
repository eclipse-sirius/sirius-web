/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.dto.RenameRepresentationSuccessPayload;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorContext;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.RenameFormDescriptionEditorInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.messages.ICollaborativeFormDescriptionEditorMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Unit tests of the rename representation event handler.
 *
 * @author arichard
 */
public class RenameFormDescriptionEditorEventHandlerTests {
    private static final String OLD_LABEL = "oldLabel"; //$NON-NLS-1$

    private static final String NEW_LABEL = "newLabel"; //$NON-NLS-1$

    @Test
    public void testRenameRepresentation() {
        String projectId = UUID.randomUUID().toString();
        String formDescriptionEditorDescriptionId = UUID.randomUUID().toString();
        String representationId = UUID.randomUUID().toString();
        UUID targetObjectId = UUID.randomUUID();

        // @formatter:off
        FormDescriptionEditor formDescriptionEditor = FormDescriptionEditor.newFormDescriptionEditor(representationId)
                .label(OLD_LABEL)
                .descriptionId(formDescriptionEditorDescriptionId)
                .targetObjectId(targetObjectId.toString())
                .toolbarActions(List.of())
                .widgets(List.of())
                .build();
        // @formatter:on

        RenameFormDescriptionEditorEventHandler handler = new RenameFormDescriptionEditorEventHandler(new IRepresentationPersistenceService.NoOp(),
                new ICollaborativeFormDescriptionEditorMessageService.NoOp(), new SimpleMeterRegistry());

        var input = new RenameFormDescriptionEditorInput(UUID.randomUUID(), projectId, representationId, NEW_LABEL);
        assertThat(handler.canHandle(input)).isTrue();

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        IFormDescriptionEditorContext formDescriptionEditorContext = new IFormDescriptionEditorContext() {

            @Override
            public void update(FormDescriptionEditor updatedFormDescriptionEditor) {
            }

            @Override
            public FormDescriptionEditor getFormDescriptionEditor() {
                return formDescriptionEditor;
            }
        };
        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), formDescriptionEditorContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.REPRESENTATION_RENAMING);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(RenameRepresentationSuccessPayload.class);
        assertThat(((RenameRepresentationSuccessPayload) payload).getRepresentation().getLabel()).isEqualTo(NEW_LABEL);
    }
}
