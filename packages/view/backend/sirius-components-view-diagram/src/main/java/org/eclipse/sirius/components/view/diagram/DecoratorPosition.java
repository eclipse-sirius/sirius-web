/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Decorator Position</b></em>', and
 * utility methods for working with them. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDecoratorPosition()
 * @model
 * @generated
 */
public enum DecoratorPosition implements Enumerator {
    /**
     * The '<em><b>NORTH</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #NORTH_VALUE
     * @generated
     * @ordered
     */
    NORTH(0, "NORTH", "NORTH"),

    /**
     * The '<em><b>NORTH WEST</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #NORTH_WEST_VALUE
     * @generated
     * @ordered
     */
    NORTH_WEST(1, "NORTH_WEST", "NORTH_WEST"),

    /**
     * The '<em><b>WEST</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #WEST_VALUE
     * @generated
     * @ordered
     */
    WEST(2, "WEST", "WEST"),

    /**
     * The '<em><b>SOUTH WEST</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #SOUTH_WEST_VALUE
     * @generated
     * @ordered
     */
    SOUTH_WEST(3, "SOUTH_WEST", "SOUTH_WEST"),

    /**
     * The '<em><b>SOUTH</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #SOUTH_VALUE
     * @generated
     * @ordered
     */
    SOUTH(4, "SOUTH", "SOUTH"),

    /**
     * The '<em><b>SOUTH EAST</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #SOUTH_EAST_VALUE
     * @generated
     * @ordered
     */
    SOUTH_EAST(5, "SOUTH_EAST", "SOUTH_EAST"),

    /**
     * The '<em><b>EAST</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #EAST_VALUE
     * @generated
     * @ordered
     */
    EAST(6, "EAST", "EAST"),

    /**
     * The '<em><b>NORTH EAST</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #NORTH_EAST_VALUE
     * @generated
     * @ordered
     */
    NORTH_EAST(7, "NORTH_EAST", "NORTH_EAST"),

    /**
     * The '<em><b>CENTER</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #CENTER_VALUE
     * @generated
     * @ordered
     */
    CENTER(8, "CENTER", "CENTER");

    /**
     * The '<em><b>NORTH</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #NORTH
     * @model
     * @generated
     * @ordered
     */
    public static final int NORTH_VALUE = 0;

    /**
     * The '<em><b>NORTH WEST</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #NORTH_WEST
     * @model
     * @generated
     * @ordered
     */
    public static final int NORTH_WEST_VALUE = 1;

    /**
     * The '<em><b>WEST</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #WEST
     * @model
     * @generated
     * @ordered
     */
    public static final int WEST_VALUE = 2;

    /**
     * The '<em><b>SOUTH WEST</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #SOUTH_WEST
     * @model
     * @generated
     * @ordered
     */
    public static final int SOUTH_WEST_VALUE = 3;

    /**
     * The '<em><b>SOUTH</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #SOUTH
     * @model
     * @generated
     * @ordered
     */
    public static final int SOUTH_VALUE = 4;

    /**
     * The '<em><b>SOUTH EAST</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #SOUTH_EAST
     * @model
     * @generated
     * @ordered
     */
    public static final int SOUTH_EAST_VALUE = 5;

    /**
     * The '<em><b>EAST</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #EAST
     * @model
     * @generated
     * @ordered
     */
    public static final int EAST_VALUE = 6;

    /**
     * The '<em><b>NORTH EAST</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #NORTH_EAST
     * @model
     * @generated
     * @ordered
     */
    public static final int NORTH_EAST_VALUE = 7;

    /**
     * The '<em><b>CENTER</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #CENTER
     * @model
     * @generated
     * @ordered
     */
    public static final int CENTER_VALUE = 8;

    /**
     * An array of all the '<em><b>Decorator Position</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    private static final DecoratorPosition[] VALUES_ARRAY = new DecoratorPosition[] { NORTH, NORTH_WEST, WEST, SOUTH_WEST, SOUTH, SOUTH_EAST, EAST, NORTH_EAST, CENTER, };

    /**
     * A public read-only list of all the '<em><b>Decorator Position</b></em>' enumerators. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    public static final List<DecoratorPosition> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Decorator Position</b></em>' literal with the specified literal value. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @param literal
     *            the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static DecoratorPosition get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            DecoratorPosition result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Decorator Position</b></em>' literal with the specified name. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param name
     *            the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static DecoratorPosition getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            DecoratorPosition result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Decorator Position</b></em>' literal with the specified integer value. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @param value
     *            the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static DecoratorPosition get(int value) {
        switch (value) {
            case NORTH_VALUE:
                return NORTH;
            case NORTH_WEST_VALUE:
                return NORTH_WEST;
            case WEST_VALUE:
                return WEST;
            case SOUTH_WEST_VALUE:
                return SOUTH_WEST;
            case SOUTH_VALUE:
                return SOUTH;
            case SOUTH_EAST_VALUE:
                return SOUTH_EAST;
            case EAST_VALUE:
                return EAST;
            case NORTH_EAST_VALUE:
                return NORTH_EAST;
            case CENTER_VALUE:
                return CENTER;
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
    private DecoratorPosition(int value, String name, String literal) {
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

} // DecoratorPosition
