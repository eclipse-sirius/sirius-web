/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.components.view.form;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Label Placement</b></em>', and
 * utility methods for working with them. <!-- end-user-doc -->
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.form.FormPackage#getLabelPlacement()
 */
public enum LabelPlacement implements Enumerator {
    /**
     * The '<em><b>End</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #END_VALUE
     */
    END(0, "end", "end"),

    /**
     * The '<em><b>Top</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #TOP_VALUE
     */
    TOP(1, "top", "top"),

    /**
     * The '<em><b>Start</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #START_VALUE
     */
    START(2, "start", "start"),

    /**
     * The '<em><b>Bottom</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #BOTTOM_VALUE
     */
    BOTTOM(3, "bottom", "bottom");

    /**
     * The '<em><b>End</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="end"
     * @generated
     * @ordered
     * @see #END
     */
    public static final int END_VALUE = 0;

    /**
     * The '<em><b>Top</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="top"
     * @generated
     * @ordered
     * @see #TOP
     */
    public static final int TOP_VALUE = 1;

    /**
     * The '<em><b>Start</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="start"
     * @generated
     * @ordered
     * @see #START
     */
    public static final int START_VALUE = 2;

    /**
     * The '<em><b>Bottom</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="bottom"
     * @generated
     * @ordered
     * @see #BOTTOM
     */
    public static final int BOTTOM_VALUE = 3;

    /**
     * An array of all the '<em><b>Label Placement</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static final LabelPlacement[] VALUES_ARRAY = new LabelPlacement[]{END, TOP, START, BOTTOM,};

    /**
     * A public read-only list of all the '<em><b>Label Placement</b></em>' enumerators. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    public static final List<LabelPlacement> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
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
    LabelPlacement(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * Returns the '<em><b>Label Placement</b></em>' literal with the specified literal value. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param literal the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static LabelPlacement get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            LabelPlacement result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Label Placement</b></em>' literal with the specified name. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param name the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static LabelPlacement getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            LabelPlacement result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Label Placement</b></em>' literal with the specified integer value. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static LabelPlacement get(int value) {
        switch (value) {
            case END_VALUE:
                return END;
            case TOP_VALUE:
                return TOP;
            case START_VALUE:
                return START;
            case BOTTOM_VALUE:
                return BOTTOM;
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

} // LabelPlacement
