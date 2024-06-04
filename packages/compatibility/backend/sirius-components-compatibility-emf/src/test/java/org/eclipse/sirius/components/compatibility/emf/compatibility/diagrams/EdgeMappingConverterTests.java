/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo and others.
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
package org.eclipse.sirius.components.compatibility.emf.compatibility.diagrams;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.api.IModelOperationHandlerSwitchProvider;
import org.eclipse.sirius.components.compatibility.api.ISemanticCandidatesProviderFactory;
import org.eclipse.sirius.components.compatibility.diagrams.EdgeMappingConverter;
import org.eclipse.sirius.components.compatibility.emf.SemanticCandidatesProvider;
import org.eclipse.sirius.components.compatibility.emf.modeloperations.ModelOperationHandlerSwitch;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
import org.eclipse.sirius.components.diagrams.LabelOverflowStrategy;
import org.eclipse.sirius.components.diagrams.LabelTextAlign;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.InsideLabelDescription;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.diagram.description.ContainerMapping;
import org.eclipse.sirius.diagram.description.DescriptionFactory;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.description.NodeMapping;
import org.junit.jupiter.api.Test;

/**
 * Unit tests of the edge mapping converter.
 *
 * @author pcdavid
 * @author sbegaudeau
 */
public class EdgeMappingConverterTests {

    /**
     * Non-regression test for the create edges issue. This test will ensure that a container description can be used as
     * a valid target for an edge.
     */
    @Test
    public void testEdgeFromNodeToContainer() {
        EdgeMapping edgeMapping = DescriptionFactory.eINSTANCE.createEdgeMapping();
        NodeMapping nodeMapping = DescriptionFactory.eINSTANCE.createNodeMapping();
        nodeMapping.setName("nodeMapping");
        ContainerMapping containerMapping = DescriptionFactory.eINSTANCE.createContainerMapping();
        containerMapping.setName("containerMapping");
        edgeMapping.getSourceMapping().add(nodeMapping);
        edgeMapping.getTargetMapping().add(containerMapping);


        String nodeMappingUUID = UUID.nameUUIDFromBytes(nodeMapping.getName().getBytes()).toString();
        String containerMappingUUID = UUID.nameUUIDFromBytes(containerMapping.getName().getBytes()).toString();
        Map<String, NodeDescription> id2NodeDescriptions = Map.of(
                nodeMappingUUID, this.createNodeDescription(nodeMappingUUID),
                containerMappingUUID, this.createNodeDescription(containerMappingUUID)
        );

        IObjectService objectService = new IObjectService.NoOp();
        IRepresentationMetadataSearchService representationMetadataSearchService = new IRepresentationMetadataSearchService.NoOp();
        IIdentifierProvider identifierProvider = new IIdentifierProvider.NoOp() {
            @Override
            public String getIdentifier(Object element) {
                return containerMappingUUID;
            }
        };
        ISemanticCandidatesProviderFactory semanticCandidatesProviderFactory = SemanticCandidatesProvider::new;
        IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider = interpeter -> new ModelOperationHandlerSwitch(objectService, representationMetadataSearchService, identifierProvider,
                List.of(), interpeter);
        EdgeMappingConverter edgeMappingConverter = new EdgeMappingConverter(new IObjectService.NoOp(), new IEditService.NoOp(), identifierProvider, semanticCandidatesProviderFactory,
                modelOperationHandlerSwitchProvider);

        EdgeDescription edgeDescription = edgeMappingConverter.convert(edgeMapping, new AQLInterpreter(List.of(), List.of()), id2NodeDescriptions);
        assertThat(edgeDescription.getTargetNodeDescriptions()).contains(id2NodeDescriptions.get(containerMappingUUID));
    }

    private NodeDescription createNodeDescription(String id) {
        LabelStyleDescription styleDescription = LabelStyleDescription.newLabelStyleDescription()
                .colorProvider(variableManager -> "")
                .fontSizeProvider(variableManager -> 0)
                .boldProvider(variableManager -> false)
                .italicProvider(variableManager -> false)
                .underlineProvider(variableManager -> false)
                .strikeThroughProvider(variableManager -> false)
                .iconURLProvider(variableManager -> List.of())
                .backgroundProvider(variableManager -> "transparent")
                .borderColorProvider(variableManager -> "black")
                .borderRadiusProvider(variableManager -> 0)
                .borderSizeProvider(variableManager -> 0)
                .borderStyleProvider(variableManager -> LineStyle.Solid)
                .build();

        InsideLabelDescription insideLabelDescription = InsideLabelDescription.newInsideLabelDescription(id)
                .idProvider(variableManager -> "")
                .textProvider(variableManager -> "")
                .styleDescriptionProvider(variableManager -> styleDescription)
                .isHeaderProvider(vm -> false)
                .displayHeaderSeparatorProvider(vm -> false)
                .insideLabelLocation(InsideLabelLocation.TOP_CENTER)
                .overflowStrategy(LabelOverflowStrategy.NONE)
                .textAlign(LabelTextAlign.CENTER)
                .build();

        return NodeDescription.newNodeDescription(id)
                .typeProvider(variableManager -> "")
                .targetObjectIdProvider(variableManager -> "")
                .targetObjectKindProvider(variableManager -> "")
                .targetObjectLabelProvider(variableManager -> "")
                .semanticElementsProvider(variableManager -> List.of())
                .insideLabelDescription(insideLabelDescription)
                .styleProvider(variableManager -> null)
                .childrenLayoutStrategyProvider(variableManager -> new FreeFormLayoutStrategy())
                .sizeProvider(variableManager -> Size.UNDEFINED)
                .borderNodeDescriptions(List.of())
                .childNodeDescriptions(List.of())
                .labelEditHandler((variableManager, newLabel) -> new Success())
                .deleteHandler(variableManager -> new Success())
                .build();
    }

