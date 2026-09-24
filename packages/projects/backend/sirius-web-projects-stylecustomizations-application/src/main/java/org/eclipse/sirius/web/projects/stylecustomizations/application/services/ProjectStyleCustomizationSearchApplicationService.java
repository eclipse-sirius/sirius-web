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
package org.eclipse.sirius.web.projects.stylecustomizations.application.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.projects.stylecustomizations.application.dto.StyleCustomizationDTO;
import org.eclipse.sirius.web.projects.stylecustomizations.application.services.api.IProjectStyleCustomizationSearchApplicationService;
import org.eclipse.sirius.web.projects.stylecustomizations.application.services.api.IStyleCustomizationDescriptionProvider;
import org.eclipse.sirius.web.projects.stylecustomizations.application.services.api.IStyleCustomizationMapper;
import org.springframework.stereotype.Service;

/**
 * Application service used to search project style customizations.
 *
 * @author gcoutable
 */
@Service
public class ProjectStyleCustomizationSearchApplicationService implements IProjectStyleCustomizationSearchApplicationService {

    private final List<IStyleCustomizationDescriptionProvider> styleCustomizationDescriptionProviders;

    private final IStyleCustomizationMapper styleCustomizationMapper;

    public ProjectStyleCustomizationSearchApplicationService(List<IStyleCustomizationDescriptionProvider> styleCustomizationDescriptionProviders, IStyleCustomizationMapper styleCustomizationMapper) {
        this.styleCustomizationDescriptionProviders = Objects.requireNonNull(styleCustomizationDescriptionProviders);
        this.styleCustomizationMapper = Objects.requireNonNull(styleCustomizationMapper);
    }

    @Override
    public List<StyleCustomizationDTO> getStyleCustomizations(String projectId) {

        return  this.styleCustomizationDescriptionProviders.stream()
                .map(styleCustomizationDescriptionProvider -> styleCustomizationDescriptionProvider.getStyleCustomizationDescriptions(projectId))
                .flatMap(List::stream)
                .map(this.styleCustomizationMapper::toDTO)
                .toList();
    }
}
