/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.application.views.viewsexplorer.services;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import org.eclipse.sirius.components.collaborative.trees.api.ITreePathProvider;
import org.eclipse.sirius.components.collaborative.trees.dto.TreePath;
import org.eclipse.sirius.components.collaborative.trees.dto.TreePathInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreePathSuccessPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.springframework.stereotype.Service;

/**
 * ITreePathProvider implementation for the Views Explorer tree.
 *
 * @author tgiraudet
 */
@Service
public class ViewsExplorerTreePathProvider implements ITreePathProvider {

    private final IObjectSearchService objectSearchService;
    private final IURLParser urlParser;

    public ViewsExplorerTreePathProvider(IObjectSearchService objectSearchService, IURLParser urlParser) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.urlParser = Objects.requireNonNull(urlParser);
    }

    @Override
    public boolean canHandle(Tree tree) {
        return tree != null && ViewsExplorerTreeDescriptionProvider.DESCRIPTION_ID.equals(tree.getDescriptionId());
    }

    @Override
    public IPayload handle(IEditingContext editingContext, Tree tree, TreePathInput input) {
        Function<String, String> kindParser = (String kindId) -> this.urlParser.getParameterValues(kindId).get("type").get(0);

        var allRepresentationMetadataAncestors = input.selectionEntryIds()
            .stream()
            .map(entryId -> this.objectSearchService.getObject(editingContext, entryId))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .filter(RepresentationMetadata.class::isInstance)
            .map(RepresentationMetadata.class::cast)
            .flatMap(representationMetadata -> Stream.of(kindParser.apply(representationMetadata.getKind()), representationMetadata.getDescriptionId()))
            .distinct()
            .toList();

        var maxDepth = Math.max(allRepresentationMetadataAncestors.size(), 2);

        return new TreePathSuccessPayload(input.id(), new TreePath(allRepresentationMetadataAncestors, maxDepth));
    }

}
