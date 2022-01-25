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
package org.eclipse.sirius.components.compatibility.diagrams;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.api.IModelOperationHandlerSwitchProvider;
import org.eclipse.sirius.components.compatibility.api.ISemanticCandidatesProviderFactory;
import org.eclipse.sirius.components.compatibility.utils.StringValueProvider;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.LabelDescription;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription.Builder;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.diagram.description.AbstractNodeMapping;
import org.eclipse.sirius.diagram.description.DiagramElementMapping;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.description.style.EdgeStyleDescription;
import org.eclipse.sirius.viewpoint.description.style.BasicLabelStyleDescription;
import org.springframework.stereotype.Service;

/**
 * This class is used to convert a Sirius EdgeMapping to an Sirius Web EdgeDescription.
 *
 * @author sbegaudeau
 */
@Service
public class EdgeMappingConverter {

    private final IObjectService objectService;

    private final IEditService editService;

    private final IIdentifierProvider identifierProvider;

    private final ISemanticCandidatesProviderFactory semanticCandidatesProviderFactory;

    private final IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider;

    public EdgeMappingConverter(IObjectService objectService, IEditService editService, IIdentifierProvider identifierProvider, ISemanticCandidatesProviderFactory semanticCandidatesProviderFactory,
            IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider) {
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.semanticCandidatesProviderFactory = Objects.requireNonNull(semanticCandidatesProviderFactory);
        this.modelOperationHandlerSwitchProvider = Objects.requireNonNull(modelOperationHandlerSwitchProvider);
    }

