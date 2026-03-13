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
package org.eclipse.sirius.components.flow.starter.view.descriptions;

import java.util.Objects;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IEdgeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.LineStyle;

/**
 * Used to create the power link edge description.
 *
 * @author sbegaudeau
 */
public class PowerLinkEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    public static final String NAME = "Power Link";

    private final IColorProvider colorProvider;

    public PowerLinkEdgeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public EdgeDescription create() {
        return new DiagramBuilders().newEdgeDescription()
                .name(NAME)
                .domainType("flow::PowerLink")
                .semanticCandidatesExpression("aql:self.elements.eAllContents(flow::PowerLink)")
                .targetExpression("feature:target")
                .sourceExpression("feature:source")
                .isDomainBasedEdge(true)
                .style(
                        new DiagramBuilders().newEdgeStyle()
                        .lineStyle(LineStyle.SOLID)
                        .color(this.colorProvider.getColor("Flow_Gray"))
                        .targetArrowStyle(ArrowStyle.INPUT_ARROW)
                        .borderSize(0)
                        .build())

                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalPowerLinkEdgeDescription = cache.getEdgeDescription(NAME);
        var optionalPowerInputNodeDescription = cache.getNodeDescription(PowerInputDescriptionProvider.NAME);
        var optionalPowerOutputNodeDescription = cache.getNodeDescription(PowerOutputDescriptionProvider.NAME);

        if (optionalPowerLinkEdgeDescription.isPresent() && optionalPowerInputNodeDescription.isPresent() && optionalPowerOutputNodeDescription.isPresent()) {
            diagramDescription.getEdgeDescriptions().add(optionalPowerLinkEdgeDescription.get());
            optionalPowerLinkEdgeDescription.get().getSourceDescriptions().add(optionalPowerOutputNodeDescription.get());
            optionalPowerLinkEdgeDescription.get().getTargetDescriptions().add(optionalPowerInputNodeDescription.get());
        }
    }
}
