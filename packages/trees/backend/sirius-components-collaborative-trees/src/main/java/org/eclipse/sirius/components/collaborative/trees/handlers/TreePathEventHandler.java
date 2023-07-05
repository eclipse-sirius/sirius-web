/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.trees.handlers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeEventHandler;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeInput;
import org.eclipse.sirius.components.collaborative.trees.api.ITreePathProvider;
import org.eclipse.sirius.components.collaborative.trees.dto.TreePath;
import org.eclipse.sirius.components.collaborative.trees.dto.TreePathInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreePathSuccessPayload;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler for TreePathInput which simply forwards to the first available ITreePathProvider which can handle the
 * request.
 *
 * @author pcdavid
 */
@Service
public class TreePathEventHandler implements ITreeEventHandler {

    private final Logger logger = LoggerFactory.getLogger(TreePathEventHandler.class);

    private final List<ITreePathProvider> treePathProviders;

    public TreePathEventHandler(List<ITreePathProvider> treePathProviders) {
        this.treePathProviders = Objects.requireNonNull(treePathProviders);
    }

    @Override
    public boolean canHandle(ITreeInput treeInput) {
        return treeInput instanceof TreePathInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, TreeDescription treeDescription, Tree tree, ITreeInput treeInput) {
        IPayload payload = new TreePathSuccessPayload(treeInput.id(), new TreePath(List.of(), 0));
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, treeInput.representationId(), treeInput);

        if (treeInput instanceof TreePathInput input) {
            Optional<ITreePathProvider> optionalPathProvider = this.treePathProviders.stream().filter(treePathProvider -> treePathProvider.canHandle(tree)).findFirst();
            if (optionalPathProvider.isPresent()) {
                IPayload resultPayload = optionalPathProvider.get().handle(editingContext, tree, input);
                if (resultPayload instanceof TreePathSuccessPayload) {
                    payload = resultPayload;
                } else if (resultPayload instanceof ErrorPayload errorPayload) {
                    this.logger.warn(errorPayload.messages().stream().map(Message::body).collect(Collectors.joining("; ")));
                }
            }
        }

        changeDescriptionSink.tryEmitNext(changeDescription);
        payloadSink.tryEmitValue(payload);
    }

}
