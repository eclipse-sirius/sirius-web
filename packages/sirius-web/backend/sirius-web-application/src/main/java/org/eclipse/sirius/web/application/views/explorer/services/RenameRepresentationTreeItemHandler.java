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
package org.eclipse.sirius.web.application.views.explorer.services;

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.trees.api.IRenameTreeItemHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataUpdateService;
import org.springframework.stereotype.Service;

/**
 * Handles representation renaming triggered via a tree item from the explorer.
 *
 * @author frouene
 */
@Service
public class RenameRepresentationTreeItemHandler implements IRenameTreeItemHandler {

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IRepresentationMetadataUpdateService representationMetadataUpdateService;

    public RenameRepresentationTreeItemHandler(IRepresentationMetadataSearchService representationMetadataSearchService, IRepresentationMetadataUpdateService representationMetadataUpdateService) {
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.representationMetadataUpdateService = Objects.requireNonNull(representationMetadataUpdateService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, TreeItem treeItem, String newLabel) {
        return treeItem.getKind().startsWith(IRepresentation.KIND_PREFIX);
    }

    @Override
    public IStatus handle(IEditingContext editingContext, TreeItem treeItem, String newLabel, Tree tree) {
        return new UUIDParser().parse(treeItem.getId())
                .filter(this.representationMetadataSearchService::existsById)
                .map(representationId -> this.representationMetadataUpdateService.updateLabel(null, representationId, newLabel))
                .filter(org.eclipse.sirius.web.domain.services.Success.class::isInstance)
                .map(success -> (IStatus) new Success(ChangeKind.REPRESENTATION_RENAMING, Map.of(IRepresentationEventProcessorRegistry.REPRESENTATION_LABEL, newLabel, IRepresentationEventProcessorRegistry.REPRESENTATION_ID, treeItem.getId())))
                .orElseGet(() -> new Failure(""));
    }
}
