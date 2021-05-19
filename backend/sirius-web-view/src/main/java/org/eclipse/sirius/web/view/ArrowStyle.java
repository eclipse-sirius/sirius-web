/**
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.view;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Arrow Style</b></em>', and
 * utility methods for working with them. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.web.view.ViewPackage#getArrowStyle()
 * @model
 * @generated
 */
public enum ArrowStyle implements Enumerator {
    /**
     * The '<em><b>None</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #NONE_VALUE
     * @generated
     * @ordered
     */
    NONE(0, "None", "None"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Output Arrow</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #OUTPUT_ARROW_VALUE
     * @generated
     * @ordered
     */
    OUTPUT_ARROW(1, "OutputArrow", "OutputArrow"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Input Arrow</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #INPUT_ARROW_VALUE
     * @generated
     * @ordered
     */
    INPUT_ARROW(2, "InputArrow", "InputArrow"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Output Closed Arrow</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #OUTPUT_CLOSED_ARROW_VALUE
     * @generated
     * @ordered
     */
    OUTPUT_CLOSED_ARROW(3, "OutputClosedArrow", "OutputClosedArrow"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Input Closed Arrow</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #INPUT_CLOSED_ARROW_VALUE
     * @generated
     * @ordered
     */
    INPUT_CLOSED_ARROW(4, "InputClosedArrow", "InputClosedArrow"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Output Fill Closed Arrow</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #OUTPUT_FILL_CLOSED_ARROW_VALUE
     * @generated
     * @ordered
     */
    OUTPUT_FILL_CLOSED_ARROW(5, "OutputFillClosedArrow", "OutputFillClosedArrow"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Input Fill Closed Arrow</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #INPUT_FILL_CLOSED_ARROW_VALUE
     * @generated
     * @ordered
     */
    INPUT_FILL_CLOSED_ARROW(6, "InputFillClosedArrow", "InputFillClosedArrow"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Diamond</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #DIAMOND_VALUE
     * @generated
     * @ordered
     */
    DIAMOND(7, "Diamond", "Diamond"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Fill Diamond</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #FILL_DIAMOND_VALUE
     * @generated
     * @ordered
     */
    FILL_DIAMOND(8, "FillDiamond", "FillDiamond"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Input Arrow With Diamond</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #INPUT_ARROW_WITH_DIAMOND_VALUE
     * @generated
     * @ordered
     */
    INPUT_ARROW_WITH_DIAMOND(9, "InputArrowWithDiamond", "InputArrowWithDiamond"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Input Arrow With Fill Diamond</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #INPUT_ARROW_WITH_FILL_DIAMOND_VALUE
     * @generated
     * @ordered
     */
    INPUT_ARROW_WITH_FILL_DIAMOND(10, "InputArrowWithFillDiamond", "InputArrowWithFillDiamond"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>None</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #NONE
     * @model name="None"
     * @generated
     * @ordered
     */
    public static final int NONE_VALUE = 0;

    /**
     * The '<em><b>Output Arrow</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #OUTPUT_ARROW
     * @model name="OutputArrow"
     * @generated
     * @ordered
     */
    public static final int OUTPUT_ARROW_VALUE = 1;

    /**
     * The '<em><b>Input Arrow</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #INPUT_ARROW
     * @model name="InputArrow"
     * @generated
     * @ordered
     */
    public static final int INPUT_ARROW_VALUE = 2;

    /**
     * The '<em><b>Output Closed Arrow</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #OUTPUT_CLOSED_ARROW
     * @model name="OutputClosedArrow"
     * @generated
     * @ordered
     */
    public static final int OUTPUT_CLOSED_ARROW_VALUE = 3;

    /**
     * The '<em><b>Input Closed Arrow</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #INPUT_CLOSED_ARROW
     * @model name="InputClosedArrow"
     * @generated
     * @ordered
     */
    public static final int INPUT_CLOSED_ARROW_VALUE = 4;

    /**
     * The '<em><b>Output Fill Closed Arrow</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #OUTPUT_FILL_CLOSED_ARROW
     * @model name="OutputFillClosedArrow"
     * @generated
     * @ordered
     */
    public static final int OUTPUT_FILL_CLOSED_ARROW_VALUE = 5;

    /**
     * The '<em><b>Input Fill Closed Arrow</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #INPUT_FILL_CLOSED_ARROW
     * @model name="InputFillClosedArrow"
     * @generated
     * @ordered
     */
    public static final int INPUT_FILL_CLOSED_ARROW_VALUE = 6;

    /**
     * The '<em><b>Diamond</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #DIAMOND
     * @model name="Diamond"
     * @generated
     * @ordered
     */
    public static final int DIAMOND_VALUE = 7;

    /**
     * The '<em><b>Fill Diamond</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #FILL_DIAMOND
     * @model name="FillDiamond"
     * @generated
     * @ordered
     */
    public static final int FILL_DIAMOND_VALUE = 8;

    /**
     * The '<em><b>Input Arrow With Diamond</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #INPUT_ARROW_WITH_DIAMOND
     * @model name="InputArrowWithDiamond"
     * @generated
     * @ordered
     */
    public static final int INPUT_ARROW_WITH_DIAMOND_VALUE = 9;

    /**
     * The '<em><b>Input Arrow With Fill Diamond</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #INPUT_ARROW_WITH_FILL_DIAMOND
     * @model name="InputArrowWithFillDiamond"
     * @generated
     * @ordered
     */
    public static final int INPUT_ARROW_WITH_FILL_DIAMOND_VALUE = 10;

    /**
     * An array of all the '<em><b>Arrow Style</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static final ArrowStyle[] VALUES_ARRAY = new ArrowStyle[] { NONE, OUTPUT_ARROW, INPUT_ARROW, OUTPUT_CLOSED_ARROW, INPUT_CLOSED_ARROW, OUTPUT_FILL_CLOSED_ARROW, INPUT_FILL_CLOSED_ARROW,
            DIAMOND, FILL_DIAMOND, INPUT_ARROW_WITH_DIAMOND, INPUT_ARROW_WITH_FILL_DIAMOND, };

    /**
     * A public read-only list of all the '<em><b>Arrow Style</b></em>' enumerators. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    public static final List<ArrowStyle> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Arrow Style</b></em>' literal with the specified literal value. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param literal
     *            the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ArrowStyle get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ArrowStyle result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Arrow Style</b></em>' literal with the specified name. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param name
     *            the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ArrowStyle getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ArrowStyle result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Arrow Style</b></em>' literal with the specified integer value. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ArrowStyle get(int value) {
        switch (value) {
        case NONE_VALUE:
            return NONE;
        case OUTPUT_ARROW_VALUE:
            return OUTPUT_ARROW;
        case INPUT_ARROW_VALUE:
            return INPUT_ARROW;
        case OUTPUT_CLOSED_ARROW_VALUE:
            return OUTPUT_CLOSED_ARROW;
        case INPUT_CLOSED_ARROW_VALUE:
            return INPUT_CLOSED_ARROW;
        case OUTPUT_FILL_CLOSED_ARROW_VALUE:
            return OUTPUT_FILL_CLOSED_ARROW;
        case INPUT_FILL_CLOSED_ARROW_VALUE:
            return INPUT_FILL_CLOSED_ARROW;
        case DIAMOND_VALUE:
            return DIAMOND;
        case FILL_DIAMOND_VALUE:
            return FILL_DIAMOND;
        case INPUT_ARROW_WITH_DIAMOND_VALUE:
            return INPUT_ARROW_WITH_DIAMOND;
        case INPUT_ARROW_WITH_FILL_DIAMOND_VALUE:
            return INPUT_ARROW_WITH_FILL_DIAMOND;
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
    private ArrowStyle(int value, String name, String literal) {
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

} // ArrowStyle
