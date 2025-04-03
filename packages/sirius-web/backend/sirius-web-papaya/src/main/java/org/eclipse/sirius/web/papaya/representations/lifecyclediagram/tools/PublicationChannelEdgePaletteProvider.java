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
import org.eclipse.sirius.components.view.builder.generated.diagram.SourceEdgeEndReconnectionToolBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ChangeContextBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.SetValueBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.UnsetValueBuilder;
import org.eclipse.sirius.components.view.diagram.EdgePalette;

/**
 * Used to create the palette of the publication#channel edge.
 *
 * @author mcharfadi
 */
public class PublicationChannelEdgePaletteProvider {

    public EdgePalette getEdgePalette(IViewDiagramElementFinder cache) {
        var publicationChannelEdgeTool = new SourceEdgeEndReconnectionToolBuilder()
                .body(new ChangeContextBuilder().expression("aql:semanticReconnectionTarget")
                    .children(new SetValueBuilder().featureName("channel")
                            .valueExpression("aql:semanticReconnectionSource")
                            .build(),
                            new ChangeContextBuilder().expression("aql:edgeSemanticElement")
                                .children(new UnsetValueBuilder().featureName("channel").build())
                                .build())
                            .build())
                .build();

        return new EdgePaletteBuilder()
                .edgeReconnectionTools(publicationChannelEdgeTool)
                .build();
    }

}
