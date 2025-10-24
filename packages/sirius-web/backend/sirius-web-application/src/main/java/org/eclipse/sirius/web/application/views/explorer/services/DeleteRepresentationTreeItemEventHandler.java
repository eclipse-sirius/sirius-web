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

import org.eclipse.sirius.components.collaborative.api.ChangeDescriptionParameters;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.dto.DeleteRepresentationInput;
import org.eclipse.sirius.components.collaborative.trees.api.IDeleteTreeItemHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataDeletionService;
import org.springframework.stereotype.Service;

/**
 * Handles representation deletion triggered via a tree item from the explorer.
 *
 * @author pcdavid
 */
@Service
public class DeleteRepresentationTreeItemEventHandler implements IDeleteTreeItemHandler {

    private final IRepresentationMetadataDeletionService representationMetadataDeletionService;

    public DeleteRepresentationTreeItemEventHandler(IRepresentationMetadataDeletionService representationMetadataDeletionService) {
        this.representationMetadataDeletionService = Objects.requireNonNull(representationMetadataDeletionService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, TreeItem treeItem) {
        return treeItem.getKind().startsWith(IRepresentation.KIND_PREFIX);
    }

    @Override
    public IStatus handle(IEditingContext editingContext, TreeItem treeItem, Tree tree) {
        var optionalRepresentationUUID = new UUIDParser().parse(treeItem.getId());
        var input = new DeleteRepresentationInput(UUID.randomUUID(), treeItem.getId());
        if (optionalRepresentationUUID.isPresent()) {
            var representationUUID = optionalRepresentationUUID.get();
            var result = this.representationMetadataDeletionService.delete(input, representationUUID);
            if (result instanceof org.eclipse.sirius.web.domain.services.Success) {
                Map<String, Object> parameters = new HashMap<>();
                parameters.put(ChangeDescriptionParameters.REPRESENTATION_ID, treeItem.getId());
                return new Success(ChangeKind.REPRESENTATION_DELETION, parameters);
            }
        }
        return new Failure("");
    }
}
