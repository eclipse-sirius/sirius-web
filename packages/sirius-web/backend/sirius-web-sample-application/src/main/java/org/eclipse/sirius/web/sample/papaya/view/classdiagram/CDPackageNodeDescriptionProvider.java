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
package org.eclipse.sirius.web.sample.papaya.view.classdiagram;

import java.util.Objects;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.INodeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.web.sample.papaya.view.PapayaToolsFactory;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewBuilder;

/**
 * Used to create the package node description.
 *
 * @author sbegaudeau
 */
public class CDPackageNodeDescriptionProvider implements INodeDescriptionProvider {

    public static final String NAME = "CD Node Package";

    private final IColorProvider colorProvider;

    public CDPackageNodeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        var nodeStyle = DiagramFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor(this.colorProvider.getColor("color_blue_7"));
        nodeStyle.setBorderColor(this.colorProvider.getColor("border_blue_3"));

        var builder = new PapayaViewBuilder();
        var domainType = builder.domainType(builder.entity("Package"));

        var nodeDescription = DiagramFactory.eINSTANCE.createNodeDescription();
        nodeDescription.setName(NAME);
        nodeDescription.setDomainType(domainType);
        nodeDescription.setSemanticCandidatesExpression("aql:self.eContents()");
        nodeDescription.setChildrenLayoutStrategy(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());
        nodeDescription.setInsideLabel(new PapayaViewBuilder().createInsideLabelDescriptionWithHeader("aql:self.name", this.colorProvider.getColor("label_black"), false));
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.getReusedChildNodeDescriptions().add(nodeDescription);
        nodeDescription.setSynchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED);
        nodeDescription.setCollapsible(true);

        return nodeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalPackageNodeDescription = cache.getNodeDescription(NAME);
        var optionalClassNodeDescription = cache.getNodeDescription(CDClassNodeDescriptionProvider.NAME);
        var optionalInterfaceNodeDescription = cache.getNodeDescription(CDInterfaceNodeDescriptionProvider.NAME);

        if (optionalPackageNodeDescription.isPresent() && optionalClassNodeDescription.isPresent() && optionalInterfaceNodeDescription.isPresent()) {
            diagramDescription.getNodeDescriptions().add(optionalPackageNodeDescription.get());

            var nodePalette = DiagramFactory.eINSTANCE.createNodePalette();
            optionalPackageNodeDescription.get().setPalette(nodePalette);

            nodePalette.getNodeTools().add(new CDCreateClassNodeToolProvider().create(cache));
            nodePalette.getNodeTools().add(new CDCreateInterfaceNodeToolProvider().create(cache));
            nodePalette.getNodeTools().add(new CDCreatePackageNodeToolProvider().create(cache));

            nodePalette.setLabelEditTool(new PapayaToolsFactory().editName());
            nodePalette.setDeleteTool(new PapayaToolsFactory().deleteTool());
        }
    }

}
