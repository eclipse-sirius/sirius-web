/*******************************************************************************
 * Copyright (c) 2024, 2024 Obeo.
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
package org.eclipse.sirius.components.view.diagram;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>User Resizable
 * Direction</b></em>', and utility methods for working with them. <!-- end-user-doc -->
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getUserResizableDirection()
 */
public enum UserResizableDirection implements Enumerator {
    /**
     * The '<em><b>BOTH</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #BOTH_VALUE
     */
    BOTH(0, "BOTH", "BOTH"),

    /**
     * The '<em><b>HORIZONTAL</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #HORIZONTAL_VALUE
     */
    HORIZONTAL(1, "HORIZONTAL", "HORIZONTAL"),

    /**
     * The '<em><b>VERTICAL</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #VERTICAL_VALUE
     */
    VERTICAL(2, "VERTICAL", "VERTICAL"),

    /**
     * The '<em><b>NONE</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #NONE_VALUE
     */
    NONE(3, "NONE", "NONE");

    /**
     * The '<em><b>BOTH</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model
     * @generated
     * @ordered
     * @see #BOTH
     */
    public static final int BOTH_VALUE = 0;

    /**
     * The '<em><b>HORIZONTAL</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model
     * @generated
     * @ordered
     * @see #HORIZONTAL
     */
    public static final int HORIZONTAL_VALUE = 1;

    /**
     * The '<em><b>VERTICAL</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model
     * @generated
     * @ordered
     * @see #VERTICAL
     */
    public static final int VERTICAL_VALUE = 2;

    /**
     * The '<em><b>NONE</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model
     * @generated
     * @ordered
     * @see #NONE
     */
    public static final int NONE_VALUE = 3;

    /**
     * An array of all the '<em><b>User Resizable Direction</b></em>' enumerators. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    private static final UserResizableDirection[] VALUES_ARRAY = new UserResizableDirection[] { BOTH, HORIZONTAL, VERTICAL, NONE, };

    /**
     * A public read-only list of all the '<em><b>User Resizable Direction</b></em>' enumerators. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final List<UserResizableDirection> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
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
    UserResizableDirection(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * Returns the '<em><b>User Resizable Direction</b></em>' literal with the specified literal value. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param literal
     *         the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static UserResizableDirection get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            UserResizableDirection result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>User Resizable Direction</b></em>' literal with the specified name. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param name
     *         the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static UserResizableDirection getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            UserResizableDirection result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>User Resizable Direction</b></em>' literal with the specified integer value. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static UserResizableDirection get(int value) {
        switch (value) {
            case BOTH_VALUE:
                return BOTH;
            case HORIZONTAL_VALUE:
                return HORIZONTAL;
            case VERTICAL_VALUE:
                return VERTICAL;
            case NONE_VALUE:
                return NONE;
        }
        return null;
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

} // UserResizableDirection
