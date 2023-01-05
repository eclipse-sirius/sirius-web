/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.view.emf.diagram;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.diagrams.api.IReconnectionToolsExecutor;
import org.eclipse.sirius.components.collaborative.diagrams.api.ReconnectionToolInterpreterData;
import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeKind;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.SourceEdgeEndReconnectionTool;
import org.eclipse.sirius.components.view.TargetEdgeEndReconnectionTool;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IJavaServiceProvider;
import org.eclipse.sirius.components.view.emf.IViewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * The reconnection tools executor for the view layer.
 *
 * @author gcoutable
 */
@Service
public class ViewReconnectionToolsExecutor implements IReconnectionToolsExecutor {

    private final IObjectService objectService;

    private final IEditService editService;

    private final IIdentifierProvider identifierProvider;

    private final IViewService viewService;

    private final List<IJavaServiceProvider> javaServiceProviders;

    private final ApplicationContext applicationContext;

    private final Logger logger = LoggerFactory.getLogger(ViewReconnectionToolsExecutor.class);

    public ViewReconnectionToolsExecutor(IObjectService objectService, IEditService editService, IIdentifierProvider identifierProvider, IViewService viewService,
            List<IJavaServiceProvider> javaServiceProviders, ApplicationContext applicationContext) {
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.viewService = Objects.requireNonNull(viewService);
        this.javaServiceProviders = Objects.requireNonNull(javaServiceProviders);
        this.applicationContext = Objects.requireNonNull(applicationContext);
    }

    @Override
    public boolean canExecute(DiagramDescription diagramDescription) {
        return this.identifierProvider.findVsmElementId(diagramDescription.getId()).isEmpty();
    }

    @Override
    public IStatus execute(IEditingContext editingContext, ReconnectionToolInterpreterData toolInterpreterData, Edge edge, EdgeDescription edgeDescription, ReconnectEdgeKind reconnectEdgeKind,
            DiagramDescription diagramDescription) {
        IStatus status = new Failure("");

        // @formatter:off
        var optionalDiagramDescription = this.viewService.getRepresentationDescription(diagramDescription.getId())
                .filter(org.eclipse.sirius.components.view.DiagramDescription.class::isInstance)
                .map(org.eclipse.sirius.components.view.DiagramDescription.class::cast);
        // @formatter:on
        if (optionalDiagramDescription.isPresent()) {
            org.eclipse.sirius.components.view.DiagramDescription viewDiagramDescription = optionalDiagramDescription.get();
            var optionalViewEdgeDescription = viewDiagramDescription.getEdgeDescriptions().stream().filter(viewEdgeDescription -> {
                var edgeDescriptionId = UUID.nameUUIDFromBytes(EcoreUtil.getURI(viewEdgeDescription).toString().getBytes());
                return edge.getDescriptionId().equals(edgeDescriptionId);
            }).findFirst();

            if (optionalViewEdgeDescription.isPresent()) {
                var viewEdgeDescription = optionalViewEdgeDescription.get();

                List<org.eclipse.sirius.components.view.EdgeReconnectionTool> edgeReconnectionTools = List.of();
                if (toolInterpreterData.getKind() == ReconnectEdgeKind.SOURCE) {
                    edgeReconnectionTools = viewEdgeDescription.getReconnectEdgeTools().stream().filter(SourceEdgeEndReconnectionTool.class::isInstance).toList();
                } else {
                    edgeReconnectionTools = viewEdgeDescription.getReconnectEdgeTools().stream().filter(TargetEdgeEndReconnectionTool.class::isInstance).toList();
                }

                for (org.eclipse.sirius.components.view.EdgeReconnectionTool edgeReconnectionTool : edgeReconnectionTools) {
                    VariableManager variableManager = this.createVariableManager(toolInterpreterData, editingContext);

                    AQLInterpreter interpreter = this.createInterpreter((View) viewDiagramDescription.eContainer(), this.getAccessibleEPackages(editingContext));
                    var diagramOperationInterpreter = new DiagramOperationInterpreter(interpreter, this.objectService, this.editService, toolInterpreterData.getDiagramContext(), Map.of());
                    diagramOperationInterpreter.executeOperations(edgeReconnectionTool.getBody(), variableManager);
                }

                status = new Success();
            }
        }
        return status;
    }

    private VariableManager createVariableManager(ReconnectionToolInterpreterData toolInterpreterData, IEditingContext editingContext) {
        VariableManager variableManager = new VariableManager();
        variableManager.put("diagram", toolInterpreterData.getDiagramContext().getDiagram());
        variableManager.put("semanticReconnectionSource", toolInterpreterData.getSemanticReconnectionSource());
        variableManager.put("reconnectionSourceView", toolInterpreterData.getReconnectionSourceView());
        variableManager.put("semanticReconnectionTarget", toolInterpreterData.getSemanticReconnectionTarget());
        variableManager.put("reconnectionTargetView", toolInterpreterData.getReconnectionTargetView());
        variableManager.put("edgeSemanticElement", toolInterpreterData.getSemanticElement());
        variableManager.put("otherEnd", toolInterpreterData.getOtherEdgeEnd());
        variableManager.put("semanticOtherEnd", toolInterpreterData.getSemanticOtherEdgeEnd());
        variableManager.put("edgeView", toolInterpreterData.getEdgeView());
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
        return variableManager;
    }

    private List<EPackage> getAccessibleEPackages(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext) {
            Registry packageRegistry = ((EditingContext) editingContext).getDomain().getResourceSet().getPackageRegistry();
            // @formatter:off
            return packageRegistry.values().stream()
                                  .filter(EPackage.class::isInstance)
                                  .map(EPackage.class::cast)
                                  .toList();
            // @formatter:on
        } else {
            return List.of();
        }
    }

    private AQLInterpreter createInterpreter(View view, List<EPackage> visibleEPackages) {
        AutowireCapableBeanFactory beanFactory = this.applicationContext.getAutowireCapableBeanFactory();
        // @formatter:off
        List<Object> serviceInstances = this.javaServiceProviders.stream()
                .flatMap(provider -> provider.getServiceClasses(view).stream())
                .map(serviceClass -> {
                    try {
                        return beanFactory.createBean(serviceClass);
                    } catch (BeansException beansException) {
                        this.logger.warn("Error while trying to instantiate Java service class " + serviceClass.getName(), beansException);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .map(Object.class::cast)
                .toList();
        // @formatter:on
        return new AQLInterpreter(List.of(), serviceInstances, visibleEPackages);
    }

}
