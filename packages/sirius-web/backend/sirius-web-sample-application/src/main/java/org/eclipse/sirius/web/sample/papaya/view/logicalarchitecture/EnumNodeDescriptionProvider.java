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

import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.INodeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.web.sample.papaya.view.PapayaToolsFactory;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewBuilder;

/**
 * Description of the enum.
 *
 * @author sbegaudeau
 */
public class EnumNodeDescriptionProvider implements INodeDescriptionProvider {

    private final IColorProvider colorProvider;

    public EnumNodeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        var nodeStyle = DiagramFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor(this.colorProvider.getColor("color_green_3"));
        nodeStyle.setBorderColor(this.colorProvider.getColor("border_green_2"));

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("Enum");
        nodeDescription.setChildrenLayoutStrategy(DiagramFactory.eINSTANCE.createListLayoutStrategyDescription());
        nodeDescription.setSemanticCandidatesExpression("aql:self.types");
        nodeDescription.setInsideLabel(new PapayaViewBuilder().createInsideLabelDescriptionWithHeader("aql:self.name", this.colorProvider.getColor("label_white"), true));
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.getChildrenDescriptions().add(this.enumLiteralNodeDescription());

        var nodePalette = DiagramFactory.eINSTANCE.createNodePalette();
        nodeDescription.setPalette(nodePalette);
        var newEnumLiteralNodeTool = new PapayaToolsFactory().createNamedElement("papaya_logical_architecture::EnumLiteral", "enumLiterals", "EnumLiteral");
        newEnumLiteralNodeTool.setName("New Enum Literal");
        nodePalette.getNodeTools().add(newEnumLiteralNodeTool);

        nodePalette.setLabelEditTool(new PapayaToolsFactory().editName());
        nodePalette.setDeleteTool(new PapayaToolsFactory().deleteTool());

        return nodeDescription;
    }

    private NodeDescription enumLiteralNodeDescription() {
        var nodeStyle = DiagramFactory.eINSTANCE.createIconLabelNodeStyleDescription();
        nodeStyle.setColor(this.colorProvider.getColor("color_green_3"));
        nodeStyle.setBorderColor(this.colorProvider.getColor("border_green_2"));

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("EnumLiteral");
        nodeDescription.setSemanticCandidatesExpression("aql:self.enumLiterals");
        nodeDescription.setInsideLabel(new PapayaViewBuilder().createInsideLabelDescription("aql:self.name", this.colorProvider.getColor("label_white")));
        nodeDescription.setStyle(nodeStyle);

        var nodePalette = DiagramFactory.eINSTANCE.createNodePalette();
        nodeDescription.setPalette(nodePalette);

        nodePalette.setLabelEditTool(new PapayaToolsFactory().editName());
        nodePalette.setDeleteTool(new PapayaToolsFactory().deleteTool());

        return nodeDescription;
    }

}
