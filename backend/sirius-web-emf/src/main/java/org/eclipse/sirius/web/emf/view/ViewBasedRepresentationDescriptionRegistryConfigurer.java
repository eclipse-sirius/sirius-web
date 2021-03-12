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
package org.eclipse.sirius.web.emf.view;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.api.configuration.IRepresentationDescriptionRegistry;
import org.eclipse.sirius.web.api.configuration.IRepresentationDescriptionRegistryConfigurer;
import org.eclipse.sirius.web.representations.IRepresentationDescription;
import org.springframework.context.annotation.Configuration;

/**
 * Populates the global {@link IRepresentationDescriptionRegistry} with diagram descriptions converted from View
 * documents in the database.
 *
 * @author pcdavid
 */
@Configuration
public class ViewBasedRepresentationDescriptionRegistryConfigurer implements IRepresentationDescriptionRegistryConfigurer {

    private final DynamicRepresentationDescriptionService dynamicDiagramDescriptionService;

    public ViewBasedRepresentationDescriptionRegistryConfigurer(DynamicRepresentationDescriptionService dynamicDiagramDescriptionService) {
        this.dynamicDiagramDescriptionService = Objects.requireNonNull(dynamicDiagramDescriptionService);
    }

    @Override
    public void addRepresentationDescriptions(IRepresentationDescriptionRegistry registry) {
        List<IRepresentationDescription> dynamicDiagramDescriptions = this.dynamicDiagramDescriptionService.findDynamicRepresentationDescriptions();
        dynamicDiagramDescriptions.forEach(registry::add);
    }

}
