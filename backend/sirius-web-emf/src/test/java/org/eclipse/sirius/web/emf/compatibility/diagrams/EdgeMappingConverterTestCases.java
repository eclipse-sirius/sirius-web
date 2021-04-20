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
package org.eclipse.sirius.web.emf.compatibility.diagrams;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.diagram.description.ContainerMapping;
import org.eclipse.sirius.diagram.description.DescriptionFactory;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.description.NodeMapping;
import org.eclipse.sirius.web.compat.api.IIdentifierProvider;
import org.eclipse.sirius.web.compat.api.IModelOperationHandlerSwitchProvider;
import org.eclipse.sirius.web.compat.api.ISemanticCandidatesProviderFactory;
import org.eclipse.sirius.web.compat.diagrams.EdgeMappingConverter;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.description.EdgeDescription;
import org.eclipse.sirius.web.diagrams.description.LabelDescription;
import org.eclipse.sirius.web.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.emf.compatibility.SemanticCandidatesProvider;
import org.eclipse.sirius.web.emf.compatibility.modeloperations.ModelOperationHandlerSwitch;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.Status;
import org.junit.Test;

/**
 * Unit tests of the edge mapping converter.
 *
 * @author pcdavid
 * @author sbegaudeau
 */
public class EdgeMappingConverterTestCases {

    /**
     * Non-regression test for the create edges issue. This test will ensure that a container description can be used as
     * a valid target for an edge.
     */
    @Test
    public void testEdgeFromNodeToContainer() {
        EdgeMapping edgeMapping = DescriptionFactory.eINSTANCE.createEdgeMapping();
        NodeMapping nodeMapping = DescriptionFactory.eINSTANCE.createNodeMapping();
        nodeMapping.setName("nodeMapping"); //$NON-NLS-1$
        ContainerMapping containerMapping = DescriptionFactory.eINSTANCE.createContainerMapping();
        containerMapping.setName("containerMapping"); //$NON-NLS-1$
        edgeMapping.getSourceMapping().add(nodeMapping);
        edgeMapping.getTargetMapping().add(containerMapping);

        // @formatter:off
        UUID nodeMappingUUID = UUID.nameUUIDFromBytes(nodeMapping.getName().getBytes());
        UUID containerMappingUUID = UUID.nameUUIDFromBytes(containerMapping.getName().getBytes());
        Map<UUID, NodeDescription> id2NodeDescriptions = Map.of(
                nodeMappingUUID, this.createNodeDescription(nodeMappingUUID),
                containerMappingUUID, this.createNodeDescription(containerMappingUUID)
        );
        // @formatter:on

        IIdentifierProvider identifierProvider = element -> containerMappingUUID.toString();
        ISemanticCandidatesProviderFactory semanticCandidatesProviderFactory = SemanticCandidatesProvider::new;
        IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider = ModelOperationHandlerSwitch::new;
        EdgeMappingConverter edgeMappingConverter = new EdgeMappingConverter(new NoOpObjectService(), new NoOpEditService(), identifierProvider, semanticCandidatesProviderFactory,
                modelOperationHandlerSwitchProvider);

        EdgeDescription edgeDescription = edgeMappingConverter.convert(edgeMapping, new AQLInterpreter(List.of(), List.of()), id2NodeDescriptions);
        assertThat(edgeDescription.getTargetNodeDescriptions()).contains(id2NodeDescriptions.get(containerMappingUUID));
    }

    private NodeDescription createNodeDescription(UUID id) {
        // @formatter:off
        LabelStyleDescription styleDescription = LabelStyleDescription.newLabelStyleDescription()
                .colorProvider(variableManager -> "") //$NON-NLS-1$
                .fontSizeProvider(variableManager -> 0)
                .boldProvider(variableManager -> false)
                .italicProvider(variableManager -> false)
                .underlineProvider(variableManager -> false)
                .strikeThroughProvider(variableManager -> false)
                .iconURLProvider(variableManager -> "") //$NON-NLS-1$
                .build();

        LabelDescription labelDescription = LabelDescription.newLabelDescription(id.toString())
                .idProvider(variableManager -> "") //$NON-NLS-1$
                .textProvider(variableManager -> "") //$NON-NLS-1$
                .styleDescriptionProvider(variableManager -> styleDescription)
                .build();

        return NodeDescription.newNodeDescription(id)
                .typeProvider(variableManager -> "") //$NON-NLS-1$
                .targetObjectIdProvider(variableManager -> "") //$NON-NLS-1$
                .targetObjectKindProvider(variableManager -> "") //$NON-NLS-1$
                .targetObjectLabelProvider(variableManager -> "")//$NON-NLS-1$
                .semanticElementsProvider(variableManager -> List.of())
                .labelDescription(labelDescription)
                .styleProvider(variableManager -> null)
                .sizeProvider(variableManager -> Size.UNDEFINED)
                .borderNodeDescriptions(List.of())
                .childNodeDescriptions(List.of())
                .labelEditHandler((variableManager, newLabel) -> Status.OK)
                .deleteHandler(variableManager -> Status.OK)
                .build();
        // @formatter:on
    }

