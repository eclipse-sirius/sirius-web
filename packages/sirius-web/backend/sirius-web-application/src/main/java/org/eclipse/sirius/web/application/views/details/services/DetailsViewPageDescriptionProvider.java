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

package org.eclipse.sirius.web.application.views.details.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.forms.api.IPropertiesDescriptionService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.web.application.views.details.services.api.IDetailsViewPageDescriptionProvider;
import org.eclipse.sirius.web.application.views.details.services.api.IDetailsViewPageDescriptionProviderDelegate;
import org.springframework.stereotype.Service;

/**
 * Service returning the page descriptions coming from different sources into a single list.
 *
 * @author tgiraudet
 */
@Service
public class DetailsViewPageDescriptionProvider implements IDetailsViewPageDescriptionProvider {

    private final IPropertiesDescriptionService propertiesDescriptionService;

    private final List<IDetailsViewPageDescriptionProviderDelegate> detailsViewPageDescriptionProviderDelegates;

    public DetailsViewPageDescriptionProvider(IPropertiesDescriptionService propertiesDescriptionService, List<IDetailsViewPageDescriptionProviderDelegate> detailsViewPageDescriptionProviderDelegates) {
        this.propertiesDescriptionService = Objects.requireNonNull(propertiesDescriptionService);
        this.detailsViewPageDescriptionProviderDelegates = Objects.requireNonNull(detailsViewPageDescriptionProviderDelegates);
    }

    @Override
    public List<PageDescription> getPageDescriptions(IEditingContext editingContext) {
        List<PageDescription> pageDescriptions = new ArrayList<>(this.propertiesDescriptionService.getPropertiesDescriptions());
        this.detailsViewPageDescriptionProviderDelegates.forEach(provider -> pageDescriptions.addAll(provider.getPageDescriptions(editingContext)));
        return pageDescriptions;
    }
}
