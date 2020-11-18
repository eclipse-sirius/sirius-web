/***********************************************************************************************
 * Copyright (c) 2019, 2020 Obeo. All Rights Reserved.
 * This software and the attached documentation are the exclusive ownership
 * of its authors and was conceded to the profit of Obeo SARL.
 * This software and the attached documentation are protected under the rights
 * of intellectual ownership, including the section "Titre II  Droits des auteurs (Articles L121-1 L123-12)"
 * By installing this software, you acknowledge being aware of this rights and
 * accept them, and as a consequence you must:
 * - be in possession of a valid license of use conceded by Obeo only.
 * - agree that you have read, understood, and will comply with the license terms and conditions.
 * - agree not to do anything that could conflict with intellectual ownership owned by Obeo or its beneficiaries
 * or the authors of this software
 *
 * Should you not agree with these terms, you must stop to use this software and give it back to its legitimate owner.
 ***********************************************************************************************/
package org.eclipse.sirius.web.freediagram.flow;

import fr.obeo.dsl.designer.sample.flow.DataFlow;
import fr.obeo.dsl.designer.sample.flow.DataSource;
import fr.obeo.dsl.designer.sample.flow.Fan;
import fr.obeo.dsl.designer.sample.flow.FlowElementUsage;
import fr.obeo.dsl.designer.sample.flow.FlowPackage;
import fr.obeo.dsl.designer.sample.flow.Processor;
import fr.obeo.dsl.designer.sample.flow.System;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.diagrams.tools.CreateEdgeTool;
import org.eclipse.sirius.web.diagrams.tools.CreateNodeTool;
import org.eclipse.sirius.web.diagrams.tools.EdgeCandidate;
import org.eclipse.sirius.web.diagrams.tools.ITool;
import org.eclipse.sirius.web.diagrams.tools.ToolSection;
import org.eclipse.sirius.web.freediagram.flow.behaviors.DataFlowBehavior;
import org.eclipse.sirius.web.freediagram.flow.behaviors.DataSourceBehavior;
import org.eclipse.sirius.web.freediagram.flow.behaviors.FanBehavior;
import org.eclipse.sirius.web.freediagram.flow.behaviors.ProcessorBehavior;
import org.eclipse.sirius.web.freediagram.flow.behaviors.SystemBehavior;
import org.eclipse.sirius.web.freediagram.flow.styles.nodes.FlowElementStyleProvider;
import org.eclipse.sirius.web.freediagram.services.EdgeDescriptionService;
import org.eclipse.sirius.web.freediagram.services.FreeDiagramService;
import org.eclipse.sirius.web.freediagram.tools.DropToolSupplier;
import org.eclipse.sirius.web.freediagram.tools.EdgeToolSupplier;
import org.eclipse.sirius.web.freediagram.tools.MagicEdgeToolSupplier;
import org.eclipse.sirius.web.freediagram.tools.NodeToolSupplier;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.services.api.objects.IObjectService;

/**
 * Free diagram edge descriptions contribution.
 *
 * @author hmarchadour
 */
public class ToolSectionsSupplier implements Supplier<List<ToolSection>> {

    private static final String PROCESSOR = "Processor"; //$NON-NLS-1$

    private static final String FAN = "Fan"; //$NON-NLS-1$

    private final NodeDescription systemNodeDescription;

    private final NodeDescription processorNodeDescription;

    private final NodeDescription dataSourceNodeDescription;

    private final NodeDescription fanNodeDescription;

    private final DataFlowBehavior dataFlowBehavior;

    private final FreeDiagramService freeDiagramService;

    private final IObjectService objectService;

