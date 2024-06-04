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
 * Used to create the relation edge description.
 *
 * @author sbegaudeau
 */
public class RelationEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    private final IColorProvider colorProvider;

    public RelationEdgeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public EdgeDescription create() {
        var palette = new DiagramBuilders().newEdgePalette()
                .toolSections(new DefaultToolsFactory().createDefaultHideRevealEdgeToolSection())
                .build();

        return new DiagramBuilders()
                .newEdgeDescription()
                .name("Relation")
                .domainType("domain::Relation")
                .isDomainBasedEdge(true)
                .centerLabelExpression("aql:self.renderRelation()")
                .semanticCandidatesExpression("aql:self.eResource().getContents().eAllContents()")
                .sourceNodesExpression("aql:self.eContainer()")
                .targetNodesExpression("aql:self.targetType")
                .style(this.relationEdgeStyle())
                .palette(palette)
                .build();
    }

    private EdgeStyle relationEdgeStyle() {
        return new DiagramBuilders()
                .newEdgeStyle()
                .sourceArrowStyle(ArrowStyle.FILL_DIAMOND)
                .color(this.colorProvider.getColor(DomainDiagramDescriptionProvider.BLACK_COLOR))
                .borderSize(0)
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalRelationEdgeDescription = cache.getEdgeDescription("Relation");
        var optionalEntityNodeDescription = cache.getNodeDescription("Entity");

        if (optionalRelationEdgeDescription.isPresent() && optionalEntityNodeDescription.isPresent()) {
            var relationEdgeDescription = optionalRelationEdgeDescription.get();
            var entityNodeDescription = optionalEntityNodeDescription.get();

            relationEdgeDescription.getSourceNodeDescriptions().add(entityNodeDescription);
            relationEdgeDescription.getTargetNodeDescriptions().add(entityNodeDescription);

            diagramDescription.getEdgeDescriptions().add(relationEdgeDescription);
        }
    }
}
