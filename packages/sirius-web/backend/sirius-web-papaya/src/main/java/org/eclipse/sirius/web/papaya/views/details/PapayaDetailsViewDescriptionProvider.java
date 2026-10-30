/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.web.papaya.views.details;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.builder.generated.form.FormBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IRepresentationDescriptionProvider;
import org.eclipse.sirius.web.papaya.views.details.api.IPageDescriptionProvider;

/**
 * Used to provide support for a custom details view for Papaya objects.
 *
 * @author sbegaudeau
 */
public class PapayaDetailsViewDescriptionProvider implements IRepresentationDescriptionProvider {

    public static final String PAPAYA_DETAILS_VIEW_NAME = "Papaya Details View";

    private final List<IPageDescriptionProvider> pageDescriptionProviders;

    public PapayaDetailsViewDescriptionProvider(List<IPageDescriptionProvider> pageDescriptionProviders) {
        this.pageDescriptionProviders = Objects.requireNonNull(pageDescriptionProviders);
    }

    @Override
    public RepresentationDescription create(IColorProvider colorProvider) {
        var viewFormDescription = new FormBuilders().newFormDescription()
                .name(PAPAYA_DETAILS_VIEW_NAME)
                .domainType("papaya:NamedElement")
                .titleExpression("")
                .preconditionExpression("aql:false")
                .build();

        var pages = this.pageDescriptionProviders.stream()
                .map(pageDescriptionProvider -> pageDescriptionProvider.getPageDescription(colorProvider))
                .toList();
        viewFormDescription.getPages().addAll(pages);

        return viewFormDescription;
    }
}
