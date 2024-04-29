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

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.domain.Attribute;
import org.eclipse.sirius.components.domain.DataType;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.DomainFactory;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.components.domain.Relation;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.migration.MigrationService;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.studio.services.api.IDefaultDomainResourceProvider;
import org.springframework.stereotype.Service;

/**
 * Provides the content of a default domain resource.
 *
 * @author sbegaudeau
 */
@Service
public class DefaultDomainResourceProvider implements IDefaultDomainResourceProvider {

    private static final String DOMAIN_DOCUMENT_NAME = "DomainNewModel";

    private final List<IMigrationParticipant> migrationParticipants;

    public DefaultDomainResourceProvider(List<IMigrationParticipant> migrationParticipants) {
        this.migrationParticipants = Objects.requireNonNull(migrationParticipants);
    }

    @Override
    public Resource getResource(String domainName) {
        Domain domain = DomainFactory.eINSTANCE.createDomain();
        domain.setName(domainName);

        Entity root = DomainFactory.eINSTANCE.createEntity();
        root.setName("Root");
        domain.getTypes().add(root);

        Entity entity1 = DomainFactory.eINSTANCE.createEntity();
        entity1.setName("Entity1");
        domain.getTypes().add(entity1);

        Relation entity1s = DomainFactory.eINSTANCE.createRelation();
        entity1s.setName("entity1s");
        entity1s.setContainment(true);
        entity1s.setOptional(true);
        entity1s.setMany(true);
        entity1s.setTargetType(entity1);
        root.getRelations().add(entity1s);

        Entity entity2 = DomainFactory.eINSTANCE.createEntity();
        entity2.setName("Entity2");
        domain.getTypes().add(entity2);

        Relation entity2s = DomainFactory.eINSTANCE.createRelation();
        entity2s.setName("entity2s");
        entity2s.setContainment(true);
        entity2s.setOptional(true);
        entity2s.setMany(true);
        entity2s.setTargetType(entity2);
        root.getRelations().add(entity2s);

        Relation linkedTo = DomainFactory.eINSTANCE.createRelation();
        linkedTo.setName("linkedTo");
        linkedTo.setContainment(false);
        linkedTo.setOptional(true);
        linkedTo.setMany(true);
        linkedTo.setTargetType(entity2);
        entity1.getRelations().add(linkedTo);

        this.addAttribute(entity1, "name", DataType.STRING);
        this.addAttribute(entity1, "attribute2", DataType.BOOLEAN);
        this.addAttribute(entity1, "attribute3", DataType.NUMBER);
        this.addAttribute(entity2, "name", DataType.STRING);

        JsonResource resource = new JSONResourceFactory().createResourceFromPath(UUID.randomUUID().toString());
        var resourceMetadataAdapter = new ResourceMetadataAdapter(DOMAIN_DOCUMENT_NAME);
        var migrationService = new MigrationService(this.migrationParticipants);

        resourceMetadataAdapter.setMigrationData(migrationService.getMostRecentParticipantMigrationData());
        resource.eAdapters().add(resourceMetadataAdapter);
        resource.getContents().add(domain);

        return resource;
    }

    private void addAttribute(Entity entity, String name, DataType type) {
        Attribute attr = DomainFactory.eINSTANCE.createAttribute();
        attr.setName(name);
        attr.setOptional(true);
        attr.setType(type);
        entity.getAttributes().add(attr);
    }
}
