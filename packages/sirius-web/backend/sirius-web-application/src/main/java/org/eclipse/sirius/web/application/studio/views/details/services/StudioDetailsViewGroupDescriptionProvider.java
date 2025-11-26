/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import java.util.Collections;
import java.util.Objects;

import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.web.application.studio.views.details.services.api.IStudioDetailsViewGroupDescriptionProvider;
import org.eclipse.sirius.web.application.studio.views.details.services.api.IStudioDetailsViewWidgetDescriptionProvider;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

/**
 * Provides group description for Studio details view.
 *
 * @author tgiraudet
 */
@Service
public class StudioDetailsViewGroupDescriptionProvider implements IStudioDetailsViewGroupDescriptionProvider {

    private final IMessageService messageService;

    private final IStudioDetailsViewWidgetDescriptionProvider studioDetailsViewWidgetDescriptionProvider;

    public StudioDetailsViewGroupDescriptionProvider(IMessageService messageService, IStudioDetailsViewWidgetDescriptionProvider studioDetailsViewWidgetDescriptionProvider) {
        this.messageService = Objects.requireNonNull(messageService);
        this.studioDetailsViewWidgetDescriptionProvider = Objects.requireNonNull(studioDetailsViewWidgetDescriptionProvider);
    }

    @Override
    public GroupDescription createCorePropertiesGroup() {
        return GroupDescription.newGroupDescription("groupId")
                .idProvider(variableManager -> "Core Properties")
                .labelProvider(variableManager -> this.messageService.coreProperties())
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .controlDescriptions(this.studioDetailsViewWidgetDescriptionProvider.getWidgets())
                .build();
    }
}

