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
package org.eclipse.sirius.components.collaborative.trees.architecture.handlers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.trees.api.ITreePathProvider;
import org.eclipse.sirius.components.collaborative.trees.dto.TreePathInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreePathSuccessPayload;
import org.eclipse.sirius.components.collaborative.trees.handlers.TreePathEventHandler;
import org.eclipse.sirius.components.collaborative.trees.services.api.ITreeNavigationService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.trees.Tree;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Tests for the tree path even handler.
 *
 * @author Jerome Gout
 */
public class TreePathEventHandlerTests {

    @Test
    public void testTreePathWithEmptyProviders() {
        var handler = new TreePathEventHandler(List.of(), new ITreeNavigationService.NoOp());
        TreePathInput input = new TreePathInput(UUID.randomUUID(), "editingContextId", "representationId", List.of());

        assertThat(handler.canHandle(input)).isTrue();

        One<IPayload> payloadSink = Sinks.one();
        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();

        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), null, null, input);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(TreePathSuccessPayload.class);
    }

    @Test
    public void testTreePathWihErrorProvider() {
        ITreePathProvider errorProvider = new ITreePathProvider() {
            @Override
            public IPayload handle(IEditingContext editingContext, Tree tree, TreePathInput input) {
                return new ErrorPayload(UUID.randomUUID(), "provider failed");
            }
            @Override
            public boolean canHandle(Tree tree) {
                return true;
            }
        };

        var handler = new TreePathEventHandler(List.of(errorProvider), new ITreeNavigationService.NoOp());
        TreePathInput input = new TreePathInput(UUID.randomUUID(), "editingContextId", "representationId", List.of());

        assertThat(handler.canHandle(input)).isTrue();

        One<IPayload> payloadSink = Sinks.one();
        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();

        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), null, null, input);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(ErrorPayload.class);
    }
}
