/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import java.util.stream.Stream;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.components.emf.services.IEditingContextEPackageService;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingDomainFactory;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * This class is used to create the editing domain used of the editing context.
 * It instantiates the ResourceSet with the right configuration.
 *
 * @author lfasani
 */
@Service
public class EditingDomainFactory implements IEditingDomainFactory {

    private final Logger logger = LoggerFactory.getLogger(EditingDomainFactory.class);

    private final IEditingContextEPackageService editingContextEPackageService;

    private final ComposedAdapterFactory composedAdapterFactory;

    private final EPackage.Registry globalEPackageRegistry;

    public EditingDomainFactory(IEditingContextEPackageService editingContextEPackageService, ComposedAdapterFactory composedAdapterFactory, EPackage.Registry globalEPackageRegistry) {
        this.editingContextEPackageService = Objects.requireNonNull(editingContextEPackageService);
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
        this.globalEPackageRegistry = Objects.requireNonNull(globalEPackageRegistry);
    }

    @Override
    public AdapterFactoryEditingDomain createEditingDomain(Project project) {
        AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(this.composedAdapterFactory, new BasicCommandStack());
        ResourceSet resourceSet = editingDomain.getResourceSet();
        resourceSet.getLoadOptions().put(JsonResource.OPTION_EXTENDED_META_DATA, new BasicExtendedMetaData(resourceSet.getPackageRegistry()));
        resourceSet.getLoadOptions().put(JsonResource.OPTION_SCHEMA_LOCATION, true);

        EPackageRegistryImpl ePackageRegistry = new EPackageRegistryImpl();
        List<EPackage> additionalEPackages = this.editingContextEPackageService.getEPackages(project.getId().toString());

        Stream.concat(this.findGlobalEPackages(), additionalEPackages.stream())
                .forEach(ePackage -> ePackageRegistry.put(ePackage.getNsURI(), ePackage));
        resourceSet.setPackageRegistry(ePackageRegistry);

        return editingDomain;
    }

    private Stream<EPackage> findGlobalEPackages() {
        return this.globalEPackageRegistry.values().stream()
                .filter(EPackage.class::isInstance)
                .map(EPackage.class::cast);
    }
}
