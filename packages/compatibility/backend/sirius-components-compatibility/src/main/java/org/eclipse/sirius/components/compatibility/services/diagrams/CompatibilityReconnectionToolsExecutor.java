/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.compatibility.services.diagrams;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.common.tools.api.interpreter.IInterpreterSiriusVariables;
import org.eclipse.sirius.components.collaborative.diagrams.api.IReconnectionToolsExecutor;
import org.eclipse.sirius.components.collaborative.diagrams.api.ReconnectionToolInterpreterData;
import org.eclipse.sirius.components.compatibility.api.IAQLInterpreterFactory;
import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.api.IModelOperationHandler;
import org.eclipse.sirius.components.compatibility.api.IModelOperationHandlerSwitchProvider;
import org.eclipse.sirius.components.compatibility.messages.ICompatibilityMessageService;
import org.eclipse.sirius.components.compatibility.services.api.IODesignRegistry;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeKind;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.interpreter.Status;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.description.tool.ReconnectEdgeDescription;
import org.eclipse.sirius.diagram.description.tool.ReconnectionKind;
import org.eclipse.sirius.viewpoint.description.tool.InitialOperation;
import org.eclipse.sirius.viewpoint.description.tool.ModelOperation;
import org.springframework.stereotype.Service;

/**
 * The reconnection tools executor for the compatibility layer.
 *
 * @author gcoutable
 */
@Service
public class CompatibilityReconnectionToolsExecutor implements IReconnectionToolsExecutor {

    private final IIdentifierProvider identifierProvider;

    private final IODesignRegistry odesignRegistry;

    private final IAQLInterpreterFactory interpreterFactory;

    private final IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider;

    private final ICompatibilityMessageService compatibilityMessageService;

    public CompatibilityReconnectionToolsExecutor(IIdentifierProvider identifierProvider, IODesignRegistry odesignRegistry, IAQLInterpreterFactory interpreterFactory,
            IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider, ICompatibilityMessageService compatibilityMessageService) {
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.odesignRegistry = Objects.requireNonNull(odesignRegistry);
        this.interpreterFactory = Objects.requireNonNull(interpreterFactory);
        this.modelOperationHandlerSwitchProvider = Objects.requireNonNull(modelOperationHandlerSwitchProvider);
        this.compatibilityMessageService = Objects.requireNonNull(compatibilityMessageService);
    }

    @Override
    public boolean canExecute(DiagramDescription diagramDescription) {
        return this.identifierProvider.findVsmElementId(diagramDescription.getId()).isPresent();
    }

    @Override
    public IStatus execute(IEditingContext editingContext, ReconnectionToolInterpreterData toolInterpreterData, Edge edge, EdgeDescription edgeDescription, ReconnectEdgeKind reconnectEdgeKind,
            DiagramDescription diagramDescription) {
        IStatus status = new Failure(this.compatibilityMessageService.noReconnectionToolDefined());

        var optionalSiriusDiagramDescription = this.identifierProvider.findVsmElementId(diagramDescription.getId()).flatMap(this::getSiriusDiagramDescription);

        if (optionalSiriusDiagramDescription.isPresent()) {

            AQLInterpreter interpreter = this.interpreterFactory.create(optionalSiriusDiagramDescription.get());
            VariableManager variableManager = this.createVariableManager(toolInterpreterData);

            // @formatter:off
            List<ReconnectEdgeDescription> reconnectEdgeDescriptions = this.identifierProvider.findVsmElementId(edgeDescription.getId().toString())
                .flatMap(this::getEdgeMapping)
                .map(EdgeMapping::getReconnections)
                .orElseGet(BasicEList::new);
            // @formatter:on

            if (!reconnectEdgeDescriptions.isEmpty()) {
                // @formatter:off
                Optional<ReconnectEdgeDescription> optionalReconnectEdgeDescription = reconnectEdgeDescriptions.stream()
                        .filter(reconnectEdgeDescription -> this.isKindOf(reconnectEdgeDescription.getReconnectionKind(), reconnectEdgeKind))
                        .filter(reconnectEdgeDescription -> this.canReconnect(variableManager, reconnectEdgeDescription, interpreter))
                        .findFirst();
                // @formatter:on

                if (optionalReconnectEdgeDescription.isPresent()) {
                    // @formatter:off
                    status = optionalReconnectEdgeDescription.map(ReconnectEdgeDescription::getInitialOperation)
                        .map(initialOperation -> this.executeReconnectionTool(initialOperation, interpreter, variableManager))
                        .orElse(new Failure(this.compatibilityMessageService.toolExecutionError()));
                    // @formatter:on
                } else {
                    status = new Failure(this.compatibilityMessageService.reconnectionToolCannotBeHandled());
                }

            }
        }

        return status;
    }

