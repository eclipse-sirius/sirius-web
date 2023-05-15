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
 * Description of the operational perimeter.
 *
 * @author sbegaudeau
 */
public class OperationalPerimeterNodeDescriptionProvider implements INodeDescriptionProvider {

    private final IColorProvider colorProvider;

    public OperationalPerimeterNodeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor(this.colorProvider.getColor("color_gray_2"));
        nodeStyle.setBorderColor(this.colorProvider.getColor("border_gray_2"));
        nodeStyle.setLabelColor(this.colorProvider.getColor("label_black"));

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("OperationalPerimeter");
        nodeDescription.setSemanticCandidatesExpression("aql:self.operationalPerimeters");
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());

        var nodePalette = ViewFactory.eINSTANCE.createNodePalette();
        nodeDescription.setPalette(nodePalette);
        var newOperationalActivityNodeTool = new PapayaToolsFactory().createNamedElement("papaya_operational_analysis::OperationalActivity", "operationalActivities", "Operational Activity");
        newOperationalActivityNodeTool.setName("New Operational Activity");
        nodePalette.getNodeTools().add(newOperationalActivityNodeTool);

        nodePalette.setLabelEditTool(new PapayaToolsFactory().editName());
        nodePalette.setDeleteTool(new PapayaToolsFactory().deleteTool());

        return nodeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalOperationalPerimeterNodeDescription = cache.getNodeDescription("Node papaya_operational_analysis::OperationalPerimeter");
        var optionalOperationalActivityNodeDescription = cache.getNodeDescription("Node papaya_operational_analysis::OperationalActivity");
        if (optionalOperationalPerimeterNodeDescription.isPresent() && optionalOperationalActivityNodeDescription.isPresent()) {
            optionalOperationalPerimeterNodeDescription.get().getChildrenDescriptions().add(optionalOperationalActivityNodeDescription.get());
        }
    }

}
