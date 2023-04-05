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

import org.eclipse.sirius.components.view.ArrowStyle;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.EdgeDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.papaya.view.IColorProvider;
import org.eclipse.sirius.web.sample.papaya.view.IEdgeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewBuilder;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewCache;

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
        var dependsOnEdgeStyle = ViewFactory.eINSTANCE.createEdgeStyle();
        dependsOnEdgeStyle.setColor(this.colorProvider.getColor("color_green_2"));
        dependsOnEdgeStyle.setEdgeWidth(1);
        dependsOnEdgeStyle.setSourceArrowStyle(ArrowStyle.OUTPUT_FILL_CLOSED_ARROW);
        dependsOnEdgeStyle.setTargetArrowStyle(ArrowStyle.INPUT_FILL_CLOSED_ARROW);

        var builder = new PapayaViewBuilder();

        var dependsOnEdgeDescription = ViewFactory.eINSTANCE.createEdgeDescription();
        dependsOnEdgeDescription.setName("Edge Depends on");
        dependsOnEdgeDescription.setStyle(dependsOnEdgeStyle);
        dependsOnEdgeDescription.setSourceNodesExpression("aql:self");
        dependsOnEdgeDescription.setTargetNodesExpression("aql:self.eResource().getContents(" + builder.domainType(builder.entity("ProvidedService"))
                + ")->select(providedService | providedService.contract <> null and providedService.contract = self.contract)->first()");

        return dependsOnEdgeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, PapayaViewCache cache) {
        var dependsOnEdgeDescription = cache.getEdgeDescription("Edge Depends on");
        var providedServiceNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::ProvidedService");
        var requiredServiceNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::RequiredService");

        diagramDescription.getEdgeDescriptions().add(dependsOnEdgeDescription);
        dependsOnEdgeDescription.getSourceNodeDescriptions().add(requiredServiceNodeDescription);
        dependsOnEdgeDescription.getTargetNodeDescriptions().add(providedServiceNodeDescription);
    }

}