    public ToolSectionsSupplier(FreeDiagramService freeDiagramService, IObjectService objectService, EdgeDescriptionService edgeDescriptionService, NodeDescription systemNodeDescription,
            NodeDescription processorNodeDescription, NodeDescription fanNodeDescription, NodeDescription dataSourceNodeDescription) {
        this.freeDiagramService = Objects.requireNonNull(freeDiagramService);
        this.objectService = Objects.requireNonNull(objectService);
        this.systemNodeDescription = Objects.requireNonNull(systemNodeDescription);
        this.processorNodeDescription = Objects.requireNonNull(processorNodeDescription);
        this.dataSourceNodeDescription = Objects.requireNonNull(dataSourceNodeDescription);
        this.fanNodeDescription = Objects.requireNonNull(fanNodeDescription);
        this.dataFlowBehavior = new DataFlowBehavior();
    }

    @Override
    public List<ToolSection> get() {
        List<ToolSection> toolSections = new ArrayList<>();
        toolSections.addAll(this.createNodeToolSection());
        toolSections.add(this.createEdgeToolSection());
        toolSections.add(this.createDropToolSection());
        return toolSections;
    }

    private List<ToolSection> createNodeToolSection() {
        CreateNodeTool createCompositeProcessorTool = new NodeToolSupplier(this.getImageURL(FlowPackage.Literals.SYSTEM), "Composite Processor", this.systemNodeDescription, //$NON-NLS-1$
                List.of(this.systemNodeDescription), this.freeDiagramService, new SystemBehavior(), true).get();
        CreateNodeTool createFanTool = new NodeToolSupplier(this.getImageURL(FlowPackage.Literals.FAN), FAN, this.fanNodeDescription, List.of(this.systemNodeDescription), this.freeDiagramService,
                new FanBehavior(), true).get();
        CreateNodeTool createProcessorTool = new NodeToolSupplier(this.getImageURL(FlowPackage.Literals.PROCESSOR), PROCESSOR, this.processorNodeDescription, List.of(this.systemNodeDescription),
                this.freeDiagramService, new ProcessorBehavior(), true).get();
        // @formatter:off
        return List.of(
                ToolSection.newToolSection(PROCESSOR)
                .tools(List.of(createProcessorTool))
                .label(PROCESSOR)
                .imageURL("") //$NON-NLS-1$
                .build(),
                ToolSection.newToolSection("CompositeProcessor") //$NON-NLS-1$
                .tools(List.of(createCompositeProcessorTool))
                .label("CompositeProcessor") //$NON-NLS-1$
                .imageURL("") //$NON-NLS-1$
                .build(),
                this.createDataSourceToolSection(this.dataSourceNodeDescription, List.of(this.systemNodeDescription)),
                ToolSection.newToolSection(FAN)
                .tools(List.of(createFanTool))
                .label(FAN)
                .imageURL("") //$NON-NLS-1$
                .build()
        );
        // @formatter:on
    }

    private String getImageURL(EClass eClass) {
        return this.objectService.getImagePath(EcoreUtil.create(eClass));
    }

    private ToolSection createEdgeToolSection() {
        // @formatter:off
        EdgeCandidate processorToProcessorEdgeCandidate = EdgeCandidate.newEdgeCandidate()
            .sources(List.of(this.processorNodeDescription))
            .targets(List.of(this.processorNodeDescription))
            .build();
        EdgeCandidate dataSourceToProcessorEdgeCandidate = EdgeCandidate.newEdgeCandidate()
            .sources(List.of(this.dataSourceNodeDescription))
            .targets(List.of(this.processorNodeDescription))
            .build();
        EdgeCandidate processor2DataSourceEdgeCandidate = EdgeCandidate.newEdgeCandidate()
            .sources(List.of(this.processorNodeDescription))
            .targets(List.of(this.dataSourceNodeDescription))
            .build();
        // @formatter:on
        List<EdgeCandidate> edgeCandidates = List.of(processorToProcessorEdgeCandidate, dataSourceToProcessorEdgeCandidate, processor2DataSourceEdgeCandidate);
        EdgeToolSupplier dataFlowEdgeToolSupplier = new EdgeToolSupplier(FlowPackage.Literals.DATA_FLOW, "Flow", this.objectService, edgeCandidates, this.dataFlowBehavior); //$NON-NLS-1$

        DataFlowBehavior customDataFlowBehavior = new DataFlowBehavior() {
            @Override
            public Optional<EObject> createEObject(VariableManager variableManager) {
                return super.createEObject(variableManager).map(eObject -> {
                    ((DataFlow) eObject).setUsage(FlowElementUsage.HIGH);
                    return eObject;
                });
            }
        };
        EdgeToolSupplier dataFlowEdgeToolSupplier2 = new EdgeToolSupplier(FlowPackage.Literals.DATA_FLOW, "Flow High", this.objectService, edgeCandidates, customDataFlowBehavior); //$NON-NLS-1$

        List<CreateEdgeTool> createEdgeTools = new ArrayList<>();
        createEdgeTools.add(dataFlowEdgeToolSupplier.get());
        createEdgeTools.add(dataFlowEdgeToolSupplier2.get());
        MagicEdgeToolSupplier magicEdgeToolSupplier = new MagicEdgeToolSupplier(createEdgeTools);
        List<ITool> result = new ArrayList<>();
        result.add(magicEdgeToolSupplier.get());
        result.addAll(createEdgeTools);
        // @formatter:off
        return ToolSection.newToolSection("EdgeTools") //$NON-NLS-1$
                .tools(result)
                .label("Edge Tools") //$NON-NLS-1$
                .imageURL("") //$NON-NLS-1$
                .build();
        // @formatter:on
    }

