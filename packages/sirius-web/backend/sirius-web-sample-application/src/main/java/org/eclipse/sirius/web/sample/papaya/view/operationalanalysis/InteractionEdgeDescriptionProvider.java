/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.web.sample.papaya.view.operationalanalysis;

import java.util.Objects;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IEdgeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewBuilder;

/**
 * Description of the interaction.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class InteractionEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    private final IColorProvider colorProvider;

    public InteractionEdgeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public EdgeDescription create() {
        var interactionEdgeStyle = DiagramFactory.eINSTANCE.createEdgeStyle();
        interactionEdgeStyle.setColor(this.colorProvider.getColor("color_black"));
        interactionEdgeStyle.setEdgeWidth(1);
        interactionEdgeStyle.setSourceArrowStyle(ArrowStyle.NONE);
        interactionEdgeStyle.setTargetArrowStyle(ArrowStyle.INPUT_FILL_CLOSED_ARROW);

        var builder = new PapayaViewBuilder();

        var interactionEdgeDescription = DiagramFactory.eINSTANCE.createEdgeDescription();
        interactionEdgeDescription.setName("Edge Interaction");
        interactionEdgeDescription.setCenterLabelExpression("aql:'interacts with'");
        interactionEdgeDescription.setSemanticCandidatesExpression("aql:self.eAllContents()");
        interactionEdgeDescription.setDomainType(builder.domainType(builder.entity("Interaction")));
        interactionEdgeDescription.setIsDomainBasedEdge(true);
        interactionEdgeDescription.setSourceNodesExpression("aql:self.eContainer()");
        interactionEdgeDescription.setTargetNodesExpression("aql:self.target");
        interactionEdgeDescription.setStyle(interactionEdgeStyle);

        return interactionEdgeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalInteractionEdgeDescription = cache.getEdgeDescription("Edge Interaction");
        var optionalOperationalActivityNodeDescription = cache.getNodeDescription("Node papaya_operational_analysis::OperationalActivity");

        if (optionalInteractionEdgeDescription.isPresent() && optionalOperationalActivityNodeDescription.isPresent()) {
            diagramDescription.getEdgeDescriptions().add(optionalInteractionEdgeDescription.get());
            optionalInteractionEdgeDescription.get().getSourceNodeDescriptions().add(optionalOperationalActivityNodeDescription.get());
            optionalInteractionEdgeDescription.get().getTargetNodeDescriptions().add(optionalOperationalActivityNodeDescription.get());
        }
    }

}
