/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.forms.handlers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.forms.api.IFormQueryService;
import org.eclipse.sirius.components.collaborative.forms.dto.ClickListItemInput;
import org.eclipse.sirius.components.collaborative.forms.dto.DeleteListItemInput;
import org.eclipse.sirius.components.collaborative.forms.messages.ICollaborativeFormMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.ClickEventKind;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.Group;
import org.eclipse.sirius.components.forms.List;
import org.eclipse.sirius.components.forms.ListItem;
import org.eclipse.sirius.components.forms.Page;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Unit tests of the list item event handlers.
 *
 * @author gcoutable
 */
public class ListItemEventHandlersTests {

    private static final UUID FORM_ID = UUID.randomUUID();
    private static final String LIST_ID = "List id";
    private static final String CHANGE_DESCRIPTION_PARAMETER_KEY = "change_description_parameter_key";

    private static Page getPage(Group group) {
        return Page.newPage("pageId")
                .label("page label")
                .groups(Collections.singletonList(group))
                .build();
    }

    private static ListItem getListItemWithClickHandler(String listItemId, Function<ClickEventKind, IStatus> clickHandler) {
        return ListItem.newListItem(listItemId)
                .deletable(true)
                .deleteHandler(() -> new Success())
                .clickHandler(clickHandler)
                .imageURL("")
                .kind("Diagram")
                .label("empty representation")
                .build();
    }

    private static ListItem getListItemWithDeleteHandler(String listItemId, Supplier<IStatus> deleteHandler) {
        return ListItem.newListItem(listItemId)
                .deletable(true)
                .deleteHandler(deleteHandler)
                .clickHandler(clickEventKind -> new Success())
                .imageURL("")
                .kind("Diagram")
                .label("empty representation")
                .build();
    }

    private static Form getForm(Page page) {
        return Form.newForm(FORM_ID.toString())
                .targetObjectId("targetObjectId")
                .descriptionId(UUID.randomUUID().toString())
                .label("form label")
                .pages(Collections.singletonList(page))
                .build();
    }

    private static Group getGroup(List list) {
        return Group.newGroup("groupId")
                .label("group label")
                .widgets(Collections.singletonList(list))
                .build();
    }

    private static List getList(String listId, ListItem listItem, boolean readOnly) {
        return List.newList(listId)
                .diagnostics(Collections.emptyList())
                .items(Collections.singletonList(listItem))
                .label("")
                .readOnly(readOnly)
                .build();
    }

    @Test
    public void testListItemDeletion() {
        String listItemId = "element id to delete";
        String changeKind = "delete something";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(CHANGE_DESCRIPTION_PARAMETER_KEY, listItemId);
        var input = new DeleteListItemInput(UUID.randomUUID(), FORM_ID.toString(), UUID.randomUUID().toString(), LIST_ID, listItemId);

        AtomicBoolean hasBeenExecuted = new AtomicBoolean();
        Supplier<IStatus> deleteHandler = () -> {
            hasBeenExecuted.set(true);
            return new Success(changeKind, parameters);
        };

        // @formatter:off
        ListItem listItem = getListItemWithDeleteHandler(listItemId, deleteHandler);

        List list = getList(LIST_ID, listItem, false);

        Group group = getGroup(list);

        Page page = getPage(group);

        Form form = getForm(page);
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

        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), form, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(changeKind);
        Map<String, Object> changeDescriptionParameters = changeDescription.getParameters();
        assertThat(changeDescriptionParameters.get(CHANGE_DESCRIPTION_PARAMETER_KEY)).isEqualTo(listItemId);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(SuccessPayload.class);

