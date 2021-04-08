/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.view;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Mode</b></em>', and utility
 * methods for working with them. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.web.view.ViewPackage#getMode()
 * @model
 * @generated
 */
public enum Mode implements Enumerator {
    /**
     * The '<em><b>AUTO</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #AUTO_VALUE
     * @generated
     * @ordered
     */
    AUTO(0, "AUTO", "AUTO"), //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>EXPLICIT</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #EXPLICIT_VALUE
     * @generated
     * @ordered
     */
    EXPLICIT(1, "EXPLICIT", "EXPLICIT"); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * The '<em><b>AUTO</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #AUTO
     * @model
     * @generated
     * @ordered
     */
    public static final int AUTO_VALUE = 0;

    /**
     * The '<em><b>EXPLICIT</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #EXPLICIT
     * @model
     * @generated
     * @ordered
     */
    public static final int EXPLICIT_VALUE = 1;

    /**
     * An array of all the '<em><b>Mode</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static final Mode[] VALUES_ARRAY = new Mode[] { AUTO, EXPLICIT, };

    /**
     * A public read-only list of all the '<em><b>Mode</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    public static final List<Mode> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Mode</b></em>' literal with the specified literal value. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param literal
     *            the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static Mode get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            Mode result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Mode</b></em>' literal with the specified name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param name
     *            the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static Mode getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            Mode result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Mode</b></em>' literal with the specified integer value. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static Mode get(int value) {
        switch (value) {
        case AUTO_VALUE:
            return AUTO;
        case EXPLICIT_VALUE:
            return EXPLICIT;
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
    private Mode(int value, String name, String literal) {
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

} // Mode
