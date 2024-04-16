/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IServiceProvider;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramServices;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Used to create an AQL interpreter parameterized for the view DSL.
 *
 * @author sbegaudeau
 */
@Service
public class ViewAQLInterpreterFactory implements IViewAQLInterpreterFactory {

    private final List<IJavaServiceProvider> javaServiceProviders;

    private final ApplicationContext applicationContext;

    private final Logger logger = LoggerFactory.getLogger(ViewAQLInterpreterFactory.class);

    public ViewAQLInterpreterFactory(List<IJavaServiceProvider> javaServiceProviders, ApplicationContext applicationContext) {
        this.javaServiceProviders = new ArrayList<>();
        this.javaServiceProviders.addAll(Objects.requireNonNull(javaServiceProviders));
        IServiceProvider nodeServiceProvider = (IReadOnlyQueryEnvironment queryEnvironment) -> ServiceUtils.getReceiverServices(null, Node.class).stream().toList();
        this.javaServiceProviders.add((View view) -> List.of(CanonicalServices.class, DiagramServices.class, nodeServiceProvider.getClass()));
        this.applicationContext = Objects.requireNonNull(applicationContext);
    }

    @Override
    public AQLInterpreter createInterpreter(IEditingContext editingContext, View view) {
        List<EPackage> visibleEPackages = this.getAccessibleEPackages(editingContext);
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

    private List<EPackage> getAccessibleEPackages(IEditingContext editingContext) {
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            EPackage.Registry packageRegistry = emfEditingContext.getDomain().getResourceSet().getPackageRegistry();
            return packageRegistry.values().stream()
                    .filter(EPackage.class::isInstance)
                    .map(EPackage.class::cast)
                    .toList();
        }
        return List.of();
    }
}
