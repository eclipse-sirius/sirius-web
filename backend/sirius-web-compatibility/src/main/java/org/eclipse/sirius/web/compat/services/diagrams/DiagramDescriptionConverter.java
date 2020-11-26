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
package org.eclipse.sirius.web.compat.services.diagrams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.sirius.diagram.business.internal.metamodel.helper.LayerHelper;
import org.eclipse.sirius.diagram.description.AdditionalLayer;
import org.eclipse.sirius.diagram.description.ContainerMappingImport;
import org.eclipse.sirius.diagram.description.Layer;
import org.eclipse.sirius.web.compat.diagrams.CanCreateDiagramPredicate;
import org.eclipse.sirius.web.compat.diagrams.ContainerMappingConverter;
import org.eclipse.sirius.web.compat.diagrams.DiagramLabelProvider;
import org.eclipse.sirius.web.compat.diagrams.EdgeMappingConverter;
import org.eclipse.sirius.web.compat.diagrams.NodeMappingConverter;
import org.eclipse.sirius.web.compat.services.diagrams.api.IDiagramDescriptionConverter;
import org.eclipse.sirius.web.compat.services.representations.AQLInterpreterFactory;
import org.eclipse.sirius.web.compat.services.representations.IdentifiedElementLabelProvider;
import org.eclipse.sirius.web.compat.services.representations.IdentifierProvider;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.description.EdgeDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.services.api.objects.IEditService;
import org.eclipse.sirius.web.services.api.objects.IObjectService;
import org.springframework.stereotype.Service;

/**
 * This class is used to convert a Sirius DiagramDescription to an Sirius Web DiagramDescription.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramDescriptionConverter implements IDiagramDescriptionConverter {

    private final IObjectService objectService;

    private final AQLInterpreterFactory interpreterFactory;

    private final IEditService editService;

    private final IdentifierProvider identifierProvider;

    private final IdentifiedElementLabelProvider identifiedElementLabelProvider;

    private final IToolProvider toolProvider;

    public DiagramDescriptionConverter(IObjectService objectService, IEditService editService, AQLInterpreterFactory interpreterFactory, IdentifierProvider identifierProvider,
            IdentifiedElementLabelProvider identifiedElementLabelProvider, IToolProvider toolProvider) {
        this.objectService = Objects.requireNonNull(objectService);
        this.interpreterFactory = Objects.requireNonNull(interpreterFactory);
        this.editService = Objects.requireNonNull(editService);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.identifiedElementLabelProvider = Objects.requireNonNull(identifiedElementLabelProvider);
        this.toolProvider = Objects.requireNonNull(toolProvider);
    }

    @Override
    public DiagramDescription convert(org.eclipse.sirius.diagram.description.DiagramDescription siriusDiagramDescription) {
        AQLInterpreter interpreter = this.interpreterFactory.create(siriusDiagramDescription);
        Function<VariableManager, String> labelProvider = new DiagramLabelProvider(interpreter, siriusDiagramDescription);

        Map<UUID, NodeDescription> id2NodeDescriptions = new HashMap<>();

        // @formatter:off
        ContainerMappingConverter containerMappingConverter = new ContainerMappingConverter(this.objectService, this.editService, interpreter, this.identifierProvider);
        List<Layer> layers = LayerHelper.getAllLayers(siriusDiagramDescription).stream()
                .filter(this::isEnabledByDefault)
                .collect(Collectors.toList());

        List<NodeDescription> containerDescriptions = layers.stream()
                .flatMap(layer -> layer.getContainerMappings().stream().filter(containerMapping -> !(containerMapping instanceof ContainerMappingImport)))
                .map(containerMapping -> containerMappingConverter.convert(containerMapping, id2NodeDescriptions))
                .collect(Collectors.toList());
        // @formatter:on

        // @formatter:off
        NodeMappingConverter nodeMappingConverter = new NodeMappingConverter(this.objectService, this.editService, interpreter, this.identifierProvider);
        List<NodeDescription> nodeDescriptions = layers.stream()
                .flatMap(layer -> layer.getNodeMappings().stream())
                .map(nodeMapping -> nodeMappingConverter.convert(nodeMapping, id2NodeDescriptions))
                .collect(Collectors.toList());
        // @formatter:on
        nodeDescriptions.addAll(containerDescriptions);

        // @formatter:off
        EdgeMappingConverter edgeMappingConverter = new EdgeMappingConverter(this.objectService, this.editService, interpreter, this.identifierProvider, id2NodeDescriptions);
        List<EdgeDescription> edgeDescriptions = layers.stream()
                .flatMap(layer -> layer.getEdgeMappings().stream())
                .map(edgeMappingConverter::convert)
                .collect(Collectors.toList());
        // @formatter:on

        Function<VariableManager, String> targetObjectIdProvider = variableManager -> {
            Object object = variableManager.getVariables().get(VariableManager.SELF);
            return Optional.ofNullable(object).map(this.objectService::getId).orElse(null);
        };

        Predicate<VariableManager> canCreatePredicate = new CanCreateDiagramPredicate(siriusDiagramDescription, interpreter);

        // @formatter:off
        return DiagramDescription.newDiagramDescription(UUID.fromString(this.identifierProvider.getIdentifier(siriusDiagramDescription)))
                .label(this.identifiedElementLabelProvider.getLabel(siriusDiagramDescription))
                .targetObjectIdProvider(targetObjectIdProvider)
                .canCreatePredicate(canCreatePredicate)
                .labelProvider(labelProvider)
                .nodeDescriptions(nodeDescriptions)
                .edgeDescriptions(edgeDescriptions)
                .toolSections(this.toolProvider.getToolSections(id2NodeDescriptions, edgeDescriptions, siriusDiagramDescription, layers))
                .build();
        // @formatter:on
    }

    private boolean isEnabledByDefault(Layer layer) {
        if (layer instanceof AdditionalLayer) {
            AdditionalLayer additionalLayer = (AdditionalLayer) layer;
            return !additionalLayer.isOptional() || additionalLayer.isActiveByDefault();
        }
        return true;
    }
}
