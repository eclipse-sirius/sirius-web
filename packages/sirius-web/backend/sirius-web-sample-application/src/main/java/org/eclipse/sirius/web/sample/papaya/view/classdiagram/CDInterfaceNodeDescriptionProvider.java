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

import org.eclipse.sirius.components.view.ViewFactory;
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
 * Used to create the interface node description.
 *
 * @author sbegaudeau
 */
public class CDInterfaceNodeDescriptionProvider implements INodeDescriptionProvider {

    public static final String NAME = "CD Node Interface";

    private final IColorProvider colorProvider;

    public CDInterfaceNodeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        var nodeStyle = DiagramFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor(this.colorProvider.getColor("color_blue_3"));
        nodeStyle.setBorderColor(this.colorProvider.getColor("border_blue_2"));

        var builder = new PapayaViewBuilder();
        var domainType = builder.domainType(builder.entity("Interface"));

        var nodeDescription = DiagramFactory.eINSTANCE.createNodeDescription();
        nodeDescription.setName(NAME);
        nodeDescription.setDomainType(domainType);
        nodeDescription.setSemanticCandidatesExpression("aql:self.types");
        nodeDescription.setChildrenLayoutStrategy(DiagramFactory.eINSTANCE.createListLayoutStrategyDescription());
        nodeDescription.setInsideLabel(new PapayaViewBuilder().createInsideLabelDescriptionWithHeader("aql:self.name", this.colorProvider.getColor("label_white"), true));
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.setSynchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED);

        var nodePalette = DiagramFactory.eINSTANCE.createNodePalette();
        nodeDescription.setPalette(nodePalette);
        nodePalette.setLabelEditTool(new PapayaToolsFactory().editName());
        nodePalette.setDeleteTool(new PapayaToolsFactory().deleteTool());

        var extendsInterfaceEdgeTool = DiagramFactory.eINSTANCE.createEdgeTool();
        extendsInterfaceEdgeTool.setName("Extends Interface");
        extendsInterfaceEdgeTool.getTargetElementDescriptions().add(nodeDescription);
        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:semanticEdgeSource");
        var setTargetValue = ViewFactory.eINSTANCE.createSetValue();
        setTargetValue.setFeatureName("extends");
        setTargetValue.setValueExpression("aql:semanticEdgeTarget");

        changeContext.getChildren().add(setTargetValue);
        extendsInterfaceEdgeTool.getBody().add(changeContext);
        nodePalette.getEdgeTools().add(extendsInterfaceEdgeTool);

        return nodeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalInterfaceNodeDescription = cache.getNodeDescription(NAME);
        var optionalPackageNodeDescription = cache.getNodeDescription(CDPackageNodeDescriptionProvider.NAME);
        if (optionalPackageNodeDescription.isPresent() && optionalInterfaceNodeDescription.isPresent()) {
            optionalPackageNodeDescription.get().getChildrenDescriptions().add(optionalInterfaceNodeDescription.get());
        }
    }
}
