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

import java.util.Objects;

import org.eclipse.sirius.web.projects.stylecustomizations.application.dto.StyleCustomizationDTO;
import org.eclipse.sirius.web.projects.stylecustomizations.application.services.api.IStyleCustomizationMapper;
import org.eclipse.sirius.web.projects.stylecustomizations.domain.services.api.IProjectStyleCustomizationSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to convert a description of style customization to a DTO.
 *
 * @author gcoutable
 */
@Service
public class StyleCustomizationMapper implements IStyleCustomizationMapper {

    private final IProjectStyleCustomizationSearchService projectStyleCustomizationSearchService;

    public StyleCustomizationMapper(IProjectStyleCustomizationSearchService projectStyleCustomizationSearchService) {
        this.projectStyleCustomizationSearchService = Objects.requireNonNull(projectStyleCustomizationSearchService);
    }

    @Override
    public StyleCustomizationDTO toDTO(String projectId, StyleCustomizationDescription styleCustomizationDescription) {
        boolean isEnabled = this.projectStyleCustomizationSearchService.existsByProjectIdAndStyleCustomizationDescriptionId(AggregateReference.to(projectId), styleCustomizationDescription.id());
        return new StyleCustomizationDTO(styleCustomizationDescription.id(), styleCustomizationDescription.label(), styleCustomizationDescription.description(), isEnabled);
    }
}
