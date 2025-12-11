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
package org.eclipse.sirius.web.papaya.representations.dashboarddiagram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.HeaderSeparatorDisplayMode;
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
import org.eclipse.sirius.components.diagrams.LabelOverflowStrategy;
import org.eclipse.sirius.components.diagrams.LabelTextAlign;
import org.eclipse.sirius.components.diagrams.LabelVisibility;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.InsideLabelDescription;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.papaya.Project;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.springframework.stereotype.Service;

/**
 * Used to provide the view model used to create dashboard diagrams.
 * The Dashboard diagram is intended to be transient. It can be opened from the specific Dashboard node from the model explorer.
 *
 * @author fbarbin
 */
@Service
public class PapayaDashboardDiagramDescriptionProvider implements IEditingContextRepresentationDescriptionProvider {

    public static final String NAME = "PapayaDashboardDiagram";

    public static final String DASHBOARD_REPRESENTATION_ID = UUID.nameUUIDFromBytes("papaya-dashboard-diagram".getBytes()).toString();;

    public static final String DASHBOARD_DESCRIPTION_ID = IDiagramIdProvider.DIAGRAM_DESCRIPTION_KIND + "papaya-dashboard-diagram-description";

    public static final String PROJECT_NODE_DESCRIPTION_ID = UUID.nameUUIDFromBytes("papaya-dashboard-project-node-description".getBytes()).toString();

    private final IIdentityService identityService;

    public PapayaDashboardDiagramDescriptionProvider(IIdentityService identityService) {
        this.identityService = Objects.requireNonNull(identityService);
    }

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(IEditingContext editingContext) {
        var dashboardDescription = DiagramDescription.newDiagramDescription(DASHBOARD_DESCRIPTION_ID)
            .label("Papaya Dashboard Diagram")
            .iconURLsProvider(variableManager -> List.of())
            .nodeDescriptions(List.of(createProjectNodeDescription()))
            .edgeDescriptions(List.of())
            .dropHandler(variableManager -> new Failure(""))
            .canCreatePredicate(variableManager -> false)
            .labelProvider(variableManager -> "Papaya Dashboard Diagram")
            .targetObjectIdProvider(this::getDiagramTargetObjectId)
            .build();
        return List.of(dashboardDescription);
    }

    private String getDiagramTargetObjectId(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, IEditingContext.class)
                .map(IEditingContext::getId)
                .orElse("");
    }

    private NodeDescription createProjectNodeDescription() {
        var nodeStyle = RectangularNodeStyle.newRectangularNodeStyle()
                .background("#FFFFFF")
                .borderColor("#000000")
                .borderSize(1)
                .borderStyle(LineStyle.Solid)
                .childrenLayoutStrategy(new FreeFormLayoutStrategy())
                .build();

        LabelStyleDescription labelStyleDescription = LabelStyleDescription.newLabelStyleDescription()
                .colorProvider(variableManager -> "#000000")
                .fontSizeProvider(variableManager -> 16)
                .boldProvider(variableManager -> false)
                .italicProvider(variableManager -> false)
                .underlineProvider(variableManager -> false)
                .strikeThroughProvider(variableManager -> false)
                .iconURLProvider(variableManager -> List.of())
                .backgroundProvider(variableManager -> "transparent")
                .borderColorProvider(variableManager -> "black")
                .borderRadiusProvider(variableManager -> 0)
                .borderSizeProvider(variableManager -> 0)
                .borderStyleProvider(variableManager -> LineStyle.Solid)
                .maxWidthProvider(variableManager -> null)
                .visibilityProvider(variableManager -> LabelVisibility.visible)
                .build();

        var insideLabelDescription = InsideLabelDescription.newInsideLabelDescription("projectLabel")
                .textProvider(this::projectNodeTextProvider)
                .styleDescriptionProvider(variableManager -> labelStyleDescription)
                .isHeaderProvider(vm -> false)
                .headerSeparatorDisplayModeProvider(vm -> HeaderSeparatorDisplayMode.NEVER)
                .insideLabelLocation(InsideLabelLocation.TOP_CENTER)
                .overflowStrategy(LabelOverflowStrategy.NONE)
                .textAlign(LabelTextAlign.CENTER)
                .build();

        return NodeDescription.newNodeDescription(PROJECT_NODE_DESCRIPTION_ID)
                .typeProvider(variableManager -> "")
                .semanticElementsProvider(this::projectNodeSemanticElementsProvider)
                .targetObjectIdProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.identityService::getId).orElse(null))
                .targetObjectKindProvider(variableManager -> "")
                .targetObjectLabelProvider(variableManager -> "")
                .styleProvider(variableManager -> nodeStyle)
                .insideLabelDescription(insideLabelDescription)
                .borderNodeDescriptions(new ArrayList<>())
                .childNodeDescriptions(new ArrayList<>())
                .initialChildBorderNodePositions(Map.of())
                .build();
    }

    private String projectNodeTextProvider(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class)
                .filter(Project.class::isInstance)
                .map(Project.class::cast)
                .map(Project::getName)
                .orElse("");
    }

    private List<?> projectNodeSemanticElementsProvider(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain)
                .map(EditingDomain::getResourceSet)
                .map(ResourceSet::getResources)
                .stream()
                .flatMap(Collection::stream)
                .map(Resource::getContents)
                .flatMap(Collection::stream)
                .filter(Project.class::isInstance)
                .map(Project.class::cast)
                .toList();

    }
}
