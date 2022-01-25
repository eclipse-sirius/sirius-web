/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.components.graphql.utils.providers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import graphql.Scalars;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLNamedOutputType;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLTypeReference;

/**
 * Unit tests of the GraphQL object type provider.
 *
 * @author sbegaudeau
 */
public class GraphQLObjectTypeProviderTests {
    /**
     * In this test, we will see if we can create a proper GraphQL object type for a POJO containing a single String
     * field. For that we will consider the following POJO:
     *
     * <pre>
     * &#64;GraphQLObjectType
     * public class ObjectTypeWithString {
     *     private String name;
     *
     *     &#64;GraphQLField
     *     public String getName() {
     *         return this.name;
     *     }
     * }
     * </pre>
     *
     * <p>
     * Using this POJO, we should create the following GraphQL object type:
     * </p>
     *
     * <pre>
     * type ObjectTypeWithString {
     *   name: String
     * }
     * </pre>
     */
    @Test
    public void testObjectTypeWithString() {
        GraphQLObjectType graphQLObjectType = new GraphQLObjectTypeProvider().getType(ObjectTypeWithString.class);
        assertThat(graphQLObjectType.getName()).isEqualTo(ObjectTypeWithString.class.getSimpleName());
        assertThat(graphQLObjectType.getFieldDefinitions()).hasSize(1);

        GraphQLFieldDefinition fieldDefinition = graphQLObjectType.getFieldDefinitions().get(0);
        assertThat(fieldDefinition.getName()).isEqualTo("name"); //$NON-NLS-1$
        assertThat(fieldDefinition.getType()).isEqualTo(Scalars.GraphQLString);
    }

    /**
     * In this test, we will see if we can create a proper GraphQL object type for a POJO containing a single list of
     * Strings field. For that we will consider the following POJO:
     *
     * <pre>
     * &#64;GraphQLObjectType
     * public class ObjectTypeWithListOfStrings {
     *     private List<String> listOfStrings;
     *
     *     &#64;GraphQLField
     *     public List<String> getListOfStrings() {
     *         return this.listOfStrings;
     *     }
     * }
     * </pre>
     *
     * <p>
     * Using this POJO, we should create the following GraphQL object type:
     * </p>
     *
     * <pre>
     * type ObjectTypeWithListOfStrings {
     *   listOfStrings: [String]
     * }
     * </pre>
     */
    @Test
    public void testObjectTypeWithListOfStrings() {
        GraphQLObjectType graphQLObjectType = new GraphQLObjectTypeProvider().getType(ObjectTypeWithListOfStrings.class);
        assertThat(graphQLObjectType.getName()).isEqualTo(ObjectTypeWithListOfStrings.class.getSimpleName());
        assertThat(graphQLObjectType.getFieldDefinitions()).hasSize(1);

        GraphQLFieldDefinition fieldDefinition = graphQLObjectType.getFieldDefinitions().get(0);
        assertThat(fieldDefinition.getName()).isEqualTo("listOfStrings"); //$NON-NLS-1$
        assertThat(fieldDefinition.getType()).isInstanceOf(GraphQLList.class);

        GraphQLList list = (GraphQLList) fieldDefinition.getType();
        assertThat(list).extracting(GraphQLList::getWrappedType).isEqualTo(Scalars.GraphQLString);
    }

