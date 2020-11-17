/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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

import org.eclipse.elk.alg.common.compaction.options.PolyominoOptions;
import org.eclipse.elk.alg.disco.options.DisCoMetaDataProvider;
import org.eclipse.elk.alg.force.options.ForceMetaDataProvider;
import org.eclipse.elk.alg.force.options.StressMetaDataProvider;
import org.eclipse.elk.alg.layered.options.LayeredMetaDataProvider;
import org.eclipse.elk.alg.layered.options.LayeredOptions;
import org.eclipse.elk.alg.mrtree.options.MrTreeMetaDataProvider;
import org.eclipse.elk.alg.radial.options.RadialMetaDataProvider;
import org.eclipse.elk.alg.spore.options.SporeMetaDataProvider;
import org.eclipse.elk.core.IGraphLayoutEngine;
import org.eclipse.elk.core.LayoutConfigurator;
import org.eclipse.elk.core.RecursiveGraphLayoutEngine;
import org.eclipse.elk.core.data.LayoutMetaDataService;
import org.eclipse.elk.core.util.BasicProgressMonitor;
import org.eclipse.elk.core.util.ElkUtil;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.layout.api.ILayoutService;
import org.eclipse.sirius.web.services.api.representations.IRepresentationDescriptionService;
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

    private final IRepresentationDescriptionService representationDescriptionService;

    public LayoutService(DiagramConverter diagramConverter, LayoutConfiguratorRegistry layoutConfiguratorRegistry, LayoutedDiagramProvider layoutedDiagramProvider,
            IRepresentationDescriptionService representationDescriptionService) {
        this.diagramConverter = Objects.requireNonNull(diagramConverter);
        this.layoutConfiguratorRegistry = Objects.requireNonNull(layoutConfiguratorRegistry);
        this.layoutedDiagramProvider = Objects.requireNonNull(layoutedDiagramProvider);
        this.representationDescriptionService = Objects.requireNonNull(representationDescriptionService);
    }

    @Override
    public Diagram layout(Diagram diagram) {
        ConvertedDiagram convertedDiagram = this.diagramConverter.convert(diagram);

        ElkNode elkDiagram = convertedDiagram.getElkDiagram();
        var representationDescription = this.representationDescriptionService.findRepresentationDescriptionById(diagram.getDescriptionId());
        LayoutConfigurator layoutConfigurator;
        if (representationDescription.isPresent() && representationDescription.get() instanceof DiagramDescription) {
            layoutConfigurator = this.layoutConfiguratorRegistry.getLayoutConfigurator(diagram, (DiagramDescription) representationDescription.get());
        } else {
            layoutConfigurator = this.layoutConfiguratorRegistry.getDefaultLayoutConfigurator();
        }

        // @formatter:off
        LayoutMetaDataService.getInstance().registerLayoutMetaDataProviders(
                new LayeredOptions(),
                new ForceMetaDataProvider(),
                new LayeredMetaDataProvider(),
                new MrTreeMetaDataProvider(),
                new RadialMetaDataProvider(),
                new StressMetaDataProvider(),
                new PolyominoOptions(),
                new DisCoMetaDataProvider(),
                new SporeMetaDataProvider());
        // @formatter:on

        ElkUtil.applyVisitors(elkDiagram, layoutConfigurator);
        IGraphLayoutEngine engine = new RecursiveGraphLayoutEngine();
        engine.layout(elkDiagram, new BasicProgressMonitor());

        Map<String, ElkGraphElement> id2ElkGraphElements = convertedDiagram.getId2ElkGraphElements();
        Diagram layoutedDiagram = this.layoutedDiagramProvider.getLayoutedDiagram(diagram, elkDiagram, id2ElkGraphElements);

        return layoutedDiagram;
    }

}
