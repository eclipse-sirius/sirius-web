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
package org.eclipse.sirius.web.application.studio.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.springframework.stereotype.Service;

/**
 * Used to find view representation descriptions.
 *
 * @author sbegaudeau
 */
@Service
public class ViewRepresentationDescriptionSearchService implements IViewRepresentationDescriptionSearchService {

    private final IURLParser urlParser;

    private final IIdentityService identityService;

    public ViewRepresentationDescriptionSearchService(IURLParser urlParser, IIdentityService identityService) {
        this.urlParser = Objects.requireNonNull(urlParser);
        this.identityService = Objects.requireNonNull(identityService);
    }

    @Override
    public Optional<RepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
        Optional<String> optionalSourceId = this.getSourceId(representationDescriptionId);
        Optional<String> optionalSourceElementId = this.getSourceElementId(representationDescriptionId);
        if (optionalSourceId.isPresent() && optionalSourceElementId.isPresent()) {
            var sourceElementId = optionalSourceElementId.get();

            List<View> views = this.findViewsBySourceId(editingContext, optionalSourceId.get());
            if (!views.isEmpty()) {
                var searchedView = views.stream()
                        .flatMap(view -> view.getDescriptions().stream())
                        .filter(representationDescription -> sourceElementId.equals(this.identityService.getId(representationDescription)))
                        .findFirst();
                if (searchedView.isPresent()) {
                    return searchedView;
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<View> findViewsBySourceId(IEditingContext editingContext, String sourceId) {
        var views = Optional.of(editingContext)
                .filter(EditingContext.class::isInstance)
                .map(EditingContext.class::cast)
                .map(EditingContext::getViews)
                .orElse(List.of());

        return views.stream()
                .filter(view -> view.eResource().getURI().segment(0).equals(sourceId))
                .toList();
    }

    private Optional<String> getSourceId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IRepresentationDescriptionIdProvider.SOURCE_ID)).orElse(List.of()).stream().findFirst();
    }

    private Optional<String> getSourceElementId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IRepresentationDescriptionIdProvider.SOURCE_ELEMENT_ID)).orElse(List.of()).stream().findFirst();
    }
}
