/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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

package org.eclipse.sirius.web.e2e.tests.diagramdnd;

import java.util.List;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.domain.Attribute;
import org.eclipse.sirius.components.domain.DataType;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.DomainFactory;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.components.domain.Relation;
import org.eclipse.sirius.web.application.studio.services.api.IDomainProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Used to contribute domains for "integration-tests-playwright/playwright/e2e/dnd.spec.ts".
 *
 * @author frouene
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@Profile("test")
@Service
public class DiagramDnDDomainProvider implements IDomainProvider {

    public static final String DOMAIN_NAME = "diagramDnD";

    @Override
    public List<Domain> getDomains(IEditingContext editingContext) {
        Domain domain = DomainFactory.eINSTANCE.createDomain();
        domain.setName(DOMAIN_NAME);

        Entity root = this.createAndAddEntity(domain, "Root");
        Entity entity1 = this.createAndAddEntity(domain, "Entity1");
        Entity entity2 = this.createAndAddEntity(domain, "Entity2");
        Entity entity3 = this.createAndAddEntity(domain, "Entity3");
        Entity entity4 = this.createAndAddEntity(domain, "Entity4");

        this.createAndAddRelation(root, entity1, "entity1s");
        this.createAndAddRelation(entity1, entity2, "entity2sOnEntity1");
        this.createAndAddRelation(root, entity2, "entity2sOnRoot");
        this.createAndAddRelation(entity1, entity3, "entity3sOnEntity1");
        this.createAndAddRelation(root, entity3, "entity3sOnRoot");
        this.createAndAddRelation(entity1, entity4, "entity4sOnEntity1");

        this.addAttribute(entity1, "name", DataType.STRING);
        this.addAttribute(entity2, "name", DataType.STRING);
        this.addAttribute(entity3, "name", DataType.STRING);
        this.addAttribute(entity4, "name", DataType.STRING);

        return List.of(domain);
    }

    private Entity createAndAddEntity(Domain domain, String name) {
        Entity entity = DomainFactory.eINSTANCE.createEntity();
        entity.setName(name);
        domain.getTypes().add(entity);
        return entity;
    }

    private void createAndAddRelation(Entity source, Entity target, String name) {
        Relation relation = DomainFactory.eINSTANCE.createRelation();
        relation.setName(name);
        relation.setContainment(true);
        relation.setOptional(true);
        relation.setMany(true);
        relation.setTargetType(target);
        source.getRelations().add(relation);
    }

    private void addAttribute(Entity entity, String name, DataType type) {
        Attribute attr = DomainFactory.eINSTANCE.createAttribute();
        attr.setName(name);
        attr.setOptional(true);
        attr.setType(type);
        entity.getAttributes().add(attr);
    }
}
