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
 * Used to contribute domains for graphical-dnd.cy.ts tests.
 *
 * @author frouene
 */
@Profile("test")
@Service
public class DiagramDnDDomainProvider implements IDomainProvider {

    public static final String DOMAIN_NAME = "diagramDnD";

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

        Entity entity2 = DomainFactory.eINSTANCE.createEntity();
        entity2.setName("Entity2");
        domain.getTypes().add(entity2);

        Relation entity1s = DomainFactory.eINSTANCE.createRelation();
        entity1s.setName("entity1s");
        entity1s.setContainment(true);
        entity1s.setOptional(true);
        entity1s.setMany(true);
        entity1s.setTargetType(entity1);
        root.getRelations().add(entity1s);

        Relation entity2sOnEntity1 = DomainFactory.eINSTANCE.createRelation();
        entity2sOnEntity1.setName("entity2sOnEntity1");
        entity2sOnEntity1.setContainment(true);
        entity2sOnEntity1.setOptional(true);
        entity2sOnEntity1.setMany(true);
        entity2sOnEntity1.setTargetType(entity2);
        entity1.getRelations().add(entity2sOnEntity1);


        Relation entity2sOnRoot = DomainFactory.eINSTANCE.createRelation();
        entity2sOnRoot.setName("entity2sOnRoot");
        entity2sOnRoot.setContainment(true);
        entity2sOnRoot.setOptional(true);
        entity2sOnRoot.setMany(true);
        entity2sOnRoot.setTargetType(entity2);
        root.getRelations().add(entity2sOnRoot);

        this.addAttribute(entity1, "name", DataType.STRING);
        this.addAttribute(entity2, "name", DataType.STRING);

        return List.of(domain);
    }

    private void addAttribute(Entity entity, String name, DataType type) {
        Attribute attr = DomainFactory.eINSTANCE.createAttribute();
        attr.setName(name);
        attr.setOptional(true);
        attr.setType(type);
        entity.getAttributes().add(attr);
    }
}