    private ToolSection createDataSourceToolSection(NodeDescription nodeDescription, List<NodeDescription> targetDescriptions) {

        CreateNodeTool createDataSourceTool = new NodeToolSupplier(this.getImageURL(FlowPackage.Literals.DATA_SOURCE), "Data Source", nodeDescription, targetDescriptions, //$NON-NLS-1$
                this.freeDiagramService, new DataSourceBehavior(), true).get();
        CreateNodeTool createCameraTool = new NodeToolSupplier(FlowElementStyleProvider.CAMERA_IMAGE_PATH, FlowElementStyleProvider.CAMERA, nodeDescription, targetDescriptions,
                this.freeDiagramService, new DataSourceBehavior(FlowElementStyleProvider.CAMERA), true).get();
        CreateNodeTool createRadarTool = new NodeToolSupplier(FlowElementStyleProvider.RADAR_IMAGE_PATH, FlowElementStyleProvider.RADAR, nodeDescription, targetDescriptions, this.freeDiagramService,
                new DataSourceBehavior(FlowElementStyleProvider.RADAR), true).get();
        CreateNodeTool createWifiTool = new NodeToolSupplier(FlowElementStyleProvider.ANTENNA_IMAGE_PATH, FlowElementStyleProvider.WIFI, nodeDescription, targetDescriptions, this.freeDiagramService,
                new DataSourceBehavior(FlowElementStyleProvider.WIFI), true).get();

        // @formatter:off
        return ToolSection.newToolSection("DataSource") //$NON-NLS-1$
            .tools(List.of(createRadarTool, createWifiTool, createCameraTool, createDataSourceTool))
            .label("DataSource") //$NON-NLS-1$
            .imageURL("") //$NON-NLS-1$
            .build();
        // @formatter:on
    }

    private ToolSection createDropToolSection() {
        Map<Class<?>, NodeDescription> nodeDescriptionMap = new HashMap<>();
        nodeDescriptionMap.put(System.class, this.systemNodeDescription);
        nodeDescriptionMap.put(Processor.class, this.processorNodeDescription);
        nodeDescriptionMap.put(Fan.class, this.fanNodeDescription);
        nodeDescriptionMap.put(DataSource.class, this.dataSourceNodeDescription);

        DropToolSupplier dropToolSupplier = new DropToolSupplier(UUID.randomUUID().toString(), this.freeDiagramService, List.of(this.systemNodeDescription), true, nodeDescriptionMap);
        // @formatter:off
        return ToolSection.newToolSection("DropTools") //$NON-NLS-1$
                .tools(
                        List.of(dropToolSupplier.get())
                )
                .label("Drop Tools") //$NON-NLS-1$
                .imageURL("") //$NON-NLS-1$
                .build();
        // @formatter:on
    }

}
