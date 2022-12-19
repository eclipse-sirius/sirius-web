/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.web.sample.configuration;

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
public class TestDomainProvider {
    @SuppressWarnings("checkstyle:JavaNCSS")
    public Domain getDomain() {
        var domain = DomainFactory.eINSTANCE.createDomain();
        domain.setName("papaya");

        var rootEntity = this.createEntity(domain, "Root", false, List.of());
        var modelElementEntity = this.createEntity(domain, "ModelElement", true, List.of());
        var namedElementEntity = this.createEntity(domain, "NamedElement", true, List.of(modelElementEntity));

        var operationalEntityEntity = this.createEntity(domain, "OperationalEntity", false, List.of(namedElementEntity));
        var operationalPerimeterEntity = this.createEntity(domain, "OperationalPerimeter", false, List.of(namedElementEntity));
        var operationalActorEntity = this.createEntity(domain, "OperationalActor", false, List.of(namedElementEntity));
        var operationalActivityEntity = this.createEntity(domain, "OperationalActivity", false, List.of(namedElementEntity));
        var interaction = this.createEntity(domain, "Interaction", false, List.of(namedElementEntity));

        var componentEntity = this.createEntity(domain, "Component", false, List.of(namedElementEntity));
        var providedServiceEntity = this.createEntity(domain, "ProvidedService", false, List.of(modelElementEntity));
        var requiredServiceEntity = this.createEntity(domain, "RequiredService", false, List.of(modelElementEntity));

        var packageEntity = this.createEntity(domain, "Package", false, List.of(namedElementEntity));
        var typeEntity = this.createEntity(domain, "Type", true, List.of(namedElementEntity));
        var typedElementEntity = this.createEntity(domain, "TypedElement", true, List.of(namedElementEntity));
        var interfaceEntity = this.createEntity(domain, "Interface", false, List.of(typeEntity));
        var classEntity = this.createEntity(domain, "Class", false, List.of(typeEntity));
        var dataTypeEntity = this.createEntity(domain, "DataType", false, List.of(namedElementEntity));
        var enumEntity = this.createEntity(domain, "Enum", false, List.of(dataTypeEntity));
        var enumLiteralEntity = this.createEntity(domain, "EnumLiteral", false, List.of(namedElementEntity));
        var attributeEntity = this.createEntity(domain, "Attribute", false, List.of(namedElementEntity));
        var referenceEntity = this.createEntity(domain, "Reference", false, List.of(typedElementEntity));
        var operationEntity = this.createEntity(domain, "Operation", false, List.of(typedElementEntity));
        var parameterEntity = this.createEntity(domain, "Parameter", false, List.of(typedElementEntity));

        rootEntity.getRelations().add(this.createRelation("operationalEntities", true, true, false, operationalEntityEntity));
        rootEntity.getRelations().add(this.createRelation("operationalActors", true, true, false, operationalActorEntity));
        rootEntity.getRelations().add(this.createRelation("components", true, true, false, componentEntity));
        namedElementEntity.getAttributes().add(this.createAttribute("name", false, false, DataType.STRING));

        operationalEntityEntity.getRelations().add(this.createRelation("operationalPerimeters", true, true, false, operationalPerimeterEntity));
        operationalEntityEntity.getRelations().add(this.createRelation("operationalActors", true, true, false, operationalActorEntity));
        operationalPerimeterEntity.getRelations().add(this.createRelation("operationalActors", true, true, false, operationalActorEntity));
        operationalPerimeterEntity.getRelations().add(this.createRelation("operationalActivities", true, true, false, operationalActivityEntity));
        operationalActorEntity.getRelations().add(this.createRelation("operationalActivities", true, true, false, operationalActivityEntity));
        operationalActivityEntity.getRelations().add(this.createRelation("interactions", true, true, false, interaction));
        operationalActivityEntity.getRelations().add(this.createRelation("realizedBy", false, false, false, componentEntity));
        interaction.getRelations().add(this.createRelation("target", false, false, false, operationalActivityEntity));

        componentEntity.getRelations().add(this.createRelation("packages", true, true, false, packageEntity));
        componentEntity.getRelations().add(this.createRelation("providedServices", true, true, false, providedServiceEntity));
        componentEntity.getRelations().add(this.createRelation("requiredServices", true, true, false, requiredServiceEntity));
        providedServiceEntity.getRelations().add(this.createRelation("contract", false, false, false, interfaceEntity));
        requiredServiceEntity.getRelations().add(this.createRelation("contract", false, false, false, interfaceEntity));

        packageEntity.getRelations().add(this.createRelation("types", true, true, false, typeEntity));
        packageEntity.getRelations().add(this.createRelation("childPackages", true, true, false, packageEntity));
        typedElementEntity.getAttributes().add(this.createAttribute("isMany", false, false, DataType.BOOLEAN));
        typedElementEntity.getRelations().add(this.createRelation("type", false, false, false, typeEntity));
        interfaceEntity.getRelations().add(this.createRelation("operations", true, true, false, operationEntity));
        interfaceEntity.getRelations().add(this.createRelation("extends", false, true, false, interfaceEntity));
        classEntity.getAttributes().add(this.createAttribute("abstract", false, false, DataType.BOOLEAN));
        classEntity.getRelations().add(this.createRelation("implements", false, true, false, interfaceEntity));
        classEntity.getRelations().add(this.createRelation("extends", false, false, false, classEntity));
        classEntity.getRelations().add(this.createRelation("attributes", true, true, false, attributeEntity));
        classEntity.getRelations().add(this.createRelation("references", true, true, false, referenceEntity));
        classEntity.getRelations().add(this.createRelation("operations", true, true, false, operationEntity));
        attributeEntity.getRelations().add(this.createRelation("type", false, false, false, dataTypeEntity));
        attributeEntity.getAttributes().add(this.createAttribute("isMany", false, false, DataType.BOOLEAN));
        operationEntity.getRelations().add(this.createRelation("parameters", true, true, false, parameterEntity));
        enumEntity.getRelations().add(this.createRelation("enumLiterals", true, true, false, enumLiteralEntity));

        return domain;
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
        attribute.setType(DataType.STRING);
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
