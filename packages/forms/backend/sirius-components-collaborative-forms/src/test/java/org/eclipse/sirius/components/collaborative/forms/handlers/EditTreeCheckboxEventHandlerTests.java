/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.forms.api.IFormQueryService;
import org.eclipse.sirius.components.collaborative.forms.dto.EditTreeCheckboxInput;
import org.eclipse.sirius.components.collaborative.forms.messages.ICollaborativeFormMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.Group;
import org.eclipse.sirius.components.forms.Page;
import org.eclipse.sirius.components.forms.TreeNode;
import org.eclipse.sirius.components.forms.TreeWidget;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Unit tests of the tree edit checkbox event handler.
 *
 * @author mcharfadi
 */
public class EditTreeCheckboxEventHandlerTests {
    private static final String FORM_ID = UUID.randomUUID().toString();

    private static final String LABEL = "Label";

    @Test
    public void testCheckboxEdition() {
        String treeId = "TreeId";
        String checkboxId = "checkboxId";

        var input = new EditTreeCheckboxInput(UUID.randomUUID(), UUID.randomUUID().toString(), FORM_ID, treeId, checkboxId, true);

        AtomicBoolean hasBeenExecuted = new AtomicBoolean();
        Function<Boolean, IStatus> newValueHandler = newValue -> {
            hasBeenExecuted.set(true);
            return new Success();
        };

        TreeNode treeNode = TreeNode.newTreeNode(checkboxId)
                .newValueHandler(newValueHandler)
                .label(LABEL)
                .iconURL(List.of())
                .endIconsURL(List.of())
                .checkable(true)
                .selectable(false)
                .value(true)
                .kind("treeNode")
                .build();

        TreeWidget tree = TreeWidget.newTreeWidget(treeId)
                .label(LABEL)
                .iconURL(List.of())
                .expandedNodesIds(List.of(checkboxId))
                .diagnostics(List.of())
                .readOnly(false)
                .nodes(List.of(treeNode))
                .build();

        Group group = Group.newGroup("groupId")
                .label("group label")
                .widgets(List.of(tree))
                .build();

        Page page = Page.newPage("pageId")
                .label("page label")
                .groups(List.of(group))
                .build();

        Form form = Form.newForm(FORM_ID)
                .targetObjectId("targetObjectId")
                .descriptionId(UUID.randomUUID().toString())
                .label("form label")
                .pages(List.of(page))
                .build();

        IFormQueryService formQueryService = new IFormQueryService.NoOp() {
            @Override
            public Optional<AbstractWidget> findWidget(Form form, String widgetId) {
                return Optional.of(tree);
            }
        };
        EditTreeCheckBoxEventHandler handler = new EditTreeCheckBoxEventHandler(formQueryService, new ICollaborativeFormMessageService.NoOp(), new SimpleMeterRegistry());
        assertThat(handler.canHandle(input)).isTrue();

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), form, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(SuccessPayload.class);

        assertThat(hasBeenExecuted.get()).isTrue();
    }

    @Test
    public void testCheckboxEditionReadOnly() {
        String treeId = "TreeId";
        String checkboxId = "checkboxId";

        var input = new EditTreeCheckboxInput(UUID.randomUUID(), UUID.randomUUID().toString(), FORM_ID, treeId, checkboxId, true);

        AtomicBoolean hasBeenExecuted = new AtomicBoolean();
        Function<Boolean, IStatus> newValueHandler = newValue -> {
            hasBeenExecuted.set(true);
            return new Success();
        };

        TreeNode treeNode = TreeNode.newTreeNode(checkboxId)
                .newValueHandler(newValueHandler)
                .label(LABEL)
                .iconURL(List.of())
                .endIconsURL(List.of())
                .checkable(true)
                .selectable(false)
                .value(true)
                .kind("treeNode")
                .build();

        TreeWidget tree = TreeWidget.newTreeWidget(treeId)
                .label(LABEL)
                .iconURL(List.of())
                .expandedNodesIds(List.of(checkboxId))
                .diagnostics(List.of())
                .readOnly(true)
                .nodes(List.of(treeNode))
                .build();

        Group group = Group.newGroup("groupId")
                .label("group label")
                .widgets(List.of(tree))
                .build();

        Page page = Page.newPage("pageId")
                .label("page label")
                .groups(List.of(group))
                .build();

        Form form = Form.newForm(FORM_ID)
                .targetObjectId("targetObjectId")
                .descriptionId(UUID.randomUUID().toString())
                .label("form label")
                .pages(List.of(page))
                .build();

        IFormQueryService formQueryService = new IFormQueryService.NoOp() {
            @Override
            public Optional<AbstractWidget> findWidget(Form form, String widgetId) {
                return Optional.of(tree);
            }
        };
        EditTreeCheckBoxEventHandler handler = new EditTreeCheckBoxEventHandler(formQueryService, new ICollaborativeFormMessageService.NoOp(), new SimpleMeterRegistry());
        assertThat(handler.canHandle(input)).isTrue();

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), form, input);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(ErrorPayload.class);
        assertThat(((ErrorPayload) payload).message()).isNotNull();
        assertThat(hasBeenExecuted.get()).isFalse();
    }
}