    /**
     * In this test, we will see if we can create a proper GraphQL object type for a POJO containing a non null list of
     * Strings field. For that we will consider the following POJO:
     *
     * <pre>
     * &#64;GraphQLObjectType
     * public class ObjectTypeWithNonNullListOfStrings {
     *     private List<String> nonNullListOfStrings;
     *
     *     &#64;NonNull
     *     &#64;GraphQLField
     *     public List<String> getNonNullListOfStrings() {
     *         return this.nonNullListOfStrings;
     *     }
     * }
     * </pre>
     *
     * <p>
     * Using this POJO, we should create the following GraphQL object type:
     * </p>
     *
     * <pre>
     * type ObjectTypeWithNonNullListOfStrings {
     *   nonNullListOfStrings: [String]!
     * }
     * </pre>
     */
    @Test
    public void testObjectTypeWithNonNullListOfStrings() {
        GraphQLObjectType graphQLObjectType = new GraphQLObjectTypeProvider().getType(ObjectTypeWithNonNullListOfStrings.class);
        assertThat(graphQLObjectType.getName()).isEqualTo(ObjectTypeWithNonNullListOfStrings.class.getSimpleName());
        assertThat(graphQLObjectType.getFieldDefinitions()).hasSize(1);

        GraphQLFieldDefinition fieldDefinition = graphQLObjectType.getFieldDefinitions().get(0);
        assertThat(fieldDefinition.getName()).isEqualTo("nonNullListOfStrings"); //$NON-NLS-1$
        assertThat(fieldDefinition.getType()).isInstanceOf(GraphQLNonNull.class);

        GraphQLNonNull nonNull = (GraphQLNonNull) fieldDefinition.getType();
        assertThat(nonNull).extracting(GraphQLNonNull::getWrappedType).isInstanceOf(GraphQLList.class);

        GraphQLList list = (GraphQLList) nonNull.getWrappedType();
        assertThat(list).extracting(GraphQLList::getWrappedType).isEqualTo(Scalars.GraphQLString);
    }

    /**
     * In this test, we will see if we can create a proper GraphQL object type for a POJO containing a non null list of
     * non null Strings field. For that we will consider the following POJO:
     *
     * <pre>
     * &#64;GraphQLObjectType
     * public class ObjectTypeWithNonNullListOfNonNullStrings {
     *     private List<String> nonNullListOfNonNullStrings;
     *
     *     &#64;NonNull
     *     public List<&#64;NonNull String> getNonNullListOfNonNullStrings() {
     *         return this.nonNullListOfNonNullStrings;
     *     }
     * }
     * </pre>
     *
     * <p>
     * Using this POJO, we should create the following GraphQL object type:
     * </p>
     *
     * <pre>
     * type ObjectTypeWithNonNullListOfNonNullStrings {
     *   nonNullListOfNonNullStrings: [String!]!
     * }
     * </pre>
     */
    @Test
    public void testObjectTypeWithNonNullListOfNonNullStrings() {
        GraphQLObjectType graphQLObjectType = new GraphQLObjectTypeProvider().getType(ObjectTypeWithNonNullListOfNonNullStrings.class);
        assertThat(graphQLObjectType.getName()).isEqualTo(ObjectTypeWithNonNullListOfNonNullStrings.class.getSimpleName());
        assertThat(graphQLObjectType.getFieldDefinitions()).hasSize(1);

        GraphQLFieldDefinition fieldDefinition = graphQLObjectType.getFieldDefinitions().get(0);
        assertThat(fieldDefinition.getName()).isEqualTo("nonNullListOfNonNullStrings"); //$NON-NLS-1$
        assertThat(fieldDefinition.getType()).isInstanceOf(GraphQLNonNull.class);

        GraphQLNonNull nonNullList = (GraphQLNonNull) fieldDefinition.getType();
        assertThat(nonNullList).extracting(GraphQLNonNull::getWrappedType).isInstanceOf(GraphQLList.class);

        GraphQLList list = (GraphQLList) nonNullList.getWrappedType();
        assertThat(list).extracting(GraphQLList::getWrappedType).isInstanceOf(GraphQLNonNull.class);

        GraphQLNonNull nonNullStrings = (GraphQLNonNull) list.getWrappedType();
        assertThat(nonNullStrings).extracting(GraphQLNonNull::getWrappedType).isEqualTo(Scalars.GraphQLString);
    }

    /**
     * In this test, we will see if we can create a proper GraphQL object type for a POJO containing a single non null
     * String field. For that we will consider the following POJO:
     *
     * <pre>
     * &#64;GraphQLObjectType
     * public class ObjectTypeWithNonNullString {
     *     private String name;
     *
     *     &#64;NonNull
     *     &#64;GraphQLField
     *     public String getName() {
     *         return this.name;
     *     }
     * }
     * </pre>
     *
     * <p>
     * Using this POJO, we should create the following GraphQL object type:
     * </p>
     *
     * <pre>
     * type ObjectTypeWithNonNullString {
     *   name: String!
     * }
     * </pre>
     */
    @Test
    public void testObjectTypeWithNonNullString() {
        GraphQLObjectType graphQLObjectType = new GraphQLObjectTypeProvider().getType(ObjectTypeWithNonNullString.class);

        assertThat(graphQLObjectType.getName()).isEqualTo(ObjectTypeWithNonNullString.class.getSimpleName());
        assertThat(graphQLObjectType.getFieldDefinitions()).hasSize(1);

        GraphQLFieldDefinition nameFieldDefinition = graphQLObjectType.getFieldDefinitions().get(0);
        assertThat(nameFieldDefinition.getName()).isEqualTo("name"); //$NON-NLS-1$
        assertThat(nameFieldDefinition.getType()).isInstanceOf(GraphQLNonNull.class);

        GraphQLNonNull graphQLNonNull = (GraphQLNonNull) nameFieldDefinition.getType();
        assertThat(graphQLNonNull.getWrappedType()).isEqualTo(Scalars.GraphQLString);
    }

