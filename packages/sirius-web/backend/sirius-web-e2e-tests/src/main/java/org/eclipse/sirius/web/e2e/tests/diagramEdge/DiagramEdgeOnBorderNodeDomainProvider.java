/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.e2e.tests.diagramEdge;

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
 * Used to contribute domains for arrange-all.cy.ts tests.
 *
 * @author frouene
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@Profile("test")
@Service
public class DiagramEdgeOnBorderNodeDomainProvider implements IDomainProvider {

    public static final String DOMAIN_NAME = "diagramEdgeOnBorderNode";

    @Override
    public List<Domain> getDomains(IEditingContext editingContext) {
        var domain = DomainFactory.eINSTANCE.createDomain();
        domain.setName(DOMAIN_NAME);

        Entity root = DomainFactory.eINSTANCE.createEntity();
        root.setName("Root");
        domain.getTypes().add(root);

        Entity entity1 = this.createContainmentChild(root, "Entity1", "entity1s");
        domain.getTypes().add(entity1);
        Entity entity2 = this.createContainmentChild(root, "Entity2", "entity2s");
        domain.getTypes().add(entity2);

        Relation entity1ContainEntity2 = DomainFactory.eINSTANCE.createRelation();
        entity1ContainEntity2.setName("entity1ContainEntity2");
        entity1ContainEntity2.setContainment(true);
        entity1ContainEntity2.setOptional(true);
        entity1ContainEntity2.setMany(true);
        entity1ContainEntity2.setTargetType(entity2);
        entity1.getRelations().add(entity1ContainEntity2);

        Relation entity1ContainEntity1 = DomainFactory.eINSTANCE.createRelation();
        entity1ContainEntity1.setName("entity1ContainEntity1");
        entity1ContainEntity1.setContainment(true);
        entity1ContainEntity1.setOptional(true);
        entity1ContainEntity1.setMany(true);
        entity1ContainEntity1.setTargetType(entity1);
        entity1.getRelations().add(entity1ContainEntity1);

        Relation entity1LinkedToEntity2 = DomainFactory.eINSTANCE.createRelation();
        entity1LinkedToEntity2.setName("entity1LinkedToEntity2");
        entity1LinkedToEntity2.setContainment(false);
        entity1LinkedToEntity2.setOptional(true);
        entity1LinkedToEntity2.setMany(true);
        entity1LinkedToEntity2.setTargetType(entity2);
        entity2.getRelations().add(entity1LinkedToEntity2);

        this.addAttribute(entity1, "name", DataType.STRING);
        this.addAttribute(entity2, "name", DataType.STRING);


        return List.of(domain);
    }

    private Entity createContainmentChild(Entity root, String name, String relationName) {
        Entity entity = DomainFactory.eINSTANCE.createEntity();
        entity.setName(name);
        Relation relation = DomainFactory.eINSTANCE.createRelation();
        relation.setName(relationName);
        relation.setContainment(true);
        relation.setOptional(true);
        relation.setMany(true);
        relation.setTargetType(entity);
        root.getRelations().add(relation);
        return entity;
    }

    private void addAttribute(Entity entity, String name, DataType type) {
        Attribute attr = DomainFactory.eINSTANCE.createAttribute();
        attr.setName(name);
        attr.setOptional(true);
        attr.setType(type);
        entity.getAttributes().add(attr);
    }
}
