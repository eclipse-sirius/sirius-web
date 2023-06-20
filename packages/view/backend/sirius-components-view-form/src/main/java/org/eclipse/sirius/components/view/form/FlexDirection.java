/**
 * Copyright (c) 2021, 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.sirius.components.view.form;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Flex Direction</b></em>', and
 * utility methods for working with them. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.form.FormPackage#getFlexDirection()
 * @model
 * @generated
 */
public enum FlexDirection implements Enumerator {
    /**
     * The '<em><b>Row</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #ROW_VALUE
     * @generated
     * @ordered
     */
    ROW(0, "row", "row"),

    /**
     * The '<em><b>Row Reverse</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #ROW_REVERSE_VALUE
     * @generated
     * @ordered
     */
    ROW_REVERSE(1, "rowReverse", "row-reverse"),

    /**
     * The '<em><b>Column</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #COLUMN_VALUE
     * @generated
     * @ordered
     */
    COLUMN(2, "column", "column"),

    /**
     * The '<em><b>Column Reverse</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #COLUMN_REVERSE_VALUE
     * @generated
     * @ordered
     */
    COLUMN_REVERSE(3, "columnReverse", "column-reverse");

    /**
     * The '<em><b>Row</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #ROW
     * @model name="row"
     * @generated
     * @ordered
     */
    public static final int ROW_VALUE = 0;

    /**
     * The '<em><b>Row Reverse</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #ROW_REVERSE
     * @model name="rowReverse" literal="row-reverse"
     * @generated
     * @ordered
     */
    public static final int ROW_REVERSE_VALUE = 1;

    /**
     * The '<em><b>Column</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #COLUMN
     * @model name="column"
     * @generated
     * @ordered
     */
    public static final int COLUMN_VALUE = 2;

    /**
     * The '<em><b>Column Reverse</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #COLUMN_REVERSE
     * @model name="columnReverse" literal="column-reverse"
     * @generated
     * @ordered
     */
    public static final int COLUMN_REVERSE_VALUE = 3;

    /**
     * An array of all the '<em><b>Flex Direction</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static final FlexDirection[] VALUES_ARRAY = new FlexDirection[] { ROW, ROW_REVERSE, COLUMN, COLUMN_REVERSE, };

    /**
     * A public read-only list of all the '<em><b>Flex Direction</b></em>' enumerators. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    public static final List<FlexDirection> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Flex Direction</b></em>' literal with the specified literal value. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param literal
     *            the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static FlexDirection get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            FlexDirection result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Flex Direction</b></em>' literal with the specified name. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param name
     *            the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static FlexDirection getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            FlexDirection result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Flex Direction</b></em>' literal with the specified integer value. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value
     *            the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static FlexDirection get(int value) {
        switch (value) {
            case ROW_VALUE:
                return ROW;
            case ROW_REVERSE_VALUE:
                return ROW_REVERSE;
            case COLUMN_VALUE:
                return COLUMN;
            case COLUMN_REVERSE_VALUE:
                return COLUMN_REVERSE;
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
    private FlexDirection(int value, String name, String literal) {
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

} // FlexDirection
