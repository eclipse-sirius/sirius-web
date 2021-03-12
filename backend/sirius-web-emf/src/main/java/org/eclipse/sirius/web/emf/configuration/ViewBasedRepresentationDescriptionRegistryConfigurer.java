/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.emf.configuration;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.api.configuration.IRepresentationDescriptionRegistry;
import org.eclipse.sirius.web.api.configuration.IRepresentationDescriptionRegistryConfigurer;
import org.eclipse.sirius.web.representations.IRepresentationDescription;
import org.eclipse.sirius.web.services.api.representations.IDynamicRepresentationDescriptionService;
import org.springframework.context.annotation.Configuration;

/**
 * Populates the global {@link IRepresentationDescriptionRegistry} with diagram descriptions converted from View
 * documents in the database.
 *
 * @author pcdavid
 */
@Configuration
public class ViewBasedRepresentationDescriptionRegistryConfigurer implements IRepresentationDescriptionRegistryConfigurer {

    private final IDynamicRepresentationDescriptionService dynamicDiagramDescriptionService;

    public ViewBasedRepresentationDescriptionRegistryConfigurer(IDynamicRepresentationDescriptionService dynamicDiagramDescriptionService) {
        this.dynamicDiagramDescriptionService = Objects.requireNonNull(dynamicDiagramDescriptionService);
    }

    @Override
    public void addRepresentationDescriptions(IRepresentationDescriptionRegistry registry) {
        List<IRepresentationDescription> dynamicDiagramDescriptions = this.dynamicDiagramDescriptionService.findDynamicRepresentationDescriptions(UUID.randomUUID());
        dynamicDiagramDescriptions.forEach(registry::add);
    }

}
