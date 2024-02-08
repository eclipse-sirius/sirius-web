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
 * Description of realized by.
 *
 * @author sbegaudeau
 */
public class RealizedByEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    private final IColorProvider colorProvider;

    public RealizedByEdgeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public EdgeDescription create() {
        var realizedByEdgeStyle = DiagramFactory.eINSTANCE.createEdgeStyle();
        realizedByEdgeStyle.setColor(this.colorProvider.getColor("color_red"));
        realizedByEdgeStyle.setEdgeWidth(1);
        realizedByEdgeStyle.setLineStyle(LineStyle.SOLID);
        realizedByEdgeStyle.setSourceArrowStyle(ArrowStyle.NONE);
        realizedByEdgeStyle.setTargetArrowStyle(ArrowStyle.INPUT_ARROW);

        var realizedByEdgeDescription = DiagramFactory.eINSTANCE.createEdgeDescription();
        realizedByEdgeDescription.setName("Edge Realized by");
        realizedByEdgeDescription.setCenterLabelExpression("aql:self.name + ' is realized by ' + self.realizedBy.name");
        realizedByEdgeDescription.setStyle(realizedByEdgeStyle);
        realizedByEdgeDescription.setSourceNodesExpression("aql:self");
        realizedByEdgeDescription.setTargetNodesExpression("aql:self.realizedBy");
        realizedByEdgeDescription.setIsDomainBasedEdge(false);

        return realizedByEdgeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalRealizedByEdgeDescription = cache.getEdgeDescription("Edge Realized by");
        var optionalOperationalActivityNodeDescription = cache.getNodeDescription("Node papaya_operational_analysis::OperationalActivity");
        var optionalComponentNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::Component");

        if (optionalRealizedByEdgeDescription.isPresent() && optionalOperationalActivityNodeDescription.isPresent() && optionalComponentNodeDescription.isPresent()) {
            diagramDescription.getEdgeDescriptions().add(optionalRealizedByEdgeDescription.get());
            optionalRealizedByEdgeDescription.get().getSourceNodeDescriptions().add(optionalOperationalActivityNodeDescription.get());
            optionalRealizedByEdgeDescription.get().getTargetNodeDescriptions().add(optionalComponentNodeDescription.get());
        }
    }

}
