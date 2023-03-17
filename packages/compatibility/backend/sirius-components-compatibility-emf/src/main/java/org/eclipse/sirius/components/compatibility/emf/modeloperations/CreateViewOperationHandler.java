/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.components.compatibility.emf.modeloperations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.api.IModelOperationHandler;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.components.NodeContainmentKind;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.diagram.description.DescriptionPackage;
import org.eclipse.sirius.diagram.description.DiagramElementMapping;
import org.eclipse.sirius.diagram.description.tool.CreateView;
import org.eclipse.sirius.viewpoint.description.tool.ModelOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles the create view model operation.
 *
 * @author sbegaudeau
 */
public class CreateViewOperationHandler implements IModelOperationHandler {

    private final Logger logger = LoggerFactory.getLogger(CreateViewOperationHandler.class);

    private final IObjectService objectService;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IIdentifierProvider identifierProvider;

    private final AQLInterpreter interpreter;

    private final ChildModelOperationHandler childModelOperationHandler;

    private final CreateView createView;

    public CreateViewOperationHandler(IObjectService objectService, IRepresentationMetadataSearchService representationSearchService, IIdentifierProvider identifierProvider,
            AQLInterpreter interpreter, ChildModelOperationHandler childModelOperationHandler, CreateView createView) {
        this.objectService = Objects.requireNonNull(objectService);
        this.representationMetadataSearchService = Objects.requireNonNull(representationSearchService);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.interpreter = Objects.requireNonNull(interpreter);
        this.childModelOperationHandler = Objects.requireNonNull(childModelOperationHandler);
        this.createView = Objects.requireNonNull(createView);
    }

    @Override
    public IStatus handle(Map<String, Object> variables) {
        String containerViewExpression = this.createView.getContainerViewExpression();

        var optionalParentElement = this.interpreter.evaluateExpression(variables, containerViewExpression).asObject();
        var optionalParentElementId = optionalParentElement.flatMap(parentElement -> {
            Optional<String> optionalElementId = Optional.empty();
            if (parentElement instanceof Diagram) {
                Diagram diagram = (Diagram) parentElement;
                optionalElementId = Optional.of(diagram.getId());
            } else if (parentElement instanceof Node) {
                Node node = (Node) parentElement;
                optionalElementId = Optional.of(node.getId());
            }
            return optionalElementId;
        });

        if (optionalParentElementId.isEmpty()) {
            return new Failure("");
        }

        DiagramElementMapping diagramElementMapping = this.createView.getMapping();
        String diagramElementMappingId = this.identifierProvider.getIdentifier(diagramElementMapping);
        String targetObjectId = this.objectService.getId(variables.get(VariableManager.SELF));

        try {
            var containmentKind = NodeContainmentKind.CHILD_NODE;
            if (diagramElementMapping != null && DescriptionPackage.eINSTANCE.getAbstractNodeMapping_BorderedNodeMappings().equals(diagramElementMapping.eContainmentFeature())) {
                containmentKind = NodeContainmentKind.BORDER_NODE;
            }

            String descriptionId = UUID.fromString(diagramElementMappingId).toString();
            ViewCreationRequest viewCreationRequest = ViewCreationRequest.newViewCreationRequest()
                    .parentElementId(optionalParentElementId.get())
                    .descriptionId(descriptionId)
                    .targetObjectId(targetObjectId)
                    .containmentKind(containmentKind)
                    .build();

            Optional.ofNullable(variables.get(IDiagramContext.DIAGRAM_CONTEXT))
                    .filter(IDiagramContext.class::isInstance)
                    .map(IDiagramContext.class::cast)
                    .ifPresent(diagramContext -> diagramContext.getViewCreationRequests().add(viewCreationRequest));
        } catch (IllegalArgumentException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }

        Map<String, Object> childVariables = new HashMap<>(variables);
        List<ModelOperation> subModelOperations = this.createView.getSubModelOperations();
        return this.childModelOperationHandler.handle(this.objectService, this.representationMetadataSearchService, this.identifierProvider, this.interpreter, childVariables, subModelOperations);
    }

}
