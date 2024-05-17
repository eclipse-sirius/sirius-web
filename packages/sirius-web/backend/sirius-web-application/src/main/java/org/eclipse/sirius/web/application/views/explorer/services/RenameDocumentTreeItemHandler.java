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

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.web.application.views.explorer.services.api.IRenameTreeItemHandler;
import org.springframework.stereotype.Service;

/**
 * Handles document renaming triggered via a tree item from the explorer.
 *
 * @author frouene
 */
@Service
public class RenameDocumentTreeItemHandler implements IRenameTreeItemHandler {

    @Override
    public boolean canHandle(IEditingContext editingContext, TreeItem treeItem, String newLabel) {
        return ExplorerDescriptionProvider.DOCUMENT_KIND.equals(treeItem.getKind());
    }

    @Override
    public IStatus handle(IEditingContext editingContext, TreeItem treeItem, String newLabel) {
        var optionalEditingDomain = Optional.of(editingContext)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain);

        if (optionalEditingDomain.isPresent()) {
            var editingDomain = optionalEditingDomain.get();
            ResourceSet resourceSet = editingDomain.getResourceSet();
            URI uri = new JSONResourceFactory().createResourceURI(treeItem.getId());

            List<Resource> resourcesToRename = resourceSet.getResources().stream()
                    .filter(resource -> resource.getURI().equals(uri))
                    .toList();
            resourcesToRename.forEach(resource -> resource.eAdapters().stream()
                    .filter(ResourceMetadataAdapter.class::isInstance)
                    .map(ResourceMetadataAdapter.class::cast)
                    .findFirst()
                    .ifPresent(adapter -> adapter.setName(newLabel)));

            return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of());
        }
        return new Failure("Something went wrong while handling this rename action.");
    }
}
