/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.emf.view;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.api.configuration.IRepresentationDescriptionRegistry;
import org.eclipse.sirius.web.api.configuration.IRepresentationDescriptionRegistryConfigurer;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.emf.services.SiriusWebJSONResourceFactoryImpl;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.repositories.IDocumentRepository;
import org.eclipse.sirius.web.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Populates the global {@link IRepresentationDescriptionRegistry} with diagram descriptions converted from View
 * documents in the database.
 *
 * @author pcdavid
 */
@Configuration
public class ViewBasedRepresentationDescriptionRegistryConfigurer implements IRepresentationDescriptionRegistryConfigurer {

    private final Logger logger = LoggerFactory.getLogger(ViewBasedRepresentationDescriptionRegistryConfigurer.class);

    private final IDocumentRepository documentRepository;

    private final EPackage.Registry ePackageRegistry;

    private final ViewConverter viewConverter;

    public ViewBasedRepresentationDescriptionRegistryConfigurer(IDocumentRepository documentRepository, EPackage.Registry ePackageRegistry, IObjectService objectService) {
        this.documentRepository = Objects.requireNonNull(documentRepository);
        this.ePackageRegistry = Objects.requireNonNull(ePackageRegistry);
        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of());
        this.viewConverter = new ViewConverter(Objects.requireNonNull(interpreter), Objects.requireNonNull(objectService));
    }

    @Override
    public void addRepresentationDescriptions(IRepresentationDescriptionRegistry registry) {
        this.documentRepository.findAll().forEach(documentEntity -> {
            Resource res = this.loadDocumentAsEMF(documentEntity);
            this.getViewDefinition(res).ifPresent(view -> this.viewConverter.convert(view).forEach(registry::add));
        });
    }

    private Optional<View> getViewDefinition(Resource res) {
        if (!res.getContents().isEmpty() && res.getContents().get(0) instanceof View) {
            return Optional.of((View) res.getContents().get(0));
        } else {
            return Optional.empty();
        }
    }

    private Resource loadDocumentAsEMF(DocumentEntity documentEntity) {
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.setPackageRegistry(this.ePackageRegistry);
        URI uri = URI.createURI(documentEntity.getId().toString());
        JsonResource resource = new SiriusWebJSONResourceFactoryImpl().createResource(uri);
        resourceSet.getResources().add(resource);
        try (var inputStream = new ByteArrayInputStream(documentEntity.getContent().getBytes())) {
            resource.load(inputStream, null);
        } catch (IOException exception) {
            this.logger.error(exception.getMessage(), exception);
        }
        return resource;
    }

}
