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
package org.eclipse.sirius.components.collaborative.widget.reference.handlers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.forms.api.IFormQueryService;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.MoveReferenceValueInput;
import org.eclipse.sirius.components.collaborative.widget.reference.messages.IReferenceMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.Group;
import org.eclipse.sirius.components.forms.Page;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.widget.reference.ReferenceValue;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.eclipse.sirius.components.widget.reference.dto.MoveReferenceValueHandlerParameters;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Unit tests of the widget reference move value event handler.
 *
 * @author Jerome Gout
 */
public class MoveReferenceValueEventHandlerTests {

    private static final UUID FORM_ID = UUID.randomUUID();

    private static final String REF_WIDGET_ID = "RefWidget id";

    private static final String CHANGE_DESCRIPTION_PARAMETER_KEY = "change_description_parameter_key";

    @Test
    public void testMoveReferenceValue() {
        String referenceValueId = "ReferenceValue Id";
        String changeKind = ChangeKind.SEMANTIC_CHANGE;

        var input = new MoveReferenceValueInput(UUID.randomUUID(), FORM_ID.toString(), UUID.randomUUID().toString(), REF_WIDGET_ID, referenceValueId, 0, 1);
        AtomicBoolean hasBeenExecuted = new AtomicBoolean();
        Function<MoveReferenceValueHandlerParameters, IStatus> moveHandler = (inputParameters) -> {
            hasBeenExecuted.set(true);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put(CHANGE_DESCRIPTION_PARAMETER_KEY, referenceValueId);
            return new Success(changeKind, parameters);
        };

        ReferenceValue referenceValue = ReferenceValue.newReferenceValue(referenceValueId)
                .label("")
                .kind("")
                .build();

        IObjectService objectService = new IObjectService.NoOp() {
            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                return Optional.of(referenceValue);
            }
        };

        ReferenceWidget referenceWidget = ReferenceWidget.newReferenceWidget(REF_WIDGET_ID)
                .diagnostics(Collections.emptyList())
                .referenceValues(List.of())
                .referenceOptionsProvider(List::of)
                .descriptionId("")
                .label("")
                .readOnly(false)
                .ownerId("")
                .ownerKind("")
                .referenceKind("")
                .many(false)
                .containment(false)
                .moveHandler(moveHandler)
                .build();

        Group group = Group.newGroup("groupId")
                .label("group label")
                .widgets(Collections.singletonList(referenceWidget))
                .build();

        Page page = Page.newPage("pageId")
                .label("page label")
                .groups(Collections.singletonList(group))
                .build();

        Form form = Form.newForm(FORM_ID.toString())
                .targetObjectId("targetObjectId")
                .descriptionId(UUID.randomUUID().toString())
                .label("form label")
                .pages(Collections.singletonList(page))
                .build();

        IFormQueryService formQueryService = new IFormQueryService.NoOp() {
            @Override
            public Optional<AbstractWidget> findWidget(Form form, String widgetId) {
                return Optional.of(referenceWidget);
            }
        };

        MoveReferenceValueEventHandler handler = new MoveReferenceValueEventHandler(formQueryService, new IReferenceMessageService.NoOp(), objectService, new SimpleMeterRegistry());
        assertThat(handler.canHandle(input)).isTrue();

        Sinks.Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        Sinks.One<IPayload> payloadSink = Sinks.one();

        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), form, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(changeKind);
        Map<String, Object> changeDescriptionParameters = changeDescription.getParameters();
        assertThat(changeDescriptionParameters.get(CHANGE_DESCRIPTION_PARAMETER_KEY)).isEqualTo(referenceValueId);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(SuccessPayload.class);

        assertThat(hasBeenExecuted.get()).isTrue();
    }

    @Test
    public void testMoveReferenceValueReadOnly() {
        String referenceValueId = "ReferenceValue Id";
        String changeKind = ChangeKind.SEMANTIC_CHANGE;

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(CHANGE_DESCRIPTION_PARAMETER_KEY, referenceValueId);
        var input = new MoveReferenceValueInput(UUID.randomUUID(), FORM_ID.toString(), UUID.randomUUID().toString(), REF_WIDGET_ID, referenceValueId, 0, 1);

        AtomicBoolean hasBeenExecuted = new AtomicBoolean();
        Function<MoveReferenceValueHandlerParameters, IStatus> moveHandler = (inputParameters) -> {
            hasBeenExecuted.set(true);
            return new Success(changeKind, parameters);
        };

        ReferenceWidget referenceWidget = ReferenceWidget.newReferenceWidget(REF_WIDGET_ID)
                .diagnostics(Collections.emptyList())
                .referenceValues(List.of())
                .referenceOptionsProvider(List::of)
                .descriptionId("")
                .label("")
                .readOnly(true)
                .ownerId("")
                .ownerKind("")
                .referenceKind("")
                .many(false)
                .containment(false)
                .moveHandler(moveHandler)
                .build();

        Group group = Group.newGroup("groupId")
                .label("group label")
                .widgets(Collections.singletonList(referenceWidget))
                .build();

        Page page = Page.newPage("pageId")
                .label("page label")
                .groups(Collections.singletonList(group))
                .build();

        Form form = Form.newForm(FORM_ID.toString())
                .targetObjectId("targetObjectId")
                .descriptionId(UUID.randomUUID().toString())
                .label("form label")
                .pages(Collections.singletonList(page))
                .build();

        IFormQueryService formQueryService = new IFormQueryService.NoOp() {
            @Override
            public Optional<AbstractWidget> findWidget(Form form, String widgetId) {
                return Optional.of(referenceWidget);
            }
        };

        IReferenceMessageService messageService = new IReferenceMessageService.NoOp() {
            @Override
            public String unableToEditReadOnlyWidget() {
                return "Read-only widget can not be edited";
            }
        };

        MoveReferenceValueEventHandler handler = new MoveReferenceValueEventHandler(formQueryService, messageService, new IObjectService.NoOp(), new SimpleMeterRegistry());
        assertThat(handler.canHandle(input)).isTrue();

        Sinks.Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        Sinks.One<IPayload> payloadSink = Sinks.one();

        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), form, input);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(ErrorPayload.class);
        assertThat(((ErrorPayload) payload).message()).isEqualTo("Read-only widget can not be edited");
        assertThat(hasBeenExecuted.get()).isFalse();
    }

}
