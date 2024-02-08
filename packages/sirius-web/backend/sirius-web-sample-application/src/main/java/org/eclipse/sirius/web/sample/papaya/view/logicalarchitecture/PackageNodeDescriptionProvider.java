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

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.INodeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.web.sample.papaya.view.PapayaToolsFactory;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewBuilder;


/**
 * Description of the package.
 *
 * @author sbegaudeau
 */
public class PackageNodeDescriptionProvider implements INodeDescriptionProvider {

    private static final String FEATURE_TYPES = "types";

    private final IColorProvider colorProvider;

    public PackageNodeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        var nodeStyle = DiagramFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor(this.colorProvider.getColor("color_blue_7"));
        nodeStyle.setBorderColor(this.colorProvider.getColor("border_blue_3"));

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("Package");
        nodeDescription.setSemanticCandidatesExpression("aql:self.eContents()");
        nodeDescription.setChildrenLayoutStrategy(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());
        nodeDescription.setInsideLabel(new PapayaViewBuilder().createInsideLabelDescriptionWithHeader("aql:self.name", this.colorProvider.getColor("label_black"), false));
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.getReusedChildNodeDescriptions().add(nodeDescription);

        var nodePalette = DiagramFactory.eINSTANCE.createNodePalette();
        nodeDescription.setPalette(nodePalette);

        var newPackageNodeTool = new PapayaToolsFactory().createNamedElement("papaya_logical_architecture::Package", "packages", "Package");
        newPackageNodeTool.setName("New Package");
        nodePalette.getNodeTools().add(newPackageNodeTool);

        var newClassNodeTool = new PapayaToolsFactory().createNamedElement("papaya_logical_architecture::Class", FEATURE_TYPES, "Class");
        newClassNodeTool.setName("New Class");
        nodePalette.getNodeTools().add(newClassNodeTool);

        var newInterfaceNodeTool = new PapayaToolsFactory().createNamedElement("papaya_logical_architecture::Interface", FEATURE_TYPES, "Interface");
        newInterfaceNodeTool.setName("New Interface");
        nodePalette.getNodeTools().add(newInterfaceNodeTool);

        var newDataTypeNodeTool = new PapayaToolsFactory().createNamedElement("papaya_logical_architecture::DataType", FEATURE_TYPES, "DataType");
        newDataTypeNodeTool.setName("New DataType");
        nodePalette.getNodeTools().add(newDataTypeNodeTool);

        var newEnumNodeTool = new PapayaToolsFactory().createNamedElement("papaya_logical_architecture::Enum", FEATURE_TYPES, "Enum");
        newEnumNodeTool.setName("New Enum");
        nodePalette.getNodeTools().add(newEnumNodeTool);

        nodePalette.setLabelEditTool(new PapayaToolsFactory().editName());
        nodePalette.setDeleteTool(new PapayaToolsFactory().deleteTool());

        return nodeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalPackageNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::Package");
        var optionalInterfaceNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::Interface");
        var optionalClassNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::Class");
        var optionalDataTypeNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::DataType");
        var optionalEnumNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::Enum");

        if (optionalPackageNodeDescription.isPresent() && optionalInterfaceNodeDescription.isPresent() && optionalClassNodeDescription.isPresent()) {
            optionalPackageNodeDescription.get().getChildrenDescriptions().add(optionalInterfaceNodeDescription.get());
            optionalPackageNodeDescription.get().getChildrenDescriptions().add(optionalClassNodeDescription.get());
        }
        if (optionalDataTypeNodeDescription.isPresent() && optionalEnumNodeDescription.isPresent()) {
            optionalPackageNodeDescription.get().getChildrenDescriptions().add(optionalDataTypeNodeDescription.get());
            optionalPackageNodeDescription.get().getChildrenDescriptions().add(optionalEnumNodeDescription.get());
        }
    }

}
