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
package org.eclipse.sirius.web.services.editingcontext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.domain.Domain;
import org.eclipse.sirius.web.domain.DomainPackage;
import org.eclipse.sirius.web.emf.domain.DomainConverter;
import org.eclipse.sirius.web.emf.services.IEditingContextEPackageService;
import org.eclipse.sirius.web.emf.services.SiriusWebJSONResourceFactoryImpl;
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.repositories.IDocumentRepository;
import org.eclipse.sirius.web.services.documents.DocumentMetadataAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * A service used to retrieve all the EPackages accessible for given editing context.
 *
 * @author sbegaudeau
 */
@Service
public class EditingContextEPackageService implements IEditingContextEPackageService {

    private final Logger logger = LoggerFactory.getLogger(EditingContextEPackageService.class);

    private final EPackage.Registry globalEPackageRegistry;

    private final IDocumentRepository documentRepository;

    private final DomainConverter domainConverter;

    private final boolean isStudioDefinitionEnabled;

    public EditingContextEPackageService(EPackage.Registry globalEPackageRegistry, IDocumentRepository documentRepository,
            @Value("${org.eclipse.sirius.web.features.studioDefinition:false}") boolean isStudioDefinitionEnabled) {
        this.globalEPackageRegistry = Objects.requireNonNull(globalEPackageRegistry);
        this.documentRepository = Objects.requireNonNull(documentRepository);
        this.isStudioDefinitionEnabled = isStudioDefinitionEnabled;
        this.domainConverter = new DomainConverter(isStudioDefinitionEnabled);
    }

    @Override
    public List<EPackage> getEPackages(UUID editingContextId) {
        // @formatter:off
        List<DocumentEntity> entities;
        if (this.isStudioDefinitionEnabled) {
            entities = StreamSupport.stream(this.documentRepository.findAllByType(DomainPackage.eNAME, DomainPackage.eNS_URI).spliterator(), false)
                                    .collect(Collectors.toList());
        } else {
            entities = List.of();
        }
        // @formatter:on

        EPackageRegistryImpl ePackageRegistry = new EPackageRegistryImpl();
        this.globalEPackageRegistry.forEach(ePackageRegistry::put);
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.setPackageRegistry(ePackageRegistry);

        for (DocumentEntity documentEntity : entities) {
            URI uri = URI.createURI(documentEntity.getId().toString());
            JsonResource resource = new SiriusWebJSONResourceFactoryImpl().createResource(uri);
            try (var inputStream = new ByteArrayInputStream(documentEntity.getContent().getBytes())) {
                resourceSet.getResources().add(resource);
                resource.load(inputStream, null);

                resource.eAdapters().add(new DocumentMetadataAdapter(documentEntity.getName()));
            } catch (IOException | IllegalArgumentException exception) {
                this.logger.warn(exception.getMessage(), exception);
            }
        }

        // @formatter:off
        List<EPackage> allEPackages = new ArrayList<>();
        // The global/static EPackages
        this.globalEPackageRegistry.values().stream()
                                   .filter(EPackage.class::isInstance)
                                   .map(EPackage.class::cast)
                                   .forEach(allEPackages::add);
        // The dynamically defined EPackage obtained from the accessible Domain definitions
        resourceSet.getResources().stream()
                .flatMap(resource -> {
                    return resource.getContents().stream()
                            .filter(Domain.class::isInstance)
                            .map(Domain.class::cast)
                            .findFirst()
                            .flatMap(this.domainConverter::convert)
                            .stream();
                })
                .forEach(allEPackages::add);
        // @formatter:on
        return allEPackages;
    }
}
