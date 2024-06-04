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
package org.eclipse.sirius.components.view.diagram;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Label Overflow
 * Strategy</b></em>', and utility methods for working with them. <!-- end-user-doc -->
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getLabelOverflowStrategy()
 */
public enum LabelOverflowStrategy implements Enumerator {
    /**
     * The '<em><b>NONE</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #NONE_VALUE
     */
    NONE(0, "NONE", "NONE"),

    /**
     * The '<em><b>WRAP</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #WRAP_VALUE
     */
    WRAP(1, "WRAP", "WRAP"),

    /**
     * The '<em><b>ELLIPSIS</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #ELLIPSIS_VALUE
     */
    ELLIPSIS(2, "ELLIPSIS", "ELLIPSIS");

    /**
     * The '<em><b>NONE</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model
     * @generated
     * @ordered
     * @see #NONE
     */
    public static final int NONE_VALUE = 0;

    /**
     * The '<em><b>WRAP</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model
     * @generated
     * @ordered
     * @see #WRAP
     */
    public static final int WRAP_VALUE = 1;

    /**
     * The '<em><b>ELLIPSIS</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model
     * @generated
     * @ordered
     * @see #ELLIPSIS
     */
    public static final int ELLIPSIS_VALUE = 2;

    /**
     * An array of all the '<em><b>Label Overflow Strategy</b></em>' enumerators. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    private static final LabelOverflowStrategy[] VALUES_ARRAY = new LabelOverflowStrategy[] { NONE, WRAP, ELLIPSIS, };

    /**
     * A public read-only list of all the '<em><b>Label Overflow Strategy</b></em>' enumerators. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public static final List<LabelOverflowStrategy> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

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
    LabelOverflowStrategy(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * Returns the '<em><b>Label Overflow Strategy</b></em>' literal with the specified literal value. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param literal
     *         the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static LabelOverflowStrategy get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            LabelOverflowStrategy result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Label Overflow Strategy</b></em>' literal with the specified name. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param name
     *         the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static LabelOverflowStrategy getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            LabelOverflowStrategy result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Label Overflow Strategy</b></em>' literal with the specified integer value. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static LabelOverflowStrategy get(int value) {
        switch (value) {
            case NONE_VALUE:
                return NONE;
            case WRAP_VALUE:
                return WRAP;
            case ELLIPSIS_VALUE:
                return ELLIPSIS;
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

} // LabelOverflowStrategy
