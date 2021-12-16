/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.forms.AbstractWidget;
import org.eclipse.sirius.web.forms.Form;
import org.eclipse.sirius.web.forms.Group;
import org.eclipse.sirius.web.forms.List;
import org.eclipse.sirius.web.forms.ListItem;
import org.eclipse.sirius.web.forms.Page;
import org.eclipse.sirius.web.representations.IStatus;
import org.eclipse.sirius.web.representations.Success;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.forms.api.IFormQueryService;
import org.eclipse.sirius.web.spring.collaborative.forms.dto.DeleteListItemInput;
import org.eclipse.sirius.web.spring.collaborative.forms.dto.DeleteListItemSuccessPayload;
import org.eclipse.sirius.web.spring.collaborative.forms.messages.ICollaborativeFormMessageService;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Unit tests of the delete list item event handler.
 *
 * @author gcoutable
 */
public class DeleteListItemEventHandlerTests {
    private static final UUID FORM_ID = UUID.randomUUID();

    @Test
    public void testListItemDeletion() {
        String listId = "List id"; //$NON-NLS-1$
        String listItemId = "element id to delete"; //$NON-NLS-1$
        String changeKind = "delete something"; //$NON-NLS-1$
        String changeDescriptionParameterKey = "change_description_parameter_key"; //$NON-NLS-1$

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(changeDescriptionParameterKey, listItemId);
        var input = new DeleteListItemInput(UUID.randomUUID(), FORM_ID.toString(), UUID.randomUUID().toString(), listId, listItemId);

        AtomicBoolean hasBeenExecuted = new AtomicBoolean();
        Supplier<IStatus> deleteHandler = () -> {
            hasBeenExecuted.set(true);
            return new Success(changeKind, parameters);
        };

        // @formatter:off
        ListItem listItem = ListItem.newListItem(listItemId)
                .deletable(true)
                .deleteHandler(deleteHandler)
                .imageURL("") //$NON-NLS-1$
                .kind("Diagram") //$NON-NLS-1$
                .label("empty representation") //$NON-NLS-1$
                .build();

        List list = List.newList(listId)
                .diagnostics(Collections.emptyList())
                .items(Collections.singletonList(listItem))
                .label("") //$NON-NLS-1$
                .build();

        Group group = Group.newGroup("groupId") //$NON-NLS-1$
                .label("group label") //$NON-NLS-1$
                .widgets(Collections.singletonList(list))
                .build();

        Page page = Page.newPage("pageId") //$NON-NLS-1$
                .label("page label") //$NON-NLS-1$
                .groups(Collections.singletonList(group))
                .build();

        Form form = Form.newForm(FORM_ID.toString())
                .targetObjectId("targetObjectId") //$NON-NLS-1$
                .label("form label") //$NON-NLS-1$
                .pages(Collections.singletonList(page))
                .build();
        // @formatter:on

        IFormQueryService formQueryService = new IFormQueryService.NoOp() {
            @Override
            public Optional<AbstractWidget> findWidget(Form form, String widgetId) {
                return Optional.of(list);
            }
        };

        DeleteListItemEventHandler handler = new DeleteListItemEventHandler(formQueryService, new ICollaborativeFormMessageService.NoOp(), new SimpleMeterRegistry());
        assertThat(handler.canHandle(input)).isTrue();

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        handler.handle(payloadSink, changeDescriptionSink, form, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(changeKind);
        Map<String, Object> changeDescriptionParameters = changeDescription.getParameters();
        assertThat(changeDescriptionParameters.get(changeDescriptionParameterKey)).isEqualTo(listItemId);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(DeleteListItemSuccessPayload.class);

        assertThat(hasBeenExecuted.get()).isTrue();
    }

}
