/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.exchange;

import java.util.Objects;

import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.INodeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.web.sample.papaya.view.PapayaToolsFactory;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewBuilder;


/**
 * Description of the componentPort.
 *
 * @author mcharfadi
 */
public class CEDComponentPortNodeProvider implements INodeDescriptionProvider {

    private final IColorProvider colorProvider;

    public CEDComponentPortNodeProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        var nodeStyle = DiagramFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor(this.colorProvider.getColor("color_red"));
        nodeStyle.setBorderColor(this.colorProvider.getColor("color_red"));

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("ComponentPort");
        nodeDescription.setSemanticCandidatesExpression("aql:self.ports");
        nodeDescription.setInsideLabel(new PapayaViewBuilder().createInsideLabelDescription("aql:self.name", this.colorProvider.getColor("label_black")));
        nodeDescription.setChildrenLayoutStrategy(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());
        nodeDescription.setUserResizable(false);
        nodeDescription.setDefaultHeightExpression("aql:'20'");
        nodeDescription.setDefaultWidthExpression("aql:'20'");
        nodeDescription.setStyle(nodeStyle);

        var nodePalette = DiagramFactory.eINSTANCE.createNodePalette();
        nodePalette.setLabelEditTool(new PapayaToolsFactory().editName());
        nodePalette.setDeleteTool(new PapayaToolsFactory().deleteTool());
        nodeDescription.setPalette(nodePalette);

        return nodeDescription;
    }

}
