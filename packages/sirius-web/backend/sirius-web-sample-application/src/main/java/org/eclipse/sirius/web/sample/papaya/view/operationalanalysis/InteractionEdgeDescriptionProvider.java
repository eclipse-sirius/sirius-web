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
package org.eclipse.sirius.web.sample.papaya.view.operationalanalysis;

import org.eclipse.sirius.components.view.ArrowStyle;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.EdgeDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.papaya.view.IEdgeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewBuilder;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewCache;

/**
 * Description of the interaction.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class InteractionEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    @Override
    public EdgeDescription create() {
        var interactionEdgeStyle = ViewFactory.eINSTANCE.createEdgeStyle();
        interactionEdgeStyle.setColor("#212121");
        interactionEdgeStyle.setEdgeWidth(1);
        interactionEdgeStyle.setSourceArrowStyle(ArrowStyle.NONE);
        interactionEdgeStyle.setTargetArrowStyle(ArrowStyle.INPUT_FILL_CLOSED_ARROW);

        var builder = new PapayaViewBuilder();

        var interactionEdgeDescription = ViewFactory.eINSTANCE.createEdgeDescription();
        interactionEdgeDescription.setName("Edge Interaction");
        interactionEdgeDescription.setLabelExpression("aql:'interacts with'");
        interactionEdgeDescription.setSemanticCandidatesExpression("aql:self.eAllContents()");
        interactionEdgeDescription.setDomainType(builder.domainType(builder.entity("Interaction")));
        interactionEdgeDescription.setIsDomainBasedEdge(true);
        interactionEdgeDescription.setSourceNodesExpression("aql:self.eContainer()");
        interactionEdgeDescription.setTargetNodesExpression("aql:self.target");
        interactionEdgeDescription.setStyle(interactionEdgeStyle);

        return interactionEdgeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, PapayaViewCache cache) {
        var interactionEdgeDescription = cache.getEdgeDescription("Edge Interaction");
        var operationalActivityNodeDescription = cache.getNodeDescription("Node papaya_operational_analysis::OperationalActivity");

        diagramDescription.getEdgeDescriptions().add(interactionEdgeDescription);
        interactionEdgeDescription.getSourceNodeDescriptions().add(operationalActivityNodeDescription);
        interactionEdgeDescription.getTargetNodeDescriptions().add(operationalActivityNodeDescription);
    }

}
