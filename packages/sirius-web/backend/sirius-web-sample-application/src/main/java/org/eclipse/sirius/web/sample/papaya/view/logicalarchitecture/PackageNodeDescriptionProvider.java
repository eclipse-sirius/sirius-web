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

import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.papaya.view.INodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaToolsFactory;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewCache;
import org.eclipse.sirius.web.sample.papaya.view.PapyaViewBuilder;

/**
 * Description of the package.
 *
 * @author sbegaudeau
 */
public class PackageNodeDescriptionProvider implements INodeDescriptionProvider {

    @Override
    public NodeDescription create() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor("#d1c4e9");
        nodeStyle.setBorderColor("#5e35b1");
        nodeStyle.setLabelColor("#1212121");

        var nodeDescription = new PapyaViewBuilder().createNodeDescription("Package");
        nodeDescription.setSemanticCandidatesExpression("aql:self.eContents()");
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());
        nodeDescription.setLabelExpression("aql:self.name");
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.getReusedChildNodeDescriptions().add(nodeDescription);

        var newPackageNodeTool = new PapayaToolsFactory().createNamedElement("papaya::Package", "packages", "Package");
        newPackageNodeTool.setName("New Package");
        nodeDescription.getNodeTools().add(newPackageNodeTool);
        nodeDescription.setLabelEditTool(new PapayaToolsFactory().editName());
        nodeDescription.setDeleteTool(new PapayaToolsFactory().deleteTool());

        return nodeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, PapayaViewCache cache) {
        var packageNodeDescription = cache.getNodeDescription("Node papaya::Package");
        var interfaceNodeDescription = cache.getNodeDescription("Node papaya::Interface");
        var classNodeDescription = cache.getNodeDescription("Node papaya::Class");
        var dataTypeNodeDescription = cache.getNodeDescription("Node papaya::DataType");
        var enumNodeDescription = cache.getNodeDescription("Node papaya::Enum");

        packageNodeDescription.getChildrenDescriptions().add(interfaceNodeDescription);
        packageNodeDescription.getChildrenDescriptions().add(classNodeDescription);
        packageNodeDescription.getChildrenDescriptions().add(dataTypeNodeDescription);
        packageNodeDescription.getChildrenDescriptions().add(enumNodeDescription);
    }

}