    /**
     * In this test, we will see if we can create a proper GraphQL object type for a POJO containing a single boolean
     * field. For that we will consider the following POJO:
     *
     * <pre>
     * &#64;GraphQLObjectType
     * public class ObjectTypeWithBoolean {
     *     private boolean alive;
     *
     *     &#64;GraphQLField
     *     public boolean isAlive() {
     *         return this.alive;
     *     }
     * }
     * </pre>
     *
     * <p>
     * Using this POJO, we should create the following GraphQL object type:
     * </p>
     *
     * <pre>
     * type ObjectTypeWithBoolean {
     *   alive: Boolean
     * }
     * </pre>
     */
    @Test
    public void testObjectTypeWithBoolean() {
        GraphQLObjectType graphQLObjectType = new GraphQLObjectTypeProvider().getType(ObjectTypeWithBoolean.class);

        assertThat(graphQLObjectType.getName()).isEqualTo(ObjectTypeWithBoolean.class.getSimpleName());
        assertThat(graphQLObjectType.getFieldDefinitions()).hasSize(1);

        GraphQLFieldDefinition aliveFieldDefinition = graphQLObjectType.getFieldDefinitions().get(0);
        assertThat(aliveFieldDefinition.getName()).isEqualTo("alive"); //$NON-NLS-1$
        assertThat(aliveFieldDefinition.getType()).isEqualTo(Scalars.GraphQLBoolean);
    }

    /**
     * In this test, we will see if we can create a proper GraphQL object type for a POJO containing a single non null
     * boolean field. For that we will consider the following POJO:
     *
     * <pre>
     * &#64;GraphQLObjectType
     * public class ObjectTypeWithNonNullBoolean {
     *     private boolean alive;
     *
     *     &#64;NonNull
     *     &#64;GraphQLField
     *     public boolean isAlive() {
     *         return this.alive;
     *     }
     * }
     * </pre>
     *
     * <p>
     * Using this POJO, we should create the following GraphQL object type:
     * </p>
     *
     * <pre>
     * type ObjectTypeWithNonNullBoolean {
     *   alive: Boolean!
     * }
     * </pre>
     */
    @Test
    public void testObjectTypeWithNonNullBoolean() {
        GraphQLObjectType graphQLObjectType = new GraphQLObjectTypeProvider().getType(ObjectTypeWithNonNullBoolean.class);

        assertThat(graphQLObjectType.getName()).isEqualTo(ObjectTypeWithNonNullBoolean.class.getSimpleName());
        assertThat(graphQLObjectType.getFieldDefinitions()).hasSize(1);

        GraphQLFieldDefinition aliveFieldDefinition = graphQLObjectType.getFieldDefinitions().get(0);
        assertThat(aliveFieldDefinition.getName()).isEqualTo("alive"); //$NON-NLS-1$
        assertThat(aliveFieldDefinition.getType()).isInstanceOf(GraphQLNonNull.class);

        GraphQLNonNull graphQLNonNull = (GraphQLNonNull) aliveFieldDefinition.getType();
        assertThat(graphQLNonNull.getWrappedType()).isEqualTo(Scalars.GraphQLBoolean);
    }

