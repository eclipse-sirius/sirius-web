/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo.
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
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DialogDescription;
import org.eclipse.sirius.components.view.emf.api.IDialogDescriptionConverter;
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

    private final List<IDialogDescriptionConverter> dialogDescriptionConverts;

    public ViewConverter(List<IJavaServiceProvider> javaServiceProviders, List<IRepresentationDescriptionConverter> representationDescriptionConverters, ApplicationContext applicationContext, List<IDialogDescriptionConverter> dialogDescriptionConverts) {
        this.javaServiceProviders = new ArrayList<>();
        this.javaServiceProviders.addAll(Objects.requireNonNull(javaServiceProviders));
        IServiceProvider nodeServiceProvider = (IReadOnlyQueryEnvironment queryEnvironment, boolean forWorkspace) -> ServiceUtils.getReceiverServices(null, Node.class).stream().toList();
        IServiceProvider treeItemServiceProvider = (IReadOnlyQueryEnvironment queryEnvironment, boolean forWorkspace) -> ServiceUtils.getReceiverServices(null, TreeItem.class).stream().toList();
        this.javaServiceProviders.add((View view) -> List.of(CanonicalServices.class, DiagramServices.class, nodeServiceProvider.getClass(), treeItemServiceProvider.getClass()));
        this.representationDescriptionConverters = Objects.requireNonNull(representationDescriptionConverters);
        this.applicationContext = Objects.requireNonNull(applicationContext);
        this.dialogDescriptionConverts = Objects.requireNonNull(dialogDescriptionConverts);
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

                result.addAll(this.convertDialogDescriptions(view, interpreter));

            } catch (NullPointerException exception) {
                // Can easily happen if the View model is currently invalid/inconsistent, typically because it is
                // currently being created or edited.
                this.logger.debug("Exception while converting view", exception);
            }
        });
        return result;
    }

    private List<IRepresentationDescription> convertDialogDescriptions(View view, AQLInterpreter interpreter) {
        List<IRepresentationDescription> representationDescriptions = new ArrayList<>();
        view.getDescriptions().stream()
                .filter(DiagramDescription.class::isInstance)
                .flatMap(this::getAllContent)
                .filter(org.eclipse.sirius.components.view.diagram.DialogDescription.class::isInstance)
                .map(org.eclipse.sirius.components.view.diagram.DialogDescription.class::cast)
                .forEach(dialogDescription -> this.convertDialogDescription(dialogDescription, interpreter, representationDescriptions));
        return representationDescriptions;
    }

    private void convertDialogDescription(DialogDescription dialogDescription, AQLInterpreter interpreter, List<IRepresentationDescription> representationDescriptions) {
        this.dialogDescriptionConverts.stream()
                .filter(converter -> converter.canConvert(dialogDescription))
                .findFirst()
                .map(converter -> converter.convert(dialogDescription, interpreter))
                .stream()
                .flatMap(List::stream)
                .forEach(representationDescriptions::add);
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
