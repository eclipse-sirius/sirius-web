/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo and others.
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
package org.eclipse.sirius.web.emf.configuration;

import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.EValidator.Registry;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.impl.EValidatorRegistryImpl;
import org.eclipse.emf.ecore.util.EcoreAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.sirius.common.tools.internal.ecore.EPackageHelper;
import org.eclipse.sirius.web.domain.DomainPackage;
import org.eclipse.sirius.web.emf.domain.DomainValidator;
import org.eclipse.sirius.web.emf.services.ILabelFeatureProvider;
import org.eclipse.sirius.web.emf.services.ISuggestedRootObjectTypesProvider;
import org.eclipse.sirius.web.emf.services.LabelFeatureProviderRegistry;
import org.eclipse.sirius.web.emf.view.ViewValidator;
import org.eclipse.sirius.web.view.ViewPackage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of the EMF beans.
 *
 * @author sbegaudeau
 */
@Configuration
public class EMFConfiguration {
    @Bean
    public ComposedAdapterFactory composedAdapterFactory(List<AdapterFactory> adapterFactories) {
        ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(adapterFactories);
        composedAdapterFactory.addAdapterFactory(new EcoreAdapterFactory());
        composedAdapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
        return composedAdapterFactory;
    }

    @Bean
    public EPackage.Registry ePackageRegistry(List<EPackage> ePackages) {
        EPackage.Registry ePackageRegistry = new EPackageRegistryImpl();
        ePackages.forEach(ePackage -> ePackageRegistry.put(ePackage.getNsURI(), ePackage));
        return ePackageRegistry;
    }

    @Bean
    public LabelFeatureProviderRegistry labelFeatureProviderRegistry(List<ILabelFeatureProvider> providers) {
        LabelFeatureProviderRegistry providerRegistry = new LabelFeatureProviderRegistry();
        providers.forEach(provider -> providerRegistry.put(provider.getEPackageNsUri(), provider));
        return providerRegistry;
    }

    @Bean
    public ISuggestedRootObjectTypesProvider suggestedRootObjectTypesProvider() {
        return EPackageHelper::getEClassRootElements;
    }

    @Bean
    public EValidator.Registry getEValidatorRegistry() {
        Registry eValidatorRegistry = new EValidatorRegistryImpl(EValidator.Registry.INSTANCE);
        eValidatorRegistry.put(DomainPackage.eINSTANCE, new DomainValidator());
        eValidatorRegistry.put(ViewPackage.eINSTANCE, new ViewValidator());
        return eValidatorRegistry;
    }
}
