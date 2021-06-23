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
package org.eclipse.sirius.web.sample.configuration;

import java.util.ArrayList;
import java.util.UUID;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.web.api.configuration.IRepresentationDescriptionRegistry;
import org.eclipse.sirius.web.api.configuration.IRepresentationDescriptionRegistryConfigurer;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.selection.description.SelectionDescription;
import org.springframework.context.annotation.Configuration;

/**
 * Test Description for the Selection Representation.
 *
 * @author arichard
 */
@Configuration
public class SelectionDescriptionConfiguration implements IRepresentationDescriptionRegistryConfigurer {

    private final IObjectService objectService;

    public SelectionDescriptionConfiguration(IObjectService objectService) {
        this.objectService = objectService;
    }

    @Override
    public void addRepresentationDescriptions(IRepresentationDescriptionRegistry registry) {
        // @formatter:off
        @SuppressWarnings({ "unchecked", "rawtypes" })
        SelectionDescription sd = SelectionDescription.newSelectionDescription(UUID.nameUUIDFromBytes("selection".getBytes())) //$NON-NLS-1$
                .canCreatePredicate(variableManager -> false)
                .objectsProvider(variableManager -> variableManager.get(VariableManager.SELF, EObject.class).map(eObject -> new ArrayList(eObject.eContents())).orElse(new ArrayList<>()))
                .messageProvider(variableManager -> "Please select an object in the following list:") //$NON-NLS-1$
                .idProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).map(UUID::fromString).orElse(null))
                .labelProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getLabel).orElse(null))
                .iconURLProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getImagePath).orElse(null))
                .targetObjectIdProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(null))
                .label("My Selection Description Test") //$NON-NLS-1$
                .build();
        // @formatter:on
        registry.add(sd);
    }

}
