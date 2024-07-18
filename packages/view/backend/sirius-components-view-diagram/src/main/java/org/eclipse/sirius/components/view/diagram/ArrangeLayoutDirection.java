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
package org.eclipse.sirius.components.view.diagram;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Arrange Layout
 * Direction</b></em>', and utility methods for working with them. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getArrangeLayoutDirection()
 * @model
 * @generated
 */
public enum ArrangeLayoutDirection implements Enumerator {
    /**
     * The '<em><b>UNDEFINED</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #UNDEFINED_VALUE
     * @generated
     * @ordered
     */
    UNDEFINED(0, "UNDEFINED", "UNDEFINED"),
    /**
     * The '<em><b>RIGHT</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #RIGHT_VALUE
     * @generated
     * @ordered
     */
    RIGHT(1, "RIGHT", "RIGHT"),

    /**
     * The '<em><b>DOWN</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #DOWN_VALUE
     * @generated
     * @ordered
     */
    DOWN(2, "DOWN", "DOWN"),

    /**
     * The '<em><b>LEFT</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #LEFT_VALUE
     * @generated
     * @ordered
     */
    LEFT(3, "LEFT", "LEFT"),

    /**
     * The '<em><b>UP</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #UP_VALUE
     * @generated
     * @ordered
     */
    UP(4, "UP", "UP");

    /**
     * The '<em><b>UNDEFINED</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #UNDEFINED
     * @model
     * @generated
     * @ordered
     */
    public static final int UNDEFINED_VALUE = 0;

    /**
     * The '<em><b>RIGHT</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #RIGHT
     * @model
     * @generated
     * @ordered
     */
    public static final int RIGHT_VALUE = 1;

    /**
     * The '<em><b>DOWN</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #DOWN
     * @model
     * @generated
     * @ordered
     */
    public static final int DOWN_VALUE = 2;

    /**
     * The '<em><b>LEFT</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #LEFT
     * @model
     * @generated
     * @ordered
     */
    public static final int LEFT_VALUE = 3;

    /**
     * The '<em><b>UP</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #UP
     * @model
     * @generated
     * @ordered
     */
    public static final int UP_VALUE = 4;

    /**
     * An array of all the '<em><b>Arrange Layout Direction</b></em>' enumerators. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    private static final ArrangeLayoutDirection[] VALUES_ARRAY = new ArrangeLayoutDirection[] { UNDEFINED, RIGHT, DOWN, LEFT, UP, };

    /**
     * A public read-only list of all the '<em><b>Arrange Layout Direction</b></em>' enumerators. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final List<ArrangeLayoutDirection> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

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
    private ArrangeLayoutDirection(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * Returns the '<em><b>Arrange Layout Direction</b></em>' literal with the specified literal value. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param literal
     *            the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ArrangeLayoutDirection get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ArrangeLayoutDirection result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Arrange Layout Direction</b></em>' literal with the specified name. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param name
     *            the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ArrangeLayoutDirection getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ArrangeLayoutDirection result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Arrange Layout Direction</b></em>' literal with the specified integer value. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ArrangeLayoutDirection get(int value) {
        switch (value) {
            case UNDEFINED_VALUE:
                return UNDEFINED;
            case RIGHT_VALUE:
                return RIGHT;
            case DOWN_VALUE:
                return DOWN;
            case LEFT_VALUE:
                return LEFT;
            case UP_VALUE:
                return UP;
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

} // ArrangeLayoutDirection
