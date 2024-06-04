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
import org.eclipse.sirius.components.view.builder.providers.IEdgeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.provider.DefaultToolsFactory;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewBuilder;


/**
 * Description of depends on.
 *
 * @author sbegaudeau
 */
public class DependsOnEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    private final IColorProvider colorProvider;

    public DependsOnEdgeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public EdgeDescription create() {
        var dependsOnEdgeStyle = DiagramFactory.eINSTANCE.createEdgeStyle();
        dependsOnEdgeStyle.setColor(this.colorProvider.getColor("color_green_2"));
        dependsOnEdgeStyle.setEdgeWidth(1);
        dependsOnEdgeStyle.setSourceArrowStyle(ArrowStyle.OUTPUT_FILL_CLOSED_ARROW);
        dependsOnEdgeStyle.setTargetArrowStyle(ArrowStyle.INPUT_FILL_CLOSED_ARROW);
        dependsOnEdgeStyle.setBorderSize(0);

        var builder = new PapayaViewBuilder();

        var dependsOnEdgeDescription = DiagramFactory.eINSTANCE.createEdgeDescription();
        dependsOnEdgeDescription.setName("Edge Depends on");
        dependsOnEdgeDescription.setStyle(dependsOnEdgeStyle);
        dependsOnEdgeDescription.setSourceNodesExpression("aql:self");
        dependsOnEdgeDescription.setTargetNodesExpression("aql:self.eResource().getContents(" + builder.domainType(builder.entity("ProvidedService"))
                + ")->select(providedService | providedService.contract <> null and providedService.contract = self.contract)->first()");

        var palette = DiagramFactory.eINSTANCE.createEdgePalette();
        palette.getToolSections().add(new DefaultToolsFactory().createDefaultHideRevealEdgeToolSection());
        dependsOnEdgeDescription.setPalette(palette);

        return dependsOnEdgeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalDependsOnEdgeDescription = cache.getEdgeDescription("Edge Depends on");
        var optionalProvidedServiceNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::ProvidedService");
        var optionalRequiredServiceNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::RequiredService");

        if (optionalDependsOnEdgeDescription.isPresent() && optionalProvidedServiceNodeDescription.isPresent() && optionalRequiredServiceNodeDescription.isPresent()) {
            diagramDescription.getEdgeDescriptions().add(optionalDependsOnEdgeDescription.get());
            optionalDependsOnEdgeDescription.get().getSourceNodeDescriptions().add(optionalRequiredServiceNodeDescription.get());
            optionalDependsOnEdgeDescription.get().getTargetNodeDescriptions().add(optionalProvidedServiceNodeDescription.get());
        }
    }

}
