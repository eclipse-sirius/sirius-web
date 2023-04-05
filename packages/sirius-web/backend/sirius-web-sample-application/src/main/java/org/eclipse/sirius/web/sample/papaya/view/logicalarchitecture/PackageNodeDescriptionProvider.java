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

import java.util.Objects;

import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.papaya.view.IColorProvider;
import org.eclipse.sirius.web.sample.papaya.view.INodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaToolsFactory;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewBuilder;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewCache;

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
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor(this.colorProvider.getColor("color_blue_7"));
        nodeStyle.setBorderColor(this.colorProvider.getColor("border_blue_3"));
        nodeStyle.setLabelColor(this.colorProvider.getColor("label_black"));

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("Package");
        nodeDescription.setSemanticCandidatesExpression("aql:self.eContents()");
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());
        nodeDescription.setLabelExpression("aql:self.name");
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.getReusedChildNodeDescriptions().add(nodeDescription);

        var nodePalette = ViewFactory.eINSTANCE.createNodePalette();
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
    public void link(DiagramDescription diagramDescription, PapayaViewCache cache) {
        var packageNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::Package");
        var interfaceNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::Interface");
        var classNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::Class");
        var dataTypeNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::DataType");
        var enumNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::Enum");

        packageNodeDescription.getChildrenDescriptions().add(interfaceNodeDescription);
        packageNodeDescription.getChildrenDescriptions().add(classNodeDescription);
        packageNodeDescription.getChildrenDescriptions().add(dataTypeNodeDescription);
        packageNodeDescription.getChildrenDescriptions().add(enumNodeDescription);
    }

}
