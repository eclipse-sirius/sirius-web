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

package org.eclipse.sirius.web.e2e.tests.diagramResize;

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
 * Used to contribute domains for diagram-resize tests.
 *
 * @author mcharfadi
 */
@Profile("test")
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class DiagramResizeDomainProvider implements IDomainProvider {

    public static final String DOMAIN_NAME = "diagramResize";

    @Override
    public List<Domain> getDomains(IEditingContext editingContext) {
        var domain = DomainFactory.eINSTANCE.createDomain();
        domain.setName(DOMAIN_NAME);

        Entity root = DomainFactory.eINSTANCE.createEntity();
        root.setName("Root");
        domain.getTypes().add(root);

        Entity entity1 = DomainFactory.eINSTANCE.createEntity();
        entity1.setName("Entity1");
        domain.getTypes().add(entity1);
        root.getRelations().add(this.linkToRoot(entity1, "entity1s"));

        this.addAttribute(entity1, "name", DataType.STRING);

        Entity entity2 = DomainFactory.eINSTANCE.createEntity();
        entity2.setName("Entity2");
        domain.getTypes().add(entity2);
        root.getRelations().add(this.linkToRoot(entity2, "entity2s"));

        this.addAttribute(entity2, "name", DataType.STRING);

        Entity entity3 = DomainFactory.eINSTANCE.createEntity();
        entity3.setName("Entity3");
        domain.getTypes().add(entity3);
        root.getRelations().add(this.linkToRoot(entity3, "entity3s"));

        this.addAttribute(entity3, "name", DataType.STRING);

        Entity entity4 = DomainFactory.eINSTANCE.createEntity();
        entity4.setName("Entity4");
        domain.getTypes().add(entity4);
        root.getRelations().add(this.linkToRoot(entity4, "entity4s"));

        this.addAttribute(entity4, "name", DataType.STRING);

        Entity entity5 = DomainFactory.eINSTANCE.createEntity();
        entity5.setName("Entity5");
        domain.getTypes().add(entity5);
        root.getRelations().add(this.linkToRoot(entity5, "entity5s"));

        this.addAttribute(entity5, "name", DataType.STRING);

        return List.of(domain);
    }

    private Relation linkToRoot(Entity entity, String relationName) {
        Relation relation = DomainFactory.eINSTANCE.createRelation();
        relation.setName(relationName);
        relation.setContainment(true);
        relation.setOptional(true);
        relation.setMany(true);
        relation.setTargetType(entity);
        return relation;
    }

    private void addAttribute(Entity entity, String name, DataType type) {
        Attribute attr = DomainFactory.eINSTANCE.createAttribute();
        attr.setName(name);
        attr.setOptional(true);
        attr.setType(type);
        entity.getAttributes().add(attr);
    }
}
