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
package org.eclipse.sirius.web.services.diagrams;

import java.util.List;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.domain.Attribute;
import org.eclipse.sirius.components.domain.DataType;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.DomainFactory;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.components.domain.Relation;
import org.eclipse.sirius.components.domain.emf.DomainConverter;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Used to provide the domain to test relation based edges.
 *
 * @author arichard
 */
@Service
@Conditional(OnStudioTests.class)
public class RelationBasedEdgeDomainProvider implements IEditingContextProcessor {

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            var resourceSet = siriusWebEditingContext.getDomain().getResourceSet();
            new DomainConverter().convert(List.of(this.createDomain()))
                    .forEach(ePackage -> resourceSet.getPackageRegistry().put(ePackage.getNsURI(), ePackage));
        }
    }

    private Domain createDomain() {
        Entity root = DomainFactory.eINSTANCE.createEntity();
        root.setName("Root");
        root.setAbstract(false);

        Entity entity = DomainFactory.eINSTANCE.createEntity();
        entity.setName("Entity");
        entity.setAbstract(false);

        Attribute nameAtt = DomainFactory.eINSTANCE.createAttribute();
        nameAtt.setMany(false);
        nameAtt.setName("name");
        nameAtt.setOptional(true);
        nameAtt.setType(DataType.STRING);
        entity.getAttributes().add(nameAtt);

        Relation entities = DomainFactory.eINSTANCE.createRelation();
        entities.setContainment(true);
        entities.setMany(true);
        entities.setName("entities");
        entities.setOptional(true);
        entities.setTargetType(entity);
        root.getRelations().add(entities);

        Relation subEntities = DomainFactory.eINSTANCE.createRelation();
        subEntities.setContainment(true);
        subEntities.setMany(true);
        subEntities.setName("subEntities");
        subEntities.setOptional(true);
        subEntities.setTargetType(entity);
        entity.getRelations().add(subEntities);

        Domain myDomain = DomainFactory.eINSTANCE.createDomain();
        myDomain.setName("rbeDomain");
        myDomain.getTypes().add(root);
        myDomain.getTypes().add(entity);

        return myDomain;
    }
}
