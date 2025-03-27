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
import org.eclipse.sirius.components.view.builder.generated.diagram.EdgePaletteBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.EdgeToolBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ChangeContextBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.SetValueBuilder;
import org.eclipse.sirius.components.view.diagram.EdgePalette;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.nodedescriptions.ChannelNodeDescriptionProvider;

/**
 * Used to create the palette of the subscription edge.
 *
 * @author mcharfadi
 */
public class SubscriptionEdgePaletteProvider {

    public EdgePalette getEdgePalette(IViewDiagramElementFinder cache) {
        var channelNodeDescription = cache.getNodeDescription(ChannelNodeDescriptionProvider.NAME).orElse(null);

        var channelEdgeTool = new EdgeToolBuilder()
                .body(new ChangeContextBuilder().expression("aql:semanticEdgeSource")
                        .children(new SetValueBuilder()
                                .featureName("channel")
                                .valueExpression("aql:semanticEdgeTarget")
                                .build())
                        .build())
                .targetElementDescriptions(channelNodeDescription)
                .build();

        return new EdgePaletteBuilder()
                .edgeTools(channelEdgeTool)
                .build();
    }

}