    /**
     * In this test, we will see if we can create a proper GraphQL object type for a POJO containing a single int field.
     * For that we will consider the following POJO:
     *
     * <pre>
     * &#64;GraphQLObjectType
     * public class ObjectTypeWithInt {
     *     private int age;
     *     &#64;GraphQLField
     *     public int getAge() {
     *         this.age;
     *     }
     * }
     * </pre>
     *
     * <p>
     * Using this POJO, we should create the following GraphQL object type:
     * </p>
     *
     * <pre>
     * type ObjectTypeWithInt {
     *   age: Int
     * }
     * </pre>
     */
    @Test
    public void testObjectTypeWithInt() {
        GraphQLObjectType graphQLObjectType = new GraphQLObjectTypeProvider().getType(ObjectTypeWithInt.class);

        assertThat(graphQLObjectType.getName()).isEqualTo(ObjectTypeWithInt.class.getSimpleName());
        assertThat(graphQLObjectType.getFieldDefinitions()).hasSize(1);

        GraphQLFieldDefinition ageFieldDefinition = graphQLObjectType.getFieldDefinitions().get(0);
        assertThat(ageFieldDefinition.getName()).isEqualTo("age"); //$NON-NLS-1$
        assertThat(ageFieldDefinition.getType()).isEqualTo(Scalars.GraphQLInt);
    }

    /**
     * In this test, we will see if we can create a proper GraphQL object type for a POJO containing a single non null
     * int field. For that we will consider the following POJO:
     *
     * <pre>
     * &#64;GraphQLObjectType
     * public class ObjectTypeWithNonNullInt {
     *     private int age;
     *
     *     &#64;NonNull
     *     &#64;GraphQLField
     *     public int getAge() {
     *         return this.age;
     *     }
     * }
     * </pre>
     *
     * <p>
     * Using this POJO, we should create the following GraphQL object type:
     * </p>
     *
     * <pre>
     * type ObjectTypeWithInt {
     *   age: Int!
     * }
     * </pre>
     */
    @Test
    public void testObjectTypeWithNonNullInt() {
        GraphQLObjectType graphQLObjectType = new GraphQLObjectTypeProvider().getType(ObjectTypeWithNonNullInt.class);
        assertThat(graphQLObjectType.getName()).isEqualTo(ObjectTypeWithNonNullInt.class.getSimpleName());
        assertThat(graphQLObjectType.getFieldDefinitions()).hasSize(1);

        GraphQLFieldDefinition fieldDefinition = graphQLObjectType.getFieldDefinitions().get(0);
        assertThat(fieldDefinition.getName()).isEqualTo("age"); //$NON-NLS-1$
        assertThat(fieldDefinition.getType()).isInstanceOf(GraphQLNonNull.class);

        GraphQLNonNull fieldDefinitionType = (GraphQLNonNull) fieldDefinition.getType();
        assertThat(fieldDefinitionType.getWrappedType()).isEqualTo(Scalars.GraphQLInt);
    }

    /**
     * In this test, we will see if we can create a proper GraphQL object type for a POJO containing a reference to
     * another type. For that we will consider the following POJO:
     *
     * <pre>
     * &#64;GraphQLObjectType
     * public class ObjectTypeWithReference {
     *     private ReferencedObject reference;
     *
     *     &#64;GraphQLField
     *     public ReferencedObject getReference() {
     *         return this.reference;
     *     }
     * }
     * </pre>
     *
     * <p>
     * Using this POJO, we should create the following GraphQL object type:
     * </p>
     *
     * <pre>
     * type ObjectTypeWithReference {
     *   reference: ReferencedObject
     * }
     * </pre>
     */
    @Test
    public void testObjectTypeWithReference() {
        GraphQLObjectType graphQLObjectType = new GraphQLObjectTypeProvider().getType(ObjectTypeWithReference.class);

        assertThat(graphQLObjectType.getName()).isEqualTo(ObjectTypeWithReference.class.getSimpleName());
        assertThat(graphQLObjectType.getFieldDefinitions()).hasSize(1);

        GraphQLFieldDefinition referenceFieldDefinition = graphQLObjectType.getFieldDefinitions().get(0);
        assertThat(referenceFieldDefinition.getName()).isEqualTo("reference"); //$NON-NLS-1$
        assertThat(referenceFieldDefinition.getType()).isInstanceOf(GraphQLTypeReference.class);

        GraphQLTypeReference graphQLTypeReference = (GraphQLTypeReference) referenceFieldDefinition.getType();
        assertThat(graphQLTypeReference.getName()).isEqualTo("ReferencedObject"); //$NON-NLS-1$
    }

