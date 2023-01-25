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
package org.eclipse.sirius.web.sample.papaya.view.operationalanalysis;

import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.papaya.view.INodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaToolsFactory;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewCache;
import org.eclipse.sirius.web.sample.papaya.view.PapyaViewBuilder;

/**
 * Description of the operational perimeter.
 *
 * @author sbegaudeau
 */
public class OperationalPerimeterNodeDescriptionProvider implements INodeDescriptionProvider {

    @Override
    public NodeDescription create() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor("#bdbdbd");
        nodeStyle.setBorderColor("#424242");
        nodeStyle.setLabelColor("1212121");

        var nodeDescription = new PapyaViewBuilder().createNodeDescription("OperationalPerimeter");
        nodeDescription.setSemanticCandidatesExpression("aql:self.operationalPerimeters");
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());

        var newOperationalPerimeterNodeTool = new PapayaToolsFactory().createNamedElement("papaya::OperationalPerimeter", "operationalPerimeters", "Operational Perimeter");
        newOperationalPerimeterNodeTool.setName("New Operational Perimeter");
        nodeDescription.getNodeTools().add(newOperationalPerimeterNodeTool);
        nodeDescription.setLabelEditTool(new PapayaToolsFactory().editName());
        nodeDescription.setDeleteTool(new PapayaToolsFactory().deleteTool());

        return nodeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, PapayaViewCache cache) {
        var operationalPerimeterNodeDescription = cache.getNodeDescription("Node papaya::OperationalPerimeter");
        var operationalActivityNodeDescription = cache.getNodeDescription("Node papaya::OperationalActivity");
        operationalPerimeterNodeDescription.getChildrenDescriptions().add(operationalActivityNodeDescription);
    }

}
