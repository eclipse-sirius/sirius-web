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

import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.INodeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewBuilder;


/**
 * Description of provided service.
 *
 * @author sbegaudeau
 */
public class ProvidedServiceNodeDescriptionProvider implements INodeDescriptionProvider {

    private final IColorProvider colorProvider;

    public ProvidedServiceNodeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        var nodeStyle = DiagramFactory.eINSTANCE.createImageNodeStyleDescription();
        nodeStyle.setShape("f13acf89-e0bc-3b42-a0f6-c39e459e311e");
        nodeStyle.setColor(this.colorProvider.getColor("color_white"));
        nodeStyle.setBorderColor(this.colorProvider.getColor("border_empty"));
        nodeStyle.setBorderSize(0);
        nodeStyle.setPositionDependentRotation(true);

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("ProvidedService");
        nodeDescription.setSemanticCandidatesExpression("aql:self.providedServices");
        nodeDescription.getOutsideLabels().add(new PapayaViewBuilder().createOutsideLabelDescription("aql:if self.contract = null then 'undefined' else self.contract.name endif",
                this.colorProvider.getColor("label_black")));
        nodeDescription.setStyle(nodeStyle);

        var nodePalette = DiagramFactory.eINSTANCE.createNodePalette();
        nodeDescription.setPalette(nodePalette);

        var fulfillsContractEdgeTool = DiagramFactory.eINSTANCE.createEdgeTool();
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
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalProvidedServiceNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::ProvidedService");
        var optionalRequiredServiceNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::RequiredService");

        if (optionalProvidedServiceNodeDescription.isPresent() && optionalRequiredServiceNodeDescription.isPresent()) {
            EdgeTool fulfillsContractEdgeTool = optionalProvidedServiceNodeDescription.get().getPalette().getEdgeTools().get(0);
            fulfillsContractEdgeTool.getTargetElementDescriptions().add(optionalRequiredServiceNodeDescription.get());
        }
    }

}