    /**
     * In this test, we will see if we can create a proper GraphQL object type for a POJO containing a non null
     * reference to another type. For that we will consider the following POJO:
     *
     * <pre>
     * &#64;GraphQLObjectType
     * public class ObjectTypeWithNonNullReference {
     *     private ReferencedObject reference;
     *
     *     &#64;NonNull
     *     &#64;GraphQLField
     *     public ReferencedObject getReference() {
     *         return this.reference;
     *     }
     * }
     * </pre>
     *
     * <p>
     * Using this POJO, we should create the following GraphQL object type:
     * </p>
     *
     * <pre>
     * type ObjectTypeWithNonNullReference {
     *   reference: ReferencedObject!
     * }
     * </pre>
     */
    @Test
    public void testObjectTypeWithNonNullReference() {
        GraphQLObjectType graphQLObjectType = new GraphQLObjectTypeProvider().getType(ObjectTypeWithNonNullReference.class);

        assertThat(graphQLObjectType.getName()).isEqualTo(ObjectTypeWithNonNullReference.class.getSimpleName());
        assertThat(graphQLObjectType.getFieldDefinitions()).hasSize(1);

        GraphQLFieldDefinition referenceFieldDefinition = graphQLObjectType.getFieldDefinitions().get(0);
        assertThat(referenceFieldDefinition.getName()).isEqualTo("reference"); //$NON-NLS-1$
        assertThat(referenceFieldDefinition.getType()).isInstanceOf(GraphQLNonNull.class);

        GraphQLNonNull graphQLNonNull = (GraphQLNonNull) referenceFieldDefinition.getType();
        assertThat(graphQLNonNull.getWrappedType()).isInstanceOf(GraphQLTypeReference.class);

        GraphQLTypeReference graphQLTypeReference = (GraphQLTypeReference) graphQLNonNull.getWrappedType();
        assertThat(graphQLTypeReference.getName()).isEqualTo("ReferencedObject"); //$NON-NLS-1$
    }

    /**
     * In this test, we will see if we can create a proper GraphQL object type for a POJO with a custom name.
     *
     * <pre>
     * &#64;GraphQLObjectType(name = "AnotherName")
     * public class ObjectTypeWithAnotherName {
     * }
     * </pre>
     *
     * <p>
     * Using this POJO, we should create the following GraphQL object type:
     * </p>
     *
     * <pre>
     * type AnotherName {
     * }
     * </pre>
     */
    @Test
    public void testObjectTypeWithAnotherName() {
        GraphQLObjectType graphQLObjectType = new GraphQLObjectTypeProvider().getType(ObjectTypeWithAnotherName.class);
        assertThat(graphQLObjectType.getName()).isEqualTo("AnotherName"); //$NON-NLS-1$
        assertThat(graphQLObjectType.getFieldDefinitions()).hasSize(0);
    }

    /**
     * In this test, we will see if we can create a proper GraphQL object type for a POJO with a reference to a renamed
     * POJO.
     *
     * <pre>
     * &#64;GraphQLObjectType
     * public class ObjectTypeWithReferenceToRenamedObject {
     *     private ObjectTypeWithAnotherName renamedReference;
     *
     *     &#64;GraphQLField
     *     public ObjectTypeWithAnotherName getRenamedReference() {
     *         return this.renamedReference;
     *     }
     * }
     * </pre>
     *
     * <p>
     * Using this POJO, we should create the following GraphQL object type:
     * </p>
     *
     * <pre>
     * type ObjectTypeWithReferenceToRenamedObject {
     *   renamedReference: AnotherName
     * }
     * </pre>
     */
    @Test
    public void testObjectTypeWithReferenceToRenamedObject() {
        GraphQLObjectType graphQLObjectType = new GraphQLObjectTypeProvider().getType(ObjectTypeWithReferenceToRenamedObject.class);
        assertThat(graphQLObjectType.getName()).isEqualTo(ObjectTypeWithReferenceToRenamedObject.class.getSimpleName());
        assertThat(graphQLObjectType.getFieldDefinitions()).hasSize(1);

        GraphQLFieldDefinition fieldDefinition = graphQLObjectType.getFieldDefinitions().get(0);
        assertThat(fieldDefinition.getName()).isEqualTo("renamedReference"); //$NON-NLS-1$
        assertThat(fieldDefinition.getType()).isInstanceOf(GraphQLTypeReference.class);

        GraphQLTypeReference graphQLTypeReference = (GraphQLTypeReference) fieldDefinition.getType();
        assertThat(graphQLTypeReference.getName()).isEqualTo("AnotherName"); //$NON-NLS-1$
    }

