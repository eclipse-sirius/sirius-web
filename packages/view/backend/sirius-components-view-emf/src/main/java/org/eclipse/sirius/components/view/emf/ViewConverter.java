/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.View;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewConverter.class);

    private final List<IJavaServiceProvider> javaServiceProviders;

    private final List<IRepresentationDescriptionConverter> representationDescriptionConverters;

    private final Optional<ApplicationContext> optionalApplicationContext;

    public ViewConverter(List<IJavaServiceProvider> javaServiceProviders, List<IRepresentationDescriptionConverter> representationDescriptionConverters, ApplicationContext applicationContext) {
        this.javaServiceProviders = Objects.requireNonNull(javaServiceProviders);
        this.representationDescriptionConverters = Objects.requireNonNull(representationDescriptionConverters);
        this.optionalApplicationContext = Optional.ofNullable(applicationContext);
    }

    /**
     * Extract and convert the {@link IRepresentationDescription} from a {@link View} model by delegating to provided
     * {@link IRepresentationDescriptionConverter}.
     */
    @Override
    public List<IRepresentationDescription> convert(View view, List<EPackage> visibleEPackages) {
        List<IRepresentationDescription> result = List.of();
        AQLInterpreter interpreter = this.createInterpreter(view, visibleEPackages);
        try {
            // @formatter:off
            result = view.getDescriptions().stream()
                    .map(representationDescription -> this.convert(representationDescription, interpreter))
                    .flatMap(Optional::stream)
                    .collect(Collectors.toList());

            // @formatter:on
        } catch (NullPointerException e) {
            // Can easily happen if the View model is currently invalid/inconsistent, typically because it is
            // currently being created or edited.
        }
        return result;
    }

    private Optional<IRepresentationDescription> convert(RepresentationDescription representationDescription, AQLInterpreter aqlInterpreter) {
        // @formatter:off
        return this.representationDescriptionConverters.stream()
                .filter(converter -> converter.canConvert(representationDescription))
                .map(converter -> converter.convert(representationDescription, aqlInterpreter))
                .findFirst();
        // @formatter:on
    }

    private AQLInterpreter createInterpreter(View view, List<EPackage> visibleEPackages) {
        if (this.optionalApplicationContext.isPresent()) {
            ApplicationContext applicationContext = this.optionalApplicationContext.get();
            AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
            // @formatter:off
            List<Object> serviceInstances = this.javaServiceProviders.stream()
                    .flatMap(provider -> provider.getServiceClasses(view).stream())
                    .map(serviceClass -> {
                        try {
                            return beanFactory.createBean(serviceClass);
                        } catch (BeansException beansException) {
                            LOGGER.error("Error while trying to instantiate Java service class " + serviceClass.getName(), beansException); //$NON-NLS-1$
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            // @formatter:on
            return new AQLInterpreter(List.of(), serviceInstances, visibleEPackages);
        } else {
            // @formatter:off
            List<Class<?>> serviceClasses = this.javaServiceProviders.stream()
                                                .flatMap(provider -> provider.getServiceClasses(view).stream())
                                                .collect(Collectors.toList());
            // @formatter:on
            return new AQLInterpreter(serviceClasses, visibleEPackages);
        }

    }
}
