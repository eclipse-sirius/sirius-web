/*******************************************************************************
 * Copyright (c) 2024, 2024 Obeo.
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
package org.eclipse.sirius.web.papaya.representations.componentdiagram.edgedescriptions;

import java.util.Objects;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IEdgeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.web.papaya.representations.componentdiagram.nodedescriptions.ComponentNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.services.PapayaColorPaletteProvider;

/**
 * Used to create the dependency edge description.
 *
 * @author sbegaudeau
 */
public class ComponentDependencyEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    public static final String NAME = "Component#dependency";

    private final IColorProvider colorProvider;

    public ComponentDependencyEdgeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public EdgeDescription create() {
        var duplicateDependencyEdgeStyle = new DiagramBuilders().newConditionalEdgeStyle()
                .condition("aql:semanticEdgeSource.dependencies->excluding(semanticEdgeTarget).allDependencies->includes(semanticEdgeTarget)")
                .color(this.colorProvider.getColor(PapayaColorPaletteProvider.PALETTE_ERROR_MAIN))
                .sourceArrowStyle(ArrowStyle.NONE)
                .targetArrowStyle(ArrowStyle.INPUT_FILL_CLOSED_ARROW)
                .lineStyle(LineStyle.SOLID)
                .edgeWidth(1)
                .build();

        var dependencyEdgeStyle = new DiagramBuilders().newEdgeStyle()
                .color(this.colorProvider.getColor(PapayaColorPaletteProvider.PALETTE_TEXT_PRIMARY))
                .sourceArrowStyle(ArrowStyle.NONE)
                .targetArrowStyle(ArrowStyle.INPUT_FILL_CLOSED_ARROW)
                .lineStyle(LineStyle.SOLID)
                .edgeWidth(1)
                .build();

        return new DiagramBuilders().newEdgeDescription()
                .name(NAME)
                .centerLabelExpression("")
                .sourceNodesExpression("aql:self")
                .targetNodesExpression("aql:self.dependencies")
                .isDomainBasedEdge(false)
                .style(dependencyEdgeStyle)
                .conditionalStyles(duplicateDependencyEdgeStyle)
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalComponentNodeDescription = cache.getNodeDescription(ComponentNodeDescriptionProvider.NAME);
        var optionalComponentDependencyEdgeDescription = cache.getEdgeDescription(NAME);

        if (optionalComponentNodeDescription.isPresent() && optionalComponentDependencyEdgeDescription.isPresent()) {
            var componentNodeDescription = optionalComponentNodeDescription.get();
            var componentDependencyEdgeDescription = optionalComponentDependencyEdgeDescription.get();

            componentDependencyEdgeDescription.getSourceNodeDescriptions().add(componentNodeDescription);
            componentDependencyEdgeDescription.getTargetNodeDescriptions().add(componentNodeDescription);

            diagramDescription.getEdgeDescriptions().add(componentDependencyEdgeDescription);
        }
    }
}
