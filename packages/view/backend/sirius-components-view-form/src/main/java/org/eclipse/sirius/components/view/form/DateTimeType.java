/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.components.view.form;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Date Time Type</b></em>', and
 * utility methods for working with them. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.form.FormPackage#getDateTimeType()
 * @model
 * @generated
 */
public enum DateTimeType implements Enumerator {
    /**
     * The '<em><b>DATE TIME</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #DATE_TIME_VALUE
     * @generated
     * @ordered
     */
    DATE_TIME(0, "DATE_TIME", "DATE_TIME"),

    /**
     * The '<em><b>DATE</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #DATE_VALUE
     * @generated
     * @ordered
     */
    DATE(1, "DATE", "DATE"),

    /**
     * The '<em><b>TIME</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #TIME_VALUE
     * @generated
     * @ordered
     */
    TIME(2, "TIME", "TIME");

    /**
     * The '<em><b>DATE TIME</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #DATE_TIME
     * @model
     * @generated
     * @ordered
     */
    public static final int DATE_TIME_VALUE = 0;

    /**
     * The '<em><b>DATE</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #DATE
     * @model
     * @generated
     * @ordered
     */
    public static final int DATE_VALUE = 1;

    /**
     * The '<em><b>TIME</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #TIME
     * @model
     * @generated
     * @ordered
     */
    public static final int TIME_VALUE = 2;

    /**
     * An array of all the '<em><b>Date Time Type</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static final DateTimeType[] VALUES_ARRAY = new DateTimeType[] { DATE_TIME, DATE, TIME, };

    /**
     * A public read-only list of all the '<em><b>Date Time Type</b></em>' enumerators. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    public static final List<DateTimeType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Date Time Type</b></em>' literal with the specified literal value. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param literal
     *            the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static DateTimeType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            DateTimeType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Date Time Type</b></em>' literal with the specified name. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param name
     *            the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static DateTimeType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            DateTimeType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Date Time Type</b></em>' literal with the specified integer value. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value
     *            the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static DateTimeType get(int value) {
        switch (value) {
            case DATE_TIME_VALUE:
                return DATE_TIME;
            case DATE_VALUE:
                return DATE;
            case TIME_VALUE:
                return TIME;
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
    private DateTimeType(int value, String name, String literal) {
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

} // DateTimeType