    public EdgeDescription convert(EdgeMapping edgeMapping, AQLInterpreter interpreter, Map<UUID, NodeDescription> id2NodeDescriptions) {
        Function<VariableManager, String> targetIdProvider = variableManager -> {
            return variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(null);
        };
        Function<VariableManager, String> targetKindProvider = variableManager -> {
            return variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getKind).orElse(null);
        };
        Function<VariableManager, String> targetLabelProvider = variableManager -> {
            return variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getLabel).orElse(null);
        };

        List<NodeDescription> sourceNodeDescriptions = this.getNodeDescriptions(edgeMapping.getSourceMapping(), id2NodeDescriptions);
        List<NodeDescription> targetNodeDescriptions = this.getNodeDescriptions(edgeMapping.getTargetMapping(), id2NodeDescriptions);

        Function<VariableManager, List<Object>> semanticElementsProvider = this.getSemanticElementsProvider(interpreter, edgeMapping, sourceNodeDescriptions);

        Function<VariableManager, List<Element>> sourceNodesProvider = null;
        if (edgeMapping.isUseDomainElement()) {
            sourceNodesProvider = new DomainBasedSourceNodesProvider(edgeMapping, interpreter, this.identifierProvider);
        } else {
            sourceNodesProvider = new RelationBasedSourceNodesProvider(edgeMapping, this.identifierProvider);
        }

        Function<VariableManager, List<Element>> targetNodesProvider = new TargetNodesProvider(edgeMapping, interpreter, this.identifierProvider);
        Function<VariableManager, EdgeStyle> styleProvider = new EdgeMappingStyleProvider(interpreter, edgeMapping);

        LabelStyleDescriptionConverter labelStyleDescriptionConverter = new LabelStyleDescriptionConverter(interpreter, this.objectService);

        // @formatter:off
        EdgeStyleDescription style = edgeMapping.getStyle();

        Optional<LabelDescription> optionalBeginLabelDescription = Optional.ofNullable(style)
                .map(EdgeStyleDescription::getBeginLabelStyleDescription)
                .map(labelDescription -> this.createLabelDescription(interpreter, labelStyleDescriptionConverter, labelDescription,  "_beginlabel", edgeMapping)); //$NON-NLS-1$

        Optional<LabelDescription> optionalCenterLabelDescription = Optional.ofNullable(style)
                .map(EdgeStyleDescription::getCenterLabelStyleDescription)
                .map(labelDescription -> this.createLabelDescription(interpreter, labelStyleDescriptionConverter, labelDescription,  "_centerlabel", edgeMapping)); //$NON-NLS-1$

        Optional<LabelDescription> optionalEndLabelDescription = Optional.ofNullable(style)
                .map(EdgeStyleDescription::getEndLabelStyleDescription)
                .map(labelDescription -> this.createLabelDescription(interpreter, labelStyleDescriptionConverter, labelDescription,  "_endlabel", edgeMapping)); //$NON-NLS-1$

        ToolConverter toolConverter = new ToolConverter(interpreter, this.editService, this.modelOperationHandlerSwitchProvider);
        var deleteHandler = toolConverter.createDeleteToolHandler(edgeMapping.getDeletionDescription());
        var labelEditHandler = toolConverter.createDirectEditToolHandler(edgeMapping.getLabelDirectEdit());

        Builder builder = EdgeDescription.newEdgeDescription(UUID.fromString(this.identifierProvider.getIdentifier(edgeMapping)))
                .targetObjectIdProvider(targetIdProvider)
                .targetObjectKindProvider(targetKindProvider)
                .targetObjectLabelProvider(targetLabelProvider)
                .semanticElementsProvider(semanticElementsProvider)
                .sourceNodeDescriptions(sourceNodeDescriptions)
                .targetNodeDescriptions(targetNodeDescriptions)
                .sourceNodesProvider(sourceNodesProvider)
                .targetNodesProvider(targetNodesProvider)
                .styleProvider(styleProvider)
                .labelEditHandler(labelEditHandler)
                .deleteHandler(deleteHandler);
        optionalBeginLabelDescription.ifPresent(builder::beginLabelDescription);
        optionalCenterLabelDescription.ifPresent(builder::centerLabelDescription);
        optionalEndLabelDescription.ifPresent(builder::endLabelDescription);
        return builder.build();
        // @formatter:on
    }

    private LabelDescription createLabelDescription(AQLInterpreter interpreter, LabelStyleDescriptionConverter labelStyleDescriptionConverter,
            BasicLabelStyleDescription siriusBasicLabelStyleDescription, String idSuffix, EdgeMapping edgeMapping) {
        String labelExpression = siriusBasicLabelStyleDescription.getLabelExpression();

        Function<VariableManager, LabelStyleDescription> labelStyleDescriptionProvider = variableManager -> {
            return labelStyleDescriptionConverter.convert(siriusBasicLabelStyleDescription);
        };

        Function<VariableManager, String> labelIdProvider = variableManager -> {
            Object parentId = variableManager.getVariables().get(LabelDescription.OWNER_ID);
            return String.valueOf(parentId) + idSuffix;
        };

        String id = this.identifierProvider.getIdentifier(edgeMapping) + idSuffix;
        StringValueProvider textProvider = new StringValueProvider(interpreter, labelExpression);
        // @formatter:off
        return LabelDescription.newLabelDescription(id)
                .idProvider(labelIdProvider)
                .textProvider(textProvider)
                .styleDescriptionProvider(labelStyleDescriptionProvider)
                .build();
        // @formatter:on

    }

    /**
     * Returns the node descriptions matching the given mappings.
     *
     * @param mappings
     *            The mappings referenced by the edge description (source or target)
     * @return The relevant node descriptions created by the node and container description converters
     */
    private List<NodeDescription> getNodeDescriptions(List<DiagramElementMapping> mappings, Map<UUID, NodeDescription> id2NodeDescriptions) {
        // @formatter:off
        return mappings.stream()
                .filter(AbstractNodeMapping.class::isInstance)
                .map(AbstractNodeMapping.class::cast)
                .map(mapping->UUID.fromString(this.identifierProvider.getIdentifier(mapping)))
                .map(id2NodeDescriptions::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        // @formatter:on
    }

    private Function<VariableManager, List<Object>> getSemanticElementsProvider(AQLInterpreter interpreter, EdgeMapping edgeMapping, List<NodeDescription> sourceNodeDescriptions) {
        Function<VariableManager, List<Object>> semanticElementsProvider = variableManager -> List.of();

        if (edgeMapping.isUseDomainElement()) {
            String semanticCandidatesExpression = edgeMapping.getSemanticCandidatesExpression();
            String preconditionExpression = edgeMapping.getPreconditionExpression();
            String domainClass = Optional.ofNullable(edgeMapping.getDomainClass()).orElse(""); //$NON-NLS-1$
            semanticElementsProvider = this.semanticCandidatesProviderFactory.getSemanticCandidatesProvider(interpreter, domainClass, semanticCandidatesExpression, preconditionExpression);
        } else {
            // @formatter:off
            List<UUID> sourceNodeDescriptionIds = sourceNodeDescriptions.stream()
                    .map(NodeDescription::getId)
                    .collect(Collectors.toList());
            // @formatter:on

            semanticElementsProvider = new RelationBasedSemanticElementsProvider(sourceNodeDescriptionIds);
        }
        return semanticElementsProvider;
    }
}
