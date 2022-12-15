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
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.FormDescriptionEditorContext;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.TestFormDescriptionEditorBuilder;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorContext;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.MoveToolbarActionInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.MoveToolbarActionSuccessPayload;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.messages.ICollaborativeFormDescriptionEditorMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IObjectService.NoOp;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.eclipse.sirius.components.view.ButtonDescription;
import org.eclipse.sirius.components.view.FormDescription;
import org.eclipse.sirius.components.view.GroupDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Tests of the move toolbarAction event handler.
 *
 * @author arichard
 */
public class MoveToolbarActionEventHandlerTests {
    @Test
    public void testMoveToolbarAction() {

        FormDescriptionEditor formDescriptionEditor = new TestFormDescriptionEditorBuilder().getFormDescriptionEditor(UUID.randomUUID().toString());

        FormDescription formDescription = ViewFactory.eINSTANCE.createFormDescription();
        GroupDescription groupDescription = ViewFactory.eINSTANCE.createGroupDescription();
        formDescription.getGroups().add(groupDescription);
        ButtonDescription toolbarButton1 = ViewFactory.eINSTANCE.createButtonDescription();
        ButtonDescription toolbarButton2 = ViewFactory.eINSTANCE.createButtonDescription();
        ButtonDescription toolbarButton3 = ViewFactory.eINSTANCE.createButtonDescription();
        groupDescription.getToolbarActions().add(toolbarButton1);
        groupDescription.getToolbarActions().add(toolbarButton2);
        groupDescription.getToolbarActions().add(toolbarButton3);

        var objectService = new IObjectService.NoOp() {
            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                Optional<Object> result = Optional.empty();
                if (formDescriptionEditor.getDescriptionId().equals(objectId)) {
                    result = Optional.of(formDescription);
                } else if ("button1".equals(objectId)) {
                    result = Optional.of(toolbarButton1);
                } else if ("button2".equals(objectId)) {
                    result = Optional.of(toolbarButton2);
                } else if ("button3".equals(objectId)) {
                    result = Optional.of(toolbarButton3);
                } else if (formDescriptionEditor.getGroups().get(0).getId().equals(objectId)) {
                    result = Optional.of(groupDescription);
                }
                return result;
            }
        };

        this.invokMove(formDescriptionEditor, objectService, "button2", 0);
        assertThat(groupDescription.getToolbarActions()).isEqualTo(List.of(toolbarButton2, toolbarButton1, toolbarButton3));
        this.invokMove(formDescriptionEditor, objectService, "button1", 2);
        assertThat(groupDescription.getToolbarActions()).isEqualTo(List.of(toolbarButton2, toolbarButton3, toolbarButton1));
    }

    private void invokMove(FormDescriptionEditor formDescriptionEditor, NoOp objectService, String toolbarActionId, int index) {
        var handler = new MoveToolbarActionEventHandler(objectService, new ICollaborativeFormDescriptionEditorMessageService.NoOp(), new SimpleMeterRegistry());
        var input = new MoveToolbarActionInput(UUID.randomUUID(), "editingContextId", formDescriptionEditor.getId(), formDescriptionEditor.getGroups().get(0).getId(), toolbarActionId, index);

        assertThat(handler.canHandle(input)).isTrue();

        One<IPayload> payloadSink = Sinks.one();
        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        IFormDescriptionEditorContext formDescriptionEditorContext = new FormDescriptionEditorContext(new TestFormDescriptionEditorBuilder().getFormDescriptionEditor(UUID.randomUUID().toString()));

        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), formDescriptionEditorContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(MoveToolbarActionSuccessPayload.class);
    }
}
