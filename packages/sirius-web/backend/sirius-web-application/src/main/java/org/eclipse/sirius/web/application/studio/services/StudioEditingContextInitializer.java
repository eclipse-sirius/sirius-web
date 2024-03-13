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
package org.eclipse.sirius.web.application.studio.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.domain.DomainPackage;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.deck.DeckPackage;
import org.eclipse.sirius.components.view.deck.adapters.DeckColorAdapter;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.adapters.DiagramColorAdapter;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.adapters.FormColorAdapter;
import org.eclipse.sirius.components.view.gantt.GanttPackage;
import org.eclipse.sirius.web.application.studio.services.api.IStudioCapableEditingContextPredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

/**
 * Used to provide the studio related features for the relevant editing context.
 *
 * @author sbegaudeau
 */
@Service
public class StudioEditingContextInitializer implements IEditingContextProcessor {

    private final IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate;

    private final Logger logger = LoggerFactory.getLogger(StudioEditingContextInitializer.class);

    public StudioEditingContextInitializer(IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate) {
        this.studioCapableEditingContextPredicate = Objects.requireNonNull(studioCapableEditingContextPredicate);
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        var isStudio = this.studioCapableEditingContextPredicate.test(editingContext);
        if (isStudio && editingContext instanceof IEMFEditingContext emfEditingContext) {
            var packageRegistry = emfEditingContext.getDomain().getResourceSet().getPackageRegistry();
            packageRegistry.put(DomainPackage.eNS_URI, DomainPackage.eINSTANCE);
            packageRegistry.put(ViewPackage.eNS_URI, ViewPackage.eINSTANCE);
            packageRegistry.put(DeckPackage.eNS_URI, DeckPackage.eINSTANCE);
            packageRegistry.put(DiagramPackage.eNS_URI, DiagramPackage.eINSTANCE);
            packageRegistry.put(FormPackage.eNS_URI, FormPackage.eINSTANCE);
            packageRegistry.put(GanttPackage.eNS_URI, GanttPackage.eINSTANCE);

            var resourceSet = emfEditingContext.getDomain().getResourceSet();
            this.loadStudioColorPalettes(resourceSet).ifPresent(view -> {
                resourceSet.eAdapters().add(new DiagramColorAdapter(view));
                resourceSet.eAdapters().add(new FormColorAdapter(view));
                resourceSet.eAdapters().add(new DeckColorAdapter(view));
            });
        }
    }

    private Optional<View> loadStudioColorPalettes(ResourceSet resourceSet) {
        ClassPathResource classPathResource = new ClassPathResource("studioColorPalettes.json");
        URI uri = URI.createURI(IEMFEditingContext.RESOURCE_SCHEME + ":///" + UUID.nameUUIDFromBytes(classPathResource.getPath().getBytes()));
        var resource = new JSONResourceFactory().createResource(uri);
        try (var inputStream = new ByteArrayInputStream(classPathResource.getContentAsByteArray())) {
            resourceSet.getResources().add(resource);
            resource.load(inputStream, null);
            resource.eAdapters().add(new ResourceMetadataAdapter("studioColorPalettes"));
        } catch (IOException exception) {
            this.logger.warn("An error occured while loading document studioColorPalettes.json: {}.", exception.getMessage());
            resourceSet.getResources().remove(resource);
        }

        return resource.getContents()
                .stream()
                .filter(View.class::isInstance)
                .map(View.class::cast)
                .findFirst();
    }
}
