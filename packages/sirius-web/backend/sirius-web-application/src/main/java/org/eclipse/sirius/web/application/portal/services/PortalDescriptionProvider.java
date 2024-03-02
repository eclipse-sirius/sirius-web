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
package org.eclipse.sirius.web.application.portal.services;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.portals.description.PortalDescription;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.web.application.portal.OnPortalEnabled;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Registers the singleton PortalDescription.
 *
 * @author pcdavid
 */
@Service
@Conditional(OnPortalEnabled.class)
public class PortalDescriptionProvider implements IEditingContextRepresentationDescriptionProvider {

    public static final String DESCRIPTION_ID = UUID.nameUUIDFromBytes("portals_description".getBytes()).toString();

    private final IObjectService objectService;

    public PortalDescriptionProvider(IObjectService objectService) {
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(IEditingContext editingContext) {
        Function<VariableManager, String> labelProvider = variableManager -> variableManager.get("name", String.class)
                .orElse("Portal");

        Function<VariableManager, String> targetObjectIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.objectService::getId)
                .orElse("");

        var portalDescription = PortalDescription.newPortalDescription(DESCRIPTION_ID)
                .label("Portal")
                .idProvider(new GetOrCreateRandomIdProvider())
                .labelProvider(labelProvider)
                .targetObjectIdProvider(targetObjectIdProvider)
                .canCreatePredicate(variableManager -> true)
                .build();

        return List.of(portalDescription);
    }
}
