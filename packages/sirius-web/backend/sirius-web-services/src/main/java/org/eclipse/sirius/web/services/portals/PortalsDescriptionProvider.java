/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.web.services.portals;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.configuration.IRepresentationDescriptionRegistry;
import org.eclipse.sirius.components.core.configuration.IRepresentationDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.portals.description.PortalDescription;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * Registers the singleton PortalDescription.
 *
 * @author pcdavid
 */
@Service
public class PortalsDescriptionProvider implements IRepresentationDescriptionRegistryConfigurer {
    public static final String DESCRIPTION_ID = UUID.nameUUIDFromBytes("portals_description".getBytes()).toString();

    private final IObjectService objectService;

    public PortalsDescriptionProvider(IObjectService objectService) {
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public void addRepresentationDescriptions(IRepresentationDescriptionRegistry registry) {
        registry.add(PortalDescription.newPortalDescription(DESCRIPTION_ID)
                .label("Portal")
                .idProvider(new GetOrCreateRandomIdProvider())
                .labelProvider(variableManager -> variableManager.get("name", String.class).orElse("Portal"))
                .targetObjectIdProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(""))
                .canCreatePredicate(variableManager -> true)
                .build());
    }
}
