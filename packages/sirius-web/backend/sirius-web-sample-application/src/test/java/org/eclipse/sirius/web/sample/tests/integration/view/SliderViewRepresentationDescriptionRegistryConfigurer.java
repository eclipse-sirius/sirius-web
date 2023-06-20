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
package org.eclipse.sirius.web.sample.tests.integration.view;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.sirius.components.core.configuration.IRepresentationDescriptionRegistry;
import org.eclipse.sirius.components.core.configuration.IRepresentationDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.utils.EMFResourceUtils;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.emf.IViewConverter;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.web.services.api.representations.IInMemoryViewRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Test-specific configuration which loads and registers a View which defines a Form with Slider widgets.
 *
 * @author pcdavid
 */
@Configuration
public class SliderViewRepresentationDescriptionRegistryConfigurer implements IRepresentationDescriptionRegistryConfigurer {
    private final IViewConverter viewConverter;

    private final EPackage.Registry ePackagesRegistry;

    private final IInMemoryViewRegistry inMemoryViewRegistry;

    public SliderViewRepresentationDescriptionRegistryConfigurer(IViewConverter viewConverter, Registry ePackagesRegistry, IInMemoryViewRegistry inMemoryViewRegistry) {
        this.viewConverter = viewConverter;
        this.ePackagesRegistry = ePackagesRegistry;
        this.inMemoryViewRegistry = inMemoryViewRegistry;
    }

    @Override
    public void addRepresentationDescriptions(IRepresentationDescriptionRegistry registry) {
        Optional<View> optionalView = this.load(new ClassPathResource("Slider_Test_Form.view"), List.of(ViewPackage.eINSTANCE, FormPackage.eINSTANCE), View.class);

        if (optionalView.isPresent()) {
            var view = optionalView.get();

            view.eAllContents().forEachRemaining(eObject -> {
                eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
            });

            this.inMemoryViewRegistry.register(view);

            List<EPackage> staticEPackages = this.ePackagesRegistry.values().stream().filter(EPackage.class::isInstance).map(EPackage.class::cast).collect(Collectors.toList());

            var representationDescriptions = this.viewConverter.convert(List.of(view), staticEPackages);
            representationDescriptions.forEach(registry::add);
        }
    }

    private <T> Optional<T> load(ClassPathResource classPathResource, List<EPackage> requiredEPackages, Class<T> rootElementType) {
        Optional<T> result = Optional.empty();

        ResourceSet resourceSet = new ResourceSetImpl();
        EPackageRegistryImpl ePackageRegistry = new EPackageRegistryImpl();
        EPackage.Registry.INSTANCE.forEach(ePackageRegistry::put);
        requiredEPackages.forEach(ePackage -> ePackageRegistry.put(ePackage.getNsURI(), ePackage));
        resourceSet.setPackageRegistry(ePackageRegistry);

        try (var inputStream = classPathResource.getInputStream()) {
            URI uri = URI.createURI(EditingContext.RESOURCE_SCHEME + ":///" + UUID.nameUUIDFromBytes(classPathResource.getPath().getBytes()));
            Resource resource = new XMIResourceImpl(uri);
            resourceSet.getResources().add(resource);
            resource.load(inputStream, new EMFResourceUtils().getXMILoadOptions());

            // @formatter:off
            return resource.getContents().stream()
                    .filter(rootElementType::isInstance)
                    .map(rootElementType::cast)
                    .findFirst();
            // @formatter:on
        } catch (IOException exception) {
        }
        return result;
    }

}
