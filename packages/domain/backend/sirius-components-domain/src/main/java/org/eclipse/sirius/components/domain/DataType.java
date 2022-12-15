/**
 * Copyright (c) 2021, 2022 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.sirius.components.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Data Type</b></em>', and utility
 * methods for working with them. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.domain.DomainPackage#getDataType()
 * @model
 * @generated
 */
public enum DataType implements Enumerator {
    /**
     * The '<em><b>STRING</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #STRING_VALUE
     * @generated
     * @ordered
     */
    STRING(0, "STRING", "STRING"),

    /**
     * The '<em><b>BOOLEAN</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #BOOLEAN_VALUE
     * @generated
     * @ordered
     */
    BOOLEAN(1, "BOOLEAN", "BOOLEAN"),

    /**
     * The '<em><b>NUMBER</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #NUMBER_VALUE
     * @generated
     * @ordered
     */
    NUMBER(2, "NUMBER", "NUMBER");

    /**
     * The '<em><b>STRING</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #STRING
     * @model
     * @generated
     * @ordered
     */
    public static final int STRING_VALUE = 0;

    /**
     * The '<em><b>BOOLEAN</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #BOOLEAN
     * @model
     * @generated
     * @ordered
     */
    public static final int BOOLEAN_VALUE = 1;

    /**
     * The '<em><b>NUMBER</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #NUMBER
     * @model
     * @generated
     * @ordered
     */
    public static final int NUMBER_VALUE = 2;

    /**
     * An array of all the '<em><b>Data Type</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static final DataType[] VALUES_ARRAY = new DataType[] { STRING, BOOLEAN, NUMBER, };

    /**
     * A public read-only list of all the '<em><b>Data Type</b></em>' enumerators. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    public static final List<DataType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Data Type</b></em>' literal with the specified literal value. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param literal
     *            the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static DataType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            DataType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Data Type</b></em>' literal with the specified name. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param name
     *            the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static DataType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            DataType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Data Type</b></em>' literal with the specified integer value. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static DataType get(int value) {
        switch (value) {
        case STRING_VALUE:
            return STRING;
        case BOOLEAN_VALUE:
            return BOOLEAN;
        case NUMBER_VALUE:
            return NUMBER;
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private final int value;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private final String name;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private final String literal;

    /**
     * Only this class can construct instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private DataType(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public int getValue() {
        return this.value;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getLiteral() {
        return this.literal;
    }

    /**
     * Returns the literal value of the enumerator, which is its string representation. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        return this.literal;
    }

} // DataType
