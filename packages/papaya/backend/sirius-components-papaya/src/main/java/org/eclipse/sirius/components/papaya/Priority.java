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
package org.eclipse.sirius.components.papaya;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Priority</b></em>', and utility
 * methods for working with them. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getPriority()
 * @model
 * @generated
 */
public enum Priority implements Enumerator {
    /**
     * The '<em><b>P1</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #P1_VALUE
     * @generated
     * @ordered
     */
    P1(1, "P1", "P1"),

    /**
     * The '<em><b>P2</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #P2_VALUE
     * @generated
     * @ordered
     */
    P2(2, "P2", "P2"),

    /**
     * The '<em><b>P3</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #P3_VALUE
     * @generated
     * @ordered
     */
    P3(3, "P3", "P3"),

    /**
     * The '<em><b>P4</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #P4_VALUE
     * @generated
     * @ordered
     */
    P4(4, "P4", "P4"),

    /**
     * The '<em><b>P5</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #P5_VALUE
     * @generated
     * @ordered
     */
    P5(5, "P5", "P5");

    /**
     * The '<em><b>P1</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #P1
     * @model
     * @generated
     * @ordered
     */
    public static final int P1_VALUE = 1;

    /**
     * The '<em><b>P2</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #P2
     * @model
     * @generated
     * @ordered
     */
    public static final int P2_VALUE = 2;

    /**
     * The '<em><b>P3</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #P3
     * @model
     * @generated
     * @ordered
     */
    public static final int P3_VALUE = 3;

    /**
     * The '<em><b>P4</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #P4
     * @model
     * @generated
     * @ordered
     */
    public static final int P4_VALUE = 4;

    /**
     * The '<em><b>P5</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #P5
     * @model
     * @generated
     * @ordered
     */
    public static final int P5_VALUE = 5;

    /**
     * An array of all the '<em><b>Priority</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static final Priority[] VALUES_ARRAY = new Priority[] { P1, P2, P3, P4, P5, };

    /**
     * A public read-only list of all the '<em><b>Priority</b></em>' enumerators. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    public static final List<Priority> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Priority</b></em>' literal with the specified literal value. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param literal
     *            the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static Priority get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            Priority result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Priority</b></em>' literal with the specified name. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @param name
     *            the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static Priority getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            Priority result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Priority</b></em>' literal with the specified integer value. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static Priority get(int value) {
        switch (value) {
            case P1_VALUE:
                return P1;
            case P2_VALUE:
                return P2;
            case P3_VALUE:
                return P3;
            case P4_VALUE:
                return P4;
            case P5_VALUE:
                return P5;
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
    private Priority(int value, String name, String literal) {
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

} // Priority
