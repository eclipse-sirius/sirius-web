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
package org.eclipse.sirius.components.flow.starter.view;

import java.util.List;

import org.eclipse.sirius.components.flow.starter.view.descriptions.DataSourceDescriptionProvider;
import org.eclipse.sirius.components.flow.starter.view.descriptions.DataSourceToProcessorEdgeDescriptionProvider;
import org.eclipse.sirius.components.flow.starter.view.descriptions.FanDescriptionProvider;
import org.eclipse.sirius.components.flow.starter.view.descriptions.ProcessorDescriptionProvider;
import org.eclipse.sirius.components.flow.starter.view.descriptions.ProcessorToProcessorEdgeDescriptionProvider;
import org.eclipse.sirius.components.flow.starter.view.descriptions.SystemDescriptionProvider;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IDiagramElementDescriptionProvider;
import org.eclipse.sirius.components.view.builder.providers.IRepresentationDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.DiagramPalette;
import org.eclipse.sirius.components.view.diagram.DiagramToolSection;
import org.eclipse.sirius.components.view.diagram.DropTool;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;

/**
 * Used to create Flow view.
 *
 * @author frouene
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class FlowTopographyUnsynchronizedViewDiagramDescriptionProvider implements IRepresentationDescriptionProvider {

    private final DiagramBuilders diagramBuilderHelper = new DiagramBuilders();

    private final ViewBuilders viewBuilderHelper = new ViewBuilders();

    @Override
    public RepresentationDescription create(IColorProvider colorProvider) {
        var diagramDescriptionBuilder = this.diagramBuilderHelper.newDiagramDescription();
        var diagramDescription = diagramDescriptionBuilder
                .autoLayout(false)
                .domainType("flow::System")
                .name("Topography unsynchronized")
                .titleExpression("Topography unsynchronized").build();


        var cache = new ViewDiagramElementFinder();
        var diagramElementDescriptionProviders = List.of(
                new DataSourceDescriptionProvider(colorProvider, SynchronizationPolicy.UNSYNCHRONIZED),
                new ProcessorDescriptionProvider(colorProvider, SynchronizationPolicy.UNSYNCHRONIZED),
                new FanDescriptionProvider(colorProvider, SynchronizationPolicy.UNSYNCHRONIZED),
                new SystemDescriptionProvider(colorProvider, SynchronizationPolicy.UNSYNCHRONIZED, false),
                new DataSourceToProcessorEdgeDescriptionProvider(colorProvider),
                new ProcessorToProcessorEdgeDescriptionProvider(colorProvider)
        );

        diagramElementDescriptionProviders.stream().map(IDiagramElementDescriptionProvider::create).forEach(cache::put);

        diagramElementDescriptionProviders.forEach(diagramElementDescriptionProvider -> diagramElementDescriptionProvider.link(diagramDescription, cache));

        var palette = this.createDiagramPalette(cache);
        diagramDescription.setPalette(palette);

        return diagramDescription;
    }

    private DiagramPalette createDiagramPalette(IViewDiagramElementFinder cache) {
        return this.diagramBuilderHelper.newDiagramPalette()
                .toolSections(this.createDiagramToolSection(cache))
                .dropTool(this.createDropTool(cache))
                .build();
    }

    private DiagramToolSection createDiagramToolSection(IViewDiagramElementFinder cache) {
        return this.diagramBuilderHelper.newDiagramToolSection()
                .name("Creation Tools")
                .nodeTools(this.createNodeToolCreateCompositeProcessor(cache),
                        this.createNodeToolCreateDataSource(cache))
                .build();
    }

    private NodeTool createNodeToolCreateCompositeProcessor(IViewDiagramElementFinder cache) {

        var setValueStatus = this.viewBuilderHelper.newSetValue()
                .featureName("status")
                .valueExpression("active");
        var setValueName = this.viewBuilderHelper.newSetValue()
                .featureName("name")
                .valueExpression("aql:'CompositeProcessor' + self.eContainer().eContents()->filter(flow::CompositeProcessor)->size()");

        cache.getNodeDescription(SystemDescriptionProvider.NAME).ifPresent(dataSourceNodeDescription -> {
            var createView = this.diagramBuilderHelper.newCreateView()
                    .parentViewExpression("aql:selectedNode")
                    .semanticElementExpression("aql:newInstance")
                    .elementDescription(dataSourceNodeDescription);

            setValueName.children(createView.build());
        });

        var changeContextNewInstance = this.viewBuilderHelper.newChangeContext()
                .expression("aql:newInstance")
                .children(setValueStatus.build(), setValueName.build());

        var createInstance = this.viewBuilderHelper.newCreateInstance()
                .typeName("flow::CompositeProcessor")
                .referenceName("elements")
                .variableName("newInstance")
                .children(changeContextNewInstance.build());

        return this.diagramBuilderHelper.newNodeTool()
                .name("Composite Processor")
                .iconURLsExpression("/icons/full/obj16/System.gif")
                .body(createInstance.build())
                .build();
    }

    private NodeTool createNodeToolCreateDataSource(IViewDiagramElementFinder cache) {

        var setValueVolume = this.viewBuilderHelper.newSetValue()
                .featureName("volume")
                .valueExpression("6");
        var setValueStatus = this.viewBuilderHelper.newSetValue()
                .featureName("status")
                .valueExpression("active");
        var setValueName = this.viewBuilderHelper.newSetValue()
                .featureName("name")
                .valueExpression("aql:'DataSource' + self.eContainer().eContents()->filter(flow::DataSource)->size()");

        cache.getNodeDescription(DataSourceDescriptionProvider.NAME).ifPresent(dataSourceNodeDescription -> {
            var createView = this.diagramBuilderHelper.newCreateView()
                    .parentViewExpression("aql:selectedNode")
                    .semanticElementExpression("aql:newInstance")
                    .elementDescription(dataSourceNodeDescription);

            setValueName.children(createView.build());
        });


        var changeContextNewInstance = this.viewBuilderHelper.newChangeContext()
                .expression("aql:newInstance")
                .children(setValueVolume.build(), setValueStatus.build(), setValueName.build());

        var createInstance = this.viewBuilderHelper.newCreateInstance()
                .typeName("flow::DataSource")
                .referenceName("elements")
                .variableName("newInstance")
                .children(changeContextNewInstance.build());

        return this.diagramBuilderHelper.newNodeTool()
                .name("Data Source")
                .iconURLsExpression("/icons/full/obj16/DataSource_active.gif")
                .body(createInstance.build())
                .build();
    }

    private DropTool createDropTool(IViewDiagramElementFinder cache) {
        var changeContext = this.viewBuilderHelper.newChangeContext()
                .expression("aql:self.drop(selectedNode, diagramContext, convertedNodes)");

        return this.diagramBuilderHelper.newDropTool()
                .name("Drop Tool")
                .body(changeContext.build())
                .build();
    }

}
