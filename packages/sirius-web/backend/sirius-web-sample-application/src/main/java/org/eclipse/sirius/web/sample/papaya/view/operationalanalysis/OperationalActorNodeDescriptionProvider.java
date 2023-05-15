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
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.INodeDescriptionProvider;
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
        var operationalActorNodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        operationalActorNodeStyle.setColor(this.colorProvider.getColor("color_gray"));
        operationalActorNodeStyle.setBorderColor(this.colorProvider.getColor("border_gray"));
        operationalActorNodeStyle.setLabelColor(this.colorProvider.getColor("label_black"));

        var operationalActorEmptyNodeStyle = ViewFactory.eINSTANCE.createImageNodeStyleDescription();
        operationalActorEmptyNodeStyle.setShape("4d9a22c0-dc36-31c9-bb6a-c18c66b51d93");
        operationalActorEmptyNodeStyle.setColor(this.colorProvider.getColor("color_white"));
        operationalActorEmptyNodeStyle.setBorderColor(this.colorProvider.getColor("border_empty"));
        operationalActorEmptyNodeStyle.setBorderSize(0);
        operationalActorEmptyNodeStyle.setLabelColor(this.colorProvider.getColor("label_black"));

        var conditionalNodeStyle = ViewFactory.eINSTANCE.createConditionalNodeStyle();
        conditionalNodeStyle.setCondition("aql:collapsingState.toString() = 'COLLAPSED'");
        conditionalNodeStyle.setStyle(operationalActorEmptyNodeStyle);

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("OperationalActor");
        nodeDescription.setSemanticCandidatesExpression("aql:self.operationalActors");
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());
        nodeDescription.setStyle(operationalActorNodeStyle);
        nodeDescription.getConditionalStyles().add(conditionalNodeStyle);
        nodeDescription.setCollapsible(true);

        var nodePalette = ViewFactory.eINSTANCE.createNodePalette();
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
