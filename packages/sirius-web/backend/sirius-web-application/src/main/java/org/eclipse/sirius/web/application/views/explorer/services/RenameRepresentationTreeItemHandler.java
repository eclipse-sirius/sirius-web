/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.application.views.explorer.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.editingcontext.EditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.trees.api.IRenameTreeItemHandler;
import org.eclipse.sirius.components.collaborative.trees.dto.RenameTreeItemInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.springframework.stereotype.Service;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataUpdateService;

/**
 * Handles representation renaming triggered via a tree item from the explorer.
 *
 * @author frouene
 *
 * @technical-debt Given the fact that {@link org.eclipse.sirius.components.trees.description.TreeDescription#getRenameHandler()}
 * is hardcoded in the tree description, we can't easily retrieve the input sent by the frontend. As a result, a placeholder is
 * created. It contains most of what the real input would have, but it does not share the same identifier, thus breaking a core
 * part of the contract of the input and payloads. When the rename handler will be deleted, this issue will be addressable.
 */
@Service
public class RenameRepresentationTreeItemHandler implements IRenameTreeItemHandler {

    private final IRepresentationMetadataUpdateService representationMetadataUpdateService;

    public RenameRepresentationTreeItemHandler(IRepresentationMetadataUpdateService representationMetadataUpdateService) {
        this.representationMetadataUpdateService = Objects.requireNonNull(representationMetadataUpdateService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, TreeItem treeItem, String newLabel) {
        return treeItem.getKind().startsWith(IRepresentation.KIND_PREFIX);
    }

    @Override
    public IStatus handle(IEditingContext editingContext, TreeItem treeItem, String newLabel, Tree tree) {
        var optionalRepresentationUUID = new UUIDParser().parse(treeItem.getId());
        if (optionalRepresentationUUID.isPresent()) {
            var representationUUID = optionalRepresentationUUID.get();

            var input = new RenameTreeItemInput(UUID.randomUUID(), editingContext.getId(), tree.getId(), representationUUID, newLabel);
            var result = this.representationMetadataUpdateService.updateLabel(input, representationUUID, newLabel);
            if (result instanceof org.eclipse.sirius.web.domain.services.Success) {
                Map<String, Object> parameters = new HashMap<>();
                parameters.put(EditingContextEventProcessor.REPRESENTATION_ID, treeItem.getId());
                parameters.put(EditingContextEventProcessor.REPRESENTATION_LABEL, newLabel);
                return new Success(ChangeKind.REPRESENTATION_RENAMING, parameters);
            }
        }
        return new Failure("");
    }
}