    /**
     * Non-regression test for the create edges issue. This test will ensure that a container description can be used as
     * a valid source for an edge.
     */
    @Test
    public void testEdgeFromContainerToNode() {
        EdgeMapping edgeMapping = DescriptionFactory.eINSTANCE.createEdgeMapping();
        NodeMapping nodeMapping = DescriptionFactory.eINSTANCE.createNodeMapping();
        nodeMapping.setName("nodeMapping");
        ContainerMapping containerMapping = DescriptionFactory.eINSTANCE.createContainerMapping();
        containerMapping.setName("containerMapping");
        edgeMapping.getSourceMapping().add(containerMapping);
        edgeMapping.getTargetMapping().add(nodeMapping);

        String nodeMappingUUID = UUID.nameUUIDFromBytes(nodeMapping.getName().getBytes()).toString();
        String containerMappingUUID = UUID.nameUUIDFromBytes(containerMapping.getName().getBytes()).toString();
        Map<String, NodeDescription> id2NodeDescriptions = Map.of(
                nodeMappingUUID, this.createNodeDescription(nodeMappingUUID),
                containerMappingUUID, this.createNodeDescription(containerMappingUUID)
        );

        IObjectService objectService = new IObjectService.NoOp();
        IRepresentationMetadataSearchService representationMetadataSearchService = new IRepresentationMetadataSearchService.NoOp();
        IIdentifierProvider identifierProvider = new IIdentifierProvider.NoOp() {
            @Override
            public String getIdentifier(Object element) {
                return containerMappingUUID;
            }
        };
        ISemanticCandidatesProviderFactory semanticCandidatesProviderFactory = SemanticCandidatesProvider::new;

        IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider = interpeter -> new ModelOperationHandlerSwitch(objectService, representationMetadataSearchService, identifierProvider,
                List.of(), interpeter);
        EdgeMappingConverter edgeMappingConverter = new EdgeMappingConverter(objectService, new IEditService.NoOp(), identifierProvider, semanticCandidatesProviderFactory,
                modelOperationHandlerSwitchProvider);

        EdgeDescription edgeDescription = edgeMappingConverter.convert(edgeMapping, new AQLInterpreter(List.of(), List.of()), id2NodeDescriptions);
        assertThat(edgeDescription.getSourceNodeDescriptions()).contains(id2NodeDescriptions.get(containerMappingUUID));
    }

    /**
     * Non-regression test for the create edges issue. This test will ensure that a container description can be used as
     * both a valid source and a valid target for an edge.
     */
    @Test
    public void testEdgeFromContainerToContainer() {
        EdgeMapping edgeMapping = DescriptionFactory.eINSTANCE.createEdgeMapping();
        ContainerMapping sourceContainerMapping = DescriptionFactory.eINSTANCE.createContainerMapping();
        sourceContainerMapping.setName("sourceContainerMapping");
        ContainerMapping targetContainerMapping = DescriptionFactory.eINSTANCE.createContainerMapping();
        targetContainerMapping.setName("targetContainerMapping");
        edgeMapping.getSourceMapping().add(sourceContainerMapping);
        edgeMapping.getTargetMapping().add(targetContainerMapping);

        String sourceContainerMappingUUID = UUID.nameUUIDFromBytes(sourceContainerMapping.getName().getBytes()).toString();
        String targetContainerMappingUUID = UUID.nameUUIDFromBytes(targetContainerMapping.getName().getBytes()).toString();
        Map<String, NodeDescription> id2NodeDescriptions = Map.of(
                sourceContainerMappingUUID, this.createNodeDescription(sourceContainerMappingUUID),
                targetContainerMappingUUID, this.createNodeDescription(targetContainerMappingUUID)
        );

        IObjectService objectService = new IObjectService.NoOp();
        IRepresentationMetadataSearchService representationMetadataSearchService = new IRepresentationMetadataSearchService.NoOp();
        IIdentifierProvider identifierProvider = new IIdentifierProvider.NoOp() {
            @Override
            public String getIdentifier(Object element) {
                return targetContainerMappingUUID;
            }
        };
        ISemanticCandidatesProviderFactory semanticCandidatesProviderFactory = SemanticCandidatesProvider::new;
        IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider = interpeter -> new ModelOperationHandlerSwitch(objectService, representationMetadataSearchService, identifierProvider,
                List.of(), interpeter);
        EdgeMappingConverter edgeMappingConverter = new EdgeMappingConverter(objectService, new IEditService.NoOp(), identifierProvider, semanticCandidatesProviderFactory,
                modelOperationHandlerSwitchProvider);

        EdgeDescription edgeDescription = edgeMappingConverter.convert(edgeMapping, new AQLInterpreter(List.of(), List.of()), id2NodeDescriptions);
        edgeDescription.getSourceNodeDescriptions().contains(id2NodeDescriptions.get(sourceContainerMappingUUID));
        assertThat(edgeDescription.getTargetNodeDescriptions()).contains(id2NodeDescriptions.get(targetContainerMappingUUID));
    }
}
