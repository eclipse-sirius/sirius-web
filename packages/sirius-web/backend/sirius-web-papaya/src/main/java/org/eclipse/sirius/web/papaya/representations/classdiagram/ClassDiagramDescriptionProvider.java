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
package org.eclipse.sirius.web.papaya.representations.classdiagram;

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
import org.eclipse.sirius.web.papaya.representations.classdiagram.edgedescriptions.ClassExtendsEdgeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.edgedescriptions.ClassImplementsEdgeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.edgedescriptions.InterfaceExtendsEdgeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.edgedescriptions.RecordImplementsEdgeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.edgedescriptions.TypeContainmentEdgeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.ClassNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.EnumNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.InterfaceNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.RecordNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.tools.ClassDiagramDropToolProvider;

/**
 * Used to provide the view model used to create class diagrams.
 *
 * @author sbegaudeau
 */
public class ClassDiagramDescriptionProvider implements IRepresentationDescriptionProvider {

    public static final String NAME = "Class Diagram";

    @Override
    public RepresentationDescription create(IColorProvider colorProvider) {
        var classDiagramDescription = DiagramFactory.eINSTANCE.createDiagramDescription();
        classDiagramDescription.setName(NAME);
        classDiagramDescription.setDomainType("papaya:Component");
        classDiagramDescription.setTitleExpression("aql:self.name + ' class diagram'");
        classDiagramDescription.setAutoLayout(false);
        classDiagramDescription.setArrangeLayoutDirection(ArrangeLayoutDirection.UP);

        var cache = new DefaultViewDiagramElementFinder();

        List<IDiagramElementDescriptionProvider<?>> diagramElementDescriptionProviders = List.of(
                new ClassNodeDescriptionProvider(colorProvider),
                new InterfaceNodeDescriptionProvider(colorProvider),
                new RecordNodeDescriptionProvider(colorProvider),
                new EnumNodeDescriptionProvider(colorProvider),
                new ClassExtendsEdgeDescriptionProvider(colorProvider),
                new ClassImplementsEdgeDescriptionProvider(colorProvider),
                new InterfaceExtendsEdgeDescriptionProvider(colorProvider),
                new RecordImplementsEdgeDescriptionProvider(colorProvider),
                new TypeContainmentEdgeDescriptionProvider(colorProvider)
        );

        diagramElementDescriptionProviders.forEach(diagramElementDescriptionProvider -> {
            var nodeDescription = diagramElementDescriptionProvider.create();
            cache.put(nodeDescription);
        });
        diagramElementDescriptionProviders.forEach(diagramElementDescriptionProvider -> diagramElementDescriptionProvider.link(classDiagramDescription, cache));

        classDiagramDescription.setPalette(this.diagramPalette(cache));

        return classDiagramDescription;
    }

    private DiagramPalette diagramPalette(IViewDiagramElementFinder cache) {
        var dropTool = new ClassDiagramDropToolProvider().getDropTool(cache);
        return new DiagramBuilders().newDiagramPalette()
                .dropTool(dropTool)
                .build();
    }
}
