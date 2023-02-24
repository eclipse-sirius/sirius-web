/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.DomainPackage;
import org.eclipse.sirius.components.domain.emf.DomainConverter;
import org.eclipse.sirius.components.emf.services.IEditingContextEPackageService;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.repositories.IDocumentRepository;
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

    private final boolean isStudioDefinitionEnabled;

    public EditingContextEPackageService(EPackage.Registry globalEPackageRegistry, IDocumentRepository documentRepository,
            @Value("${org.eclipse.sirius.web.features.studioDefinition:false}") boolean isStudioDefinitionEnabled) {
        this.globalEPackageRegistry = Objects.requireNonNull(globalEPackageRegistry);
        this.documentRepository = Objects.requireNonNull(documentRepository);
        this.isStudioDefinitionEnabled = isStudioDefinitionEnabled;
    }

    @Override
    public List<EPackage> getEPackages(String editingContextId) {
        LinkedHashMap<String, EPackage> allEPackages = new LinkedHashMap<>();
        this.findGlobalEPackages().forEach(ePackage -> {
            EPackage previous = allEPackages.put(ePackage.getNsURI(), ePackage);
            if (previous != null) {
                // This should never happen for EPackages coming from the global registry, but
                // it does not cost much to check it.
                this.logger.warn("Duplicate EPackages with nsURI {} found.", ePackage.getNsURI());
            }
        });
        if (this.isStudioDefinitionEnabled) {
            this.findDynamicEPackages().forEach(ePackage -> {
                EPackage previous = allEPackages.put(ePackage.getNsURI(), ePackage);
                if (previous != null) {
                    this.logger.warn("Duplicate EPackages with nsURI {} found.", ePackage.getNsURI());
                }
            });

        }

        return List.copyOf(allEPackages.values());
    }

    /**
     * Returns all the statically defined/contributed EPackages.
     */
    private Stream<EPackage> findGlobalEPackages() {
        return this.globalEPackageRegistry.values().stream().filter(EPackage.class::isInstance).map(EPackage.class::cast);
    }

    /**
     * Returns all the EPackages defined by a Domain definition.
     */
    private Stream<EPackage> findDynamicEPackages() {
        ResourceSet resourceSet = new ResourceSetImpl();

        EPackageRegistryImpl ePackageRegistry = new EPackageRegistryImpl();
        this.globalEPackageRegistry.forEach(ePackageRegistry::put);
        resourceSet.setPackageRegistry(ePackageRegistry);

        var domainDocumentEntities = this.documentRepository.findAllByType(DomainPackage.eNAME, DomainPackage.eNS_URI);
        StreamSupport.stream(domainDocumentEntities.spliterator(), false).distinct().forEach(dp -> this.loadDomainDefinitions(resourceSet, dp));

        return this.convertDomains(resourceSet.getResources());
    }

    private void loadDomainDefinitions(ResourceSet resourceSet, DocumentEntity domainDocument) {
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(domainDocument.getId().toString());
        try (var inputStream = new ByteArrayInputStream(domainDocument.getContent().getBytes())) {
            resourceSet.getResources().add(resource);
            resource.load(inputStream, null);
        } catch (IOException | IllegalArgumentException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
    }

    private Stream<EPackage> convertDomains(List<Resource> resources) {
        List<Domain> domains = resources.stream().flatMap(r -> r.getContents().stream()).filter(Domain.class::isInstance).map(Domain.class::cast).toList();
        return new DomainConverter().convert(domains);
    }

}