        assertThat(hasBeenExecuted.get()).isTrue();
    }

    @Test
    public void testListItemDeletionReadOnly() {
        String listItemId = "element id to delete";
        String changeKind = "delete something";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(CHANGE_DESCRIPTION_PARAMETER_KEY, listItemId);
        var input = new DeleteListItemInput(UUID.randomUUID(), FORM_ID.toString(), UUID.randomUUID().toString(), LIST_ID, listItemId);

        AtomicBoolean hasBeenExecuted = new AtomicBoolean();
        Supplier<IStatus> deleteHandler = () -> {
            hasBeenExecuted.set(true);
            return new Success(changeKind, parameters);
        };

        // @formatter:off
        ListItem listItem = getListItemWithDeleteHandler(listItemId, deleteHandler);

        List list = getList(LIST_ID, listItem, true);

        Group group = getGroup(list);

        Page page = getPage(group);

        Form form = getForm(page);
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

        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), form, input);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(ErrorPayload.class);
        assertThat(((ErrorPayload) payload).message()).isEqualTo("Read-only widget can not be edited");
        assertThat(hasBeenExecuted.get()).isFalse();
    }

    @Test
    public void testListItemSelection() {
        String listItemId = "ListItem Id";
        String changeKind = ChangeKind.SEMANTIC_CHANGE;

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(CHANGE_DESCRIPTION_PARAMETER_KEY, listItemId);
        var input = new ClickListItemInput(UUID.randomUUID(), FORM_ID.toString(), UUID.randomUUID().toString(), LIST_ID, listItemId, ClickEventKind.SINGLE_CLICK);

        AtomicBoolean hasBeenExecuted = new AtomicBoolean();
        Function<ClickEventKind, IStatus> selectHandler = (clickEventKind) -> {
            hasBeenExecuted.set(true);
            return new Success(changeKind, parameters);
        };

        // @formatter:off
        ListItem listItem = getListItemWithClickHandler(listItemId, selectHandler);

        List list = getList(LIST_ID, listItem, false);

        Group group = getGroup(list);

        Page page = getPage(group);

        Form form = getForm(page);
        // @formatter:on

        IFormQueryService formQueryService = new IFormQueryService.NoOp() {
            @Override
            public Optional<AbstractWidget> findWidget(Form form, String widgetId) {
                return Optional.of(list);
            }
        };

        ClickListItemEventHandler handler = new ClickListItemEventHandler(formQueryService, new ICollaborativeFormMessageService.NoOp(), new SimpleMeterRegistry());
        assertThat(handler.canHandle(input)).isTrue();

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), form, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(changeKind);
        Map<String, Object> changeDescriptionParameters = changeDescription.getParameters();
        assertThat(changeDescriptionParameters.get(CHANGE_DESCRIPTION_PARAMETER_KEY)).isEqualTo(listItemId);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(SuccessPayload.class);

        assertThat(hasBeenExecuted.get()).isTrue();
    }

    @Test
    public void testListItemSelectionReadOnly() {
        String listItemId = "ListItem Id";
        String changeKind = ChangeKind.SEMANTIC_CHANGE;

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(CHANGE_DESCRIPTION_PARAMETER_KEY, listItemId);
        var input = new ClickListItemInput(UUID.randomUUID(), FORM_ID.toString(), UUID.randomUUID().toString(), LIST_ID, listItemId, ClickEventKind.SINGLE_CLICK);

        AtomicBoolean hasBeenExecuted = new AtomicBoolean();
        Function<ClickEventKind, IStatus> selectHandler = (clickEventKind) -> {
            hasBeenExecuted.set(true);
            return new Success(changeKind, parameters);
        };

        // @formatter:off
        ListItem listItem = getListItemWithClickHandler(listItemId, selectHandler);

        List list = getList(LIST_ID, listItem, true);

        Group group = getGroup(list);

        Page page = getPage(group);

        Form form = getForm(page);
        // @formatter:on

        IFormQueryService formQueryService = new IFormQueryService.NoOp() {
            @Override
            public Optional<AbstractWidget> findWidget(Form form, String widgetId) {
                return Optional.of(list);
            }
        };

        ClickListItemEventHandler handler = new ClickListItemEventHandler(formQueryService, new ICollaborativeFormMessageService.NoOp(), new SimpleMeterRegistry());
        assertThat(handler.canHandle(input)).isTrue();

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), form, input);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(ErrorPayload.class);
        assertThat(((ErrorPayload) payload).message()).isEqualTo("Read-only widget can not be edited");
        assertThat(hasBeenExecuted.get()).isFalse();
    }

}
