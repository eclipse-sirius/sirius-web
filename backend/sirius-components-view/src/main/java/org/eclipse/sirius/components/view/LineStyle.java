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
package org.eclipse.sirius.components.view;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Line Style</b></em>', and utility
 * methods for working with them. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getLineStyle()
 * @model
 * @generated
 */
public enum LineStyle implements Enumerator {
    /**
     * The '<em><b>Solid</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #SOLID_VALUE
     * @generated
     * @ordered
     */
    SOLID(0, "Solid", "Solid"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Dash</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #DASH_VALUE
     * @generated
     * @ordered
     */
    DASH(1, "Dash", "Dash"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Dot</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #DOT_VALUE
     * @generated
     * @ordered
     */
    DOT(2, "Dot", "Dot"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Dash Dot</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #DASH_DOT_VALUE
     * @generated
     * @ordered
     */
    DASH_DOT(3, "Dash_Dot", "Dash_Dot"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>Solid</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #SOLID
     * @model name="Solid"
     * @generated
     * @ordered
     */
    public static final int SOLID_VALUE = 0;

    /**
     * The '<em><b>Dash</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #DASH
     * @model name="Dash"
     * @generated
     * @ordered
     */
    public static final int DASH_VALUE = 1;

    /**
     * The '<em><b>Dot</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #DOT
     * @model name="Dot"
     * @generated
     * @ordered
     */
    public static final int DOT_VALUE = 2;

    /**
     * The '<em><b>Dash Dot</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #DASH_DOT
     * @model name="Dash_Dot"
     * @generated
     * @ordered
     */
    public static final int DASH_DOT_VALUE = 3;

    /**
     * An array of all the '<em><b>Line Style</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static final LineStyle[] VALUES_ARRAY = new LineStyle[] { SOLID, DASH, DOT, DASH_DOT, };

    /**
     * A public read-only list of all the '<em><b>Line Style</b></em>' enumerators. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    public static final List<LineStyle> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Line Style</b></em>' literal with the specified literal value. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param literal
     *            the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static LineStyle get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            LineStyle result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Line Style</b></em>' literal with the specified name. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param name
     *            the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static LineStyle getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            LineStyle result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Line Style</b></em>' literal with the specified integer value. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static LineStyle get(int value) {
        switch (value) {
        case SOLID_VALUE:
            return SOLID;
        case DASH_VALUE:
            return DASH;
        case DOT_VALUE:
            return DOT;
        case DASH_DOT_VALUE:
            return DASH_DOT;
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
    private LineStyle(int value, String name, String literal) {
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

} // LineStyle
