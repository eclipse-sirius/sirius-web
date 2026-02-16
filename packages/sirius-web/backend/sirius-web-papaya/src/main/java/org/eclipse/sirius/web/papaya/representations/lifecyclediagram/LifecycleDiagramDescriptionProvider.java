/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
package org.eclipse.sirius.web.papaya.representations.lifecyclediagram;

import java.util.List;

import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.builder.DefaultViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IDiagramElementDescriptionProvider;
import org.eclipse.sirius.components.view.builder.providers.IRepresentationDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.ArrangeLayoutDirection;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.edgedescriptions.ControllerCallsEdgeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.edgedescriptions.EventCausedByEdgeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.edgedescriptions.PublicationChannelEdgeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.edgedescriptions.PublicationEdgeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.edgedescriptions.ServiceCallsEdgeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.edgedescriptions.SubscriptionChannelEdgeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.edgedescriptions.SubscriptionEdgeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.nodedescriptions.ApplicationConcernNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.nodedescriptions.ApplicationLayerNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.nodedescriptions.ApplicationServiceNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.nodedescriptions.ChannelNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.nodedescriptions.CommandNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.nodedescriptions.ControllerNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.nodedescriptions.DomainLayerNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.nodedescriptions.DomainNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.nodedescriptions.DomainServiceNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.nodedescriptions.EventNodeDescriptionProvider;

/**
 * Used to provide the view model used to create lifecycle diagrams.
 *
 * @author sbegaudeau
 */
public class LifecycleDiagramDescriptionProvider implements IRepresentationDescriptionProvider {

    public static final String NAME = "Lifecycle Diagram";

    @Override
    public RepresentationDescription create(IColorProvider colorProvider) {
        var lifecycleDiagramDescription = DiagramFactory.eINSTANCE.createDiagramDescription();
        lifecycleDiagramDescription.setName(NAME);
        lifecycleDiagramDescription.setDomainType("papaya:ApplicationConcern");
        lifecycleDiagramDescription.setTitleExpression("aql:self.name + ' Lifecycle Diagram'");
        lifecycleDiagramDescription.setAutoLayout(false);
        lifecycleDiagramDescription.setArrangeLayoutDirection(ArrangeLayoutDirection.UP);
        lifecycleDiagramDescription.setIconExpression("aql:'/papaya-representations/lifecycle-diagram.svg'");
        lifecycleDiagramDescription.setToolbar(new DiagramBuilders().newDiagramToolbar().build());

        var cache = new DefaultViewDiagramElementFinder();

        List<IDiagramElementDescriptionProvider<?>> diagramElementDescriptionProviders = List.of(
                new ApplicationConcernNodeDescriptionProvider(colorProvider),
                new ApplicationLayerNodeDescriptionProvider(colorProvider),
                new CommandNodeDescriptionProvider(colorProvider),
                new ControllerNodeDescriptionProvider(colorProvider),
                new ApplicationServiceNodeDescriptionProvider(colorProvider),
                new DomainLayerNodeDescriptionProvider(colorProvider),
                new DomainNodeDescriptionProvider(colorProvider),
                new DomainServiceNodeDescriptionProvider(colorProvider),
                new EventNodeDescriptionProvider(colorProvider),
                new ChannelNodeDescriptionProvider(colorProvider),
                new ControllerCallsEdgeDescriptionProvider(colorProvider),
                new EventCausedByEdgeDescriptionProvider(colorProvider),
                new ServiceCallsEdgeDescriptionProvider(colorProvider),
                new PublicationEdgeDescriptionProvider(colorProvider),
                new SubscriptionEdgeDescriptionProvider(colorProvider),
                new PublicationChannelEdgeDescriptionProvider(colorProvider),
                new SubscriptionChannelEdgeDescriptionProvider(colorProvider)
        );

        diagramElementDescriptionProviders.forEach(diagramElementDescriptionProvider -> {
            var nodeDescription = diagramElementDescriptionProvider.create();
            cache.put(nodeDescription);
        });
        diagramElementDescriptionProviders.forEach(diagramElementDescriptionProvider -> diagramElementDescriptionProvider.link(lifecycleDiagramDescription, cache));
        return lifecycleDiagramDescription;
    }
}
