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

import java.util.Objects;

import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.EdgeTool;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.INodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaToolsFactory;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewBuilder;


/**
 * Description of the operational activity.
 *
 * @author sbegaudeau
 */
public class OperationalActivityNodeDescriptionProvider implements INodeDescriptionProvider {

    private final IColorProvider colorProvider;

    public OperationalActivityNodeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor(this.colorProvider.getColor("color_orange"));
        nodeStyle.setBorderColor(this.colorProvider.getColor("border_orange"));
        nodeStyle.setLabelColor(this.colorProvider.getColor("label_black"));

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("OperationalActivity");
        nodeDescription.setSemanticCandidatesExpression("aql:self.operationalActivities");
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());

        var nodePalette = ViewFactory.eINSTANCE.createNodePalette();
        nodeDescription.setPalette(nodePalette);
        nodePalette.setLabelEditTool(new PapayaToolsFactory().editName());
        nodePalette.setDeleteTool(new PapayaToolsFactory().deleteTool());

        nodePalette.getEdgeTools().add(this.createInteractionEdgeTool(nodeDescription));
        nodePalette.getEdgeTools().add(this.createRealizedByEdgeTool());

        return nodeDescription;
    }

    private EdgeTool createInteractionEdgeTool(NodeDescription nodeDescription) {
        var interactionEdgeTool = ViewFactory.eINSTANCE.createEdgeTool();
        interactionEdgeTool.setName("Interacts with");
        interactionEdgeTool.getTargetElementDescriptions().add(nodeDescription);
        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:semanticEdgeSource");
        var builder = new PapayaViewBuilder();
        var createInstance = ViewFactory.eINSTANCE.createCreateInstance();
        createInstance.setTypeName(builder.domainType(builder.entity("Interaction")));
        createInstance.setReferenceName("interactions");
        createInstance.setVariableName("self");

        var setTargetValue = ViewFactory.eINSTANCE.createSetValue();
        setTargetValue.setFeatureName("target");
        setTargetValue.setValueExpression("aql:semanticEdgeTarget");

        createInstance.getChildren().add(setTargetValue);
        changeContext.getChildren().add(createInstance);
        interactionEdgeTool.getBody().add(changeContext);
        return interactionEdgeTool;
    }

    private EdgeTool createRealizedByEdgeTool() {
        var realizedByEdgeTool = ViewFactory.eINSTANCE.createEdgeTool();
        realizedByEdgeTool.setName("Realized by");
        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:semanticEdgeSource");
        var setTargetValue = ViewFactory.eINSTANCE.createSetValue();
        setTargetValue.setFeatureName("realizedBy");
        setTargetValue.setValueExpression("aql:semanticEdgeTarget");

        changeContext.getChildren().add(setTargetValue);
        realizedByEdgeTool.getBody().add(changeContext);
        return realizedByEdgeTool;
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalOperationalActivityNodeDescription = cache.getNodeDescription("Node papaya_operational_analysis::OperationalActivity");
        var optionalComponentNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::Component");

        if (optionalOperationalActivityNodeDescription.isPresent() && optionalComponentNodeDescription.isPresent()) {
            var tools = optionalOperationalActivityNodeDescription.get().getPalette().getEdgeTools();
            tools.stream().filter(tool -> tool.getName().equals("Realized by")).findFirst().ifPresent(edgeRealizedByTool -> {
                edgeRealizedByTool.getTargetElementDescriptions().add(optionalComponentNodeDescription.get());
            });
        }

    }
}
