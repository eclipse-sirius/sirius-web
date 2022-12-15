/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
import static org.eclipse.sirius.components.domains.emf.DiagnosticAssertions.assertThat;

import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.sirius.components.domain.Attribute;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.DomainFactory;
import org.eclipse.sirius.components.domain.DomainPackage;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.components.domain.NamedElement;
import org.eclipse.sirius.components.domain.Relation;
import org.eclipse.sirius.components.domain.emf.DomainValidator;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link DomainValidator}.
 *
 * @author gcoutable
 */
public class DomainValidatorTests {

    private static final String FAMILY = "Family";

    private static final String PERSON = "Person";

    @Test
    public void testDomainShouldBeValid() {
        Domain domain = DomainFactory.eINSTANCE.createDomain();
        domain.setName(FAMILY);

        this.assertNameIsValid(domain);
    }

    @Test
    public void testDomainEmptyName() {
        Domain domain = DomainFactory.eINSTANCE.createDomain();
        domain.setName("");

        this.assertNameIsInvalid(domain);
    }

    @Test
    public void testDomainNameWithSpaces() {
        Domain domain = DomainFactory.eINSTANCE.createDomain();
        domain.setName("Sample Family");

        this.assertNameIsInvalid(domain);
    }

    @Test
    public void testDomainNameWithInvalidCharacters() {
        Domain domain = DomainFactory.eINSTANCE.createDomain();
        domain.setName("Domain.Family");

        this.assertNameIsInvalid(domain);
    }

    @Test
    public void testDomainNameWithUnderscore() {
        Domain domain = DomainFactory.eINSTANCE.createDomain();
        domain.setName("Basic_Family");

        this.assertNameIsValid(domain);
    }

    @Test
    public void testEntityEmptyName() {
        Entity entity = DomainFactory.eINSTANCE.createEntity();
        entity.setName("");

        this.assertNameIsInvalid(entity);
    }

    @Test
    public void testEntityNameWithSpaces() {
        Entity entity = DomainFactory.eINSTANCE.createEntity();
        entity.setName("A person");

        this.assertNameIsInvalid(entity);
    }

    @Test
    public void testEntityNameWithInvalidCharacters() {
        Entity entity = DomainFactory.eINSTANCE.createEntity();
        entity.setName("Grand-parent");

        this.assertNameIsInvalid(entity);
    }

    @Test
    public void testEntityNameWithUnderscore() {
        Entity entity = DomainFactory.eINSTANCE.createEntity();
        entity.setName("Grand_Parent");

        this.assertNameIsValid(entity);
    }

    @Test
    public void testAttributeEmptyName() {
        Attribute attribute = DomainFactory.eINSTANCE.createAttribute();
        attribute.setName("");

        this.assertNameIsInvalid(attribute);
    }

    @Test
    public void testAttributeNameWithSpaces() {
        Attribute attribute = DomainFactory.eINSTANCE.createAttribute();
        attribute.setName("an attribute");

        this.assertNameIsInvalid(attribute);
    }

    @Test
    public void testAttributeNameWithInvalidCharacters() {
        Attribute attribute = DomainFactory.eINSTANCE.createAttribute();
        attribute.setName("an-attribute");

        this.assertNameIsInvalid(attribute);
    }

    @Test
    public void testAttributeNameWithUnderscore() {
        Attribute attribute = DomainFactory.eINSTANCE.createAttribute();
        attribute.setName("an_attribute");

        this.assertNameIsValid(attribute);
    }

    @Test
    public void testRelationEmptyName() {
        Relation relation = DomainFactory.eINSTANCE.createRelation();
        relation.setName("");

        this.assertNameIsInvalid(relation);
    }

    @Test
    public void testRelationNameWithSpaces() {
        Relation relation = DomainFactory.eINSTANCE.createRelation();
        relation.setName("a relation");

        this.assertNameIsInvalid(relation);
    }

    @Test
    public void testRelationNameWithInvalidCharacters() {
        Relation relation = DomainFactory.eINSTANCE.createRelation();
        relation.setName("some-relation");

        this.assertNameIsInvalid(relation);
    }

    @Test
    public void testRelationNameWithUnderscore() {
        Relation relation = DomainFactory.eINSTANCE.createRelation();
        relation.setName("some_relation");

        this.assertNameIsValid(relation);
    }

    @Test
    public void testEntityNameUniqueInDomain() {
        Domain domain = DomainFactory.eINSTANCE.createDomain();
        domain.setName(FAMILY);

        Entity e1 = DomainFactory.eINSTANCE.createEntity();
        e1.setName(PERSON);
        domain.getTypes().add(e1);

        Entity e2 = DomainFactory.eINSTANCE.createEntity();
        e2.setName(PERSON);
        domain.getTypes().add(e2);

        BasicDiagnostic diagnosticChain = new BasicDiagnostic(Diagnostic.OK, null, 0, null, null);

        Map<Object, Object> defaultContext = Diagnostician.INSTANCE.createDefaultContext();
        boolean validationResult = new DomainValidator().validate(e2.eClass(), e2, diagnosticChain, defaultContext);
        assertThat(validationResult).isFalse();

        // @formatter:off
        BasicDiagnostic expected = new BasicDiagnostic(Diagnostic.ERROR,
                DomainValidator.SIRIUS_COMPONENTS_EMF_PACKAGE,
                0,
                DomainValidator.ENTITY_DISTINCT_NAME_ERROR_MESSAGE,
                new Object [] {
                        e2,
                        DomainPackage.Literals.NAMED_ELEMENT__NAME,
        });
        // @formatter:on

        assertThat(diagnosticChain).contains(expected);
    }

