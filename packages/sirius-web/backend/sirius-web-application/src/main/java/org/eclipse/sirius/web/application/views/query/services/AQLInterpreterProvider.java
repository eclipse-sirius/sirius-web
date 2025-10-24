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
package org.eclipse.sirius.web.application.views.query.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.query.EditingContextServices;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.api.IInterpreter;
import org.eclipse.sirius.web.application.views.query.services.api.IInterpreterJavaServiceProvider;
import org.eclipse.sirius.web.application.views.query.services.api.IInterpreterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Used to provide the AQL interpreter.
 *
 * @author sbegaudeau
 */
@Service
public class AQLInterpreterProvider implements IInterpreterProvider {

    private final List<IInterpreterJavaServiceProvider> interpreterJavaServiceProviders;

    private final ApplicationContext applicationContext;

    private final Logger logger = LoggerFactory.getLogger(AQLInterpreterProvider.class);

    public AQLInterpreterProvider(List<IInterpreterJavaServiceProvider> interpreterJavaServiceProviders, ApplicationContext applicationContext) {
        this.interpreterJavaServiceProviders = Objects.requireNonNull(interpreterJavaServiceProviders);
        this.applicationContext = Objects.requireNonNull(applicationContext);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String expression) {
        return expression != null && (expression.startsWith("aql:")
                || expression.startsWith("var:")
                || expression.startsWith("feature"));
    }

    @Override
    public IInterpreter getInterpreter(IEditingContext editingContext) {
        var ePackages = this.getEPackages(editingContext);
        var services = this.getServices(editingContext);
        return new AQLInterpreter(List.of(EditingContextServices.class), services, ePackages);
    }

    private List<EPackage> getEPackages(IEditingContext editingContext) {
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            EPackage.Registry packageRegistry = emfEditingContext.getDomain().getResourceSet().getPackageRegistry();
            return packageRegistry.values().stream()
                    .filter(EPackage.class::isInstance)
                    .map(EPackage.class::cast)
                    .toList();
        }
        return List.of();
    }

    private List<Object> getServices(IEditingContext editingContext) {
        AutowireCapableBeanFactory beanFactory = this.applicationContext.getAutowireCapableBeanFactory();
        return this.interpreterJavaServiceProviders.stream()
                .flatMap(provider -> provider.getServiceClasses(editingContext).stream())
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
    }
}
