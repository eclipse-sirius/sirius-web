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
import java.util.Optional;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.sirius.components.collaborative.diagrams.api.IReconnectionToolsExecutor;
import org.eclipse.sirius.components.collaborative.diagrams.api.ReconnectionToolInterpreterData;
import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IURLParser;
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
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IJavaServiceProvider;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
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

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    private final List<IJavaServiceProvider> javaServiceProviders;

    private final ApplicationContext applicationContext;

    private final IURLParser urlParser;

    private final Logger logger = LoggerFactory.getLogger(ViewReconnectionToolsExecutor.class);

    public ViewReconnectionToolsExecutor(IObjectService objectService, IEditService editService, IIdentifierProvider identifierProvider,
            IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService, List<IJavaServiceProvider> javaServiceProviders, ApplicationContext applicationContext, IURLParser urlParser) {
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
        this.javaServiceProviders = Objects.requireNonNull(javaServiceProviders);
        this.applicationContext = Objects.requireNonNull(applicationContext);
        this.urlParser = Objects.requireNonNull(urlParser);
    }

    @Override
    public boolean canExecute(DiagramDescription diagramDescription) {
        if (diagramDescription.getId().startsWith(IDiagramIdProvider.DIAGRAM_DESCRIPTION_KIND)) {
            Map<String, List<String>> parameters = this.urlParser.getParameterValues(diagramDescription.getId());
            List<String> values = Optional.ofNullable(parameters.get(IDiagramIdProvider.SOURCE_KIND)).orElse(List.of());
            return values.contains(IDiagramIdProvider.VIEW_SOURCE_KIND);
        }
        return false;
    }

    @Override
    public IStatus execute(IEditingContext editingContext, ReconnectionToolInterpreterData toolInterpreterData, Edge edge, EdgeDescription edgeDescription, ReconnectEdgeKind reconnectEdgeKind,
            DiagramDescription diagramDescription) {
        IStatus status = new Failure("");

        var optionalDiagramDescription = this.viewRepresentationDescriptionSearchService.findById(diagramDescription.getId())
                .filter(org.eclipse.sirius.components.view.DiagramDescription.class::isInstance)
                .map(org.eclipse.sirius.components.view.DiagramDescription.class::cast);
        if (optionalDiagramDescription.isPresent()) {
            org.eclipse.sirius.components.view.DiagramDescription viewDiagramDescription = optionalDiagramDescription.get();
            var optionalViewEdgeDescription = viewDiagramDescription.getEdgeDescriptions().stream().filter(viewEdgeDescription -> {
                Map<String, List<String>> parameters = this.urlParser.getParameterValues(diagramDescription.getId());
                List<String> values = Optional.ofNullable(parameters.get(IDiagramIdProvider.SOURCE_ID)).orElse(List.of());
                Optional<String> sourceId = values.stream().findFirst();

                if (sourceId.isPresent()) {
                    String sourceElementId = this.objectService.getId(viewEdgeDescription);
                    String formattedEdgeDescriptionId = IDiagramIdProvider.EDGE_DESCRIPTION_KIND + '?' + IDiagramIdProvider.SOURCE_KIND + '=' + IDiagramIdProvider.VIEW_SOURCE_KIND + '&' + IDiagramIdProvider.SOURCE_ID + '=' + sourceId.get() + '&' + IDiagramIdProvider.SOURCE_ELEMENT_ID + '=' + sourceElementId;
                    return edge.getDescriptionId().equals(formattedEdgeDescriptionId);
                } else {
                    return false;
                }
            }).findFirst();

            if (optionalViewEdgeDescription.isPresent()) {
                var viewEdgeDescription = optionalViewEdgeDescription.get();

                List<org.eclipse.sirius.components.view.EdgeReconnectionTool> edgeReconnectionTools = new ToolFinder().findReconnectionTools(viewEdgeDescription, toolInterpreterData.getKind());
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
            return packageRegistry.values().stream()
                    .filter(EPackage.class::isInstance)
                    .map(EPackage.class::cast)
                    .toList();
        } else {
            return List.of();
        }
    }

    private AQLInterpreter createInterpreter(View view, List<EPackage> visibleEPackages) {
        AutowireCapableBeanFactory beanFactory = this.applicationContext.getAutowireCapableBeanFactory();
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
        return new AQLInterpreter(List.of(), serviceInstances, visibleEPackages);
    }

}
