/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo and others.
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
package org.eclipse.sirius.components.diagrams.layout;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.elk.alg.layered.options.LayeredOptions;
import org.eclipse.elk.core.IGraphLayoutEngine;
import org.eclipse.elk.core.RecursiveGraphLayoutEngine;
import org.eclipse.elk.core.data.LayoutMetaDataService;
import org.eclipse.elk.core.util.BasicProgressMonitor;
import org.eclipse.elk.core.util.ElkUtil;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.json.ElkGraphJson;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layout.api.ILayoutService;
import org.eclipse.sirius.components.diagrams.layout.incremental.IncrementalLayoutConvertedDiagram;
import org.eclipse.sirius.components.diagrams.layout.incremental.IncrementalLayoutDiagramConverter;
import org.eclipse.sirius.components.diagrams.layout.incremental.IncrementalLayoutEngine;
import org.eclipse.sirius.components.diagrams.layout.incremental.IncrementalLayoutedDiagramProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.ILayoutData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Perform the layouting of the given diagram.
 *
 * @author sbegaudeau
 */
@Service
public class LayoutService implements ILayoutService {

    private final IELKDiagramConverter elkDiagramConverter;

    private final LayoutConfiguratorRegistry layoutConfiguratorRegistry;

    private final ELKLayoutedDiagramProvider elkLayoutedDiagramProvider;

    private final IncrementalLayoutedDiagramProvider incrementalLayoutedDiagramProvider;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IncrementalLayoutDiagramConverter incrementalLayoutDiagramConverter;

    private final IncrementalLayoutEngine incrementalLayoutEngine;

    private final Logger logger = LoggerFactory.getLogger(LayoutService.class);

    public LayoutService(IELKDiagramConverter elkDiagramConverter, IncrementalLayoutDiagramConverter incrementalLayoutDiagramConverter, LayoutConfiguratorRegistry layoutConfiguratorRegistry,
            ELKLayoutedDiagramProvider layoutedDiagramProvider, IncrementalLayoutedDiagramProvider incrementalLayoutedDiagramProvider,
            IRepresentationDescriptionSearchService representationDescriptionSearchService, IncrementalLayoutEngine incrementalLayoutEngine) {
        this.elkDiagramConverter = Objects.requireNonNull(elkDiagramConverter);
        this.incrementalLayoutDiagramConverter = Objects.requireNonNull(incrementalLayoutDiagramConverter);
        this.layoutConfiguratorRegistry = Objects.requireNonNull(layoutConfiguratorRegistry);
        this.elkLayoutedDiagramProvider = Objects.requireNonNull(layoutedDiagramProvider);
        this.incrementalLayoutedDiagramProvider = Objects.requireNonNull(incrementalLayoutedDiagramProvider);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.incrementalLayoutEngine = Objects.requireNonNull(incrementalLayoutEngine);
        LayoutMetaDataService.getInstance().registerLayoutMetaDataProviders(new LayeredOptions());
    }

    @Override
    public Diagram layout(IEditingContext editingContext, Diagram diagram) {
        ISiriusWebLayoutConfigurator layoutConfigurator = this.getLayoutConfigurator(editingContext, diagram);

        ELKConvertedDiagram convertedDiagram = this.prepareForLayout(editingContext, diagram, layoutConfigurator);
        ElkNode elkDiagram = convertedDiagram.getElkDiagram();

        IGraphLayoutEngine engine = new RecursiveGraphLayoutEngine();
        engine.layout(elkDiagram, new BasicProgressMonitor());

        elkDiagram = layoutConfigurator.applyAfterLayout(elkDiagram, editingContext, diagram);

        Map<String, ElkGraphElement> id2ElkGraphElements = convertedDiagram.getId2ElkGraphElements();
        Diagram layoutedDiagram = this.elkLayoutedDiagramProvider.getLayoutedDiagram(diagram, elkDiagram, id2ElkGraphElements, layoutConfigurator);

        if (this.logger.isDebugEnabled()) {
            // @formatter:off
            String json = ElkGraphJson.forGraph(elkDiagram)
                    .omitLayout(true)
                    .omitZeroDimension(true)
                    .omitZeroPositions(true)
                    .shortLayoutOptionKeys(false)
                    .prettyPrint(true)
                    .toJson();
            // @formatter:on
            this.logger.debug(json);
        }

        return layoutedDiagram;
    }

    @Override
    public Diagram incrementalLayout(IEditingContext editingContext, Diagram newDiagram, Optional<IDiagramEvent> optionalDiagramElementEvent) {
        ISiriusWebLayoutConfigurator layoutConfigurator = this.getLayoutConfigurator(editingContext, newDiagram);
        IncrementalLayoutConvertedDiagram convertedDiagram = this.incrementalLayoutDiagramConverter.convert(newDiagram, layoutConfigurator);
        DiagramLayoutData diagramLayoutData = convertedDiagram.getDiagramLayoutData();

        this.incrementalLayoutEngine.layout(optionalDiagramElementEvent, convertedDiagram, layoutConfigurator);

        Map<String, ILayoutData> id2LayoutData = convertedDiagram.getId2LayoutData();
        return this.incrementalLayoutedDiagramProvider.getLayoutedDiagram(newDiagram, diagramLayoutData, id2LayoutData);
    }

    private ISiriusWebLayoutConfigurator getLayoutConfigurator(IEditingContext editingContext, Diagram diagram) {
        // @formatter:off
        var optionalDiagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast);
        // @formatter:on

        ISiriusWebLayoutConfigurator layoutConfigurator = this.layoutConfiguratorRegistry.getDefaultLayoutConfigurator();
        if (optionalDiagramDescription.isPresent()) {
            var diagramDescription = optionalDiagramDescription.get();
            layoutConfigurator = this.layoutConfiguratorRegistry.getLayoutConfigurator(diagram, diagramDescription);
        }
        return layoutConfigurator;
    }

    private ELKConvertedDiagram prepareForLayout(IEditingContext editingContext, Diagram diagram, ISiriusWebLayoutConfigurator layoutConfigurator) {
        ELKConvertedDiagram convertedDiagram = this.elkDiagramConverter.convert(diagram, layoutConfigurator);
        ElkNode elkDiagram = convertedDiagram.getElkDiagram();

        ElkUtil.applyVisitors(elkDiagram, layoutConfigurator);
        elkDiagram = layoutConfigurator.applyBeforeLayout(elkDiagram, editingContext, diagram);

        return convertedDiagram;
    }

}
