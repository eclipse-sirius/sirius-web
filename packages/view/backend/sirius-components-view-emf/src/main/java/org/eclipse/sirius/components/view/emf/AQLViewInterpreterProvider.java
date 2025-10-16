/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IServiceProvider;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramServices;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.api.IViewInterpreterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Get an AQL interpreter correctly initialized.
 *
 * @author mcharfadi
 */
@Service
public class AQLViewInterpreterProvider implements IViewInterpreterProvider {

    private final Logger logger = LoggerFactory.getLogger(AQLViewInterpreterProvider.class);

    private final List<IJavaServiceProvider> javaServiceProviders;

    private final ApplicationContext applicationContext;

    public AQLViewInterpreterProvider(List<IJavaServiceProvider> javaServiceProviders, ApplicationContext applicationContext) {
        this.javaServiceProviders = new ArrayList<>();
        this.javaServiceProviders.addAll(Objects.requireNonNull(javaServiceProviders));
        IServiceProvider nodeServiceProvider = (IReadOnlyQueryEnvironment queryEnvironment, boolean forWorkspace) -> ServiceUtils.getReceiverServices(null, Node.class).stream().toList();
        IServiceProvider treeItemServiceProvider = (IReadOnlyQueryEnvironment queryEnvironment, boolean forWorkspace) -> ServiceUtils.getReceiverServices(null, TreeItem.class).stream().toList();
        this.javaServiceProviders.add((View view) -> List.of(CanonicalServices.class, DiagramServices.class, nodeServiceProvider.getClass(), treeItemServiceProvider.getClass()));
        this.applicationContext = Objects.requireNonNull(applicationContext);
    }

    @Override
    public AQLInterpreter createInterpreter(IEMFEditingContext editingContext, View view) {
        var visibleEPackages = editingContext.getDomain().getResourceSet().getPackageRegistry().values().stream()
                .filter(EPackage.class::isInstance)
                .map(EPackage.class::cast)
                .toList();

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
