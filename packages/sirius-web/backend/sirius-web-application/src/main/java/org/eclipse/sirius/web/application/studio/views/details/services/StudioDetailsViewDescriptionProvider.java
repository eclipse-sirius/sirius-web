/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
package org.eclipse.sirius.web.application.studio.views.details.services;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistry;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistryConfigurer;
import org.eclipse.sirius.web.application.studio.views.details.services.api.IStudioDetailsViewPageDescriptionProvider;
import org.springframework.stereotype.Service;

/**
 * Used to contribute custom details view page for some studio related objects.
 *
 * @author pcdavid
 */
@Service
public class StudioDetailsViewDescriptionProvider implements IPropertiesDescriptionRegistryConfigurer {

    private final IStudioDetailsViewPageDescriptionProvider studioDetailsViewPageDescriptionProvider;

    public StudioDetailsViewDescriptionProvider(IStudioDetailsViewPageDescriptionProvider studioDetailsViewPageDescriptionProvider) {
        this.studioDetailsViewPageDescriptionProvider = Objects.requireNonNull(studioDetailsViewPageDescriptionProvider);
    }

    @Override
    public void addPropertiesDescriptions(IPropertiesDescriptionRegistry registry) {
        registry.add(this.studioDetailsViewPageDescriptionProvider.createPageDescription());
    }


}
