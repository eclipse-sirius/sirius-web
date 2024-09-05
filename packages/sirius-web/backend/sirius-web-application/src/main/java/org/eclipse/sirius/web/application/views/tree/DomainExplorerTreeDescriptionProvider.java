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
package org.eclipse.sirius.web.application.views.tree;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.tree.ITreeIdProvider;
import org.eclipse.sirius.web.application.studio.services.representations.DomainViewTreeDescriptionProvider;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerTreeDescriptionProvider;
import org.springframework.stereotype.Service;

/**
 * This class is used to provide tree descriptions that can be used as explorer for Domain.
 *
 * @author Jerome Gout
 */
@Service
public class DomainExplorerTreeDescriptionProvider implements IExplorerTreeDescriptionProvider {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    public DomainExplorerTreeDescriptionProvider(IRepresentationDescriptionSearchService representationDescriptionSearchService, IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
    }

    @Override
    public List<TreeDescription> getDescriptions(IEditingContext editingContext) {
        var optionalDomainExplorerDescription = this.getDomainExplorerTreeDescription(editingContext);

        return optionalDomainExplorerDescription.map(List::of).orElseGet(List::of);
    }

    private Optional<TreeDescription> getDomainExplorerTreeDescription(IEditingContext editingContext) {
        if (this.isContainingDomainElement(editingContext)) {
            return this.representationDescriptionSearchService
                    .findAll(editingContext).values().stream()
                    .filter(TreeDescription.class::isInstance)
                    .map(TreeDescription.class::cast)
                    .filter(td -> this.isDomainExplorerViewTreeDescription(td, editingContext))
                    .findFirst();
        }
        return Optional.empty();
    }

    private boolean isContainingDomainElement(IEditingContext editingContext) {
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            return emfEditingContext.getDomain().getResourceSet().getResources().stream()
                    .flatMap(res -> res.getContents().stream())
                    .anyMatch(Domain.class::isInstance);
        }
        return false;
    }

    private boolean isDomainExplorerViewTreeDescription(TreeDescription treeDescription, IEditingContext editingContext) {
        if (treeDescription.getId().startsWith(ITreeIdProvider.TREE_DESCRIPTION_KIND)) {
            // this tree description comes from a tree DSL
            var optionalViewTreeDescription = this.viewRepresentationDescriptionSearchService.findById(editingContext, treeDescription.getId());
            if (optionalViewTreeDescription.isPresent()) {
                return optionalViewTreeDescription.get().getName().equals(DomainViewTreeDescriptionProvider.DOMAIN_EXPLORER_DESCRIPTION_NAME);
            }
        }
        return false;
    }
}
