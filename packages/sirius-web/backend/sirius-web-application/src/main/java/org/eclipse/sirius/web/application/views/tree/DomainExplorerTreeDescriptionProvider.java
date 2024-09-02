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

    public DomainExplorerTreeDescriptionProvider(IRepresentationDescriptionSearchService representationDescriptionSearchService) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
    }

    @Override
    public List<TreeDescription> getDescriptions(IEditingContext editingContext) {
        var optionalDomainExplorerDescription = this.getDomainExplorerTreeDescription(editingContext);

        return optionalDomainExplorerDescription.map(List::of).orElseGet(List::of);
    }

    private Optional<TreeDescription> getDomainExplorerTreeDescription(IEditingContext editingContext) {
        if (this.isContainingDomainElement(editingContext)) {
            return this.representationDescriptionSearchService
                    .findById(editingContext, DomainExplorerRepresentationDescriptionProvider.DESCRIPTION_ID)
                    .filter(TreeDescription.class::isInstance)
                    .map(TreeDescription.class::cast);
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

}
