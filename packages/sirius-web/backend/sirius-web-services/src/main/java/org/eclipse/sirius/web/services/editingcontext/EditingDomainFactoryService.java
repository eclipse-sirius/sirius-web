/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.web.services.editingcontext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.components.domain.DomainPackage;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.emf.services.IEditingContextEPackageService;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.diagram.adapters.DiagramColorAdapter;
import org.eclipse.sirius.components.view.form.adapters.FormColorAdapter;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.services.api.projects.Nature;
import org.eclipse.sirius.web.services.editingcontext.api.IEditingDomainFactoryService;
import org.eclipse.sirius.web.services.projects.api.IEditingContextMetadataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

/**
 * This class is used to create the editing domain used as editing context.</br> It instantiates the ResourceSet with the right configuration.
 *
 * @author lfasani
 */
@Service
public class EditingDomainFactoryService implements IEditingDomainFactoryService {

    private final Logger logger = LoggerFactory.getLogger(EditingDomainFactoryService.class);

    private final IEditingContextEPackageService editingContextEPackageService;

    private final IEditingContextMetadataProvider editingContextMetadataProvider;

    private final ComposedAdapterFactory composedAdapterFactory;

    private final EPackage.Registry globalEPackageRegistry;

    private final Optional<Registry> optionalResourceFactoryRegistry;

    public EditingDomainFactoryService(IEditingContextEPackageService editingContextEPackageService, IEditingContextMetadataProvider editingContextMetadataProvider,
            ComposedAdapterFactory composedAdapterFactory, EPackage.Registry globalEPackageRegistry, Optional<Resource.Factory.Registry> optionalResourceFactoryRegistry) {
        this.editingContextEPackageService = Objects.requireNonNull(editingContextEPackageService);
        this.editingContextMetadataProvider = Objects.requireNonNull(editingContextMetadataProvider);
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
        this.globalEPackageRegistry = Objects.requireNonNull(globalEPackageRegistry);
        this.optionalResourceFactoryRegistry = optionalResourceFactoryRegistry;
    }

    @Override
    public AdapterFactoryEditingDomain createEditingDomain(String editingContextId) {
        AdapterFactoryEditingDomain editingDomain = new AdapterFactoryEditingDomain(this.composedAdapterFactory, new BasicCommandStack());
        ResourceSet resourceSet = editingDomain.getResourceSet();
        resourceSet.getLoadOptions().put(JsonResource.OPTION_EXTENDED_META_DATA, new BasicExtendedMetaData(resourceSet.getPackageRegistry()));
        resourceSet.getLoadOptions().put(JsonResource.OPTION_SCHEMA_LOCATION, true);

        var isStudioProjectNature = this.editingContextMetadataProvider.getMetadata(editingContextId).natures().stream().map(Nature::natureId)
                .anyMatch("siriusComponents://nature?kind=studio"::equals);

        EPackageRegistryImpl ePackageRegistry = new EPackageRegistryImpl();
        List<EPackage> additionalEPackages = this.editingContextEPackageService.getEPackages(editingContextId);

        Stream.concat(this.findGlobalEPackages(), additionalEPackages.stream())
                .filter(ePackage -> isStudioProjectNature || !List.of(DomainPackage.eNS_URI, ViewPackage.eNS_URI).contains(ePackage.getNsURI()))
                .forEach(ePackage -> ePackageRegistry.put(ePackage.getNsURI(), ePackage));
        resourceSet.setPackageRegistry(ePackageRegistry);

        if (isStudioProjectNature) {
            Optional<View> optionalView = this.loadStudioColorPalettes(resourceSet);
            if (optionalView.isPresent()) {
                var colorPalettesView = optionalView.get();
                resourceSet.eAdapters().add(new DiagramColorAdapter(colorPalettesView));
                resourceSet.eAdapters().add(new FormColorAdapter(colorPalettesView));
            }
        }
        this.optionalResourceFactoryRegistry.ifPresent(resourceSet::setResourceFactoryRegistry);

        return editingDomain;
    }

    private Stream<EPackage> findGlobalEPackages() {
        return this.globalEPackageRegistry.values().stream()
                .filter(EPackage.class::isInstance)
                .map(EPackage.class::cast);
    }

    private Optional<View> loadStudioColorPalettes(ResourceSet resourceSet) {
        ClassPathResource classPathResource = new ClassPathResource("studioColorPalettes.json");
        URI uri = URI.createURI(EditingContext.RESOURCE_SCHEME + ":///" + UUID.nameUUIDFromBytes(classPathResource.getPath().getBytes()));
        Resource resource = null;
        Optional<Resource> existingResource = resourceSet.getResources().stream().filter(r -> uri.equals(r.getURI())).findFirst();
        if (existingResource.isEmpty()) {
            resource = new JSONResourceFactory().createResource(uri);
            try (var inputStream = new ByteArrayInputStream(classPathResource.getContentAsByteArray())) {
                resourceSet.getResources().add(resource);
                resource.load(inputStream, null);
                resource.eAdapters().add(new ResourceMetadataAdapter("studioColorPalettes"));
            } catch (IOException exception) {
                this.logger.warn("An error occured while loading document studioColorPalettes.json: {}.", exception.getMessage());
                resourceSet.getResources().remove(resource);
            }
        } else {
            resource = existingResource.get();
        }
        return resource.getContents()
                .stream()
                .filter(View.class::isInstance)
                .map(View.class::cast)
                .findFirst();
    }
}
