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
package org.eclipse.sirius.components.view.emf;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.core.api.IDynamicDialogDescription;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.selection.description.SelectionDescription;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.DynamicDialogDescription;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.dynamicdialogs.ViewDynamicDialogDescriptionConverter;
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

    private final ViewDynamicDialogDescriptionConverter viewDynamicDialogDescriptionConverter;

    public ViewConverter(List<IJavaServiceProvider> javaServiceProviders, List<IRepresentationDescriptionConverter> representationDescriptionConverters, ApplicationContext applicationContext,
            IObjectService objectService, ViewDynamicDialogDescriptionConverter viewDynamicDialogDescriptionConverter) {
        this.javaServiceProviders = new ArrayList<>();
        this.javaServiceProviders.addAll(Objects.requireNonNull(javaServiceProviders));
        this.javaServiceProviders.add((View view) -> List.of(CanonicalServices.class));
        this.representationDescriptionConverters = Objects.requireNonNull(representationDescriptionConverters);
        this.applicationContext = Objects.requireNonNull(applicationContext);
        this.objectService = Objects.requireNonNull(objectService);
        this.viewDynamicDialogDescriptionConverter = Objects.requireNonNull(viewDynamicDialogDescriptionConverter);
    }

    /**
     * Extract and convert the {@link IRepresentationDescription} from a list of {@link View} models by delegating to
     * provided {@link IRepresentationDescriptionConverter}.
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
            .filter(org.eclipse.sirius.components.view.SelectionDescription.class::isInstance)
            .map(org.eclipse.sirius.components.view.SelectionDescription.class::cast)
            .map(selectionDescription -> this.convertSelectionDialog(selectionDescription, interpreter))
            .toList();
    }

    private IRepresentationDescription convertSelectionDialog(org.eclipse.sirius.components.view.SelectionDescription selectionDescription, AQLInterpreter interpreter) {
        String selectionDescriptionId = this.objectService.getId(selectionDescription);

        return SelectionDescription.newSelectionDescription(selectionDescriptionId)
                .objectsProvider(variableManager -> {
                    Result result = interpreter.evaluateExpression(variableManager.getVariables(), selectionDescription.getSelectionCandidatesExpression());
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
                .idProvider(new GetOrCreateRandomIdProvider())
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

    @Override
    public List<IDynamicDialogDescription> convertDynamicDialog(List<View> views, List<EPackage> visibleEPackages) {
        List<IDynamicDialogDescription> result = new ArrayList<>();

        views.forEach(view -> {
            AQLInterpreter interpreter = this.createInterpreter(view, visibleEPackages);
            try {
                // @formatter:off
                view.getDynamicDialogFolder().eAllContents().forEachRemaining(eObject-> {
                    if (eObject instanceof DynamicDialogDescription dynamicDialogDescription) {
                        result.add(this.convert(dynamicDialogDescription, interpreter));
                    }
                });
                // @formatter:on
            } catch (NullPointerException e) {
                // Can easily happen if the View model is currently invalid/inconsistent, typically because it is
                // currently being created or edited.
            }
        });
        return result;
    }


    /**
     * @param dynamicDialogDescription
     * @param interpreter
     * @return
     */
    private IDynamicDialogDescription convert(org.eclipse.sirius.components.view.DynamicDialogDescription dynamicDialogDescription, AQLInterpreter interpreter) {
        return this.viewDynamicDialogDescriptionConverter.convert(dynamicDialogDescription, interpreter);
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
