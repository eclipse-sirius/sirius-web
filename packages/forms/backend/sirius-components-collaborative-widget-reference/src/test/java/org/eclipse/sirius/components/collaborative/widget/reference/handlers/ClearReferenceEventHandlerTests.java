/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.forms.api.IFormQueryService;
import org.eclipse.sirius.components.collaborative.widget.reference.api.IDefaultReferenceWidgetClearHandler;
import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetClearHandler;
import org.eclipse.sirius.components.collaborative.widget.reference.dto.ClearReferenceInput;
import org.eclipse.sirius.components.collaborative.widget.reference.messages.IReferenceMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Unit tests of the widget reference clear event handler.
 *
 * @author Jerome Gout
 */
public class ClearReferenceEventHandlerTests {

    private static final UUID FORM_ID = UUID.randomUUID();

    private static final String REF_WIDGET_ID = "RefWidget id";

    private static final String REFERENCE_VALUE_ID = "referenceValueId";

    @Test
    @DisplayName("Given an editable reference widget, when it is cleared with a custom handler, then the handler clears it")
    public void givenAnEditableReferenceWidgetWhenItIsClearedWithACustomHandlerThenTheHandlerClearsIt() {
        String referenceValueId = "ReferenceValue Id";
        AtomicBoolean hasBeenExecuted = new AtomicBoolean();
        AtomicBoolean hasDefaultBeenExecuted = new AtomicBoolean();
        var input = new ClearReferenceInput(UUID.randomUUID(), FORM_ID.toString(), UUID.randomUUID().toString(), REF_WIDGET_ID);
        IReferenceWidgetClearHandler clearHandler = this.createReferenceWidgetClearHandler(hasBeenExecuted, referenceValueId);
        IDefaultReferenceWidgetClearHandler defaultClearHandler = this.createDefaultReferenceWidgetClearHandler(hasDefaultBeenExecuted, referenceValueId);
        var handler = this.createHandler(this.createReferenceWidget(false), List.of(clearHandler), defaultClearHandler, new IReferenceMessageService.NoOp());

        Sinks.Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        Sinks.One<IPayload> payloadSink = Sinks.one();
        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), this.createForm(input.representationId()), input);

        assertThat(changeDescriptionSink.asFlux().blockFirst().getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);
        assertThat(payloadSink.asMono().block()).isInstanceOf(SuccessPayload.class);
        assertThat(hasBeenExecuted.get()).isTrue();
        assertThat(hasDefaultBeenExecuted.get()).isFalse();
    }

    @Test
    @DisplayName("Given a read-only reference widget, when it is cleared, then an error is returned")
    public void givenAReadOnlyReferenceWidgetWhenItIsClearedThenAnErrorIsReturned() {
        AtomicBoolean hasBeenExecuted = new AtomicBoolean();
        AtomicBoolean hasDefaultBeenExecuted = new AtomicBoolean();
        var input = new ClearReferenceInput(UUID.randomUUID(), FORM_ID.toString(), UUID.randomUUID().toString(), REF_WIDGET_ID);
        IReferenceWidgetClearHandler clearHandler = this.createReferenceWidgetClearHandler(hasBeenExecuted, REFERENCE_VALUE_ID);
        IDefaultReferenceWidgetClearHandler defaultClearHandler = this.createDefaultReferenceWidgetClearHandler(hasDefaultBeenExecuted, REFERENCE_VALUE_ID);
        IReferenceMessageService messageService = new IReferenceMessageService.NoOp() {
            @Override
            public String unableToEditReadOnlyWidget() {
                return "Read-only widget can not be edited";
            }
        };
        var handler = this.createHandler(this.createReferenceWidget(true), List.of(clearHandler), defaultClearHandler, messageService);

        Sinks.One<IPayload> payloadSink = Sinks.one();
        handler.handle(payloadSink, Sinks.many().unicast().onBackpressureBuffer(), new IEditingContext.NoOp(), this.createForm(input.representationId()), input);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(ErrorPayload.class);
        assertThat(((ErrorPayload) payload).messages()).map(Message::body).contains("Read-only widget can not be edited");
        assertThat(hasBeenExecuted.get()).isFalse();
        assertThat(hasDefaultBeenExecuted.get()).isFalse();
    }

    @Test
    @DisplayName("Given an editable reference widget, when it is cleared without a custom handler, then the default handler clears it")
    public void givenAnEditableReferenceWidgetWhenItIsClearedWithoutACustomHandlerThenTheDefaultHandlerClearsIt() {
        var input = new ClearReferenceInput(UUID.randomUUID(), FORM_ID.toString(), UUID.randomUUID().toString(), REF_WIDGET_ID);
        var hasDefaultBeenExecuted = new AtomicBoolean();
        IDefaultReferenceWidgetClearHandler defaultClearHandler = this.createDefaultReferenceWidgetClearHandler(hasDefaultBeenExecuted, REFERENCE_VALUE_ID);
        ReferenceWidget referenceWidget = this.createReferenceWidget(false);
        var handler = this.createHandler(referenceWidget, List.of(), defaultClearHandler, new IReferenceMessageService.NoOp());

        Sinks.Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        Sinks.One<IPayload> payloadSink = Sinks.one();
        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), this.createForm(input.representationId()), input);

        assertThat(changeDescriptionSink.asFlux().blockFirst().getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);
        assertThat(payloadSink.asMono().block()).isInstanceOf(SuccessPayload.class);
        assertThat(hasDefaultBeenExecuted.get()).isTrue();
    }

    private ClearReferenceEventHandler createHandler(ReferenceWidget referenceWidget, List<IReferenceWidgetClearHandler> clearHandlers, IDefaultReferenceWidgetClearHandler defaultClearHandler,
            IReferenceMessageService messageService) {
        IFormQueryService formQueryService = new IFormQueryService.NoOp() {
            @Override
            public Optional<AbstractWidget> findWidget(Form form, String widgetId) {
                return Optional.of(referenceWidget);
            }
        };
        return new ClearReferenceEventHandler(formQueryService, messageService, clearHandlers, defaultClearHandler, new SimpleMeterRegistry());
    }

    private Form createForm(String id) {
        return Form.newForm(id)
                .targetObjectId("targetObjectId")
                .descriptionId(UUID.randomUUID().toString())
                .pages(Collections.emptyList())
                .build();
    }

    private ReferenceWidget createReferenceWidget(boolean readOnly) {
        return ReferenceWidget.newReferenceWidget(REF_WIDGET_ID)
                .diagnostics(Collections.emptyList())
                .referenceValues(Collections.emptyList())
                .referenceOptionsProvider(Collections::emptyList)
                .descriptionId("descriptionId")
                .label("")
                .readOnly(readOnly)
                .ownerId("ownerId")
                .ownerKind("")
                .referenceKind("")
                .referenceName("references")
                .many(false)
                .containment(false)
                .build();
    }

    private IDefaultReferenceWidgetClearHandler createDefaultReferenceWidgetClearHandler(AtomicBoolean hasBeenExecuted, String referenceValueId) {
        return new IDefaultReferenceWidgetClearHandler.NoOp() {
            @Override
            public IStatus clear(IEditingContext editingContext, ReferenceWidget referenceWidget) {
                hasBeenExecuted.set(true);
                return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(REFERENCE_VALUE_ID, referenceValueId));
            }
        };
    }

    private IReferenceWidgetClearHandler createReferenceWidgetClearHandler(AtomicBoolean hasBeenExecuted, String referenceValueId) {
        return new IReferenceWidgetClearHandler.NoOp() {
            @Override
            public boolean canHandle(String descriptionId) {
                return true;
            }

            @Override
            public IStatus clear(IEditingContext editingContext, ReferenceWidget referenceWidget) {
                hasBeenExecuted.set(true);
                return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(REFERENCE_VALUE_ID, referenceValueId));
            }
        };
    }
}
