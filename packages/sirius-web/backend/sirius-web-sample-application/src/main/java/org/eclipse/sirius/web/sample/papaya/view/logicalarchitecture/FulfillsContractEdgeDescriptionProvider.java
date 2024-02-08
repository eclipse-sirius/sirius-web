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
package org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture;

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
 * Description of fulfills contract.
 *
 * @author sbegaudeau
 */
public class FulfillsContractEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    private final IColorProvider colorProvider;

    public FulfillsContractEdgeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public EdgeDescription create() {
        var fulfillsContractEdgeStyle = DiagramFactory.eINSTANCE.createEdgeStyle();
        fulfillsContractEdgeStyle.setColor(this.colorProvider.getColor("color_blue_3"));
        fulfillsContractEdgeStyle.setEdgeWidth(1);
        fulfillsContractEdgeStyle.setLineStyle(LineStyle.DOT);
        fulfillsContractEdgeStyle.setSourceArrowStyle(ArrowStyle.NONE);
        fulfillsContractEdgeStyle.setTargetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW);

        var fulfillsContractEdgeDescription = DiagramFactory.eINSTANCE.createEdgeDescription();
        fulfillsContractEdgeDescription.setName("Edge Fulfills contract");
        fulfillsContractEdgeDescription.setCenterLabelExpression("aql:'fulfills'");
        fulfillsContractEdgeDescription.setStyle(fulfillsContractEdgeStyle);
        fulfillsContractEdgeDescription.setSourceNodesExpression("aql:self");
        fulfillsContractEdgeDescription.setTargetNodesExpression("aql:self.contract");
        fulfillsContractEdgeDescription.setIsDomainBasedEdge(false);

        return fulfillsContractEdgeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalFulfillsContractEdgeDescription = cache.getEdgeDescription("Edge Fulfills contract");
        var optionalProvidedServiceNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::ProvidedService");
        var optionalRequiredServiceNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::RequiredService");
        var optionalInterfaceNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::Interface");

        if (optionalFulfillsContractEdgeDescription.isPresent() && optionalProvidedServiceNodeDescription.isPresent() && optionalRequiredServiceNodeDescription.isPresent() && optionalInterfaceNodeDescription.isPresent()) {
            diagramDescription.getEdgeDescriptions().add(optionalFulfillsContractEdgeDescription.get());
            optionalFulfillsContractEdgeDescription.get().getSourceNodeDescriptions().add(optionalProvidedServiceNodeDescription.get());
            optionalFulfillsContractEdgeDescription.get().getSourceNodeDescriptions().add(optionalRequiredServiceNodeDescription.get());
            optionalFulfillsContractEdgeDescription.get().getTargetNodeDescriptions().add(optionalInterfaceNodeDescription.get());
        }
    }

}
