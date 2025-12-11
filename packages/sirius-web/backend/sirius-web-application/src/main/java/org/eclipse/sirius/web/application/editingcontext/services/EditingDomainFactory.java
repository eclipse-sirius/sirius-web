/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
package org.eclipse.sirius.web.application.editingcontext.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory.Descriptor;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingDomainFactory;
import org.springframework.stereotype.Service;

/**
 * This class is used to create the editing domain used of the editing context.
 * It instantiates the ResourceSet with the right configuration.
 *
 * @author lfasani
 */
@Service
public class EditingDomainFactory implements IEditingDomainFactory {

    private final List<Descriptor> composedAdapterFactoryDescriptors;

    private final EPackage.Registry globalEPackageRegistry;

    public EditingDomainFactory(List<Descriptor> composedAdapterFactoryDescriptors, EPackage.Registry globalEPackageRegistry) {
        this.composedAdapterFactoryDescriptors = Objects.requireNonNull(composedAdapterFactoryDescriptors);
        this.globalEPackageRegistry = Objects.requireNonNull(globalEPackageRegistry);
    }

    @Override
    public AdapterFactoryEditingDomain createEditingDomain() {
        List<AdapterFactory> adapterFactories = this.composedAdapterFactoryDescriptors.stream()
                .map(Descriptor::createAdapterFactory)
                .toList();
        var composedAdapterFactory = new ComposedAdapterFactory(adapterFactories);

        AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(composedAdapterFactory, new BasicCommandStack());
        ResourceSet resourceSet = editingDomain.getResourceSet();
        resourceSet.getLoadOptions().put(JsonResource.OPTION_EXTENDED_META_DATA, new BasicExtendedMetaData(resourceSet.getPackageRegistry()));
        resourceSet.getLoadOptions().put(JsonResource.OPTION_SCHEMA_LOCATION, true);

        EPackageRegistryImpl ePackageRegistry = new EPackageRegistryImpl();
        var globalEPackages = this.globalEPackageRegistry.values().stream()
                .filter(EPackage.class::isInstance)
                .map(EPackage.class::cast);
        globalEPackages.forEach(ePackage -> ePackageRegistry.put(ePackage.getNsURI(), ePackage));

        resourceSet.setPackageRegistry(ePackageRegistry);


        return editingDomain;
    }
}
