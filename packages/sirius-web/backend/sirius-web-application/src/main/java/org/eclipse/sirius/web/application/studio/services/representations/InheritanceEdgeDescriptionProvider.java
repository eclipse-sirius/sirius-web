/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.studio.services.representations;

import java.util.Objects;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IEdgeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeStyle;
import org.eclipse.sirius.components.view.diagram.provider.DefaultToolsFactory;

/**
 * Used to create the inheritance edge description.
 *
 * @author sbegaudeau
 */
public class InheritanceEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    private final IColorProvider colorProvider;

    public InheritanceEdgeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public EdgeDescription create() {
        var palette = new DiagramBuilders()
                .newEdgePalette()
                .toolSections(new DefaultToolsFactory().createDefaultHideRevealEdgeToolSection())
                .build();

        return new DiagramBuilders()
                .newEdgeDescription()
                .name("Inheritance")
                .targetNodesExpression("aql:self.superTypes")
                .style(this.inheritanceEdgeStyle())
                .palette(palette)
                .build();
    }

    private EdgeStyle inheritanceEdgeStyle() {
        return new DiagramBuilders()
                .newEdgeStyle()
                .targetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW)
                .color(this.colorProvider.getColor(DomainDiagramDescriptionProvider.LIGHT_GREY_COLOR))
                .borderSize(0)
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalInheritanceEdgeDescription = cache.getEdgeDescription("Inheritance");
        var optionalEntityNodeDescription = cache.getNodeDescription("Entity");

        if (optionalInheritanceEdgeDescription.isPresent() && optionalEntityNodeDescription.isPresent()) {
            var inheritanceEdgeDescription = optionalInheritanceEdgeDescription.get();
            var entityNodeDescription = optionalEntityNodeDescription.get();

            inheritanceEdgeDescription.getSourceNodeDescriptions().add(entityNodeDescription);
            inheritanceEdgeDescription.getTargetNodeDescriptions().add(entityNodeDescription);

            diagramDescription.getEdgeDescriptions().add(inheritanceEdgeDescription);
        }
    }
}
