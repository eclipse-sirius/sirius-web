/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.papaya.representations.lifecyclediagram.tools;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.generated.diagram.EdgeToolBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ChangeContextBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.SetValueBuilder;
import org.eclipse.sirius.components.view.diagram.NodePalette;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.edgedescriptions.PublicationEdgeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.edgedescriptions.SubscriptionEdgeDescriptionProvider;

/**
 * Used to create the palette of the channel node.
 *
 * @author sbegaudeau
 */
public class ChannelNodePaletteProvider {

    public NodePalette getNodePalette(IViewDiagramElementFinder cache) {
        var publicationEdgeDescription = cache.getEdgeDescription(PublicationEdgeDescriptionProvider.NAME).orElse(null);
        var subscriptionEdgeDescription = cache.getEdgeDescription(SubscriptionEdgeDescriptionProvider.NAME).orElse(null);

        var channelEdgeTool = new EdgeToolBuilder()
                .body(new ChangeContextBuilder().expression("aql:semanticEdgeTarget")
                        .children(new SetValueBuilder()
                                .featureName("channel")
                                .valueExpression("aql:semanticEdgeSource")
                                .build())
                        .build())
                .targetElementDescriptions(subscriptionEdgeDescription, publicationEdgeDescription)
                .build();

        return new DiagramBuilders().newNodePalette()
                .edgeTools(channelEdgeTool).build();
    }
}
