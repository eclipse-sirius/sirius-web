/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.forms.handlers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.forms.AbstractWidget;
import org.eclipse.sirius.web.forms.Checkbox;
import org.eclipse.sirius.web.forms.Form;
import org.eclipse.sirius.web.forms.Group;
import org.eclipse.sirius.web.forms.Page;
import org.eclipse.sirius.web.representations.IStatus;
import org.eclipse.sirius.web.representations.Success;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
import org.eclipse.sirius.web.spring.collaborative.forms.api.IFormQueryService;
import org.eclipse.sirius.web.spring.collaborative.forms.dto.EditCheckboxInput;
import org.eclipse.sirius.web.spring.collaborative.forms.dto.EditCheckboxSuccessPayload;
import org.eclipse.sirius.web.spring.collaborative.forms.messages.ICollaborativeFormMessageService;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Unit tests of the edit checkbox event handler.
 *
 * @author sbegaudeau
 */
public class EditCheckboxEventHandlerTests {
    private static final UUID FORM_ID = UUID.randomUUID();

    @Test
    public void testCheckboxEdition() {
        String id = "Checkbox id"; //$NON-NLS-1$

        var input = new EditCheckboxInput(UUID.randomUUID(), UUID.randomUUID(), FORM_ID, id, true);

        AtomicBoolean hasBeenExecuted = new AtomicBoolean();
        Function<Boolean, IStatus> newValueHandler = newValue -> {
            hasBeenExecuted.set(true);
            return new Success();
        };

        // @formatter:off
        Checkbox checkbox = Checkbox.newCheckbox(id)
                .label("label") //$NON-NLS-1$
                .newValueHandler(newValueHandler)
                .diagnostics(List.of())
                .build();

        Group group = Group.newGroup("groupId") //$NON-NLS-1$
                .label("group label") //$NON-NLS-1$
                .widgets(List.of(checkbox))
                .build();

        Page page = Page.newPage("pageId") //$NON-NLS-1$
                .label("page label") //$NON-NLS-1$
                .groups(List.of(group))
                .build();

        Form form = Form.newForm(FORM_ID)
                .targetObjectId("targetObjectId") //$NON-NLS-1$
                .descriptionId(UUID.randomUUID())
                .label("form label") //$NON-NLS-1$
                .pages(List.of(page))
                .build();
        // @formatter:on

        IFormQueryService formQueryService = new IFormQueryService.NoOp() {
            @Override
            public Optional<AbstractWidget> findWidget(Form form, String widgetId) {
                return Optional.of(checkbox);
            }
        };
        EditCheckboxEventHandler handler = new EditCheckboxEventHandler(formQueryService, new ICollaborativeFormMessageService.NoOp(), new SimpleMeterRegistry());
        assertThat(handler.canHandle(input)).isTrue();

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        handler.handle(payloadSink, changeDescriptionSink, form, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(EditCheckboxSuccessPayload.class);

        assertThat(hasBeenExecuted.get()).isTrue();
    }
}
