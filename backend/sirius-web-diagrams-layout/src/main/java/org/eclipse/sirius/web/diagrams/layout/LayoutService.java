/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.diagrams.layout;

import java.util.Map;
import java.util.Objects;

import org.eclipse.elk.alg.layered.options.LayeredOptions;
import org.eclipse.elk.core.IGraphLayoutEngine;
import org.eclipse.elk.core.LayoutConfigurator;
import org.eclipse.elk.core.RecursiveGraphLayoutEngine;
import org.eclipse.elk.core.data.LayoutMetaDataService;
import org.eclipse.elk.core.util.BasicProgressMonitor;
import org.eclipse.elk.core.util.ElkUtil;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.layout.api.ILayoutService;
import org.springframework.stereotype.Service;

/**
 * Perform the layouting of the given diagram.
 *
 * @author sbegaudeau
 */
@Service
public class LayoutService implements ILayoutService {

    private final DiagramConverter diagramConverter;

    private final LayoutConfiguratorRegistry layoutConfiguratorRegistry;

    private final LayoutedDiagramProvider layoutedDiagramProvider;

    public LayoutService(DiagramConverter diagramConverter, LayoutConfiguratorRegistry layoutConfiguratorRegistry, LayoutedDiagramProvider layoutedDiagramProvider) {
        this.diagramConverter = Objects.requireNonNull(diagramConverter);
        this.layoutConfiguratorRegistry = Objects.requireNonNull(layoutConfiguratorRegistry);
        this.layoutedDiagramProvider = Objects.requireNonNull(layoutedDiagramProvider);
    }

    @Override
    public Diagram layout(Diagram diagram) {
        ConvertedDiagram convertedDiagram = this.diagramConverter.convert(diagram);

        ElkNode elkDiagram = convertedDiagram.getElkDiagram();
        LayoutConfigurator layoutConfigurator = this.layoutConfiguratorRegistry.getLayoutConfigurator();

        LayoutMetaDataService.getInstance().registerLayoutMetaDataProviders(new LayeredOptions());

        ElkUtil.applyVisitors(elkDiagram, layoutConfigurator);
        IGraphLayoutEngine engine = new RecursiveGraphLayoutEngine();
        engine.layout(elkDiagram, new BasicProgressMonitor());

        Map<String, ElkGraphElement> id2ElkGraphElements = convertedDiagram.getId2ElkGraphElements();
        Diagram layoutedDiagram = this.layoutedDiagramProvider.getLayoutedDiagram(diagram, elkDiagram, id2ElkGraphElements);

        return layoutedDiagram;
    }

}