    /**
     * In this test, we will see if we can create a proper GraphQL object type for a POJO extending a POJO marked as an
     * interface. For that we will consider the following POJO:
     *
     * <pre>
     * &#64;GraphQLObjectType
     * public class ObjectTypeImplementsInterfaceTypeWithString extends AbstractInterfaceTypeWithString {
     *     private String address;
     *
     *     &#64;GraphQLField
     *     public String getAddress() {
     *         return this.address;
     *     }
     * }
     * </pre>
     *
     * <p>
     * Using this POJO, we should create the following GraphQL interface type:
     * </p>
     *
     * <pre>
     * type ObjectTypeImplementsInterfaceTypeWithString implements AbstractInterfaceTypeWithString {
     *   address: String
     *   description: String
     * }
     * </pre>
     */
    @Test
    public void testObjectTypeImplementsInterfaceTypeWithString() {
        GraphQLObjectType graphQLObjectType = new GraphQLObjectTypeProvider().getType(ObjectTypeImplementsInterfaceTypeWithString.class);
        assertThat(graphQLObjectType.getName()).isEqualTo(ObjectTypeImplementsInterfaceTypeWithString.class.getSimpleName());
        assertThat(graphQLObjectType.getFieldDefinitions()).hasSize(2);
        assertThat(graphQLObjectType.getInterfaces()).hasSize(1);

        GraphQLOutputType firstInterface = graphQLObjectType.getInterfaces().get(0);
        assertThat(firstInterface).isInstanceOf(GraphQLNamedOutputType.class);

        GraphQLNamedOutputType firstInterfaceType = (GraphQLNamedOutputType) firstInterface;
        assertThat(firstInterfaceType.getName()).isEqualTo(AbstractInterfaceTypeWithString.class.getSimpleName());

        GraphQLFieldDefinition addressFieldDefinition = graphQLObjectType.getFieldDefinitions().get(0);
        assertThat(addressFieldDefinition.getName()).isEqualTo("address"); //$NON-NLS-1$
        assertThat(addressFieldDefinition.getType()).isEqualTo(Scalars.GraphQLString);

        GraphQLFieldDefinition descriptionFieldDefinition = graphQLObjectType.getFieldDefinitions().get(1);
        assertThat(descriptionFieldDefinition.getName()).isEqualTo("description"); //$NON-NLS-1$
        assertThat(descriptionFieldDefinition.getType()).isEqualTo(Scalars.GraphQLString);
    }

    /**
     * In this test, we will see if we can create a proper GraphQL object type for a POJO containing a single ID field.
     * For that we will consider the following POJO:
     *
     * <pre>
     * &#64;GraphQLObjectType
     * public class ObjectTypeWithID {
     *     private String id;
     *
     *     &#64;GraphQLID
     *     &#64;GraphQLField
     *     public String getId() {
     *         return this.id;
     *     }
     * }
     * </pre>
     *
     * <p>
     * Using this POJO, we should create the following GraphQL object type:
     * </p>
     *
     * <pre>
     * type ObjectTypeWithID {
     *   id: ID
     * }
     * </pre>
     */
    @Test
    public void testObjectTypeWithID() {
        GraphQLObjectType graphQLObjectType = new GraphQLObjectTypeProvider().getType(ObjectTypeWithID.class);
        assertThat(graphQLObjectType.getName()).isEqualTo(ObjectTypeWithID.class.getSimpleName());
        assertThat(graphQLObjectType.getFieldDefinitions()).hasSize(1);

        GraphQLFieldDefinition fieldDefinition = graphQLObjectType.getFieldDefinitions().get(0);
        assertThat(fieldDefinition.getName()).isEqualTo("id"); //$NON-NLS-1$
        assertThat(fieldDefinition.getType()).isEqualTo(Scalars.GraphQLID);
    }

