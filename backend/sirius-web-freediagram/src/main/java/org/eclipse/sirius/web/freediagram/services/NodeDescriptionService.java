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
package org.eclipse.sirius.web.freediagram.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.sirius.web.diagrams.description.LabelDescription;
import org.eclipse.sirius.web.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.freediagram.styles.INodeStyleProvider;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.services.api.objects.IEditService;
import org.eclipse.sirius.web.services.api.objects.IObjectService;

/**
 * Create an OD Web NodeDescription.
 *
 * @author hmarchadour
 */
public class NodeDescriptionService {

    private final IObjectService objectService;

    private final IEditService editService;

    private final FreeDiagramService freeDiagramService;

    public NodeDescriptionService(IObjectService objectService, IEditService editService, FreeDiagramService freeDiagramService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
        this.freeDiagramService = Objects.requireNonNull(freeDiagramService);
    }

    public NodeDescription create(UUID nodeDescriptionId, boolean synchronised, INodeStyleProvider styleProvider) {
        // @formatter:off
        return NodeDescription.newNodeDescription(nodeDescriptionId)
           .idProvider(this::getId)
           .synchronised(synchronised)
           .typeProvider(vM -> styleProvider.getNodeType(vM))
           .targetObjectIdProvider(this::getTargetObjectId)
           .targetObjectKindProvider(this::getTargetObjectKind)
           .targetObjectLabelProvider(this::getTargetObjectLabel)
           .semanticElementsProvider(this::getSemanticElementsProvider)
           .labelDescription(this.getLabelDescription(nodeDescriptionId.toString(), styleProvider.getLabelStyleDescription()))
           .styleProvider(styleProvider::getNodeStyle)
           .borderNodeDescriptions(this.getBorderNodeDescriptions())
           .childNodeDescriptions(this.getChildNodeDescriptions())
           .labelEditHandler(this::handleLabelEdit)
           .deleteHandler(variableManager-> this.handleDelete(variableManager, synchronised))
           .build();
        // @formatter:on
    }

    private String getId(VariableManager variableManager) {
        return UUID.randomUUID().toString();
    }

    private String getTargetObjectId(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(null);
    }

    private String getTargetObjectKind(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getKind).orElse(null);
    }

    private String getTargetObjectLabel(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getLabel).orElse(null);
    }

    private List<NodeDescription> getBorderNodeDescriptions() {
        return List.of();
    }

    private List<NodeDescription> getChildNodeDescriptions() {
        return List.of();
    }

    private List<Object> getSemanticElementsProvider(VariableManager variableManager) {
        return List.of();
    }

    public LabelDescription getLabelDescription(String nodeDescriptionId, LabelStyleDescription labelStyleDescription) {
        // @formatter:off
        Function<VariableManager, String> textProvider = variableManager ->
            variableManager.get(VariableManager.SELF, Object.class)
                .map(this.objectService::getLabel)
                .orElse(null);

        Function<VariableManager, String> labelIdProvider = variableManager -> {
            Object parentId = variableManager.getVariables().get(LabelDescription.OWNER_ID);
            return String.valueOf(parentId) + LabelDescription.LABEL_SUFFIX;
        };

        return LabelDescription.newLabelDescription(nodeDescriptionId + LabelDescription.LABEL_SUFFIX)
                .idProvider(labelIdProvider)
                .textProvider(textProvider)
                .styleDescription(labelStyleDescription)
                .build();
        // @formatter:on
    }

    private Status handleLabelEdit(VariableManager variableManager, String labelInput) {
        // @formatter:off
        return variableManager.get(VariableManager.SELF, Object.class)
            .map(self -> {
                Optional<String> optionalLabelField = this.objectService.getLabelField(self);
                if (optionalLabelField.isPresent()) {
                    this.editService.editLabel(self, optionalLabelField.get(), labelInput);
                    return Status.OK;
                } else {
                    return Status.ERROR;
                }
            }).orElse(Status.ERROR);
        // @formatter:on
    }

    private Status handleDelete(VariableManager variableManager, boolean synchronised) {
        if (synchronised) {
            return this.removeFromResource(variableManager);
        } else {
            return this.freeDiagramService.removeNode(variableManager);
        }
    }

    private Status removeFromResource(VariableManager variableManager) {
        // @formatter:off
        return variableManager.get(VariableManager.SELF, Object.class)
            .map(object -> {
                this.editService.delete(object);
                return Status.OK;
            })
            .orElse(Status.ERROR);
        // @formatter:on
    }

}
