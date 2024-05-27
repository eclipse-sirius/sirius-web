/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.papaya.representations.classdiagram.edgedescriptions;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IEdgeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.ClassNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.EnumNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.InterfaceNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.RecordNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.services.PapayaColorPaletteProvider;

/**
 * Used to create the type containment edge description.
 *
 * @author sbegaudeau
 */
public class TypeContainmentEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    public static final String NAME = "Type#containment";

    private final IColorProvider colorProvider;

    public TypeContainmentEdgeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public EdgeDescription create() {
        var implementsEdgeStyle = new DiagramBuilders().newEdgeStyle()
                .color(this.colorProvider.getColor(PapayaColorPaletteProvider.PALETTE_TEXT_PRIMARY))
                .sourceArrowStyle(ArrowStyle.INPUT_ARROW_WITH_DIAMOND)
                .targetArrowStyle(ArrowStyle.NONE)
                .lineStyle(LineStyle.SOLID)
                .edgeWidth(1)
                .build();

        return new DiagramBuilders().newEdgeDescription()
                .name(NAME)
                .centerLabelExpression("")
                .sourceNodesExpression("aql:self")
                .targetNodesExpression("aql:self.types")
                .isDomainBasedEdge(false)
                .style(implementsEdgeStyle)
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalClassNodeDescription = cache.getNodeDescription(ClassNodeDescriptionProvider.NAME);
        var optionalInterfaceNodeDescription = cache.getNodeDescription(InterfaceNodeDescriptionProvider.NAME);
        var optionalEnumNodeDescription = cache.getNodeDescription(EnumNodeDescriptionProvider.NAME);
        var optionalRecordNodeDescription = cache.getNodeDescription(RecordNodeDescriptionProvider.NAME);
        var optionalTypeContainmentEdgeDescription = cache.getEdgeDescription(NAME);

        var areDescriptionsPresent = optionalClassNodeDescription.isPresent();
        areDescriptionsPresent = areDescriptionsPresent && optionalInterfaceNodeDescription.isPresent();
        areDescriptionsPresent = areDescriptionsPresent && optionalEnumNodeDescription.isPresent();
        areDescriptionsPresent = areDescriptionsPresent && optionalRecordNodeDescription.isPresent();
        areDescriptionsPresent = areDescriptionsPresent && optionalTypeContainmentEdgeDescription.isPresent();

        if (areDescriptionsPresent) {
            var classNodeDescription = optionalClassNodeDescription.get();
            var interfaceNodeDescription = optionalInterfaceNodeDescription.get();
            var enumNodeDescription = optionalEnumNodeDescription.get();
            var recordNodeDescription = optionalRecordNodeDescription.get();

            var typeContainmentEdgeDescription = optionalTypeContainmentEdgeDescription.get();

            typeContainmentEdgeDescription.getSourceNodeDescriptions().addAll(List.of(classNodeDescription, interfaceNodeDescription, enumNodeDescription, recordNodeDescription));
            typeContainmentEdgeDescription.getTargetNodeDescriptions().addAll(List.of(classNodeDescription, interfaceNodeDescription, enumNodeDescription, recordNodeDescription));

            diagramDescription.getEdgeDescriptions().add(typeContainmentEdgeDescription);
        }
    }
}
