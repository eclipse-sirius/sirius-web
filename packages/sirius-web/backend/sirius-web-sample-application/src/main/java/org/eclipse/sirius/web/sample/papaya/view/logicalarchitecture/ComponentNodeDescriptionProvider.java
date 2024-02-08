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
 * Description of the component.
 *
 * @author sbegaudeau
 */
public class ComponentNodeDescriptionProvider implements INodeDescriptionProvider {

    private final IColorProvider colorProvider;

    public ComponentNodeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        var nodeStyle = DiagramFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor(this.colorProvider.getColor("color_blue_4"));
        nodeStyle.setBorderColor(this.colorProvider.getColor("border_blue_4"));

        var nodeDescription = new PapayaViewBuilder().createNodeDescription("Component");
        nodeDescription.setSemanticCandidatesExpression("aql:self.components");
        nodeDescription.setInsideLabel(new PapayaViewBuilder().createInsideLabelDescriptionWithHeader("aql:self.name", this.colorProvider.getColor("label_black"), false));
        nodeDescription.setChildrenLayoutStrategy(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());
        nodeDescription.setStyle(nodeStyle);

        var nodePalette = DiagramFactory.eINSTANCE.createNodePalette();
        nodeDescription.setPalette(nodePalette);

        var newPackageNodeTool = new PapayaToolsFactory().createNamedElement("papaya_logical_architecture::Package", "packages", "Package");
        newPackageNodeTool.setName("New Package");
        nodePalette.getNodeTools().add(newPackageNodeTool);

        nodePalette.setLabelEditTool(new PapayaToolsFactory().editName());
        nodePalette.setDeleteTool(new PapayaToolsFactory().deleteTool());

        return nodeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalComponentNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::Component");
        var optionalProvidedServiceNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::ProvidedService");
        var optionalRequiredServiceNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::RequiredService");
        var optionalPackageNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::Package");

        if (optionalComponentNodeDescription.isPresent() && optionalProvidedServiceNodeDescription.isPresent() && optionalRequiredServiceNodeDescription.isPresent() && optionalPackageNodeDescription.isPresent()) {
            diagramDescription.getNodeDescriptions().add(optionalComponentNodeDescription.get());
            optionalComponentNodeDescription.get().getBorderNodesDescriptions().add(optionalProvidedServiceNodeDescription.get());
            optionalComponentNodeDescription.get().getBorderNodesDescriptions().add(optionalRequiredServiceNodeDescription.get());
            optionalComponentNodeDescription.get().getChildrenDescriptions().add(optionalPackageNodeDescription.get());
        }
    }

}
