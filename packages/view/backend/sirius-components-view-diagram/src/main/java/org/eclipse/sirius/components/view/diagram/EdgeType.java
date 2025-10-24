/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Edge Type</b></em>', and utility
 * methods for working with them. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getEdgeType()
 * @model
 * @generated
 */
public enum EdgeType implements Enumerator {
    /**
     * The '<em><b>Manhattan</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #MANHATTAN_VALUE
     * @generated
     * @ordered
     */
    MANHATTAN(0, "Manhattan", "Manhattan"),

    /**
     * The '<em><b>Smart Manhattan</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #SMART_MANHATTAN_VALUE
     * @generated
     * @ordered
     */
    SMART_MANHATTAN(1, "SmartManhattan", "SmartManhattan"),
    /**
     * The '<em><b>Oblique</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #OBLIQUE_VALUE
     * @generated
     * @ordered
     */
    OBLIQUE(2, "Oblique", "Oblique");

    /**
     * The '<em><b>Manhattan</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #MANHATTAN
     * @model name="Manhattan"
     * @generated
     * @ordered
     */
    public static final int MANHATTAN_VALUE = 0;

    /**
     * The '<em><b>Smart Manhattan</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #SMART_MANHATTAN
     * @model name="SmartManhattan"
     * @generated
     * @ordered
     */
    public static final int SMART_MANHATTAN_VALUE = 1;

    /**
     * The '<em><b>Oblique</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #OBLIQUE
     * @model name="Oblique"
     * @generated
     * @ordered
     */
    public static final int OBLIQUE_VALUE = 2;

    /**
     * An array of all the '<em><b>Edge Type</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static final EdgeType[] VALUES_ARRAY = new EdgeType[] { MANHATTAN, SMART_MANHATTAN, OBLIQUE, };

    /**
     * A public read-only list of all the '<em><b>Edge Type</b></em>' enumerators. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    public static final List<EdgeType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

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
    private EdgeType(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * Returns the '<em><b>Edge Type</b></em>' literal with the specified literal value. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param literal
     *            the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static EdgeType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            EdgeType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Edge Type</b></em>' literal with the specified name. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param name
     *            the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static EdgeType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            EdgeType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Edge Type</b></em>' literal with the specified integer value. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static EdgeType get(int value) {
        switch (value) {
            case MANHATTAN_VALUE:
                return MANHATTAN;
            case SMART_MANHATTAN_VALUE:
                return SMART_MANHATTAN;
            case OBLIQUE_VALUE:
                return OBLIQUE;
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

} // EdgeType
