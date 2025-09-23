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
 * Used to contribute domains for edges-creation.cy.ts tests.
 *
 * @author mcharfadi
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@Profile("test")
@Service
public class DiagramEdgeDomainProvider implements IDomainProvider {

    public static final String DOMAIN_NAME = "diagramEdges";

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

        Entity entity3 = this.createContainmentChild(root, "E1toE2A", "toEdge1");
        domain.getTypes().add(entity3);
        Entity entity4 = this.createContainmentChild(root, "E1toE2B", "toEdge2");
        domain.getTypes().add(entity4);

        Entity entity5 = this.createContainmentChild(root, "EdgeToE2", "toEdge3");
        domain.getTypes().add(entity5);

        Entity entity6 = this.createContainmentChild(root, "E2ToEdge1A", "toEdge4");
        domain.getTypes().add(entity6);
        Entity entity7 = this.createContainmentChild(root, "E2ToEdge1B", "toEdge5");
        domain.getTypes().add(entity7);

        this.addAttribute(entity1, "name", DataType.STRING);
        this.addAttribute(entity2, "name", DataType.STRING);
        this.addAttribute(entity3, "name", DataType.STRING);
        this.addAttribute(entity4, "name", DataType.STRING);
        this.addAttribute(entity5, "name", DataType.STRING);
        this.addAttribute(entity6, "name", DataType.STRING);
        this.addAttribute(entity7, "name", DataType.STRING);
        this.addSourceTargetRelation(entity3, entity1, entity2);
        this.addSourceTargetRelation(entity4, entity1, entity2);
        this.addSourceTargetRelation(entity5, entity3, entity2);
        this.addSourceTargetRelation(entity6, entity2, entity3);
        this.addSourceTargetRelation(entity7, entity2, entity3);

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

    private void addSourceTargetRelation(Entity entity, Entity source, Entity target) {
        Relation sourceRelation = DomainFactory.eINSTANCE.createRelation();
        sourceRelation.setName("source");
        sourceRelation.setContainment(false);
        sourceRelation.setOptional(true);
        sourceRelation.setMany(true);
        sourceRelation.setTargetType(source);
        entity.getRelations().add(sourceRelation);

        Relation targetRelation = DomainFactory.eINSTANCE.createRelation();
        targetRelation.setName("target");
        targetRelation.setContainment(false);
        targetRelation.setOptional(true);
        targetRelation.setMany(true);
        targetRelation.setTargetType(target);
        entity.getRelations().add(targetRelation);
    }
}