    /**
     * Test POJO used to verify that we can support the creation of a GraphQL object type for a POJO with a simple
     * String field.
     *
     * @author sbegaudeau
     */
    @org.eclipse.sirius.components.annotations.graphql.GraphQLObjectType
    public static class ObjectTypeWithString {
        private String name;

        @org.eclipse.sirius.components.annotations.graphql.GraphQLField
        public String getName() {
            return this.name;
        }
    }

    /**
     * Test POJO used to verify that we can support the creation of a GraphQL object type for a POJO with a simple non
     * null String field.
     *
     * @author sbegaudeau
     */
    @org.eclipse.sirius.components.annotations.graphql.GraphQLObjectType
    public static class ObjectTypeWithNonNullString {
        private String name;

        @org.eclipse.sirius.components.annotations.graphql.GraphQLNonNull
        @org.eclipse.sirius.components.annotations.graphql.GraphQLField
        public String getName() {
            return this.name;
        }
    }

    /**
     * Test POJO used to verify that we can support the creation of a GraphQL object type for a POJO with a simple list
     * of strings field.
     *
     * @author sbegaudeau
     */
    @org.eclipse.sirius.components.annotations.graphql.GraphQLObjectType
    public static class ObjectTypeWithListOfStrings {
        private List<String> listOfStrings;

        @org.eclipse.sirius.components.annotations.graphql.GraphQLField
        public List<String> getListOfStrings() {
            return this.listOfStrings;
        }
    }

    /**
     * Test POJO used to verify that we can support the creation of a GraphQL object type for a POJO with a simple non
     * null list of strings field.
     *
     * @author sbegaudeau
     */
    @org.eclipse.sirius.components.annotations.graphql.GraphQLObjectType
    public static class ObjectTypeWithNonNullListOfStrings {
        private List<String> nonNullListOfStrings;

        @org.eclipse.sirius.components.annotations.graphql.GraphQLNonNull
        @org.eclipse.sirius.components.annotations.graphql.GraphQLField
        public List<String> getNonNullListOfStrings() {
            return this.nonNullListOfStrings;
        }
    }

    /**
     * Test POJO used to verify that we can support the creation of a GraphQL object type for a POJO with a simple non
     * null list of non null strings field.
     *
     * @author sbegaudeau
     */
    @org.eclipse.sirius.components.annotations.graphql.GraphQLObjectType
    public static class ObjectTypeWithNonNullListOfNonNullStrings {
        private List<String> nonNullListOfNonNullStrings;

        @org.eclipse.sirius.components.annotations.graphql.GraphQLNonNull
        @org.eclipse.sirius.components.annotations.graphql.GraphQLField
        public List<@org.eclipse.sirius.components.annotations.graphql.GraphQLNonNull String> getNonNullListOfNonNullStrings() {
            return this.nonNullListOfNonNullStrings;
        }
    }

    /**
     * Test POJO used to verify that we can support the creation of a GraphQL object type for a POJO with a simple
     * boolean field.
     *
     * @author sbegaudeau
     */
    @org.eclipse.sirius.components.annotations.graphql.GraphQLObjectType
    public static class ObjectTypeWithBoolean {
        private boolean alive;

        @org.eclipse.sirius.components.annotations.graphql.GraphQLField
        public boolean isAlive() {
            return this.alive;
        }
    }

    /**
     * Test POJO used to verify that we can support the creation of a GraphQL object type for a POJO with a simple non
     * null boolean field.
     *
     * @author sbegaudeau
     */
    @org.eclipse.sirius.components.annotations.graphql.GraphQLObjectType
    public static class ObjectTypeWithNonNullBoolean {
        private boolean alive;

        @org.eclipse.sirius.components.annotations.graphql.GraphQLNonNull
        @org.eclipse.sirius.components.annotations.graphql.GraphQLField
        public boolean isAlive() {
            return this.alive;
        }
    }

    /**
     * Test POJO used to verify that we can support the creation of a GraphQL object type for a POJO with a simple int
     * field.
     *
     * @author sbegaudeau
     */
    @org.eclipse.sirius.components.annotations.graphql.GraphQLObjectType
    public static class ObjectTypeWithInt {
        private int age;

        @org.eclipse.sirius.components.annotations.graphql.GraphQLField
        public int getAge() {
            return this.age;
        }
    }