    private IStatus executeReconnectionTool(InitialOperation initialOperation, AQLInterpreter interpreter, VariableManager variableManager) {
        Function<ModelOperation, Optional<IModelOperationHandler>> modelOperationHandlerSwitch = this.modelOperationHandlerSwitchProvider.getModelOperationHandlerSwitch(interpreter);
        // @formatter:off
        return modelOperationHandlerSwitch.apply(initialOperation.getFirstModelOperations())
                .map(handler -> {
                    return handler.handle(variableManager.getVariables());
                })
                .orElse(new Failure("")); //$NON-NLS-1$
        // @formatter:on
    }

    private Optional<EdgeMapping> getEdgeMapping(String edgeMappingId) {
        // @formatter:off
        return this.getMapping(edgeMappingId)
                .filter(EdgeMapping.class::isInstance)
                .map(EdgeMapping.class::cast)
                .findFirst();
        // @formatter:on
    }

    private Optional<org.eclipse.sirius.diagram.description.DiagramDescription> getSiriusDiagramDescription(String siriusDiagramDescriptionId) {
        // @formatter:off
        return this.getMapping(siriusDiagramDescriptionId)
                .filter(org.eclipse.sirius.diagram.description.DiagramDescription.class::isInstance)
                .map(org.eclipse.sirius.diagram.description.DiagramDescription.class::cast)
                .findFirst();
        // @formatter:on
    }

    private Stream<EObject> getMapping(String mappingId) {
        // @formatter:off
        return this.odesignRegistry.getODesigns().stream()
                .map(EObject::eResource)
                .map(resource -> resource.getResourceSet().getEObject(URI.createURI(mappingId), false))
                .filter(Objects::nonNull);
        // @formatter:on
    }

    private boolean isKindOf(ReconnectionKind reconnectionKind, ReconnectEdgeKind reconnectEdgeKind) {
        boolean canHandle = reconnectionKind.equals(ReconnectionKind.RECONNECT_BOTH_LITERAL);

        canHandle = canHandle || reconnectionKind.equals(ReconnectionKind.RECONNECT_TARGET_LITERAL) && reconnectEdgeKind.equals(ReconnectEdgeKind.TARGET);
        canHandle = canHandle || reconnectionKind.equals(ReconnectionKind.RECONNECT_SOURCE_LITERAL) && reconnectEdgeKind.equals(ReconnectEdgeKind.SOURCE);

        return canHandle;
    }

    private boolean canReconnect(VariableManager variableManager, ReconnectEdgeDescription reconnectEdgeDescription, AQLInterpreter interpreter) {
        boolean canReconnect = true;

        String precondition = reconnectEdgeDescription.getPrecondition();

        if (precondition != null && !precondition.isBlank()) {
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), precondition);
            canReconnect = result.getStatus().compareTo(Status.WARNING) <= 0 && result.asBoolean().orElse(Boolean.FALSE);
        }

        return canReconnect;
    }

    private VariableManager createVariableManager(ReconnectionToolInterpreterData toolInterpreterData) {
        VariableManager variableManager = new VariableManager();
        variableManager.put(IInterpreterSiriusVariables.DIAGRAM, toolInterpreterData.getDiagramContext().getDiagram());
        variableManager.put(IInterpreterSiriusVariables.SOURCE, toolInterpreterData.getSemanticReconnectionSource());
        variableManager.put(IInterpreterSiriusVariables.SOURCE_VIEW, toolInterpreterData.getReconnectionSourceView());
        variableManager.put(IInterpreterSiriusVariables.TARGET, toolInterpreterData.getSemanticReconnectionTarget());
        variableManager.put(IInterpreterSiriusVariables.TARGET_VIEW, toolInterpreterData.getReconnectionTargetView());
        variableManager.put(IInterpreterSiriusVariables.ELEMENT, toolInterpreterData.getSemanticElement());
        variableManager.put("otherEnd", toolInterpreterData.getOtherEdgeEnd()); //$NON-NLS-1$
        variableManager.put("edgeView", toolInterpreterData.getEdgeView()); //$NON-NLS-1$
        variableManager.put(Environment.ENVIRONMENT, new Environment(Environment.SIRIUS_COMPONENTS));
        return variableManager;
    }

}
