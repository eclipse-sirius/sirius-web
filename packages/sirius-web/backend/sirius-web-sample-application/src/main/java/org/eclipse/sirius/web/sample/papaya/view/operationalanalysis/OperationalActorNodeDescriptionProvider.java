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
 * Description of the operational actor.
 *
 * @author sbegaudeau
 */
public class OperationalActorNodeDescriptionProvider implements INodeDescriptionProvider {

    private final IColorProvider colorProvider;

    public OperationalActorNodeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        var operationalActorNodeStyle = DiagramFactory.eINSTANCE.createRectangularNodeStyleDescription();
        operationalActorNodeStyle.setColor(this.colorProvider.getColor("color_gray"));
        operationalActorNodeStyle.setBorderColor(this.colorProvider.getColor("border_gray"));

        var operationalActorEmptyNodeStyle = DiagramFactory.eINSTANCE.createImageNodeStyleDescription();
        operationalActorEmptyNodeStyle.setShape("4d9a22c0-dc36-31c9-bb6a-c18c66b51d93");
        operationalActorEmptyNodeStyle.setColor(this.colorProvider.getColor("color_white"));
        operationalActorEmptyNodeStyle.setBorderColor(this.colorProvider.getColor("border_empty"));
        operationalActorEmptyNodeStyle.setBorderSize(0);

        var conditionalNodeStyle = DiagramFactory.eINSTANCE.createConditionalNodeStyle();
        conditionalNodeStyle.setCondition("aql:collapsingState.toString() = 'COLLAPSED'");
        conditionalNodeStyle.setStyle(operationalActorEmptyNodeStyle);

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("OperationalActor");
        nodeDescription.setSemanticCandidatesExpression("aql:self.operationalActors");
        nodeDescription.setChildrenLayoutStrategy(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());
        nodeDescription.setInsideLabel(new PapayaViewBuilder().createInsideLabelDescription("aql:self.name", this.colorProvider.getColor("label_black")));
        nodeDescription.setStyle(operationalActorNodeStyle);
        nodeDescription.getConditionalStyles().add(conditionalNodeStyle);
        nodeDescription.setCollapsible(true);

        var nodePalette = DiagramFactory.eINSTANCE.createNodePalette();
        nodeDescription.setPalette(nodePalette);
        nodePalette.setLabelEditTool(new PapayaToolsFactory().editName());
        nodePalette.setDeleteTool(new PapayaToolsFactory().deleteTool());

        var newOperationalActivityNodeTool = new PapayaToolsFactory().createNamedElement("papaya_operational_analysis::OperationalActivity", "operationalActivities", "Operational Activity");
        newOperationalActivityNodeTool.setName("New Operational Activity");
        nodePalette.getNodeTools().add(newOperationalActivityNodeTool);

        return nodeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalOperationalActorNodeDescription = cache.getNodeDescription("Node papaya_operational_analysis::OperationalActor");
        var optionalOperationalActivityNodeDescription = cache.getNodeDescription("Node papaya_operational_analysis::OperationalActivity");
        if (optionalOperationalActorNodeDescription.isPresent() && optionalOperationalActivityNodeDescription.isPresent()) {
            diagramDescription.getNodeDescriptions().add(optionalOperationalActorNodeDescription.get());
            optionalOperationalActorNodeDescription.get().getReusedChildNodeDescriptions().add(optionalOperationalActivityNodeDescription.get());
        }
    }

}
