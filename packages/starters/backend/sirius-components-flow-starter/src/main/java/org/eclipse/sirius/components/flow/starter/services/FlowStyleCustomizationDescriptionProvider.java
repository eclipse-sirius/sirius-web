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
package org.eclipse.sirius.components.flow.starter.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.projects.domain.Nature;
import org.eclipse.sirius.web.projects.domain.services.api.IProjectSearchService;
import org.eclipse.sirius.web.projects.stylecustomizations.application.services.StyleCustomizationDescription;
import org.eclipse.sirius.web.projects.stylecustomizations.application.services.api.IStyleCustomizationDescriptionProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Provider for descriptions of style customization for flow element.
 *
 * @author gcoutable
 */
@Service
public class FlowStyleCustomizationDescriptionProvider implements IStyleCustomizationDescriptionProvider {

    public static final String FLOW_STYLE_CUSTOMIZATION_POWER_STATUS = "flow-style-customization-power-status";

    public static final String FLOW_STYLE_CUSTOMIZATION_STATUS = "flow-style-customization-status";

    private final boolean nodeCustomizationEnabled;

    private final IProjectSearchService projectSearchService;

    public FlowStyleCustomizationDescriptionProvider(@Value("${sirius.web.style.customization.enabled:false}") boolean nodeCustomizationEnabled, IProjectSearchService projectSearchService) {
        this.nodeCustomizationEnabled = nodeCustomizationEnabled;
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
    }

    @Override
    public List<StyleCustomizationDescription> getStyleCustomizationDescriptions(String projectId) {
        if (this.nodeCustomizationEnabled && this.hasFlowNature(projectId)) {
            return List.of(
                    new StyleCustomizationDescription(FLOW_STYLE_CUSTOMIZATION_POWER_STATUS, "Power Status",
                            "Change the style of flow elements depending on their power status"),
                    new StyleCustomizationDescription(FLOW_STYLE_CUSTOMIZATION_STATUS, "Status",
                            "Change the style of flow elements depending on their status"));
        }
        return List.of();
    }

    private boolean hasFlowNature(String projectId) {
        return this.projectSearchService.findById(projectId)
                .filter(project -> project.getNatures().stream()
                        .map(Nature::name)
                        .anyMatch(FlowProjectTemplatesProvider.FLOW_NATURE::equals))
                .isPresent();
    }
}
