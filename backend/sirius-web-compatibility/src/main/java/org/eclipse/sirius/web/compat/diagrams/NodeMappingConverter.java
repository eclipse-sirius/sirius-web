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
package org.eclipse.sirius.web.compat.diagrams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.sirius.diagram.description.NodeMapping;
import org.eclipse.sirius.diagram.description.style.WorkspaceImageDescription;
import org.eclipse.sirius.web.compat.services.representations.IdentifierProvider;
import org.eclipse.sirius.web.compat.utils.SemanticCandidatesProvider;
import org.eclipse.sirius.web.compat.utils.StringValueProvider;
import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.NodeType;
import org.eclipse.sirius.web.diagrams.description.LabelDescription;
import org.eclipse.sirius.web.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.services.api.objects.IEditService;
import org.eclipse.sirius.web.services.api.objects.IObjectService;

/**
 * This class is used to convert a Sirius NodeMapping to an Sirius Web NodeDescription.
 *
 * @author sbegaudeau
 */
public class NodeMappingConverter {

    private static final String EMPTY_STRING = ""; //$NON-NLS-1$

    private final IObjectService objectService;

    private final IEditService editService;

    private final AQLInterpreter interpreter;

    private final IdentifierProvider identifierProvider;

    private final LabelStyleDescriptionConverter labelStyleDescriptionConverter;

    public NodeMappingConverter(IObjectService objectService, IEditService editService, AQLInterpreter interpreter, IdentifierProvider identifierProvider) {
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
        this.interpreter = Objects.requireNonNull(interpreter);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.labelStyleDescriptionConverter = new LabelStyleDescriptionConverter(this.interpreter, this.objectService);
    }

    public NodeDescription convert(NodeMapping nodeMapping, Map<UUID, NodeDescription> id2NodeDescriptions) {
        String labelExpression = EMPTY_STRING;
        if (nodeMapping.getStyle() != null) {
            labelExpression = nodeMapping.getStyle().getLabelExpression();
        }
        LabelStyleDescription labelStyleDescription = this.labelStyleDescriptionConverter.convert(nodeMapping.getStyle());
        Function<VariableManager, String> labelIdProvider = variableManager -> {
            Object parentId = variableManager.getVariables().get(LabelDescription.OWNER_ID);
            return String.valueOf(parentId) + LabelDescription.LABEL_SUFFIX;
        };

        // @formatter:off
        LabelDescription labelDescription = LabelDescription.newLabelDescription(this.identifierProvider.getIdentifier(nodeMapping) + LabelDescription.LABEL_SUFFIX)
                .idProvider(labelIdProvider)
                .textProvider(new StringValueProvider(this.interpreter, labelExpression))
                .styleDescription(labelStyleDescription)
                .build();
        // @formatter:on

        Function<VariableManager, String> semanticTargetIdProvider = variableManager -> {
            return variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(null);
        };
        Function<VariableManager, String> semanticTargetKindProvider = variableManager -> {
            return variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getKind).orElse(null);
        };
        Function<VariableManager, String> semanticTargetLabelProvider = variableManager -> {
            return variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getLabel).orElse(null);
        };
        Function<VariableManager, String> typeProvider = variableManager -> {
            if (nodeMapping.getStyle() instanceof WorkspaceImageDescription) {
                return NodeType.NODE_IMAGE;
            }
            return NodeType.NODE_RECTANGLE;
        };

        Function<VariableManager, INodeStyle> styleProvider = new NodeMappingStyleProvider(this.interpreter, nodeMapping);

        String domainClass = nodeMapping.getDomainClass();
        String semanticCandidatesExpression = nodeMapping.getSemanticCandidatesExpression();
        String preconditionExpression = nodeMapping.getPreconditionExpression();
        Function<VariableManager, List<Object>> semanticElementsProvider = new SemanticCandidatesProvider(this.interpreter, domainClass, semanticCandidatesExpression, preconditionExpression);

        // @formatter:off
        List<NodeDescription> borderNodeDescriptions = nodeMapping.getBorderedNodeMappings().stream()
                .map(borderNodeMapping -> new NodeMappingConverter(this.objectService, this.editService, this.interpreter, this.identifierProvider).convert(borderNodeMapping, id2NodeDescriptions))
                .collect(Collectors.toList());
        // @formatter:on

        ToolConverter toolConverter = new ToolConverter(this.interpreter, this.editService);
        var deleteHandler = toolConverter.createDeleteToolHandler(nodeMapping.getDeletionDescription());
        var labelEditHandler = toolConverter.createDirectEditToolHandler(nodeMapping.getLabelDirectEdit());

        // @formatter:off
        NodeDescription description = NodeDescription.newNodeDescription(UUID.fromString(this.identifierProvider.getIdentifier(nodeMapping)))
                .typeProvider(typeProvider)
                .targetObjectIdProvider(semanticTargetIdProvider)
                .targetObjectKindProvider(semanticTargetKindProvider)
                .targetObjectLabelProvider(semanticTargetLabelProvider)
                .semanticElementsProvider(semanticElementsProvider)
                .labelDescription(labelDescription)
                .styleProvider(styleProvider)
                .borderNodeDescriptions(borderNodeDescriptions)
                .childNodeDescriptions(new ArrayList<>())
                .labelEditHandler(labelEditHandler)
                .deleteHandler(deleteHandler)
                .build();
        // @formatter:on

        id2NodeDescriptions.put(description.getId(), description);

        return description;
    }

}
