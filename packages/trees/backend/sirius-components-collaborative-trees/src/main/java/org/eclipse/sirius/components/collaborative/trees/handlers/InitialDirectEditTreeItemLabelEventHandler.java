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
package org.eclipse.sirius.components.collaborative.trees.handlers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.trees.api.IInitialDirectEditTreeItemLabelProvider;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeEventHandler;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeInput;
import org.eclipse.sirius.components.collaborative.trees.dto.InitialDirectEditElementLabelInput;
import org.eclipse.sirius.components.collaborative.trees.dto.InitialDirectEditElementLabelSuccessPayload;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler for InitialDirectEditElementLabelInput which simply forwards to the first available IInitialDirectEditTreeItemLabelProvider which can handle the
 * request.
 *
 * @author mcharfadi
 */
@Service
public class InitialDirectEditTreeItemLabelEventHandler implements ITreeEventHandler {

    private final Logger logger = LoggerFactory.getLogger(InitialDirectEditTreeItemLabelEventHandler.class);

    private final List<IInitialDirectEditTreeItemLabelProvider> initialDirectEditTreeItemLabelProvider;

    public InitialDirectEditTreeItemLabelEventHandler(List<IInitialDirectEditTreeItemLabelProvider> initialDirectEditTreeItemLabelProvider) {
        this.initialDirectEditTreeItemLabelProvider = Objects.requireNonNull(initialDirectEditTreeItemLabelProvider);
    }

    @Override
    public boolean canHandle(ITreeInput treeInput) {
        return treeInput instanceof InitialDirectEditElementLabelInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, TreeDescription treeDescription, Tree tree, ITreeInput treeInput) {
        IPayload payload = new InitialDirectEditElementLabelSuccessPayload(treeInput.id(), "");
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, treeInput.representationId(), treeInput);

        if (treeInput instanceof InitialDirectEditElementLabelInput input) {
            Optional<IInitialDirectEditTreeItemLabelProvider> optionalPathProvider = this.initialDirectEditTreeItemLabelProvider.stream().filter(treePathProvider -> treePathProvider.canHandle(tree)).findFirst();
            if (optionalPathProvider.isPresent()) {
                IPayload resultPayload = optionalPathProvider.get().handle(editingContext, tree, input);
                if (resultPayload instanceof InitialDirectEditElementLabelSuccessPayload) {
                    payload = resultPayload;
                } else if (resultPayload instanceof ErrorPayload errorPayload) {
                    this.logger.warn(errorPayload.message());
                }
            }
        }
        changeDescriptionSink.tryEmitNext(changeDescription);
        payloadSink.tryEmitValue(payload);
    }

}