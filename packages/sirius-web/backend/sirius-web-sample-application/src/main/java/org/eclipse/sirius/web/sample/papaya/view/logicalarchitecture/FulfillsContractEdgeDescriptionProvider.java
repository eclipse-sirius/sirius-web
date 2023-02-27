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

import org.eclipse.sirius.components.view.ArrowStyle;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.EdgeDescription;
import org.eclipse.sirius.components.view.LineStyle;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.papaya.view.IEdgeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewCache;

/**
 * Description of fulfills contract.
 *
 * @author sbegaudeau
 */
public class FulfillsContractEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    @Override
    public EdgeDescription create() {
        var fulfillsContractEdgeStyle = ViewFactory.eINSTANCE.createEdgeStyle();
        fulfillsContractEdgeStyle.setColor("#3f51b5");
        fulfillsContractEdgeStyle.setEdgeWidth(1);
        fulfillsContractEdgeStyle.setLineStyle(LineStyle.DOT);
        fulfillsContractEdgeStyle.setSourceArrowStyle(ArrowStyle.NONE);
        fulfillsContractEdgeStyle.setTargetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW);

        var fulfillsContractEdgeDescription = ViewFactory.eINSTANCE.createEdgeDescription();
        fulfillsContractEdgeDescription.setName("Edge Fulfills contract");
        fulfillsContractEdgeDescription.setLabelExpression("aql:'fulfills'");
        fulfillsContractEdgeDescription.setStyle(fulfillsContractEdgeStyle);
        fulfillsContractEdgeDescription.setSourceNodesExpression("aql:self");
        fulfillsContractEdgeDescription.setTargetNodesExpression("aql:self.contract");
        fulfillsContractEdgeDescription.setIsDomainBasedEdge(false);

        var fulfillsContractEdgeTool = ViewFactory.eINSTANCE.createEdgeTool();
        fulfillsContractEdgeTool.setName("Fulfills contract");
        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:semanticEdgeSource");
        var setTargetValue = ViewFactory.eINSTANCE.createSetValue();
        setTargetValue.setFeatureName("contract");
        setTargetValue.setValueExpression("aql:semanticEdgeTarget");

        changeContext.getChildren().add(setTargetValue);
        fulfillsContractEdgeTool.getBody().add(changeContext);

        fulfillsContractEdgeDescription.getEdgeTools().add(fulfillsContractEdgeTool);

        return fulfillsContractEdgeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, PapayaViewCache cache) {
        var fulfillsContractEdgeDescription = cache.getEdgeDescription("Edge Fulfills contract");
        var providedServiceNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::ProvidedService");
        var requiredServiceNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::RequiredService");
        var interfaceNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::Interface");

        diagramDescription.getEdgeDescriptions().add(fulfillsContractEdgeDescription);
        fulfillsContractEdgeDescription.getSourceNodeDescriptions().add(providedServiceNodeDescription);
        fulfillsContractEdgeDescription.getSourceNodeDescriptions().add(requiredServiceNodeDescription);
        fulfillsContractEdgeDescription.getTargetNodeDescriptions().add(interfaceNodeDescription);
    }

}
