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
package org.eclipse.sirius.web.sample.papaya.view.operationalanalysis;

import java.util.Objects;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.INodeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.web.sample.papaya.view.PapayaToolsFactory;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewBuilder;

/**
 * Description of the operational entity.
 *
 * @author sbegaudeau
 */
public class OperationalEntityNodeDescriptionProvider implements INodeDescriptionProvider {

    private final IColorProvider colorProvider;

    public OperationalEntityNodeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        var nodeStyle = DiagramFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor(this.colorProvider.getColor("color_gray"));
        nodeStyle.setBorderColor(this.colorProvider.getColor("border_gray"));

        var builder = new PapayaViewBuilder();

        var nodeDescription = builder.createNodeDescription("OperationalEntity");
        nodeDescription.setSemanticCandidatesExpression("aql:self.operationalEntities");
        nodeDescription.setInsideLabel(new PapayaViewBuilder().createInsideLabelDescriptionWithHeader("aql:self.name", this.colorProvider.getColor("label_black"), false));
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.setChildrenLayoutStrategy(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());

        var nodePalette = DiagramFactory.eINSTANCE.createNodePalette();
        nodeDescription.setPalette(nodePalette);
        nodePalette.setLabelEditTool(new PapayaToolsFactory().editName());
        nodePalette.setDeleteTool(new PapayaToolsFactory().deleteTool());

        var newOperationalPerimeterNodeTool = new PapayaToolsFactory().createNamedElement("papaya_operational_analysis::OperationalPerimeter", "operationalPerimeters", "Operational Perimeter");
        newOperationalPerimeterNodeTool.setName("New Operational Perimeter");
        nodePalette.getNodeTools().add(newOperationalPerimeterNodeTool);

        var newOperationalActorNodeTool = new PapayaToolsFactory().createNamedElement("papaya_operational_analysis::OperationalActor", "operationalActors", "Operational Actor");
        newOperationalActorNodeTool.setName("New Operational Actor");
        nodePalette.getNodeTools().add(newOperationalActorNodeTool);

        return nodeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalOperationalEntityNodeDescription = cache.getNodeDescription("Node papaya_operational_analysis::OperationalEntity");
        var optionalOperationalPerimeterNodeDescription = cache.getNodeDescription("Node papaya_operational_analysis::OperationalPerimeter");
        var optionalOperationalActorNodeDescription = cache.getNodeDescription("Node papaya_operational_analysis::OperationalActor");

        if (optionalOperationalEntityNodeDescription.isPresent() && optionalOperationalPerimeterNodeDescription.isPresent() && optionalOperationalActorNodeDescription.isPresent()) {
            diagramDescription.getNodeDescriptions().add(optionalOperationalEntityNodeDescription.get());
            optionalOperationalEntityNodeDescription.get().getChildrenDescriptions().add(optionalOperationalPerimeterNodeDescription.get());
            optionalOperationalEntityNodeDescription.get().getReusedChildNodeDescriptions().add(optionalOperationalActorNodeDescription.get());
        }

    }

}
