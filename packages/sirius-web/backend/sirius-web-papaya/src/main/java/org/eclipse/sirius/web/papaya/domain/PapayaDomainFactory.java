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
package org.eclipse.sirius.web.papaya.domain;

import java.util.List;

import org.eclipse.sirius.components.domain.Attribute;
import org.eclipse.sirius.components.domain.DataType;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.DomainFactory;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.components.domain.Relation;

/**
 * Used to create the Papaya domains.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class PapayaDomainFactory {

    public static final String PAPAYA_CORE = "papaya_core";

    public static final String PAPAYA_OPERATIONAL_ANALYSIS = "papaya_operational_analysis";

    public static final String PAPAYA_LOGICAL_ARCHITECTURE = "papaya_logical_architecture";

    public static final String COMPONENT = "Component";

    public static final String PACKAGE = "Package";

    public static final String TYPE_PARAMETER = "TypeParameter";

    public static final String TYPE = "Type";

    public static final String ANNOTATION = "Annotation";

    public static final String DATA_TYPE = "DataType";

    public static final String INTERFACE = "Interface";

    public static final String RECORD = "Record";

    public static final String CLASS = "Class";

    public static final String ENUM = "Enum";

    public static final String PAPAYA_PLANNING = "papaya_planning";

    private Domain coreDomain;

    private Domain operationalAnalysisDomain;

    private Domain logicalArchitectureDomain;

    private Domain planningDomain;

    private Entity rootEntity;

    private Entity tagEntity;

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

    private Entity classifierEntity;

    private Entity typeParameterEntity;

    private Entity genericTypeEntity;

    private Entity interfaceEntity;

    private Entity classEntity;

    private Entity dataTypeEntity;

    private Entity enumEntity;

    private Entity enumLiteralEntity;

    private Entity attributeEntity;

    private Entity referenceEntity;

    private Entity operationEntity;

    private Entity parameterEntity;

    private Entity recordEntity;

    private Entity annotationEntity;

    private Entity annotableEntity;

    private Entity componentPortEntity;

    private Entity componentExchangeEntity;

    private Entity projectEntity;

    private Entity taskEntity;

    private Entity iterationEntity;

    private Entity contributionEntity;

    public List<Domain> getDomains() {
        this.coreDomain = DomainFactory.eINSTANCE.createDomain();
        this.coreDomain.setName(PAPAYA_CORE);

        this.operationalAnalysisDomain = DomainFactory.eINSTANCE.createDomain();
        this.operationalAnalysisDomain.setName(PAPAYA_OPERATIONAL_ANALYSIS);

        this.logicalArchitectureDomain = DomainFactory.eINSTANCE.createDomain();
        this.logicalArchitectureDomain.setName(PAPAYA_LOGICAL_ARCHITECTURE);

        this.planningDomain = DomainFactory.eINSTANCE.createDomain();
        this.planningDomain.setName(PAPAYA_PLANNING);

        this.createEObjects();
        this.linkEObjects();

        return List.of(this.coreDomain, this.operationalAnalysisDomain, this.logicalArchitectureDomain, this.planningDomain);
    }

    private void createEObjects() {
        this.createCoreObjects();
        this.createOperationalAnalysisObjects();
        this.createLogicalArchitectureObjects();
        this.createPlanningObjects();
    }

    private void createCoreObjects() {
        this.rootEntity = this.createEntity(this.coreDomain, "Root", false, List.of());
        this.tagEntity = this.createEntity(this.coreDomain, "Tag", false, List.of());
        this.modelElementEntity = this.createEntity(this.coreDomain, "ModelElement", true, List.of());
        this.namedElementEntity = this.createEntity(this.coreDomain, "NamedElement", true, List.of(this.modelElementEntity));
    }

    private void createOperationalAnalysisObjects() {
        this.operationalEntityEntity = this.createEntity(this.operationalAnalysisDomain, "OperationalEntity", false, List.of(this.namedElementEntity));
        this.operationalPerimeterEntity = this.createEntity(this.operationalAnalysisDomain, "OperationalPerimeter", false, List.of(this.namedElementEntity));
        this.operationalActorEntity = this.createEntity(this.operationalAnalysisDomain, "OperationalActor", false, List.of(this.namedElementEntity));
        this.operationalActivityEntity = this.createEntity(this.operationalAnalysisDomain, "OperationalActivity", false, List.of(this.namedElementEntity));
        this.interaction = this.createEntity(this.operationalAnalysisDomain, "Interaction", false, List.of(this.namedElementEntity));
    }

    private void createLogicalArchitectureObjects() {
        this.componentEntity = this.createEntity(this.logicalArchitectureDomain, "Component", false, List.of(this.namedElementEntity));
        this.providedServiceEntity = this.createEntity(this.logicalArchitectureDomain, "ProvidedService", false, List.of(this.modelElementEntity));
        this.requiredServiceEntity = this.createEntity(this.logicalArchitectureDomain, "RequiredService", false, List.of(this.modelElementEntity));

        this.componentPortEntity = this.createEntity(this.logicalArchitectureDomain, "ComponentPort", false, List.of(this.namedElementEntity));
        this.componentExchangeEntity = this.createEntity(this.logicalArchitectureDomain, "ComponentExchange", false, List.of(this.namedElementEntity));

        this.annotableEntity = this.createEntity(this.logicalArchitectureDomain, "Annotable", true, List.of());
        this.packageEntity = this.createEntity(this.logicalArchitectureDomain, "Package", false, List.of(this.namedElementEntity, this.annotableEntity));
        this.typeEntity = this.createEntity(this.logicalArchitectureDomain, "Type", true, List.of(this.namedElementEntity, this.annotableEntity));
        this.annotationEntity = this.createEntity(this.logicalArchitectureDomain, "Annotation", false, List.of(this.typeEntity));

        this.typedElementEntity = this.createEntity(this.logicalArchitectureDomain, "TypedElement", true, List.of(this.namedElementEntity));

        this.classifierEntity = this.createEntity(this.logicalArchitectureDomain, "Classifier", true, List.of(this.typeEntity));
        this.typeParameterEntity = this.createEntity(this.logicalArchitectureDomain, "TypeParameter", false, List.of(this.namedElementEntity));
        this.genericTypeEntity = this.createEntity(this.logicalArchitectureDomain, "GenericType", false, List.of(this.modelElementEntity));

        this.interfaceEntity = this.createEntity(this.logicalArchitectureDomain, "Interface", false, List.of(this.classifierEntity));
        this.classEntity = this.createEntity(this.logicalArchitectureDomain, "Class", false, List.of(this.classifierEntity));
        this.recordEntity = this.createEntity(this.logicalArchitectureDomain, "Record", false, List.of(this.classifierEntity, this.annotableEntity));

        this.dataTypeEntity = this.createEntity(this.logicalArchitectureDomain, "DataType", false, List.of(this.typeEntity));
        this.enumEntity = this.createEntity(this.logicalArchitectureDomain, "Enum", false, List.of(this.dataTypeEntity));
        this.enumLiteralEntity = this.createEntity(this.logicalArchitectureDomain, "EnumLiteral", false, List.of(this.namedElementEntity, this.annotableEntity));

        this.attributeEntity = this.createEntity(this.logicalArchitectureDomain, "Attribute", false, List.of(this.namedElementEntity, this.annotableEntity));
        this.referenceEntity = this.createEntity(this.logicalArchitectureDomain, "Reference", false, List.of(this.typedElementEntity, this.annotableEntity));
        this.operationEntity = this.createEntity(this.logicalArchitectureDomain, "Operation", false, List.of(this.typedElementEntity, this.annotableEntity));
        this.parameterEntity = this.createEntity(this.logicalArchitectureDomain, "Parameter", false, List.of(this.typedElementEntity, this.annotableEntity));
    }

    private void createPlanningObjects() {
        this.projectEntity = this.createEntity(this.planningDomain, "Project", false, List.of(this.namedElementEntity));
        this.taskEntity = this.createEntity(this.planningDomain, "Task", false, List.of(this.namedElementEntity));
        this.iterationEntity = this.createEntity(this.planningDomain, "Iteration", false, List.of(this.namedElementEntity));
        this.contributionEntity = this.createEntity(this.planningDomain, "Contribution", false, List.of(this.namedElementEntity));
    }

    private void linkEObjects() {
        this.linkCoreObjects();
        this.linkOperationAnalysisObjects();
        this.linkLogicalArchitectureObjects();
        this.linkPlanningObjects();
    }

    private void linkCoreObjects() {
        this.rootEntity.getRelations().add(this.createRelation("tags", true, true, true, this.tagEntity));
        this.rootEntity.getRelations().add(this.createRelation("operationalEntities", true, true, false, this.operationalEntityEntity));
        this.rootEntity.getRelations().add(this.createRelation("operationalActors", true, true, false, this.operationalActorEntity));
        this.rootEntity.getRelations().add(this.createRelation("components", true, true, false, this.componentEntity));
        this.rootEntity.getRelations().add(this.createRelation("componentExchanges", true, true, false, this.componentExchangeEntity));
        this.rootEntity.getRelations().add(this.createRelation("projects", true, true, false, this.projectEntity));

        this.tagEntity.getAttributes().add(this.createAttribute("key", false, false, DataType.STRING));
        this.tagEntity.getAttributes().add(this.createAttribute("value", false, false, DataType.STRING));

        this.modelElementEntity.getRelations().add(this.createRelation("tags", false, true, true, this.tagEntity));
        this.namedElementEntity.getAttributes().add(this.createAttribute("name", false, false, DataType.STRING));
    }

    private void linkOperationAnalysisObjects() {
        this.operationalEntityEntity.getRelations().add(this.createRelation("operationalPerimeters", true, true, false, this.operationalPerimeterEntity));
        this.operationalEntityEntity.getRelations().add(this.createRelation("operationalActors", true, true, false, this.operationalActorEntity));
        this.operationalPerimeterEntity.getRelations().add(this.createRelation("operationalActors", true, true, false, this.operationalActorEntity));
        this.operationalPerimeterEntity.getRelations().add(this.createRelation("operationalActivities", true, true, false, this.operationalActivityEntity));
        this.operationalActorEntity.getRelations().add(this.createRelation("operationalActivities", true, true, false, this.operationalActivityEntity));
        this.operationalActivityEntity.getRelations().add(this.createRelation("interactions", true, true, false, this.interaction));
        this.operationalActivityEntity.getRelations().add(this.createRelation("realizedBy", false, false, false, this.componentEntity));
        this.interaction.getRelations().add(this.createRelation("target", false, false, false, this.operationalActivityEntity));
    }

    private void linkLogicalArchitectureObjects() {
        this.componentEntity.getRelations().add(this.createRelation("dependencies", false, true, true, this.componentEntity));
        this.componentEntity.getRelations().add(this.createRelation("packages", true, true, false, this.packageEntity));
        this.componentEntity.getRelations().add(this.createRelation("providedServices", true, true, false, this.providedServiceEntity));
        this.componentEntity.getRelations().add(this.createRelation("requiredServices", true, true, false, this.requiredServiceEntity));
        this.componentEntity.getRelations().add(this.createRelation("components", true, true, false, this.componentEntity));
        this.componentEntity.getRelations().add(this.createRelation("ports", true, true, false, this.componentPortEntity));
        this.componentPortEntity.getAttributes().add(this.createAttribute("protocol", false, false, DataType.STRING));
        this.componentExchangeEntity.getAttributes().add(this.createAttribute("role", false, false, DataType.STRING));
        this.componentExchangeEntity.getAttributes().add(this.createAttribute("description", false, false, DataType.STRING));
        this.componentExchangeEntity.getRelations().add(this.createRelation("ports", false, true, true, this.componentPortEntity));
        this.providedServiceEntity.getRelations().add(this.createRelation("contract", false, false, false, this.interfaceEntity));
        this.requiredServiceEntity.getRelations().add(this.createRelation("contract", false, false, false, this.interfaceEntity));

        this.annotableEntity.getRelations().add(this.createRelation("annotations", false, true, true, this.annotationEntity));

        this.packageEntity.getAttributes().add(this.createAttribute("exported", false, false, DataType.BOOLEAN));
        this.packageEntity.getRelations().add(this.createRelation("types", true, true, false, this.typeEntity));
        this.packageEntity.getRelations().add(this.createRelation("packages", true, true, false, this.packageEntity));

        this.typeEntity.getRelations().add(this.createRelation("types", true, true, false, this.typeEntity));

        this.typedElementEntity.getRelations().add(this.createRelation("type", true, false, false, this.genericTypeEntity));

        this.classifierEntity.getRelations().add(this.createRelation("typeParameters", true, true, false, this.typeParameterEntity));
        this.typeParameterEntity.getRelations().add(this.createRelation("bounds", true, true, false, this.genericTypeEntity));
        this.genericTypeEntity.getRelations().add(this.createRelation("classifier", false, false, true, this.classifierEntity));
        this.genericTypeEntity.getRelations().add(this.createRelation("typeArguments", true, true, false, this.genericTypeEntity));

        this.interfaceEntity.getRelations().add(this.createRelation("operations", true, true, true, this.operationEntity));
        this.interfaceEntity.getRelations().add(this.createRelation("extends", false, true, true, this.interfaceEntity));

        this.classEntity.getAttributes().add(this.createAttribute("abstract", false, false, DataType.BOOLEAN));
        this.classEntity.getRelations().add(this.createRelation("implements", false, true, true, this.interfaceEntity));
        this.classEntity.getRelations().add(this.createRelation("extends", false, false, true, this.classEntity));
        this.classEntity.getRelations().add(this.createRelation("attributes", true, true, true, this.attributeEntity));
        this.classEntity.getRelations().add(this.createRelation("references", true, true, true, this.referenceEntity));
        this.classEntity.getRelations().add(this.createRelation("operations", true, true, true, this.operationEntity));

        this.recordEntity.getRelations().add(this.createRelation("implements", false, true, true, this.interfaceEntity));

        this.attributeEntity.getRelations().add(this.createRelation("type", false, false, false, this.dataTypeEntity));
        this.attributeEntity.getAttributes().add(this.createAttribute("many", false, false, DataType.BOOLEAN));

        this.operationEntity.getRelations().add(this.createRelation("parameters", true, true, false, this.parameterEntity));

        this.enumEntity.getRelations().add(this.createRelation("enumLiterals", true, true, false, this.enumLiteralEntity));
    }

    private void linkPlanningObjects() {
        this.projectEntity.getAttributes().add(this.createAttribute("description", false, false, DataType.STRING));
        this.projectEntity.getRelations().add(this.createRelation("iterations", true, true, false, this.iterationEntity));
        this.projectEntity.getRelations().add(this.createRelation("tasks", true, true, false, this.taskEntity));
        this.projectEntity.getRelations().add(this.createRelation("contributions", true, true, false, this.contributionEntity));

        this.taskEntity.getAttributes().add(this.createAttribute("description", false, false, DataType.STRING));
        this.taskEntity.getAttributes().add(this.createAttribute("priority", false, false, DataType.NUMBER));
        this.taskEntity.getRelations().add(this.createRelation("targets", false, true, false, this.modelElementEntity));
        this.taskEntity.getAttributes().add(this.createAttribute("done", false, false, DataType.BOOLEAN));
        this.taskEntity.getAttributes().add(this.createAttribute("startDate", false, false, DataType.STRING));
        this.taskEntity.getAttributes().add(this.createAttribute("endDate", false, false, DataType.STRING));
        this.taskEntity.getRelations().add(this.createRelation("tasks", true, true, false, this.taskEntity));

        this.iterationEntity.getAttributes().add(this.createAttribute("startDate", false, false, DataType.STRING));
        this.iterationEntity.getAttributes().add(this.createAttribute("endDate", false, false, DataType.STRING));
        this.iterationEntity.getRelations().add(this.createRelation("tasks", false, true, false, this.taskEntity));
        this.iterationEntity.getRelations().add(this.createRelation("contributions", false, true, false, this.contributionEntity));

        this.contributionEntity.getRelations().add(this.createRelation("targets", false, true, false, this.modelElementEntity));
        this.contributionEntity.getRelations().add(this.createRelation("relatedTasks", false, true, false, this.taskEntity));
        this.contributionEntity.getAttributes().add(this.createAttribute("done", false, false, DataType.BOOLEAN));
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
        attribute.setOptional(isOptional);
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
