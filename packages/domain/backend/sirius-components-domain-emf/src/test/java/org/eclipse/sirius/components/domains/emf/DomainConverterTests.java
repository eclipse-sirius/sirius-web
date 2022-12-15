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
package org.eclipse.sirius.components.domains.emf;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.domain.Attribute;
import org.eclipse.sirius.components.domain.DataType;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.DomainFactory;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.components.domain.Relation;
import org.eclipse.sirius.components.domain.emf.DomainConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Unit tests for {@link DomainConverter}.
 *
 * @author pcdavid
 */
public class DomainConverterTests {

    private static final String FIXTURE = "fixture";

    private Optional<EPackage> convert(Domain domain) {
        return new DomainConverter().convert(domain);
    }

    @Test
    public void testConvertDomainWithNullName() {
        Domain fixture = DomainFactory.eINSTANCE.createDomain();
        fixture.setName(null);
        Optional<EPackage> converted = this.convert(fixture);
        assertThat(converted).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "42", "almost!valid", "foo-bar", "foo.bar", "foo/bar" })
    public void testConvertDomainWithInvalidName(String name) {
        Domain fixture = DomainFactory.eINSTANCE.createDomain();
        fixture.setName(name);
        Optional<EPackage> converted = this.convert(fixture);
        assertThat(converted).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = { "ecore", "view", "SampleDomain", "Sample_Domain", "SysMLv2" })
    public void testConvertDomainWithValidName(String name) {
        Domain fixture = DomainFactory.eINSTANCE.createDomain();
        fixture.setName(name);
        Optional<EPackage> converted = this.convert(fixture);
        assertThat(converted).isPresent();
        assertThat(converted.get()).extracting(EPackage::getName).isEqualTo(name);
    }

    @Test
    public void testConvertEntityWithNullName() {
        Domain fixture = DomainFactory.eINSTANCE.createDomain();
        fixture.setName(FIXTURE);
        Entity invalidEntity = DomainFactory.eINSTANCE.createEntity();
        invalidEntity.setName(null);
        fixture.getTypes().add(invalidEntity);
        Optional<EPackage> converted = this.convert(fixture);
        assertThat(converted).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "42", "almost!valid", "foo-bar", "foo.bar", "foo/bar" })
    public void testConvertEntityWithInvalidName(String name) {
        Domain fixture = DomainFactory.eINSTANCE.createDomain();
        fixture.setName(FIXTURE);
        Entity invalidEntity = DomainFactory.eINSTANCE.createEntity();
        invalidEntity.setName(name);
        fixture.getTypes().add(invalidEntity);
        Optional<EPackage> converted = this.convert(fixture);
        assertThat(converted).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = { "lowercase", "snake_case", "CamelCase", "Sample_Entity", "A1" })
    public void testConvertEntityWithValidName(String name) {
        Domain fixture = DomainFactory.eINSTANCE.createDomain();
        fixture.setName(FIXTURE);
        Entity entity = DomainFactory.eINSTANCE.createEntity();
        entity.setName(name);
        fixture.getTypes().add(entity);
        Optional<EPackage> converted = this.convert(fixture);
        assertThat(converted).isPresent();
        EList<EClassifier> convertedTypes = converted.get().getEClassifiers();
        assertThat(convertedTypes).hasSize(1);
        assertThat(convertedTypes.get(0)).extracting(EClassifier::getName).isEqualTo(name);
    }

    @Test
    void testConvertSystemDomainDefinition() {
        Domain componentDomain = DomainFactory.eINSTANCE.createDomain();
        componentDomain.setName("component");

        Entity namedEntity = DomainFactory.eINSTANCE.createEntity();
        namedEntity.setName("Named");
        namedEntity.setAbstract(true);
        Attribute nameAttribute = DomainFactory.eINSTANCE.createAttribute();
        nameAttribute.setName("name");
        nameAttribute.setMany(false);
        nameAttribute.setOptional(false);
        nameAttribute.setType(DataType.STRING);
        namedEntity.getAttributes().add(nameAttribute);

        componentDomain.getTypes().add(namedEntity);

        Entity systemEntity = DomainFactory.eINSTANCE.createEntity();
        systemEntity.setName("System");
        systemEntity.getSuperTypes().add(namedEntity);
        componentDomain.getTypes().add(systemEntity);

        Entity componentEntity = DomainFactory.eINSTANCE.createEntity();
        componentEntity.setName("Component");
        componentEntity.getSuperTypes().add(namedEntity);
        componentDomain.getTypes().add(componentEntity);

        Relation systemComponentsRelation = DomainFactory.eINSTANCE.createRelation();
        systemComponentsRelation.setName("parts");
        systemComponentsRelation.setContainment(true);
        systemComponentsRelation.setMany(true);
        systemComponentsRelation.setOptional(true);
        systemComponentsRelation.setTargetType(componentEntity);
        systemEntity.getRelations().add(systemComponentsRelation);

        Optional<EPackage> converted = this.convert(componentDomain);
        assertThat(converted).isPresent();

        EPackage convertedPackage = converted.get();
        assertThat(convertedPackage.getEClassifiers()).hasSize(3);
        // @formatter:off
        assertThat(convertedPackage.getEClassifier(namedEntity.getName()))
                .asInstanceOf(InstanceOfAssertFactories.type(EClass.class))
                .matches(EClass::isAbstract)
                .matches(eClass -> eClass.getEAllAttributes().size() == 1)
                .matches(eClass -> eClass.getEAllReferences().isEmpty());
        assertThat(convertedPackage.getEClassifier(systemEntity.getName()))
                .asInstanceOf(InstanceOfAssertFactories.type(EClass.class))
                .matches(eClass -> !eClass.isAbstract())
                .matches(eClass -> eClass.getESuperTypes().size() == 1)
                .matches(eClass -> eClass.getEAttributes().isEmpty())
                .matches(eClass -> eClass.getEAllAttributes().size() == 1);
        assertThat(convertedPackage.getEClassifier(componentEntity.getName()))
                .asInstanceOf(InstanceOfAssertFactories.type(EClass.class))
                .matches(eClass -> !eClass.isAbstract())
                .matches(eClass -> eClass.getESuperTypes().size() == 1)
                .matches(eClass -> eClass.getEAttributes().isEmpty())
                .matches(eClass -> eClass.getEAllAttributes().size() == 1);
        // @formatter:on

    }

}
