/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.api.IModelOperationHandlerSwitchProvider;
import org.eclipse.sirius.components.compatibility.api.ISemanticCandidatesProviderFactory;
import org.eclipse.sirius.components.compatibility.utils.StringValueProvider;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.ILayoutStrategy;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.IconLabelNodeStyle;
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.description.InsideLabelDescription;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.diagram.business.api.query.ContainerMappingQuery;
import org.eclipse.sirius.diagram.description.AbstractNodeMapping;
import org.eclipse.sirius.diagram.description.ContainerMapping;
import org.eclipse.sirius.viewpoint.description.style.BasicLabelStyleDescription;
import org.eclipse.sirius.viewpoint.description.style.StyleFactory;
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

    public NodeDescription convert(AbstractNodeMapping abstractNodeMapping, AQLInterpreter interpreter, Map<String, NodeDescription> id2NodeDescriptions) {
        LabelStyleDescriptionConverter labelStyleDescriptionConverter = new LabelStyleDescriptionConverter(interpreter, this.objectService);
        Function<VariableManager, org.eclipse.sirius.viewpoint.description.style.LabelStyleDescription> abstractNodeMappingDescriptionProvider = new LabelStyleDescriptionProvider(interpreter,
                abstractNodeMapping);

        Function<VariableManager, String> labelIdProvider = this.getLabelIdProvider();
        Function<VariableManager, String> labelExpressionProvider = this.getLabelExpressionProvider(interpreter, abstractNodeMappingDescriptionProvider);
        Function<VariableManager, LabelStyleDescription> labelStyleDescriptionProvider = this.getLabelStyleDescriptionProvider(labelStyleDescriptionConverter, abstractNodeMappingDescriptionProvider);
        Function<VariableManager, Boolean> isHeaderProvider = new AbstractNodeMappingIsHeaderProvider(interpreter, abstractNodeMapping);

        InsideLabelDescription insideLabelDescription = InsideLabelDescription.newInsideLabelDescription(this.identifierProvider.getIdentifier(abstractNodeMapping) + InsideLabelDescription.INSIDE_LABEL_SUFFIX)
                .idProvider(labelIdProvider)
                .textProvider(labelExpressionProvider)
                .styleDescriptionProvider(labelStyleDescriptionProvider)
                .isHeaderProvider(isHeaderProvider)
                .displayHeaderSeparatorProvider(isHeaderProvider)
                .insideLabelLocation(InsideLabelLocation.TOP_CENTER)
                .build();

        Function<VariableManager, String> semanticTargetIdProvider = variableManager -> {
            return variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(null);
        };
        Function<VariableManager, String> semanticTargetKindProvider = variableManager -> {
            return variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getKind).orElse(null);
        };
        Function<VariableManager, String> semanticTargetLabelProvider = variableManager -> {
            return variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getLabel).orElse(null);
        };

        Function<VariableManager, INodeStyle> styleProvider = new AbstractNodeMappingStyleProvider(interpreter, abstractNodeMapping);
        Function<VariableManager, ILayoutStrategy> childrenLayoutStrategyProvider = new AbstractNodeMappingChildrenLayoutStrategyProvider(abstractNodeMapping);

        Function<VariableManager, String> typeProvider = variableManager -> {
            var result = NodeType.NODE_RECTANGLE;
            INodeStyle style = styleProvider.apply(variableManager);
            if (style instanceof ImageNodeStyle) {
                result = NodeType.NODE_IMAGE;
            } else if (style instanceof IconLabelNodeStyle) {
                result = NodeType.NODE_ICON_LABEL;
            }
            return result;
        };
        Function<VariableManager, Size> sizeProvider = variableManager -> Size.UNDEFINED;

        String domainClass = abstractNodeMapping.getDomainClass();
        String semanticCandidatesExpression = abstractNodeMapping.getSemanticCandidatesExpression();
        String preconditionExpression = abstractNodeMapping.getPreconditionExpression();
        Function<VariableManager, List<?>> semanticElementsProvider = this.semanticCandidatesProviderFactory.getSemanticCandidatesProvider(interpreter, domainClass, semanticCandidatesExpression,
                preconditionExpression);

        List<NodeDescription> childNodeDescriptions = this.getChildNodeDescriptions(abstractNodeMapping, interpreter, id2NodeDescriptions);

        List<NodeDescription> borderNodeDescriptions = abstractNodeMapping.getBorderedNodeMappings().stream()
                .map(borderNodeMapping -> this.convert(borderNodeMapping, interpreter, id2NodeDescriptions))
                .toList();

        ToolConverter toolConverter = new ToolConverter(interpreter, this.editService, this.modelOperationHandlerSwitchProvider);
        var deleteHandler = toolConverter.createDeleteToolHandler(abstractNodeMapping.getDeletionDescription());
        var labelEditHandler = toolConverter.createNodeDirectEditToolHandler(abstractNodeMapping.getLabelDirectEdit());

        SynchronizationPolicy synchronizationPolicy = SynchronizationPolicy.SYNCHRONIZED;
        if (!abstractNodeMapping.isCreateElements()) {
            synchronizationPolicy = SynchronizationPolicy.UNSYNCHRONIZED;
        }

        boolean collapsible = false;
        if (abstractNodeMapping instanceof ContainerMapping containerMapping) {
            collapsible = new ContainerMappingQuery(containerMapping).isRegion();
        }

        NodeDescription description = NodeDescription.newNodeDescription(this.identifierProvider.getIdentifier(abstractNodeMapping))
                .typeProvider(typeProvider)
                .targetObjectIdProvider(semanticTargetIdProvider)
                .targetObjectKindProvider(semanticTargetKindProvider)
                .targetObjectLabelProvider(semanticTargetLabelProvider)
                .semanticElementsProvider(semanticElementsProvider)
                .synchronizationPolicy(synchronizationPolicy)
                .insideLabelDescription(insideLabelDescription)
                .styleProvider(styleProvider)
                .childrenLayoutStrategyProvider(childrenLayoutStrategyProvider)
                .sizeProvider(sizeProvider)
                .borderNodeDescriptions(borderNodeDescriptions)
                .childNodeDescriptions(childNodeDescriptions)
                .collapsible(collapsible)
                .labelEditHandler(labelEditHandler)
                .deleteHandler(deleteHandler)
                .build();

        id2NodeDescriptions.put(description.getId(), description);

        return description;
    }

    private Function<VariableManager, LabelStyleDescription> getLabelStyleDescriptionProvider(LabelStyleDescriptionConverter labelStyleDescriptionConverter,
            Function<VariableManager, org.eclipse.sirius.viewpoint.description.style.LabelStyleDescription> abstractNodeMappingDescriptionProvider) {
        return variableManager -> {
            org.eclipse.sirius.viewpoint.description.style.LabelStyleDescription styleDescription = abstractNodeMappingDescriptionProvider.apply(variableManager);
            BasicLabelStyleDescription basicLabelStyleDescription = Optional.ofNullable(styleDescription).map(BasicLabelStyleDescription.class::cast).orElse(this.getDefaultLabelStyle());
            return labelStyleDescriptionConverter.convert(basicLabelStyleDescription);
        };
    }

    private Function<VariableManager, String> getLabelExpressionProvider(AQLInterpreter interpreter,
            Function<VariableManager, org.eclipse.sirius.viewpoint.description.style.LabelStyleDescription> abstractNodeMappingDescriptionProvider) {
        return variableManager -> {
            org.eclipse.sirius.viewpoint.description.style.LabelStyleDescription styleDescription = abstractNodeMappingDescriptionProvider.apply(variableManager);
            String labelExpression = Optional.ofNullable(styleDescription).map(BasicLabelStyleDescription::getLabelExpression).orElse("");
            return new StringValueProvider(interpreter, labelExpression).apply(variableManager);
        };
    }

    private Function<VariableManager, String> getLabelIdProvider() {
        return variableManager -> {
            Object parentId = variableManager.getVariables().get(InsideLabelDescription.OWNER_ID);
            return parentId + InsideLabelDescription.INSIDE_LABEL_SUFFIX;
        };
    }

    private List<NodeDescription> getChildNodeDescriptions(AbstractNodeMapping abstractNodeMapping, AQLInterpreter interpreter, Map<String, NodeDescription> id2NodeDescriptions) {
        List<NodeDescription> childNodeDescriptions = new ArrayList<>();

        if (abstractNodeMapping instanceof ContainerMapping containerMapping) {
            List<NodeDescription> childNodeMappingDescriptions = containerMapping.getSubNodeMappings().stream()
                    .map(childNodeMapping -> this.convert(childNodeMapping, interpreter, id2NodeDescriptions))
                    .toList();

            List<NodeDescription> childContainerMappingDescriptions = containerMapping.getSubContainerMappings().stream()
                    .map(childContainerMapping -> this.convert(childContainerMapping, interpreter, id2NodeDescriptions))
                    .toList();

            childNodeDescriptions.addAll(childNodeMappingDescriptions);
            childNodeDescriptions.addAll(childContainerMappingDescriptions);
        }

        return childNodeDescriptions;

    }

    private BasicLabelStyleDescription getDefaultLabelStyle() {
        var labelStyle = StyleFactory.eINSTANCE.createBasicLabelStyleDescription();
        labelStyle.setLabelExpression("");
        labelStyle.setShowIcon(true);
        labelStyle.setLabelSize(16);
        return labelStyle;
    }

}
