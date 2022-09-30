/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.web.services.representations;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.web.services.api.representations.IDynamicRepresentationDescriptionService;

/**
 * Service used to search the representation descriptions available.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
public class RepresentationDescriptionSearchService implements IRepresentationDescriptionSearchService {

    private final RepresentationDescriptionRegistry registry;

    private final IDynamicRepresentationDescriptionService dynamicRepresentationDescriptionService;

    public RepresentationDescriptionSearchService(RepresentationDescriptionRegistry registry, IDynamicRepresentationDescriptionService dynamicRepresentationDescriptionService) {
        this.registry = Objects.requireNonNull(registry);
        this.dynamicRepresentationDescriptionService = Objects.requireNonNull(dynamicRepresentationDescriptionService);
    }

    @Override
    public Map<String, IRepresentationDescription> findAll(IEditingContext editingContext) {
        Map<String, IRepresentationDescription> allRepresentationDescriptions = new LinkedHashMap<>();
        this.registry.getRepresentationDescriptions().forEach(representationDescription -> {
            allRepresentationDescriptions.put(representationDescription.getId(), representationDescription);
        });
        this.dynamicRepresentationDescriptionService.findDynamicRepresentationDescriptions(editingContext).forEach(representationDescription -> {
            // The dynamically discovered version wins over the version discovered on startup.
            allRepresentationDescriptions.put(representationDescription.getId(), representationDescription);
        });
        return allRepresentationDescriptions;
    }

    @Override
    public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
        return Optional.ofNullable(this.findAll(editingContext).get(representationDescriptionId));
    }

}
