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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.springframework.stereotype.Service;

/**
 * Use to create an explorer for Domain meta model.
 *
 * @author Jerome Gout
 */
@Service
public class DomainExplorerRepresentationDescriptionProvider implements IEditingContextRepresentationDescriptionProvider {

    public static final String DESCRIPTION_ID = "domain_explorer_description";

    private final DomainTreeRepresentationDescriptionProvider domainTreeRepresentationDescriptionProvider;

    public DomainExplorerRepresentationDescriptionProvider(DomainTreeRepresentationDescriptionProvider domainTreeRepresentationDescriptionProvider) {
        this.domainTreeRepresentationDescriptionProvider = domainTreeRepresentationDescriptionProvider;
    }


    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(IEditingContext editingContext) {
        var result = this.domainTreeRepresentationDescriptionProvider.getRepresentationDescriptions(editingContext);
        TreeDescription domainTreeDescription = (TreeDescription) result.get(0);

        var explorerDescription = TreeDescription.newTreeDescription(domainTreeDescription)
                .id(DESCRIPTION_ID)
                .label("Domain explorer")
                .idProvider(new GetOrCreateRandomIdProvider())
                .canCreatePredicate(variableManager -> false)
                .targetObjectIdProvider(variableManager -> variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class).map(IEditingContext::getId).orElse(null))
                .elementsProvider(this::getElements)
                .contextMenuEntries(List.of())
                .build();

        return List.of(explorerDescription);
    }

    private List<Domain> getElements(VariableManager variableManager) {
        var optionalEditingContext = Optional.of(variableManager.getVariables().get(IEditingContext.EDITING_CONTEXT));
        var optionalResourceSet = optionalEditingContext.filter(IEditingContext.class::isInstance)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain)
                .map(EditingDomain::getResourceSet);
        return optionalResourceSet.map(resourceSet -> resourceSet.getResources().stream()
                .flatMap(res -> res.getContents().stream())
                .filter(Domain.class::isInstance)
                .map(Domain.class::cast)
                .toList()).orElseGet(ArrayList::new);
    }
}
