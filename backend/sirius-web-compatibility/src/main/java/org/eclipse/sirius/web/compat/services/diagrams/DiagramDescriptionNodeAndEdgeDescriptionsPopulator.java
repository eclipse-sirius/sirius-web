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
package org.eclipse.sirius.web.compat.services.diagrams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.sirius.diagram.business.internal.metamodel.helper.LayerHelper;
import org.eclipse.sirius.diagram.description.AdditionalLayer;
import org.eclipse.sirius.diagram.description.ContainerMappingImport;
import org.eclipse.sirius.diagram.description.DiagramDescription;
import org.eclipse.sirius.diagram.description.Layer;
import org.eclipse.sirius.web.compat.diagrams.AbstractNodeMappingConverter;
import org.eclipse.sirius.web.compat.diagrams.EdgeMappingConverter;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription.Builder;
import org.eclipse.sirius.web.diagrams.description.EdgeDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.springframework.stereotype.Service;

/**
 * Used to populate both the nodeDescriptions, the edgeDescriptions and the tool sections of the diagram description
 * builder.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramDescriptionNodeAndEdgeDescriptionsPopulator implements IDiagramDescriptionPopulator {

    private final IToolProvider toolProvider;

    private final AbstractNodeMappingConverter abstractNodeMappingConverter;

    private final EdgeMappingConverter edgeMappingConverter;

    public DiagramDescriptionNodeAndEdgeDescriptionsPopulator(IToolProvider toolProvider, AbstractNodeMappingConverter abstractNodeMappingConverter, EdgeMappingConverter edgeMappingConverter) {
        this.toolProvider = Objects.requireNonNull(toolProvider);
        this.abstractNodeMappingConverter = Objects.requireNonNull(abstractNodeMappingConverter);
        this.edgeMappingConverter = Objects.requireNonNull(edgeMappingConverter);
    }

    @Override
    public Builder populate(Builder builder, DiagramDescription siriusDiagramDescription, AQLInterpreter interpreter) {
        Map<UUID, NodeDescription> id2NodeDescriptions = new HashMap<>();

        // @formatter:off
        List<Layer> layers = LayerHelper.getAllLayers(siriusDiagramDescription).stream()
                .filter(this::isEnabledByDefault)
                .collect(Collectors.toList());

        List<NodeDescription> containerDescriptions = layers.stream()
                .flatMap(layer -> layer.getContainerMappings().stream().filter(containerMapping -> !(containerMapping instanceof ContainerMappingImport)))
                .map(containerMapping -> this.abstractNodeMappingConverter.convert(containerMapping, interpreter, id2NodeDescriptions))
                .collect(Collectors.toList());
        // @formatter:on

        // @formatter:off
        List<NodeDescription> nodeDescriptions = layers.stream()
                .flatMap(layer -> layer.getNodeMappings().stream())
                .map(nodeMapping -> this.abstractNodeMappingConverter.convert(nodeMapping, interpreter, id2NodeDescriptions))
                .collect(Collectors.toList());
        // @formatter:on
        nodeDescriptions.addAll(containerDescriptions);

        // @formatter:off
        List<EdgeDescription> edgeDescriptions = layers.stream()
                .flatMap(layer -> layer.getEdgeMappings().stream())
                .map(edgeMapping -> this.edgeMappingConverter.convert(edgeMapping, interpreter, id2NodeDescriptions))
                .collect(Collectors.toList());

        return builder.nodeDescriptions(nodeDescriptions)
                .edgeDescriptions(edgeDescriptions)
                .toolSections(this.toolProvider.getToolSections(id2NodeDescriptions, edgeDescriptions, siriusDiagramDescription, layers))
                .unsynchronizedDiagramElementsDescriptionIds(this.getUnsynchronizedDiagramElementsDescriptionIds(nodeDescriptions, edgeDescriptions));
        // @formatter:on
    }

    private boolean isEnabledByDefault(Layer layer) {
        if (layer instanceof AdditionalLayer) {
            AdditionalLayer additionalLayer = (AdditionalLayer) layer;
            return !additionalLayer.isOptional() || additionalLayer.isActiveByDefault();
        }
        return true;
    }

    private List<UUID> getUnsynchronizedDiagramElementsDescriptionIds(List<NodeDescription> nodeDescriptions, List<EdgeDescription> edgeDescriptions) {
        List<UUID> unsynchronizedDiagramElementsDescriptionIds = new ArrayList<>();

        // @formatter:off
        List<UUID> unsynchronizedNodeDescriptionsIds = nodeDescriptions.stream()
                .filter(nodeDesc -> SynchronizationPolicy.UNSYNCHRONIZED.equals(nodeDesc.getSynchronizationPolicy()))
                .map(NodeDescription::getId)
                .collect(Collectors.toList());
        List<UUID> unsynchronizedEdgeDescriptionsIds = edgeDescriptions.stream()
                .filter(edgeDesc -> SynchronizationPolicy.UNSYNCHRONIZED.equals(edgeDesc.getSynchronizationPolicy()))
                .map(EdgeDescription::getId)
                .collect(Collectors.toList());
        // @formatter:on

        unsynchronizedDiagramElementsDescriptionIds.addAll(unsynchronizedNodeDescriptionsIds);
        unsynchronizedDiagramElementsDescriptionIds.addAll(unsynchronizedEdgeDescriptionsIds);

        return unsynchronizedDiagramElementsDescriptionIds;
    }

}