    @Test
    public void testAttributeNameUniqueAmongEntityAttributes() {
        Entity entity = DomainFactory.eINSTANCE.createEntity();
        entity.setName(PERSON);

        String sharedName = "name";
        Attribute name1 = DomainFactory.eINSTANCE.createAttribute();
        name1.setName(sharedName);
        entity.getAttributes().add(name1);
        Attribute name2 = DomainFactory.eINSTANCE.createAttribute();
        name2.setName(sharedName);
        entity.getAttributes().add(name2);

        BasicDiagnostic diagnosticChain = new BasicDiagnostic(Diagnostic.OK, null, 0, null, null);

        Map<Object, Object> defaultContext = Diagnostician.INSTANCE.createDefaultContext();
        boolean validationResult = new DomainValidator().validate(name1.eClass(), name1, diagnosticChain, defaultContext);
        assertThat(validationResult).isFalse();

        // @formatter:off
        BasicDiagnostic expected = new BasicDiagnostic(Diagnostic.ERROR,
                DomainValidator.SIRIUS_COMPONENTS_EMF_PACKAGE,
                0,
                DomainValidator.FEATURE_DISTINCT_NAME_ERROR_MESSAGE,
                new Object [] {
                        name1,
                        DomainPackage.Literals.NAMED_ELEMENT__NAME,
        });
        // @formatter:on

        assertThat(diagnosticChain).contains(expected);
    }

    @Test
    public void testRelationNameUniqueAmongEntityRelations() {
        Entity entity = DomainFactory.eINSTANCE.createEntity();
        entity.setName(PERSON);

        String sharedName = "reference";
        Relation rel1 = DomainFactory.eINSTANCE.createRelation();
        rel1.setName(sharedName);
        entity.getRelations().add(rel1);
        Relation rel2 = DomainFactory.eINSTANCE.createRelation();
        rel2.setName(sharedName);
        entity.getRelations().add(rel2);

        BasicDiagnostic diagnosticChain = new BasicDiagnostic(Diagnostic.OK, null, 0, null, null);

        Map<Object, Object> defaultContext = Diagnostician.INSTANCE.createDefaultContext();
        boolean validationResult = new DomainValidator().validate(rel1.eClass(), rel1, diagnosticChain, defaultContext);
        assertThat(validationResult).isFalse();

        // @formatter:off
        BasicDiagnostic expected = new BasicDiagnostic(Diagnostic.ERROR,
                DomainValidator.SIRIUS_COMPONENTS_EMF_PACKAGE,
                0,
                DomainValidator.FEATURE_DISTINCT_NAME_ERROR_MESSAGE,
                new Object [] {
                        rel1,
                        DomainPackage.Literals.NAMED_ELEMENT__NAME,
        });
        // @formatter:on

        assertThat(diagnosticChain).contains(expected);
    }

    @Test
    public void testRelationNameUniqueAmongEntityFeatures() {
        Entity entity = DomainFactory.eINSTANCE.createEntity();
        entity.setName(PERSON);

        String sharedName = "reference";
        Relation rel = DomainFactory.eINSTANCE.createRelation();
        rel.setName(sharedName);
        entity.getRelations().add(rel);

        Attribute attr = DomainFactory.eINSTANCE.createAttribute();
        attr.setName(sharedName);
        entity.getAttributes().add(attr);

        BasicDiagnostic diagnosticChain = new BasicDiagnostic(Diagnostic.OK, null, 0, null, null);

        Map<Object, Object> defaultContext = Diagnostician.INSTANCE.createDefaultContext();
        boolean validationResult = new DomainValidator().validate(rel.eClass(), rel, diagnosticChain, defaultContext);
        assertThat(validationResult).isFalse();

        // @formatter:off
        BasicDiagnostic expected = new BasicDiagnostic(Diagnostic.ERROR,
                DomainValidator.SIRIUS_COMPONENTS_EMF_PACKAGE,
                0,
                DomainValidator.FEATURE_DISTINCT_NAME_ERROR_MESSAGE,
                new Object [] {
                        rel,
                        DomainPackage.Literals.NAMED_ELEMENT__NAME,
        });
        // @formatter:on

        assertThat(diagnosticChain).contains(expected);
    }

    private void assertNameIsInvalid(NamedElement namedElement) {
        BasicDiagnostic diagnosticChain = new BasicDiagnostic(Diagnostic.OK, null, 0, null, null);

        Map<Object, Object> defaultContext = Diagnostician.INSTANCE.createDefaultContext();
        boolean validationResult = new DomainValidator().validate(namedElement.eClass(), namedElement, diagnosticChain, defaultContext);
        assertThat(validationResult).isFalse();

        BasicDiagnostic expected = new BasicDiagnostic(Diagnostic.WARNING, null, 0, null, null);
        // @formatter:off
        expected.add(new BasicDiagnostic(Diagnostic.WARNING,
                DomainValidator.SIRIUS_COMPONENTS_EMF_PACKAGE,
                0,
                String.format(DomainValidator.INVALID_NAME_ERROR_MESSAGE, namedElement.getName()),
                new Object [] {
                        namedElement,
                        DomainPackage.Literals.NAMED_ELEMENT__NAME,
        }));
        // @formatter:on

        assertThat(diagnosticChain).isEqualTo(expected);
    }

    private void assertNameIsValid(NamedElement namedElement) {
        BasicDiagnostic diagnosticChain = new BasicDiagnostic(Diagnostic.OK, null, 0, null, null);

        Map<Object, Object> defaultContext = Diagnostician.INSTANCE.createDefaultContext();
        boolean validationResult = new DomainValidator().validate(namedElement.eClass(), namedElement, diagnosticChain, defaultContext);
        assertThat(validationResult).isTrue();
    }

}
