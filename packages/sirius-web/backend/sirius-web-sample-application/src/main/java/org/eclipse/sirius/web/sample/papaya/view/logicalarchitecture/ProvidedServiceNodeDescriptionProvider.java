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

import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.EdgeTool;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.papaya.view.INodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewBuilder;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewCache;

/**
 * Description of provided service.
 *
 * @author sbegaudeau
 */
public class ProvidedServiceNodeDescriptionProvider implements INodeDescriptionProvider {

    @Override
    public NodeDescription create() {
        var nodeStyle = ViewFactory.eINSTANCE.createImageNodeStyleDescription();
        nodeStyle.setShape("f13acf89-e0bc-3b42-a0f6-c39e459e311e");
        nodeStyle.setColor("white");
        nodeStyle.setBorderColor("");
        nodeStyle.setBorderSize(0);
        nodeStyle.setLabelColor("#1212121");

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("ProvidedService");
        nodeDescription.setSemanticCandidatesExpression("aql:self.providedServices");
        nodeDescription.setLabelExpression("aql:if self.contract = null then 'undefined' else self.contract.name endif");
        nodeDescription.setStyle(nodeStyle);

        var nodePalette = ViewFactory.eINSTANCE.createNodePalette();
        nodeDescription.setPalette(nodePalette);

        var fulfillsContractEdgeTool = ViewFactory.eINSTANCE.createEdgeTool();
        fulfillsContractEdgeTool.setName("Fulfills contract");
        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:semanticEdgeSource");
        var setTargetValue = ViewFactory.eINSTANCE.createSetValue();
        setTargetValue.setFeatureName("contract");
        setTargetValue.setValueExpression("aql:semanticEdgeTarget");

        changeContext.getChildren().add(setTargetValue);
        fulfillsContractEdgeTool.getBody().add(changeContext);

        nodePalette.getEdgeTools().add(fulfillsContractEdgeTool);

        return nodeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, PapayaViewCache cache) {
        var providedServiceNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::ProvidedService");
        var requiredServiceNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::RequiredService");

        EdgeTool fulfillsContractEdgeTool = providedServiceNodeDescription.getPalette().getEdgeTools().get(0);
        fulfillsContractEdgeTool.getTargetElementDescriptions().add(requiredServiceNodeDescription);
    }

}
