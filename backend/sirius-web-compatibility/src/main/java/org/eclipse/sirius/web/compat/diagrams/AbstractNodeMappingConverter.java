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
package org.eclipse.sirius.web.compat.diagrams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.sirius.diagram.ContainerLayout;
import org.eclipse.sirius.diagram.description.AbstractNodeMapping;
import org.eclipse.sirius.diagram.description.ContainerMapping;
import org.eclipse.sirius.diagram.description.style.FlatContainerStyleDescription;
import org.eclipse.sirius.diagram.description.style.WorkspaceImageDescription;
import org.eclipse.sirius.viewpoint.description.style.BasicLabelStyleDescription;
import org.eclipse.sirius.viewpoint.description.style.StyleFactory;
import org.eclipse.sirius.web.compat.api.IIdentifierProvider;
import org.eclipse.sirius.web.compat.api.IModelOperationHandlerSwitchProvider;
import org.eclipse.sirius.web.compat.api.ISemanticCandidatesProviderFactory;
import org.eclipse.sirius.web.compat.utils.StringValueProvider;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.NodeType;
import org.eclipse.sirius.web.diagrams.description.LabelDescription;
import org.eclipse.sirius.web.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.services.api.objects.IEditService;
import org.springframework.stereotype.Service;

/**
 * This class is used to convert a Sirius AbstractNodeMapping to an Sirius Web NodeDescription.
 *
 * @author sbegaudeau
 */
@Service
public class AbstractNodeMappingConverter {

    private final IObjectService objectService;

    private final IEditService editService;

    private final IIdentifierProvider identifierProvider;

    private final ISemanticCandidatesProviderFactory semanticCandidatesProviderFactory;

    private final IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider;

    public AbstractNodeMappingConverter(IObjectService objectService, IEditService editService, IIdentifierProvider identifierProvider,
            ISemanticCandidatesProviderFactory semanticCandidatesProviderFactory, IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider) {
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.semanticCandidatesProviderFactory = Objects.requireNonNull(semanticCandidatesProviderFactory);
        this.modelOperationHandlerSwitchProvider = Objects.requireNonNull(modelOperationHandlerSwitchProvider);
    }

    public NodeDescription convert(AbstractNodeMapping abstractNodeMapping, AQLInterpreter interpreter, Map<UUID, NodeDescription> id2NodeDescriptions) {
        LabelStyleDescriptionConverter labelStyleDescriptionConverter = new LabelStyleDescriptionConverter(interpreter, this.objectService);

        Function<VariableManager, org.eclipse.sirius.viewpoint.description.style.LabelStyleDescription> abstractNodeMappingDescriptionProvider = new LabelStyleDescriptionProvider(interpreter,
                abstractNodeMapping);

        Function<VariableManager, LabelStyleDescription> labelStyleDescriptionProvider = variableManager -> {
            org.eclipse.sirius.viewpoint.description.style.LabelStyleDescription styleDescription = abstractNodeMappingDescriptionProvider.apply(variableManager);
            BasicLabelStyleDescription basicLabelStyleDescription = Optional.ofNullable(styleDescription).map(BasicLabelStyleDescription.class::cast).orElse(this.getDefaultLabelStyle());
            return labelStyleDescriptionConverter.convert(basicLabelStyleDescription);
        };

        Function<VariableManager, String> labelExpressionProvider = variableManager -> {
            org.eclipse.sirius.viewpoint.description.style.LabelStyleDescription styleDescription = abstractNodeMappingDescriptionProvider.apply(variableManager);
            String labelExpression = Optional.ofNullable(styleDescription).map(BasicLabelStyleDescription::getLabelExpression).orElse(""); //$NON-NLS-1$
            return new StringValueProvider(interpreter, labelExpression).apply(variableManager);
        };

        Function<VariableManager, String> labelIdProvider = variableManager -> {
            Object parentId = variableManager.getVariables().get(LabelDescription.OWNER_ID);
            return String.valueOf(parentId) + LabelDescription.LABEL_SUFFIX;
        };

        // @formatter:off
        LabelDescription labelDescription = LabelDescription.newLabelDescription(this.identifierProvider.getIdentifier(abstractNodeMapping) + LabelDescription.LABEL_SUFFIX)
                .idProvider(labelIdProvider)
                .textProvider(labelExpressionProvider)
                .styleDescriptionProvider(labelStyleDescriptionProvider)
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
            org.eclipse.sirius.viewpoint.description.style.LabelStyleDescription style = abstractNodeMappingDescriptionProvider.apply(variableManager);
            String nodeType = NodeType.NODE_RECTANGLE;
            if (style instanceof WorkspaceImageDescription) {
                nodeType = NodeType.NODE_IMAGE;
            } else if (style instanceof FlatContainerStyleDescription && abstractNodeMapping instanceof ContainerMapping
                    && ContainerLayout.LIST.equals(((ContainerMapping) abstractNodeMapping).getChildrenPresentation())) {
                nodeType = NodeType.NODE_LIST;
            } else if (style.eContainer() != null && style.eContainer().eContainer() instanceof ContainerMapping) {
                ContainerMapping parentMapping = (ContainerMapping) style.eContainer().eContainer();
                org.eclipse.sirius.viewpoint.description.style.LabelStyleDescription labelStyleDescription = new LabelStyleDescriptionProvider(interpreter, parentMapping).apply(variableManager);
                if (labelStyleDescription instanceof FlatContainerStyleDescription && ContainerLayout.LIST.equals(parentMapping.getChildrenPresentation())) {
                    nodeType = NodeType.NODE_LIST_ITEM;
                }
            }
            return nodeType;
        };

