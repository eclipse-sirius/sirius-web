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

import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.papaya.view.INodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaToolsFactory;
import org.eclipse.sirius.web.sample.papaya.view.PapyaViewBuilder;

/**
 * Description of the operational activity.
 *
 * @author sbegaudeau
 */
public class OperationalActivityNodeDescriptionProvider implements INodeDescriptionProvider {

    @Override
    public NodeDescription create() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor("#ffcc80");
        nodeStyle.setBorderColor("#fb8c00");
        nodeStyle.setLabelColor("#212121");

        var nodeDescription = new PapyaViewBuilder().createNodeDescription("OperationalActivity");
        nodeDescription.setSemanticCandidatesExpression("aql:self.operationalActivities");
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());

        var newOperationalActivityNodeTool = new PapayaToolsFactory().createNamedElement("papaya::OperationalActivity", "operationalActivities", "Operational Activity");
        newOperationalActivityNodeTool.setName("New Operational Activity");
        nodeDescription.getNodeTools().add(newOperationalActivityNodeTool);
        nodeDescription.setLabelEditTool(new PapayaToolsFactory().editName());
        nodeDescription.setDeleteTool(new PapayaToolsFactory().deleteTool());

        return nodeDescription;
    }

}
