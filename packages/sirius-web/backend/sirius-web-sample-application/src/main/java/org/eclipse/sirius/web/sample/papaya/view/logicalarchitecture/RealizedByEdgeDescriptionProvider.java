/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import org.eclipse.sirius.components.view.ArrowStyle;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.EdgeDescription;
import org.eclipse.sirius.components.view.LineStyle;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.papaya.view.IColorProvider;
import org.eclipse.sirius.web.sample.papaya.view.IEdgeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewCache;

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
        var realizedByEdgeStyle = ViewFactory.eINSTANCE.createEdgeStyle();
        realizedByEdgeStyle.setColor(this.colorProvider.getColor("color_red"));
        realizedByEdgeStyle.setEdgeWidth(1);
        realizedByEdgeStyle.setLineStyle(LineStyle.SOLID);
        realizedByEdgeStyle.setSourceArrowStyle(ArrowStyle.NONE);
        realizedByEdgeStyle.setTargetArrowStyle(ArrowStyle.INPUT_ARROW);

        var realizedByEdgeDescription = ViewFactory.eINSTANCE.createEdgeDescription();
        realizedByEdgeDescription.setName("Edge Realized by");
        realizedByEdgeDescription.setLabelExpression("aql:self.name + ' is realized by ' + self.realizedBy.name");
        realizedByEdgeDescription.setStyle(realizedByEdgeStyle);
        realizedByEdgeDescription.setSourceNodesExpression("aql:self");
        realizedByEdgeDescription.setTargetNodesExpression("aql:self.realizedBy");
        realizedByEdgeDescription.setIsDomainBasedEdge(false);

        return realizedByEdgeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, PapayaViewCache cache) {
        var realizedByEdgeDescription = cache.getEdgeDescription("Edge Realized by");
        var operationalActivityNodeDescription = cache.getNodeDescription("Node papaya_operational_analysis::OperationalActivity");
        var componentNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::Component");

        diagramDescription.getEdgeDescriptions().add(realizedByEdgeDescription);
        realizedByEdgeDescription.getSourceNodeDescriptions().add(operationalActivityNodeDescription);
        realizedByEdgeDescription.getTargetNodeDescriptions().add(componentNodeDescription);
    }

}
