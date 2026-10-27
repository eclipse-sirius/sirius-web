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

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.variables.CoreVariables;
import org.eclipse.sirius.components.flow.starter.view.FlowTopographyUnsynchronizedViewDiagramDescriptionProvider;
import org.eclipse.sirius.components.flow.starter.view.FlowTopographyViewDiagramDescriptionProvider;
import org.eclipse.sirius.components.flow.starter.view.FlowTopographyWithAutoLayoutViewDiagramDescriptionProvider;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.IRepresentationRenderVariableCustomizer;
import org.eclipse.sirius.components.representations.Variable;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.web.application.project.services.api.IProjectEditingContextService;
import org.eclipse.sirius.web.projects.stylecustomizations.domain.services.api.IProjectStyleCustomizationSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * The renderer variable customizer registering variables for flow diagrams rendering.
 *
 * @author gcoutable
 */
@Service
public class FlowRendererVariableCustomizer implements IRepresentationRenderVariableCustomizer {

    public static final Variable IS_FLOW_STYLE_CUSTOMIZATION_POWER_STATUS_ENABLED = new Variable("isFlowStyleCustomizationPowerStatusEnabled", Boolean.class, false, "Whether the style customization for flow power status is enabled");

    public static final Variable IS_FLOW_STYLE_CUSTOMIZATION_STATUS_ENABLED = new Variable("isFlowStyleCustomizationStatusEnabled", Boolean.class, false, "Whether the style customization for flow status is enabled");

    private final IProjectStyleCustomizationSearchService projectStyleCustomizationSearchService;

    private final IProjectEditingContextService projectEditingContextService;

    public FlowRendererVariableCustomizer(IProjectStyleCustomizationSearchService projectStyleCustomizationSearchService, IProjectEditingContextService projectEditingContextService) {
        this.projectStyleCustomizationSearchService = Objects.requireNonNull(projectStyleCustomizationSearchService);
        this.projectEditingContextService = Objects.requireNonNull(projectEditingContextService);
    }

    @Override
    public VariableManager customize(IRepresentationDescription representationDescription, VariableManager variableManager) {
        if (this.isFlowDiagram(representationDescription)) {
            var isFlowStyleCustomizationPowerStatusEnabled = variableManager.get(CoreVariables.EDITING_CONTEXT.name(), IEditingContext.class)
                    .map(IEditingContext::getId)
                    .flatMap(this.projectEditingContextService::getProjectId)
                    .map(projectId -> this.projectStyleCustomizationSearchService.existsByProjectIdAndStyleCustomizationDescriptionId(AggregateReference.to(projectId), FlowStyleCustomizationDescriptionProvider.FLOW_STYLE_CUSTOMIZATION_POWER_STATUS))
                    .orElse(Boolean.FALSE);

            var isFlowStyleCustomizationStatusEnabled = variableManager.get(CoreVariables.EDITING_CONTEXT.name(), IEditingContext.class)
                    .map(IEditingContext::getId)
                    .flatMap(this.projectEditingContextService::getProjectId)
                    .map(projectId -> this.projectStyleCustomizationSearchService.existsByProjectIdAndStyleCustomizationDescriptionId(AggregateReference.to(projectId), FlowStyleCustomizationDescriptionProvider.FLOW_STYLE_CUSTOMIZATION_STATUS))
                    .orElse(Boolean.FALSE);

            variableManager.put(IS_FLOW_STYLE_CUSTOMIZATION_POWER_STATUS_ENABLED.name(), isFlowStyleCustomizationPowerStatusEnabled);
            variableManager.put(IS_FLOW_STYLE_CUSTOMIZATION_STATUS_ENABLED.name(), isFlowStyleCustomizationStatusEnabled);
        }

        return variableManager;
    }

    private boolean isFlowDiagram(IRepresentationDescription representationDescription) {
        var flowRepresentationIds = List.of(
                FlowTopographyViewDiagramDescriptionProvider.TOPOGRAPHY_VIEW_DESCRIPTION_ID,
                FlowTopographyUnsynchronizedViewDiagramDescriptionProvider.TOPOGRAPHY_UNSYNCHRONIZED_VIEW_DESCRIPTION_ID,
                FlowTopographyWithAutoLayoutViewDiagramDescriptionProvider.TOPOGRAPHY_WITH_AUTO_LAYOUT_VIEW_DESCRIPTION_ID
        );
        return flowRepresentationIds.contains(representationDescription.getId());
    }
}