    /**
     * Test POJO used to verify that we can support the creation of a GraphQL object type for a POJO with a simple non
     * null int field.
     *
     * @author sbegaudeau
     */
    @org.eclipse.sirius.components.annotations.graphql.GraphQLObjectType
    public static class ObjectTypeWithNonNullInt {
        private int age;

        @org.eclipse.sirius.components.annotations.graphql.GraphQLNonNull
        @org.eclipse.sirius.components.annotations.graphql.GraphQLField
        public int getAge() {
            return this.age;
        }
    }

    /**
     * Test POJO used to verify that we can support the creation of a GraphQL object type for a POJO with a field
     * referencing another POJO.
     *
     * @author sbegaudeau
     */
    @org.eclipse.sirius.components.annotations.graphql.GraphQLObjectType
    public static class ObjectTypeWithReference {
        private ReferencedObject reference;

        @org.eclipse.sirius.components.annotations.graphql.GraphQLField
        public ReferencedObject getReference() {
            return this.reference;
        }
    }

    /**
     * Test POJO used to verify that we can support the creation of a GraphQL object type for a POJO with a non null
     * field referencing another POJO.
     *
     * @author sbegaudeau
     */
    @org.eclipse.sirius.components.annotations.graphql.GraphQLObjectType
    public static class ObjectTypeWithNonNullReference {
        private ReferencedObject reference;

        @org.eclipse.sirius.components.annotations.graphql.GraphQLNonNull
        @org.eclipse.sirius.components.annotations.graphql.GraphQLField
        public ReferencedObject getReference() {
            return this.reference;
        }
    }

    /**
     * Test POJO used to be referenced by other POJOs.
     *
     * @author sbegaudeau
     */
    @org.eclipse.sirius.components.annotations.graphql.GraphQLObjectType
    public static class ReferencedObject {
        // Do nothing on purpose
    }

    /**
     * Test POJO used to verify that we can support the creation of a GraphQL object type with a custom name.
     *
     * @author sbegaudeau
     */
    @org.eclipse.sirius.components.annotations.graphql.GraphQLObjectType(name = "AnotherName")
    public static class ObjectTypeWithAnotherName {
        // Do nothing on purpose
    }

    /**
     * Test POJO used to verify that we can support the creation of a GraphQL object type for a POJO with a reference to
     * another POJO which has been renamed.
     *
     * @author sbegaudeau
     */
    @org.eclipse.sirius.components.annotations.graphql.GraphQLObjectType
    public static class ObjectTypeWithReferenceToRenamedObject {
        private ObjectTypeWithAnotherName renamedReference;

        @org.eclipse.sirius.components.annotations.graphql.GraphQLField
        public ObjectTypeWithAnotherName getRenamedReference() {
            return this.renamedReference;
        }
    }

    /**
     * Test POJO used to verify that we can support the creation of a GraphQL interface type with a String field.
     *
     * @author sbegaudeau
     */
    @org.eclipse.sirius.components.annotations.graphql.GraphQLInterfaceType
    public abstract static class AbstractInterfaceTypeWithString {
        private String description;

        @org.eclipse.sirius.components.annotations.graphql.GraphQLField
        public String getDescription() {
            return this.description;
        }
    }

    /**
     * Test POJO used to verify that we can support the creation of a GraphQL object type for a POJO which implements an
     * interface.
     *
     * @author sbegaudeau
     */
    @org.eclipse.sirius.components.annotations.graphql.GraphQLObjectType
    public static class ObjectTypeImplementsInterfaceTypeWithString extends AbstractInterfaceTypeWithString {
        private String address;

        @org.eclipse.sirius.components.annotations.graphql.GraphQLField
        public String getAddress() {
            return this.address;
        }
    }

    /**
     * Test POJO used to verify that we can support the creation of a GraphQL object type for a POJO with a simple ID
     * field.
     *
     * @author sbegaudeau
     */
    @org.eclipse.sirius.components.annotations.graphql.GraphQLObjectType
    public static class ObjectTypeWithID {
        private String id;

        @org.eclipse.sirius.components.annotations.graphql.GraphQLID
        @org.eclipse.sirius.components.annotations.graphql.GraphQLField
        public String getId() {
            return this.id;
        }
    }
}
