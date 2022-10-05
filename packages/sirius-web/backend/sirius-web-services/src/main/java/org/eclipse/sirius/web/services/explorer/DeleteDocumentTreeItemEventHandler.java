/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.web.services.explorer;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.repositories.IDocumentRepository;
import org.eclipse.sirius.web.services.api.id.IDParser;
import org.eclipse.sirius.web.services.explorer.api.IDeleteTreeItemHandler;
import org.springframework.stereotype.Service;

/**
 * Handles document deletion triggered via a tree item from the explorer.
 *
 * @author pcdavid
 */
@Service
public class DeleteDocumentTreeItemEventHandler implements IDeleteTreeItemHandler {

    private final IDocumentRepository documentRepository;

    public DeleteDocumentTreeItemEventHandler(IDocumentRepository documentRepository) {
        this.documentRepository = Objects.requireNonNull(documentRepository);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, TreeItem treeItem) {
        return treeItem.getKind().equals(ExplorerDescriptionProvider.DOCUMENT_KIND);
    }

    @Override
    public IStatus handle(IEditingContext editingContext, TreeItem treeItem) {
        // @formatter:off
        var optionalEditingDomain = Optional.of(editingContext)
                .filter(EditingContext.class::isInstance)
                .map(EditingContext.class::cast)
                .map(EditingContext::getDomain);
        // @formatter:on

        var optionalDocumentEntity = new IDParser().parse(treeItem.getId()).flatMap(this.documentRepository::findById);
        if (optionalEditingDomain.isPresent() && optionalDocumentEntity.isPresent()) {
            AdapterFactoryEditingDomain editingDomain = optionalEditingDomain.get();
            DocumentEntity documentEntity = optionalDocumentEntity.get();

            ResourceSet resourceSet = editingDomain.getResourceSet();
            URI uri = new JSONResourceFactory().createResourceURI(documentEntity.getId().toString());

            // @formatter:off
                List<Resource> resourcesToDelete = resourceSet.getResources().stream()
                        .filter(resource -> resource.getURI().equals(uri))
                        .collect(Collectors.toUnmodifiableList());
                resourcesToDelete.stream().forEach(resourceSet.getResources()::remove);
                // @formatter:on

            this.documentRepository.delete(documentEntity);

            return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of());
        }
        return new Failure(""); //$NON-NLS-1$
    }
}