    /**
     * Non-regression test for the create edges issue. This test will ensure that a container description can be used as
     * a valid source for an edge.
     */
    @Test
    public void testEdgeFromContainerToNode() {
        EdgeMapping edgeMapping = DescriptionFactory.eINSTANCE.createEdgeMapping();
        NodeMapping nodeMapping = DescriptionFactory.eINSTANCE.createNodeMapping();
        nodeMapping.setName("nodeMapping"); //$NON-NLS-1$
        ContainerMapping containerMapping = DescriptionFactory.eINSTANCE.createContainerMapping();
        containerMapping.setName("containerMapping"); //$NON-NLS-1$
        edgeMapping.getSourceMapping().add(containerMapping);
        edgeMapping.getTargetMapping().add(nodeMapping);

        // @formatter:off
        UUID nodeMappingUUID = UUID.nameUUIDFromBytes(nodeMapping.getName().getBytes());
        UUID containerMappingUUID = UUID.nameUUIDFromBytes(containerMapping.getName().getBytes());
        Map<UUID, NodeDescription> id2NodeDescriptions = Map.of(
                nodeMappingUUID, this.createNodeDescription(nodeMappingUUID),
                containerMappingUUID, this.createNodeDescription(containerMappingUUID)
        );
        // @formatter:on
        IIdentifierProvider identifierProvider = element -> containerMappingUUID.toString();
        ISemanticCandidatesProviderFactory semanticCandidatesProviderFactory = SemanticCandidatesProvider::new;
        IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider = ModelOperationHandlerSwitch::new;
        EdgeMappingConverter edgeMappingConverter = new EdgeMappingConverter(new NoOpObjectService(), new NoOpEditService(), identifierProvider, semanticCandidatesProviderFactory,
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
        sourceContainerMapping.setName("sourceContainerMapping"); //$NON-NLS-1$
        ContainerMapping targetContainerMapping = DescriptionFactory.eINSTANCE.createContainerMapping();
        targetContainerMapping.setName("targetContainerMapping"); //$NON-NLS-1$
        edgeMapping.getSourceMapping().add(sourceContainerMapping);
        edgeMapping.getTargetMapping().add(targetContainerMapping);

        // @formatter:off
        UUID sourceContainerMappingUUID = UUID.nameUUIDFromBytes(sourceContainerMapping.getName().getBytes());
        UUID targetContainerMappingUUID = UUID.nameUUIDFromBytes(targetContainerMapping.getName().getBytes());
        Map<UUID, NodeDescription> id2NodeDescriptions = Map.of(
                sourceContainerMappingUUID, this.createNodeDescription(sourceContainerMappingUUID),
                targetContainerMappingUUID, this.createNodeDescription(targetContainerMappingUUID)
        );
        // @formatter:on
        IIdentifierProvider identifierProvider = element -> targetContainerMappingUUID.toString();
        ISemanticCandidatesProviderFactory semanticCandidatesProviderFactory = SemanticCandidatesProvider::new;
        IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider = ModelOperationHandlerSwitch::new;
        EdgeMappingConverter edgeMappingConverter = new EdgeMappingConverter(new NoOpObjectService(), new NoOpEditService(), identifierProvider, semanticCandidatesProviderFactory,
                modelOperationHandlerSwitchProvider);

        EdgeDescription edgeDescription = edgeMappingConverter.convert(edgeMapping, new AQLInterpreter(List.of(), List.of()), id2NodeDescriptions);
        edgeDescription.getSourceNodeDescriptions().contains(id2NodeDescriptions.get(sourceContainerMappingUUID));
        assertThat(edgeDescription.getTargetNodeDescriptions()).contains(id2NodeDescriptions.get(targetContainerMappingUUID));
    }
}
