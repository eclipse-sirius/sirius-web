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
package org.eclipse.sirius.components.view.emf.form;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.form.api.IViewFormDescriptionSearchService;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.form.FormElementDescription;
import org.springframework.stereotype.Service;

/**
 * Used to find information from view based form descriptions.
 *
 * @author Arthur Daussy
 */
@Service
public class ViewFormDescriptionSearchService implements IViewFormDescriptionSearchService {

    private final IIdentityService identityService;

    private final IURLParser urlParser;

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    public ViewFormDescriptionSearchService(IIdentityService identityService, IURLParser urlParser, IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.urlParser = Objects.requireNonNull(urlParser);
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
    }

    @Override
    public Optional<FormDescription> findById(IEditingContext editingContext, String formDescriptionId) {
        return this.viewRepresentationDescriptionSearchService.findById(editingContext, formDescriptionId).filter(FormDescription.class::isInstance).map(FormDescription.class::cast);
    }

    @Override
    public Optional<FormElementDescription> findFormElementDescriptionById(IEditingContext editingContext, String formElementDescriptionId) {
        Optional<String> optionalSourceElementId = this.getSourceElementId(formElementDescriptionId);
        Optional<String> optionalSourceId = this.getSourceId(formElementDescriptionId);
        if (optionalSourceElementId.isPresent() && optionalSourceId.isPresent()) {
            var sourceElementId = optionalSourceElementId.get();
            var sourceId = optionalSourceId.get();

            var views = this.viewRepresentationDescriptionSearchService.findViewsBySourceId(editingContext, sourceId);

            return views.stream()
                    .flatMap(view -> view.getDescriptions().stream())
                    .filter(FormDescription.class::isInstance)
                    .map(FormDescription.class::cast)
                    .map(viewFormDescription -> this.findFormElementDescriptionById(viewFormDescription, sourceElementId))
                    .flatMap(Optional::stream)
                    .findFirst();
        }
        return Optional.empty();
    }

    private Optional<FormElementDescription> findFormElementDescriptionById(FormDescription viewFormDescription, String sourceElementId) {

        TreeIterator<EObject> contentIterator = viewFormDescription.eAllContents();
        while (contentIterator.hasNext()) {
            EObject next = contentIterator.next();
            if (next instanceof FormElementDescription desc && sourceElementId.equals(this.identityService.getId(desc))) {
                return Optional.of(desc);
            }
        }
        return Optional.empty();
    }

    private Optional<String> getSourceElementId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IRepresentationDescriptionIdProvider.SOURCE_ELEMENT_ID)).orElse(List.of()).stream().findFirst();
    }

    private Optional<String> getSourceId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IRepresentationDescriptionIdProvider.SOURCE_ID)).orElse(List.of()).stream().findFirst();
    }

}
