/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.components.view.emf;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IServiceProvider;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramServices;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.selection.Selection;
import org.eclipse.sirius.components.selection.description.SelectionDescription;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Converts a View into an equivalent list of {@link RepresentationDescription}.
 *
 * @author pcdavid
 */
@Service
public class ViewConverter implements IViewConverter {

    private final Logger logger = LoggerFactory.getLogger(ViewConverter.class);

    private final List<IJavaServiceProvider> javaServiceProviders;

    private final List<IRepresentationDescriptionConverter> representationDescriptionConverters;

    private final ApplicationContext applicationContext;

    private final IObjectService objectService;

    private final IDiagramIdProvider diagramIdProvider;

    public ViewConverter(List<IJavaServiceProvider> javaServiceProviders, List<IRepresentationDescriptionConverter> representationDescriptionConverters, ApplicationContext applicationContext, IObjectService objectService, IDiagramIdProvider diagramIdProvider) {
        this.javaServiceProviders = new ArrayList<>();
        this.javaServiceProviders.addAll(Objects.requireNonNull(javaServiceProviders));
        IServiceProvider nodeServiceProvider = (IReadOnlyQueryEnvironment queryEnvironment) -> ServiceUtils.getReceiverServices(null, Node.class).stream().toList();
        this.javaServiceProviders.add((View view) -> List.of(CanonicalServices.class, DiagramServices.class, nodeServiceProvider.getClass()));
        this.representationDescriptionConverters = Objects.requireNonNull(representationDescriptionConverters);
        this.applicationContext = Objects.requireNonNull(applicationContext);
        this.objectService = Objects.requireNonNull(objectService);
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
    }

    /**
     * Extract and convert the {@link IRepresentationDescription} from a list of {@link View} models by delegating to provided
     * {@link IRepresentationDescriptionConverter}.
     */
    @Override
    public List<IRepresentationDescription> convert(List<View> views, List<EPackage> visibleEPackages) {
        List<IRepresentationDescription> result = new ArrayList<>();
        List<RepresentationDescription> allViewsRepresentationDescriptions = views.stream().flatMap(v -> v.getDescriptions().stream()).toList();
        views.forEach(view -> {
            AQLInterpreter interpreter = this.createInterpreter(view, visibleEPackages);
            try {
                result.addAll(view.getDescriptions().stream()
                        .map(representationDescription -> this.convert(representationDescription, allViewsRepresentationDescriptions, interpreter))
                        .flatMap(Optional::stream)
                        .toList());

                result.addAll(this.convertSelectionsDialogs(view, interpreter));

            } catch (NullPointerException exception) {
                // Can easily happen if the View model is currently invalid/inconsistent, typically because it is
                // currently being created or edited.
                this.logger.debug("Exception while converting view", exception);
            }
        });
        return result;
    }

    private List<IRepresentationDescription> convertSelectionsDialogs(View view, AQLInterpreter interpreter) {
        return view.getDescriptions().stream().filter(DiagramDescription.class::isInstance)
            .flatMap(this::getAllContent)
            .filter(org.eclipse.sirius.components.view.diagram.SelectionDialogDescription.class::isInstance)
            .map(org.eclipse.sirius.components.view.diagram.SelectionDialogDescription.class::cast)
            .map(selectionDescription -> this.convertSelectionDialog(selectionDescription, interpreter))
            .toList();
    }

    private IRepresentationDescription convertSelectionDialog(org.eclipse.sirius.components.view.diagram.SelectionDialogDescription selectionDescription, AQLInterpreter interpreter) {
        String selectionDescriptionId = this.diagramIdProvider.getId(selectionDescription);
        return SelectionDescription.newSelectionDescription(selectionDescriptionId)
                .objectsProvider(variableManager -> {
                    String expression = Optional.ofNullable(selectionDescription.getSelectionDialogTreeDescription())
                            .map(SelectionDialogTreeDescription::getElementsExpression)
                            .orElse("");
                    Result result = interpreter.evaluateExpression(variableManager.getVariables(), expression);
                    return result.asObjects().orElse(List.of()).stream()
                            .filter(Objects::nonNull)
                            .toList();
                })
                .messageProvider(variableManager -> {
                    String message = selectionDescription.getSelectionMessage();
                    if (message == null) {
                        message = "";
                    }
                    return message;
                })
                .idProvider(variableManager -> Selection.PREFIX)
                .labelProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getLabel).orElse(null))
                .iconURLProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getImagePath).orElse(null))
                .targetObjectIdProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(null))
                .selectionObjectsIdProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(null))
                .label("Selection Description")
                .canCreatePredicate(variableManager -> false)
                .build();
    }

    private Stream<EObject> getAllContent(EObject representationDescription) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(representationDescription.eAllContents(), Spliterator.ORDERED), false);
    }

    private Optional<IRepresentationDescription> convert(RepresentationDescription representationDescription, List<RepresentationDescription> allViewsRepresentationDescriptions, AQLInterpreter aqlInterpreter) {
        return this.representationDescriptionConverters.stream()
                .filter(converter -> converter.canConvert(representationDescription))
                .map(converter -> converter.convert(representationDescription, allViewsRepresentationDescriptions, aqlInterpreter))
                .findFirst();
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
