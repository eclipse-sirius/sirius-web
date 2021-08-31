/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo and others.
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
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.web.diagrams.layout.api.ILayoutService;
import org.eclipse.sirius.web.diagrams.layout.incremental.IncrementalLayoutConvertedDiagram;
import org.eclipse.sirius.web.diagrams.layout.incremental.IncrementalLayoutDiagramConverter;
import org.eclipse.sirius.web.diagrams.layout.incremental.IncrementalLayoutEngine;
import org.eclipse.sirius.web.diagrams.layout.incremental.IncrementalLayoutedDiagramProvider;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.DiagramLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.ILayoutData;
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

    private final ELKDiagramConverter elkDiagramConverter;

    private final LayoutConfiguratorRegistry layoutConfiguratorRegistry;

    private final ELKLayoutedDiagramProvider elkLayoutedDiagramProvider;

    private final IncrementalLayoutedDiagramProvider incrementalLayoutedDiagramProvider;

    private final IncrementalLayoutDiagramConverter incrementalLayoutDiagramConverter;

    private final IncrementalLayoutEngine incrementalLayoutEngine;

    private final Logger logger = LoggerFactory.getLogger(LayoutService.class);

    public LayoutService(ELKDiagramConverter elkDiagramConverter, IncrementalLayoutDiagramConverter incrementalLayoutDiagramConverter, LayoutConfiguratorRegistry layoutConfiguratorRegistry,
            ELKLayoutedDiagramProvider layoutedDiagramProvider, IncrementalLayoutedDiagramProvider incrementalLayoutedDiagramProvider, IncrementalLayoutEngine incrementalLayoutEngine) {
        this.elkDiagramConverter = Objects.requireNonNull(elkDiagramConverter);
        this.incrementalLayoutDiagramConverter = Objects.requireNonNull(incrementalLayoutDiagramConverter);
        this.layoutConfiguratorRegistry = Objects.requireNonNull(layoutConfiguratorRegistry);
        this.elkLayoutedDiagramProvider = Objects.requireNonNull(layoutedDiagramProvider);
        this.incrementalLayoutedDiagramProvider = Objects.requireNonNull(incrementalLayoutedDiagramProvider);
        this.incrementalLayoutEngine = Objects.requireNonNull(incrementalLayoutEngine);
        LayoutMetaDataService.getInstance().registerLayoutMetaDataProviders(new LayeredOptions());
    }

    @Override
    public Diagram layout(Diagram diagram, DiagramDescription diagramDescription) {
        ELKConvertedDiagram convertedDiagram = this.elkDiagramConverter.convert(diagram);

        ElkNode elkDiagram = convertedDiagram.getElkDiagram();
        ISiriusWebLayoutConfigurator layoutConfigurator = this.getLayoutConfigurator(diagram, diagramDescription);

        ElkUtil.applyVisitors(elkDiagram, layoutConfigurator);
        IGraphLayoutEngine engine = new RecursiveGraphLayoutEngine();
        engine.layout(elkDiagram, new BasicProgressMonitor());

        Map<String, ElkGraphElement> id2ElkGraphElements = convertedDiagram.getId2ElkGraphElements();
        Diagram layoutedDiagram = this.elkLayoutedDiagramProvider.getLayoutedDiagram(diagram, elkDiagram, id2ElkGraphElements);

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
    public Diagram incrementalLayout(Diagram newDiagram, DiagramDescription diagramDescription, Optional<IDiagramEvent> optionalDiagramElementEvent) {
        IncrementalLayoutConvertedDiagram convertedDiagram = this.incrementalLayoutDiagramConverter.convert(newDiagram);
        DiagramLayoutData diagramLayoutData = convertedDiagram.getDiagramLayoutData();

        ISiriusWebLayoutConfigurator layoutConfigurator = this.getLayoutConfigurator(newDiagram, diagramDescription);
        this.incrementalLayoutEngine.layout(optionalDiagramElementEvent, diagramLayoutData, layoutConfigurator);

        Map<String, ILayoutData> id2LayoutData = convertedDiagram.getId2LayoutData();
        return this.incrementalLayoutedDiagramProvider.getLayoutedDiagram(newDiagram, diagramLayoutData, id2LayoutData);
    }

    private ISiriusWebLayoutConfigurator getLayoutConfigurator(Diagram diagram, DiagramDescription diagramDescription) {
        if (diagramDescription != null) {
            return this.layoutConfiguratorRegistry.getLayoutConfigurator(diagram, diagramDescription);
        } else {
            return this.layoutConfiguratorRegistry.getDefaultLayoutConfigurator();
        }
    }

}