        Function<VariableManager, INodeStyle> styleProvider = new AbstractNodeMappingStyleProvider(interpreter, abstractNodeMapping);
        AbstractNodeMappingSizeProvider sizeProvider = new AbstractNodeMappingSizeProvider(interpreter, abstractNodeMapping);

        String domainClass = abstractNodeMapping.getDomainClass();
        String semanticCandidatesExpression = abstractNodeMapping.getSemanticCandidatesExpression();
        String preconditionExpression = abstractNodeMapping.getPreconditionExpression();
        Function<VariableManager, List<Object>> semanticElementsProvider = this.semanticCandidatesProviderFactory.getSemanticCandidatesProvider(interpreter, domainClass, semanticCandidatesExpression,
                preconditionExpression);

        List<NodeDescription> childNodeDescriptions = this.getChildNodeDescriptions(abstractNodeMapping, interpreter, id2NodeDescriptions);

        // @formatter:off
        List<NodeDescription> borderNodeDescriptions = abstractNodeMapping.getBorderedNodeMappings().stream()
                .map(borderNodeMapping -> this.convert(borderNodeMapping, interpreter, id2NodeDescriptions))
                .collect(Collectors.toList());
        // @formatter:on

        ToolConverter toolConverter = new ToolConverter(interpreter, this.editService, this.modelOperationHandlerSwitchProvider);
        var deleteHandler = toolConverter.createDeleteToolHandler(abstractNodeMapping.getDeletionDescription());
        var labelEditHandler = toolConverter.createDirectEditToolHandler(abstractNodeMapping.getLabelDirectEdit());

        // @formatter:off
        NodeDescription description = NodeDescription.newNodeDescription(UUID.fromString(this.identifierProvider.getIdentifier(abstractNodeMapping)))
                .typeProvider(typeProvider)
                .targetObjectIdProvider(semanticTargetIdProvider)
                .targetObjectKindProvider(semanticTargetKindProvider)
                .targetObjectLabelProvider(semanticTargetLabelProvider)
                .semanticElementsProvider(semanticElementsProvider)
                .labelDescription(labelDescription)
                .styleProvider(styleProvider)
                .sizeProvider(sizeProvider)
                .borderNodeDescriptions(borderNodeDescriptions)
                .childNodeDescriptions(childNodeDescriptions)
                .labelEditHandler(labelEditHandler)
                .deleteHandler(deleteHandler)
                .build();
        // @formatter:on

        id2NodeDescriptions.put(description.getId(), description);

        return description;
    }

    private List<NodeDescription> getChildNodeDescriptions(AbstractNodeMapping abstractNodeMapping, AQLInterpreter interpreter, Map<UUID, NodeDescription> id2NodeDescriptions) {
        // @formatter:off
        List<NodeDescription> childNodeDescriptions = new ArrayList<>();

        if (abstractNodeMapping instanceof ContainerMapping) {
            ContainerMapping containerMapping = (ContainerMapping) abstractNodeMapping;
            List<NodeDescription> childNodeMappingDescriptions = containerMapping.getSubNodeMappings().stream()
                    .map(childNodeMapping -> this.convert(childNodeMapping, interpreter, id2NodeDescriptions))
                    .collect(Collectors.toList());

            List<NodeDescription> childContainerMappingDescriptions = containerMapping.getSubContainerMappings().stream()
                    .map(childContainerMapping -> this.convert(childContainerMapping, interpreter, id2NodeDescriptions))
                    .collect(Collectors.toList());

            childNodeDescriptions.addAll(childNodeMappingDescriptions);
            childNodeDescriptions.addAll(childContainerMappingDescriptions);
        }
        // @formatter:on

        return childNodeDescriptions;

    }

    private BasicLabelStyleDescription getDefaultLabelStyle() {
        var labelStyle = StyleFactory.eINSTANCE.createBasicLabelStyleDescription();
        labelStyle.setLabelExpression(""); //$NON-NLS-1$
        labelStyle.setShowIcon(true);
        labelStyle.setLabelSize(16);
        return labelStyle;
    }

}
