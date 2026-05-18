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
package org.eclipse.sirius.components.papaya;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Start Or End</b></em>', and
 * utility methods for working with them. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getStartOrEnd()
 * @model
 * @generated
 */
public enum StartOrEnd implements Enumerator {
    /**
     * The '<em><b>Start</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #START_VALUE
     * @generated
     * @ordered
     */
    START(0, "Start", "START"),

    /**
     * The '<em><b>End</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #END_VALUE
     * @generated
     * @ordered
     */
    END(1, "End", "END");

    /**
     * The '<em><b>Start</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #START
     * @model name="Start" literal="START"
     * @generated
     * @ordered
     */
    public static final int START_VALUE = 0;

    /**
     * The '<em><b>End</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #END
     * @model name="End" literal="END"
     * @generated
     * @ordered
     */
    public static final int END_VALUE = 1;

    /**
     * An array of all the '<em><b>Start Or End</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static final StartOrEnd[] VALUES_ARRAY = new StartOrEnd[] { START, END, };

    /**
     * A public read-only list of all the '<em><b>Start Or End</b></em>' enumerators. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    public static final List<StartOrEnd> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Start Or End</b></em>' literal with the specified literal value. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param literal
     *            the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static StartOrEnd get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            StartOrEnd result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Start Or End</b></em>' literal with the specified name. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param name
     *            the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static StartOrEnd getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            StartOrEnd result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Start Or End</b></em>' literal with the specified integer value. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value
     *            the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static StartOrEnd get(int value) {
        switch (value) {
            case START_VALUE:
                return START;
            case END_VALUE:
                return END;
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
    private StartOrEnd(int value, String name, String literal) {
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

} // StartOrEnd
