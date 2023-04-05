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
import org.eclipse.sirius.components.view.LineStyle;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.papaya.view.IColorProvider;
import org.eclipse.sirius.web.sample.papaya.view.IEdgeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewCache;

/**
 * Description of the references class.
 *
 * @author sbegaudeau
 */
public class ReferencesClassEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    private final IColorProvider colorProvider;

    public ReferencesClassEdgeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public EdgeDescription create() {
        var extendsClassEdgeStyle = ViewFactory.eINSTANCE.createEdgeStyle();
        extendsClassEdgeStyle.setColor(this.colorProvider.getColor("color_blue_6"));
        extendsClassEdgeStyle.setEdgeWidth(1);
        extendsClassEdgeStyle.setLineStyle(LineStyle.SOLID);
        extendsClassEdgeStyle.setSourceArrowStyle(ArrowStyle.NONE);
        extendsClassEdgeStyle.setTargetArrowStyle(ArrowStyle.DIAMOND);

        var extendsClassEdgeDescription = ViewFactory.eINSTANCE.createEdgeDescription();
        extendsClassEdgeDescription.setName("Edge References class");
        extendsClassEdgeDescription.setLabelExpression("aql:self.name");
        extendsClassEdgeDescription.setStyle(extendsClassEdgeStyle);
        extendsClassEdgeDescription.setSemanticCandidatesExpression("aql:self.eAllContents()");
        extendsClassEdgeDescription.setDomainType("papaya_logical_architecture::Reference");
        extendsClassEdgeDescription.setSourceNodesExpression("aql:self.eContainer()");
        extendsClassEdgeDescription.setTargetNodesExpression("aql:self.type");
        extendsClassEdgeDescription.setIsDomainBasedEdge(true);

        return extendsClassEdgeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, PapayaViewCache cache) {
        var referencesClassEdgeDescription = cache.getEdgeDescription("Edge References class");
        var classNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::Class");

        diagramDescription.getEdgeDescriptions().add(referencesClassEdgeDescription);
        referencesClassEdgeDescription.getSourceNodeDescriptions().add(classNodeDescription);
        referencesClassEdgeDescription.getTargetNodeDescriptions().add(classNodeDescription);
    }

}
