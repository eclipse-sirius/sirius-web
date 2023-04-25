/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.MovePageInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.messages.ICollaborativeFormDescriptionEditorMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.eclipse.sirius.components.view.FormDescription;
import org.eclipse.sirius.components.view.PageDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Tests of the move page event handler.
 *
 * @author frouene
 */
public class MovePageEventHandlerTests {

    @Test
    public void testMovePageAction() {

        FormDescription formDescription = ViewFactory.eINSTANCE.createFormDescription();
        PageDescription pageDescription1 = ViewFactory.eINSTANCE.createPageDescription();
        formDescription.getPages().add(pageDescription1);
        PageDescription pageDescription2 = ViewFactory.eINSTANCE.createPageDescription();
        formDescription.getPages().add(pageDescription2);
        PageDescription pageDescription3 = ViewFactory.eINSTANCE.createPageDescription();
        formDescription.getPages().add(pageDescription3);


        FormDescriptionEditor formDescriptionEditor = new TestFormDescriptionEditorBuilder().getFormDescriptionEditor(UUID.randomUUID().toString());

        var objectService = new IObjectService.NoOp() {
            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                Optional<Object> result = Optional.empty();
                if (formDescriptionEditor.getTargetObjectId().equals(objectId)) {
                    result = Optional.of(formDescription);
                } else if ("page1".equals(objectId)) {
                    result = Optional.of(pageDescription1);
                } else if ("page2".equals(objectId)) {
                    result = Optional.of(pageDescription2);
                } else if ("page3".equals(objectId)) {
                    result = Optional.of(pageDescription3);
                }
                return result;
            }
        };

        this.invokMove(formDescriptionEditor, objectService, "page2", 0);
        assertThat(formDescription.getPages()).isEqualTo(List.of(pageDescription2, pageDescription1, pageDescription3));
        this.invokMove(formDescriptionEditor, objectService, "page1", 2);
        assertThat(formDescription.getPages()).isEqualTo(List.of(pageDescription2, pageDescription3, pageDescription1));

    }

    private void invokMove(FormDescriptionEditor formDescriptionEditor, IObjectService.NoOp objectService, String pageId, int index) {
        var handler = new MovePageEventHandler(objectService, new ICollaborativeFormDescriptionEditorMessageService.NoOp(), new SimpleMeterRegistry());
        var input = new MovePageInput(UUID.randomUUID(), "editingContextId", formDescriptionEditor.getId(), pageId, index);

        assertThat(handler.canHandle(input)).isTrue();

        Sinks.One<IPayload> payloadSink = Sinks.one();
        Sinks.Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        IFormDescriptionEditorContext formDescriptionEditorContext = new FormDescriptionEditorContext(new TestFormDescriptionEditorBuilder().getFormDescriptionEditor(UUID.randomUUID()
                .toString()));

        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), formDescriptionEditorContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(SuccessPayload.class);
    }

}
