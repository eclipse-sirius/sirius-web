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
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Visibility</b></em>', and utility
 * methods for working with them. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getVisibility()
 * @model
 * @generated
 */
public enum Visibility implements Enumerator {
    /**
     * The '<em><b>PUBLIC</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #PUBLIC_VALUE
     * @generated
     * @ordered
     */
    PUBLIC(0, "PUBLIC", "PUBLIC"),

    /**
     * The '<em><b>PROTECTED</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #PROTECTED_VALUE
     * @generated
     * @ordered
     */
    PROTECTED(1, "PROTECTED", "PROTECTED"),
    /**
     * The '<em><b>PACKAGE</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #PACKAGE_VALUE
     * @generated
     * @ordered
     */
    PACKAGE(2, "PACKAGE", "PACKAGE"),
    /**
     * The '<em><b>PRIVATE</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #PRIVATE_VALUE
     * @generated
     * @ordered
     */
    PRIVATE(3, "PRIVATE", "PRIVATE");

    /**
     * The '<em><b>PUBLIC</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #PUBLIC
     * @model
     * @generated
     * @ordered
     */
    public static final int PUBLIC_VALUE = 0;

    /**
     * The '<em><b>PROTECTED</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #PROTECTED
     * @model
     * @generated
     * @ordered
     */
    public static final int PROTECTED_VALUE = 1;

    /**
     * The '<em><b>PACKAGE</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #PACKAGE
     * @model
     * @generated
     * @ordered
     */
    public static final int PACKAGE_VALUE = 2;

    /**
     * The '<em><b>PRIVATE</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #PRIVATE
     * @model
     * @generated
     * @ordered
     */
    public static final int PRIVATE_VALUE = 3;

    /**
     * An array of all the '<em><b>Visibility</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static final Visibility[] VALUES_ARRAY = new Visibility[] { PUBLIC, PROTECTED, PACKAGE, PRIVATE, };

    /**
     * A public read-only list of all the '<em><b>Visibility</b></em>' enumerators. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    public static final List<Visibility> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Visibility</b></em>' literal with the specified literal value. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param literal
     *            the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static Visibility get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            Visibility result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Visibility</b></em>' literal with the specified name. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param name
     *            the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static Visibility getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            Visibility result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Visibility</b></em>' literal with the specified integer value. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static Visibility get(int value) {
        switch (value) {
            case PUBLIC_VALUE:
                return PUBLIC;
            case PROTECTED_VALUE:
                return PROTECTED;
            case PACKAGE_VALUE:
                return PACKAGE;
            case PRIVATE_VALUE:
                return PRIVATE;
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
    private Visibility(int value, String name, String literal) {
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

} // Visibility
