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

import org.eclipse.sirius.web.domain.boundedcontexts.project.Nature;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.projects.stylecustomizations.application.dto.StyleCustomizationDTO;
import org.eclipse.sirius.web.projects.stylecustomizations.application.services.api.IProjectStyleCustomizationSearchApplicationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Application service used to search project style customizations.
 *
 * @author gcoutable
 */
@Service
public class ProjectStyleCustomizationSearchApplicationService implements IProjectStyleCustomizationSearchApplicationService {

    private static final String FLOW_NATURE = "siriusWeb://nature?kind=flow";

    private final boolean nodeCustomizationEnabled;

    private final IProjectSearchService projectSearchService;

    public ProjectStyleCustomizationSearchApplicationService(@Value("${sirius.web.style.customization.enabled:false}") boolean nodeCustomizationEnabled, IProjectSearchService projectSearchService) {
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
        this.nodeCustomizationEnabled = nodeCustomizationEnabled;
    }

    @Override
    public List<StyleCustomizationDTO> getStyleCustomizations(String projectId) {
        if (this.nodeCustomizationEnabled && this.hasFlowNature(projectId)) {
            return List.of(new StyleCustomizationDTO("flow-style-customization-I-m-blue", "I'm blue style customization",
                            "Change the background of flow element graphical node to blue if label contains 'I'm Blue'", true),
                    new StyleCustomizationDTO("flow-style-customization-da-be-di-da-be-dai", "Da be di da be dai style customization",
                            "Change the border style of node if label contains 'Da be di da be dai'", true));
        }
        return List.of();
    }

    private boolean hasFlowNature(String projectId) {
        return this.projectSearchService.findById(projectId)
                .filter(project -> project.getNatures().stream()
                        .map(Nature::name)
                        .anyMatch(FLOW_NATURE::equals))
                .isPresent();
    }
}
