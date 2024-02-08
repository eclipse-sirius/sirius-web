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
package org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.exchange;

import java.util.Objects;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IEdgeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.LineStyle;


/**
 * Description of the edge.
 *
 * @author mcharfadi
 */
public class CEDComponentExchangeEdgeProvider implements IEdgeDescriptionProvider {

    private final IColorProvider colorProvider;

    public CEDComponentExchangeEdgeProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public EdgeDescription create() {
        var componentExchangeEdgeStyle = DiagramFactory.eINSTANCE.createEdgeStyle();
        componentExchangeEdgeStyle.setColor(this.colorProvider.getColor("color_red"));
        componentExchangeEdgeStyle.setEdgeWidth(1);
        componentExchangeEdgeStyle.setLineStyle(LineStyle.SOLID);
        componentExchangeEdgeStyle.setSourceArrowStyle(ArrowStyle.NONE);
        componentExchangeEdgeStyle.setTargetArrowStyle(ArrowStyle.NONE);

        var componentExchangeEdgeDescription = DiagramFactory.eINSTANCE.createEdgeDescription();
        componentExchangeEdgeDescription.setName("Edge Component Exchange");
        componentExchangeEdgeDescription.setCenterLabelExpression("aql:self.name");
        componentExchangeEdgeDescription.setStyle(componentExchangeEdgeStyle);
        componentExchangeEdgeDescription.setDomainType("papaya_logical_architecture::ComponentExchange");
        componentExchangeEdgeDescription.setSemanticCandidatesExpression("aql:self.componentExchanges");
        componentExchangeEdgeDescription.setSourceNodesExpression("aql:self.ports->excluding(self.ports->last())");
        componentExchangeEdgeDescription.setTargetNodesExpression("aql:self.ports->at(self.ports->indexOf(semanticEdgeSource) +1)");
        componentExchangeEdgeDescription.setIsDomainBasedEdge(true);

        return componentExchangeEdgeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalComponentExchangeEdgeDescription = cache.getEdgeDescription("Edge Component Exchange");
        var optionalComponentPortNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::ComponentPort");

        if (optionalComponentExchangeEdgeDescription.isPresent() && optionalComponentPortNodeDescription.isPresent()) {
            diagramDescription.getEdgeDescriptions().add(optionalComponentExchangeEdgeDescription.get());
            optionalComponentExchangeEdgeDescription.get().getSourceNodeDescriptions().add(optionalComponentPortNodeDescription.get());
            optionalComponentExchangeEdgeDescription.get().getTargetNodeDescriptions().add(optionalComponentPortNodeDescription.get());
        }
    }

}
