/*******************************************************************************
 * Copyright (c) 2024, 2024 Obeo.
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
package org.eclipse.sirius.web.papaya.representations.componentdiagram;

import java.util.List;

import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.builder.DefaultViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IDiagramElementDescriptionProvider;
import org.eclipse.sirius.components.view.builder.providers.IRepresentationDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.ArrangeLayoutDirection;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.DiagramPalette;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.web.papaya.representations.componentdiagram.edgedescriptions.ComponentDependencyEdgeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.componentdiagram.nodedescriptions.ComponentNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.componentdiagram.tools.CreateComponentNodeToolProvider;
import org.eclipse.sirius.web.papaya.representations.componentdiagram.tools.ComponentDiagramDropToolProvider;

/**
 * Used to provide the view model used to create component diagrams.
 *
 * @author sbegaudeau
 */
public class ComponentDiagramDescriptionProvider implements IRepresentationDescriptionProvider {

    public static final String NAME = "Component Diagram";

    @Override
    public RepresentationDescription create(IColorProvider colorProvider) {
        var componentDiagramDescription = DiagramFactory.eINSTANCE.createDiagramDescription();
        componentDiagramDescription.setName(NAME);
        componentDiagramDescription.setDomainType("papaya:Component");
        componentDiagramDescription.setTitleExpression("aql:self.name + ' component diagram'");
        componentDiagramDescription.setAutoLayout(false);
        componentDiagramDescription.setArrangeLayoutDirection(ArrangeLayoutDirection.DOWN);

        var cache = new DefaultViewDiagramElementFinder();

        List<IDiagramElementDescriptionProvider<?>> diagramElementDescriptionProviders = List.of(
                new ComponentNodeDescriptionProvider(colorProvider),
                new ComponentDependencyEdgeDescriptionProvider(colorProvider)
        );

        diagramElementDescriptionProviders.forEach(diagramElementDescriptionProvider -> {
            var nodeDescription = diagramElementDescriptionProvider.create();
            cache.put(nodeDescription);
        });
        diagramElementDescriptionProviders.forEach(diagramElementDescriptionProvider -> diagramElementDescriptionProvider.link(componentDiagramDescription, cache));

        componentDiagramDescription.setPalette(this.diagramPalette(cache));

        return componentDiagramDescription;
    }

    private DiagramPalette diagramPalette(IViewDiagramElementFinder cache) {
        var nodeTools = List.of(
                new CreateComponentNodeToolProvider().create(cache)
        );
        var dropTool = new ComponentDiagramDropToolProvider().getDropTool(cache);

        return new DiagramBuilders().newDiagramPalette()
                .nodeTools(nodeTools.toArray(new NodeTool[nodeTools.size()]))
                .dropTool(dropTool)
                .build();
    }
}
