/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.web.sample.papaya;

import java.util.List;

import org.eclipse.sirius.components.domain.Attribute;
import org.eclipse.sirius.components.domain.DataType;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.DomainFactory;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.components.domain.Relation;

/**
 * Used to create the test domain.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class PapayaDomainProvider {
    private Entity rootEntity;

    private Entity modelElementEntity;

    private Entity namedElementEntity;

    private Entity operationalEntityEntity;

    private Entity operationalPerimeterEntity;

    private Entity operationalActorEntity;

    private Entity operationalActivityEntity;

    private Entity interaction;

    private Entity componentEntity;

    private Entity providedServiceEntity;

    private Entity requiredServiceEntity;

    private Entity packageEntity;

    private Entity typeEntity;

    private Entity typedElementEntity;

    private Entity interfaceEntity;

    private Entity classEntity;

    private Entity dataTypeEntity;

    private Entity enumEntity;

    private Entity enumLiteralEntity;

    private Entity attributeEntity;

    private Entity referenceEntity;

    private Entity operationEntity;

    private Entity parameterEntity;

    public Domain getDomain() {
        var domain = DomainFactory.eINSTANCE.createDomain();
        domain.setName("papaya");

        this.createEObjects(domain);
        this.linkEObjects();

        return domain;
    }

    private void createEObjects(Domain domain) {
        this.rootEntity = this.createEntity(domain, "Root", false, List.of());
        this.modelElementEntity = this.createEntity(domain, "ModelElement", true, List.of());
        this.namedElementEntity = this.createEntity(domain, "NamedElement", true, List.of(this.modelElementEntity));

        this.operationalEntityEntity = this.createEntity(domain, "OperationalEntity", false, List.of(this.namedElementEntity));
        this.operationalPerimeterEntity = this.createEntity(domain, "OperationalPerimeter", false, List.of(this.namedElementEntity));
        this.operationalActorEntity = this.createEntity(domain, "OperationalActor", false, List.of(this.namedElementEntity));
        this.operationalActivityEntity = this.createEntity(domain, "OperationalActivity", false, List.of(this.namedElementEntity));
        this.interaction = this.createEntity(domain, "Interaction", false, List.of(this.namedElementEntity));

        this.componentEntity = this.createEntity(domain, "Component", false, List.of(this.namedElementEntity));
        this.providedServiceEntity = this.createEntity(domain, "ProvidedService", false, List.of(this.modelElementEntity));
        this.requiredServiceEntity = this.createEntity(domain, "RequiredService", false, List.of(this.modelElementEntity));

        this.packageEntity = this.createEntity(domain, "Package", false, List.of(this.namedElementEntity));
        this.typeEntity = this.createEntity(domain, "Type", true, List.of(this.namedElementEntity));
        this.typedElementEntity = this.createEntity(domain, "TypedElement", true, List.of(this.namedElementEntity));
        this.interfaceEntity = this.createEntity(domain, "Interface", false, List.of(this.typeEntity));
        this.classEntity = this.createEntity(domain, "Class", false, List.of(this.typeEntity));
        this.dataTypeEntity = this.createEntity(domain, "DataType", false, List.of(this.typeEntity));
        this.enumEntity = this.createEntity(domain, "Enum", false, List.of(this.dataTypeEntity));
        this.enumLiteralEntity = this.createEntity(domain, "EnumLiteral", false, List.of(this.namedElementEntity));
        this.attributeEntity = this.createEntity(domain, "Attribute", false, List.of(this.namedElementEntity));
        this.referenceEntity = this.createEntity(domain, "Reference", false, List.of(this.typedElementEntity));
        this.operationEntity = this.createEntity(domain, "Operation", false, List.of(this.typedElementEntity));
        this.parameterEntity = this.createEntity(domain, "Parameter", false, List.of(this.typedElementEntity));
    }

    private void linkEObjects() {
        this.rootEntity.getRelations().add(this.createRelation("operationalEntities", true, true, false, this.operationalEntityEntity));
        this.rootEntity.getRelations().add(this.createRelation("operationalActors", true, true, false, this.operationalActorEntity));
        this.rootEntity.getRelations().add(this.createRelation("components", true, true, false, this.componentEntity));
        this.namedElementEntity.getAttributes().add(this.createAttribute("name", false, false, DataType.STRING));

        this.operationalEntityEntity.getRelations().add(this.createRelation("operationalPerimeters", true, true, false, this.operationalPerimeterEntity));
        this.operationalEntityEntity.getRelations().add(this.createRelation("operationalActors", true, true, false, this.operationalActorEntity));
        this.operationalPerimeterEntity.getRelations().add(this.createRelation("operationalActors", true, true, false, this.operationalActorEntity));
        this.operationalPerimeterEntity.getRelations().add(this.createRelation("operationalActivities", true, true, false, this.operationalActivityEntity));
        this.operationalActorEntity.getRelations().add(this.createRelation("operationalActivities", true, true, false, this.operationalActivityEntity));
        this.operationalActivityEntity.getRelations().add(this.createRelation("interactions", true, true, false, this.interaction));
        this.operationalActivityEntity.getRelations().add(this.createRelation("realizedBy", false, false, false, this.componentEntity));
        this.interaction.getRelations().add(this.createRelation("target", false, false, false, this.operationalActivityEntity));

        this.componentEntity.getRelations().add(this.createRelation("packages", true, true, false, this.packageEntity));
        this.componentEntity.getRelations().add(this.createRelation("providedServices", true, true, false, this.providedServiceEntity));
        this.componentEntity.getRelations().add(this.createRelation("requiredServices", true, true, false, this.requiredServiceEntity));
        this.providedServiceEntity.getRelations().add(this.createRelation("contract", false, false, false, this.interfaceEntity));
        this.requiredServiceEntity.getRelations().add(this.createRelation("contract", false, false, false, this.interfaceEntity));

        this.packageEntity.getRelations().add(this.createRelation("types", true, true, false, this.typeEntity));
        this.packageEntity.getRelations().add(this.createRelation("packages", true, true, false, this.packageEntity));
        this.typedElementEntity.getAttributes().add(this.createAttribute("many", false, false, DataType.BOOLEAN));
        this.typedElementEntity.getRelations().add(this.createRelation("type", false, false, false, this.typeEntity));
        this.interfaceEntity.getRelations().add(this.createRelation("operations", true, true, false, this.operationEntity));
        this.interfaceEntity.getRelations().add(this.createRelation("extends", false, true, false, this.interfaceEntity));
        this.classEntity.getAttributes().add(this.createAttribute("abstract", false, false, DataType.BOOLEAN));
        this.classEntity.getRelations().add(this.createRelation("implements", false, true, false, this.interfaceEntity));
        this.classEntity.getRelations().add(this.createRelation("extends", false, false, false, this.classEntity));
        this.classEntity.getRelations().add(this.createRelation("attributes", true, true, false, this.attributeEntity));
        this.classEntity.getRelations().add(this.createRelation("references", true, true, false, this.referenceEntity));
        this.classEntity.getRelations().add(this.createRelation("operations", true, true, false, this.operationEntity));
        this.attributeEntity.getRelations().add(this.createRelation("type", false, false, false, this.dataTypeEntity));
        this.attributeEntity.getAttributes().add(this.createAttribute("many", false, false, DataType.BOOLEAN));
        this.operationEntity.getRelations().add(this.createRelation("parameters", true, true, false, this.parameterEntity));
        this.enumEntity.getRelations().add(this.createRelation("enumLiterals", true, true, false, this.enumLiteralEntity));
    }

    private Entity createEntity(Domain domain, String name, boolean isAbstract, List<Entity> superTypes) {
        var entity = DomainFactory.eINSTANCE.createEntity();
        entity.setName(name);
        entity.setAbstract(isAbstract);
        entity.getSuperTypes().addAll(superTypes);
        domain.getTypes().add(entity);
        return entity;
    }

    private Attribute createAttribute(String name, boolean isMany, boolean isOptional, DataType type) {
        var attribute = DomainFactory.eINSTANCE.createAttribute();
        attribute.setName(name);
        attribute.setMany(isMany);
        attribute.setOptional(false);
        attribute.setType(type);
        return attribute;
    }

    private Relation createRelation(String name, boolean isContainment, boolean isMany, boolean isOptional, Entity type) {
        var relation = DomainFactory.eINSTANCE.createRelation();
        relation.setName(name);
        relation.setContainment(isContainment);
        relation.setMany(isMany);
        relation.setOptional(isOptional);
        relation.setTargetType(type);
        return relation;
    }
}
