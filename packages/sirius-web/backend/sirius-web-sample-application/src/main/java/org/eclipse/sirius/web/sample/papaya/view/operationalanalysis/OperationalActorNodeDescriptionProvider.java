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
 * Description of the operational actor.
 *
 * @author sbegaudeau
 */
public class OperationalActorNodeDescriptionProvider implements INodeDescriptionProvider {

    @Override
    public NodeDescription create() {
        var operationalActorNodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        operationalActorNodeStyle.setColor("#e0e0e0");
        operationalActorNodeStyle.setBorderColor("#616161");
        operationalActorNodeStyle.setLabelColor("#1212121");

        var operationalActorEmptyNodeStyle = ViewFactory.eINSTANCE.createImageNodeStyleDescription();
        operationalActorEmptyNodeStyle.setShape("4d9a22c0-dc36-31c9-bb6a-c18c66b51d93");
        operationalActorEmptyNodeStyle.setColor("white");
        operationalActorEmptyNodeStyle.setBorderColor("");
        operationalActorEmptyNodeStyle.setBorderSize(0);
        operationalActorEmptyNodeStyle.setLabelColor("#1212121");

        var conditionalNodeStyle = ViewFactory.eINSTANCE.createConditionalNodeStyle();
        conditionalNodeStyle.setCondition("aql:self.operationalActivities->size() = 0");
        conditionalNodeStyle.setStyle(operationalActorEmptyNodeStyle);

        var nodeDescription = new PapyaViewBuilder().createNodeDescription("OperationalActor");
        nodeDescription.setSemanticCandidatesExpression("aql:self.operationalActors");
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());
        nodeDescription.setStyle(operationalActorNodeStyle);
        nodeDescription.getConditionalStyles().add(conditionalNodeStyle);

        var newOperationalActorNodeTool = new PapayaToolsFactory().createNamedElement("papaya::OperationalActor", "operationalActors", "Operational Actor");
        newOperationalActorNodeTool.setName("New Operational Actor");
        nodeDescription.getNodeTools().add(newOperationalActorNodeTool);
        nodeDescription.setLabelEditTool(new PapayaToolsFactory().editName());
        nodeDescription.setDeleteTool(new PapayaToolsFactory().deleteTool());

        return nodeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, PapayaViewCache cache) {
        var operationalActorNodeDescription = cache.getNodeDescription("Node papaya::OperationalActor");
        var operationalActivityNodeDescription = cache.getNodeDescription("Node papaya::OperationalActivity");

        diagramDescription.getNodeDescriptions().add(operationalActorNodeDescription);
        operationalActorNodeDescription.getReusedChildNodeDescriptions().add(operationalActivityNodeDescription);
    }

}
