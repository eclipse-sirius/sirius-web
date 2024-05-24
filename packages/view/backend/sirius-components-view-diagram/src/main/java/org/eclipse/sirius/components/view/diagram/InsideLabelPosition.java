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
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Inside Label Position</b></em>',
 * and utility methods for working with them. <!-- end-user-doc -->
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getInsideLabelPosition()
 */
public enum InsideLabelPosition implements Enumerator {
    /**
     * The '<em><b>TOP CENTER</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #TOP_CENTER_VALUE
     */
    TOP_CENTER(0, "TOP_CENTER", "TOP_CENTER"),
    /**
     * The '<em><b>TOP LEFT</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #TOP_LEFT_VALUE
     */
    TOP_LEFT(1, "TOP_LEFT", "TOP_LEFT"),
    /**
     * The '<em><b>TOP RIGHT</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #TOP_RIGHT_VALUE
     */
    TOP_RIGHT(2, "TOP_RIGHT", "TOP_RIGHT"),
    /**
     * The '<em><b>MIDDLE LEFT</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #MIDDLE_LEFT_VALUE
     */
    MIDDLE_LEFT(3, "MIDDLE_LEFT", "MIDDLE_LEFT"),
    /**
     * The '<em><b>MIDDLE CENTER</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #MIDDLE_CENTER_VALUE
     */
    MIDDLE_CENTER(4, "MIDDLE_CENTER", "MIDDLE_CENTER"),
    /**
     * The '<em><b>MIDDLE RIGHT</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #MIDDLE_RIGHT_VALUE
     */
    MIDDLE_RIGHT(5, "MIDDLE_RIGHT", "MIDDLE_RIGHT"),
    /**
     * The '<em><b>BOTTOM LEFT</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #BOTTOM_LEFT_VALUE
     */
    BOTTOM_LEFT(6, "BOTTOM_LEFT", "BOTTOM_LEFT"),
    /**
     * The '<em><b>BOTTOM CENTER</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #BOTTOM_CENTER_VALUE
     */
    BOTTOM_CENTER(7, "BOTTOM_CENTER", "BOTTOM_CENTER"),
    /**
     * The '<em><b>BOTTOM RIGHT</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #BOTTOM_RIGHT_VALUE
     */
    BOTTOM_RIGHT(8, "BOTTOM_RIGHT", "BOTTOM_RIGHT");

    /**
     * The '<em><b>TOP CENTER</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model
     * @generated
     * @ordered
     * @see #TOP_CENTER
     */
    public static final int TOP_CENTER_VALUE = 0;

    /**
     * The '<em><b>TOP LEFT</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model
     * @generated
     * @ordered
     * @see #TOP_LEFT
     */
    public static final int TOP_LEFT_VALUE = 1;

    /**
     * The '<em><b>TOP RIGHT</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model
     * @generated
     * @ordered
     * @see #TOP_RIGHT
     */
    public static final int TOP_RIGHT_VALUE = 2;

    /**
     * The '<em><b>MIDDLE LEFT</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model
     * @generated
     * @ordered
     * @see #MIDDLE_LEFT
     */
    public static final int MIDDLE_LEFT_VALUE = 3;

    /**
     * The '<em><b>MIDDLE CENTER</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model
     * @generated
     * @ordered
     * @see #MIDDLE_CENTER
     */
    public static final int MIDDLE_CENTER_VALUE = 4;

    /**
     * The '<em><b>MIDDLE RIGHT</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model
     * @generated
     * @ordered
     * @see #MIDDLE_RIGHT
     */
    public static final int MIDDLE_RIGHT_VALUE = 5;

    /**
     * The '<em><b>BOTTOM LEFT</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model
     * @generated
     * @ordered
     * @see #BOTTOM_LEFT
     */
    public static final int BOTTOM_LEFT_VALUE = 6;

    /**
     * The '<em><b>BOTTOM CENTER</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model
     * @generated
     * @ordered
     * @see #BOTTOM_CENTER
     */
    public static final int BOTTOM_CENTER_VALUE = 7;

    /**
     * The '<em><b>BOTTOM RIGHT</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model
     * @generated
     * @ordered
     * @see #BOTTOM_RIGHT
     */
    public static final int BOTTOM_RIGHT_VALUE = 8;

    /**
     * An array of all the '<em><b>Inside Label Position</b></em>' enumerators. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    private static final InsideLabelPosition[] VALUES_ARRAY = new InsideLabelPosition[] { TOP_CENTER, TOP_LEFT, TOP_RIGHT, MIDDLE_LEFT, MIDDLE_CENTER, MIDDLE_RIGHT, BOTTOM_LEFT, BOTTOM_CENTER,
                                                                                          BOTTOM_RIGHT, };

    /**
     * A public read-only list of all the '<em><b>Inside Label Position</b></em>' enumerators. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public static final List<InsideLabelPosition> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

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
    InsideLabelPosition(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * Returns the '<em><b>Inside Label Position</b></em>' literal with the specified literal value. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @param literal
     *         the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static InsideLabelPosition get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            InsideLabelPosition result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Inside Label Position</b></em>' literal with the specified name. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param name
     *         the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static InsideLabelPosition getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            InsideLabelPosition result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Inside Label Position</b></em>' literal with the specified integer value. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @param value
     *         the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static InsideLabelPosition get(int value) {
        switch (value) {
            case TOP_CENTER_VALUE:
                return TOP_CENTER;
            case TOP_LEFT_VALUE:
                return TOP_LEFT;
            case TOP_RIGHT_VALUE:
                return TOP_RIGHT;
            case MIDDLE_LEFT_VALUE:
                return MIDDLE_LEFT;
            case MIDDLE_CENTER_VALUE:
                return MIDDLE_CENTER;
            case MIDDLE_RIGHT_VALUE:
                return MIDDLE_RIGHT;
            case BOTTOM_LEFT_VALUE:
                return BOTTOM_LEFT;
            case BOTTOM_CENTER_VALUE:
                return BOTTOM_CENTER;
            case BOTTOM_RIGHT_VALUE:
                return BOTTOM_RIGHT;
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

} // InsideLabelPosition
