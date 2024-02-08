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
import org.eclipse.sirius.components.view.diagram.LineStyle;

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
        var extendsClassEdgeStyle = DiagramFactory.eINSTANCE.createEdgeStyle();
        extendsClassEdgeStyle.setColor(this.colorProvider.getColor("color_blue_6"));
        extendsClassEdgeStyle.setEdgeWidth(1);
        extendsClassEdgeStyle.setLineStyle(LineStyle.SOLID);
        extendsClassEdgeStyle.setSourceArrowStyle(ArrowStyle.NONE);
        extendsClassEdgeStyle.setTargetArrowStyle(ArrowStyle.DIAMOND);

        var extendsClassEdgeDescription = DiagramFactory.eINSTANCE.createEdgeDescription();
        extendsClassEdgeDescription.setName("Edge References class");
        extendsClassEdgeDescription.setCenterLabelExpression("aql:self.name");
        extendsClassEdgeDescription.setStyle(extendsClassEdgeStyle);
        extendsClassEdgeDescription.setSemanticCandidatesExpression("aql:self.eAllContents()");
        extendsClassEdgeDescription.setDomainType("papaya_logical_architecture::Reference");
        extendsClassEdgeDescription.setSourceNodesExpression("aql:self.eContainer()");
        extendsClassEdgeDescription.setTargetNodesExpression("aql:self.type");
        extendsClassEdgeDescription.setIsDomainBasedEdge(true);

        return extendsClassEdgeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalReferencesClassEdgeDescription = cache.getEdgeDescription("Edge References class");
        var optionalClassNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::Class");

        if (optionalReferencesClassEdgeDescription.isPresent() && optionalClassNodeDescription.isPresent()) {
            diagramDescription.getEdgeDescriptions().add(optionalReferencesClassEdgeDescription.get());
            optionalReferencesClassEdgeDescription.get().getSourceNodeDescriptions().add(optionalClassNodeDescription.get());
            optionalReferencesClassEdgeDescription.get().getTargetNodeDescriptions().add(optionalClassNodeDescription.get());
        }
    }

}
