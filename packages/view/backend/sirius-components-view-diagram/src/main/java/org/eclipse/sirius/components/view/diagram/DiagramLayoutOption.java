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
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Layout Option</b></em>', and
 * utility methods for working with them. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDiagramLayoutOption()
 * @model
 * @generated
 */
public enum DiagramLayoutOption implements Enumerator {
    /**
     * The '<em><b>NONE</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #NONE_VALUE
     * @generated
     * @ordered
     */
    NONE(0, "NONE", "NONE"),

    /**
     * The '<em><b>AUTO LAYOUT</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #AUTO_LAYOUT_VALUE
     * @generated
     * @ordered
     */
    AUTO_LAYOUT(1, "AUTO_LAYOUT", "AUTO_LAYOUT"),

    /**
     * The '<em><b>AUTO UNTIL MANUAL</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #AUTO_UNTIL_MANUAL_VALUE
     * @generated
     * @ordered
     */
    AUTO_UNTIL_MANUAL(2, "AUTO_UNTIL_MANUAL", "AUTO_UNTIL_MANUAL");

    /**
     * The '<em><b>NONE</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #NONE
     * @model
     * @generated
     * @ordered
     */
    public static final int NONE_VALUE = 0;

    /**
     * The '<em><b>AUTO LAYOUT</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #AUTO_LAYOUT
     * @model
     * @generated
     * @ordered
     */
    public static final int AUTO_LAYOUT_VALUE = 1;

    /**
     * The '<em><b>AUTO UNTIL MANUAL</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #AUTO_UNTIL_MANUAL
     * @model
     * @generated
     * @ordered
     */
    public static final int AUTO_UNTIL_MANUAL_VALUE = 2;

    /**
     * An array of all the '<em><b>Layout Option</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static final DiagramLayoutOption[] VALUES_ARRAY = new DiagramLayoutOption[] { NONE, AUTO_LAYOUT, AUTO_UNTIL_MANUAL, };

    /**
     * A public read-only list of all the '<em><b>Layout Option</b></em>' enumerators. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    public static final List<DiagramLayoutOption> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Layout Option</b></em>' literal with the specified literal value. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param literal
     *            the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static DiagramLayoutOption get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            DiagramLayoutOption result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Layout Option</b></em>' literal with the specified name. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param name
     *            the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static DiagramLayoutOption getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            DiagramLayoutOption result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Layout Option</b></em>' literal with the specified integer value. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value
     *            the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static DiagramLayoutOption get(int value) {
        switch (value) {
            case NONE_VALUE:
                return NONE;
            case AUTO_LAYOUT_VALUE:
                return AUTO_LAYOUT;
            case AUTO_UNTIL_MANUAL_VALUE:
                return AUTO_UNTIL_MANUAL;
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
    private DiagramLayoutOption(int value, String name, String literal) {
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

} // DiagramLayoutOption
