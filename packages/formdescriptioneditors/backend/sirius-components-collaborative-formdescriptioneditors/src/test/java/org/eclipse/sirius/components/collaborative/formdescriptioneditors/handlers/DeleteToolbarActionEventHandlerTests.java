/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.FormDescriptionEditorContext;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.TestFormDescriptionEditorBuilder;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorContext;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.DeleteToolbarActionInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.messages.ICollaborativeFormDescriptionEditorMessageService;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.eclipse.sirius.components.view.form.ButtonDescription;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.form.FormFactory;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Tests of the delete toolbarAction event handler.
 *
 * @author arichard
 */
public class DeleteToolbarActionEventHandlerTests {

    private static final String TOOLBAR_ACTION_ID = "toolbarActionId";

    @Test
    public void testDeleteToolbarActionFromGroup() {
        FormDescriptionEditor formDescriptionEditor = new TestFormDescriptionEditorBuilder().getFormDescriptionEditor(UUID.randomUUID().toString());

        FormDescription formDescription = FormFactory.eINSTANCE.createFormDescription();
        PageDescription pageDescription = FormFactory.eINSTANCE.createPageDescription();
        GroupDescription groupDescription = FormFactory.eINSTANCE.createGroupDescription();
        pageDescription.getGroups().add(groupDescription);
        formDescription.getPages().add(pageDescription);
        ButtonDescription toolbarButton = FormFactory.eINSTANCE.createButtonDescription();
        groupDescription.getToolbarActions().add(toolbarButton);

        var objectSearchService = new IObjectSearchService.NoOp() {
            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                Optional<Object> result = Optional.empty();
                if (formDescriptionEditor.getDescriptionId().equals(objectId)) {
                    result = Optional.of(groupDescription);
                } else if (TOOLBAR_ACTION_ID.equals(objectId)) {
                    result = Optional.of(toolbarButton);
                }
                return result;
            }
        };
        AtomicBoolean toolbarActionDeleted = new AtomicBoolean(false);
        IEditService.NoOp editService = new IEditService.NoOp() {
            @Override
            public void delete(Object object) {
                if (object == toolbarButton) {
                    toolbarActionDeleted.set(true);
                }
            }
        };
        var handler = new DeleteToolbarActionEventHandler(objectSearchService, editService, new ICollaborativeFormDescriptionEditorMessageService.NoOp(), new SimpleMeterRegistry());
        var input = new DeleteToolbarActionInput(UUID.randomUUID(), "editingContextId", "representationId", TOOLBAR_ACTION_ID);

        assertThat(handler.canHandle(input)).isTrue();

        One<IPayload> payloadSink = Sinks.one();
        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        IFormDescriptionEditorContext formDescriptionEditorContext = new FormDescriptionEditorContext(formDescriptionEditor);

        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), formDescriptionEditorContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(SuccessPayload.class);
        assertThat(toolbarActionDeleted).isTrue();
    }

    @Test
    public void testDeleteToolbarActionFromPage() {
        FormDescriptionEditor formDescriptionEditor = new TestFormDescriptionEditorBuilder().getFormDescriptionEditor(UUID.randomUUID().toString());

        FormDescription formDescription = FormFactory.eINSTANCE.createFormDescription();
        PageDescription pageDescription = FormFactory.eINSTANCE.createPageDescription();
        formDescription.getPages().add(pageDescription);
        ButtonDescription toolbarButton = FormFactory.eINSTANCE.createButtonDescription();
        pageDescription.getToolbarActions().add(toolbarButton);

        var objectSearchService = new IObjectSearchService.NoOp() {
            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                Optional<Object> result = Optional.empty();
                if (formDescriptionEditor.getDescriptionId().equals(objectId)) {
                    result = Optional.of(pageDescription);
                } else if (TOOLBAR_ACTION_ID.equals(objectId)) {
                    result = Optional.of(toolbarButton);
                }
                return result;
            }
        };
        AtomicBoolean toolbarActionDeleted = new AtomicBoolean(false);
        IEditService.NoOp editService = new IEditService.NoOp() {
            @Override
            public void delete(Object object) {
                if (object == toolbarButton) {
                    toolbarActionDeleted.set(true);
                }
            }
        };
        var handler = new DeleteToolbarActionEventHandler(objectSearchService, editService, new ICollaborativeFormDescriptionEditorMessageService.NoOp(), new SimpleMeterRegistry());
        var input = new DeleteToolbarActionInput(UUID.randomUUID(), "editingContextId", "representationId", TOOLBAR_ACTION_ID);

        assertThat(handler.canHandle(input)).isTrue();

        One<IPayload> payloadSink = Sinks.one();
        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        IFormDescriptionEditorContext formDescriptionEditorContext = new FormDescriptionEditorContext(formDescriptionEditor);

        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), formDescriptionEditorContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(SuccessPayload.class);
        assertThat(toolbarActionDeleted).isTrue();
    }
}
