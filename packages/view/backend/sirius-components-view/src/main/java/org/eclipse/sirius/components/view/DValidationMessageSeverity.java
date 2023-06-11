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
package org.eclipse.sirius.components.view;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>DValidation Message
 * Severity</b></em>', and utility methods for working with them. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getDValidationMessageSeverity()
 * @model
 * @generated
 */
public enum DValidationMessageSeverity implements Enumerator {
    /**
     * The '<em><b>Info</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #INFO_VALUE
     * @generated
     * @ordered
     */
    INFO(0, "Info", "Info"),

    /**
     * The '<em><b>Warning</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #WARNING_VALUE
     * @generated
     * @ordered
     */
    WARNING(1, "Warning", "Warning"),

    /**
     * The '<em><b>Error</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #ERROR_VALUE
     * @generated
     * @ordered
     */
    ERROR(2, "Error", "Error");

    /**
     * The '<em><b>Info</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #INFO
     * @model name="Info"
     * @generated
     * @ordered
     */
    public static final int INFO_VALUE = 0;

    /**
     * The '<em><b>Warning</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #WARNING
     * @model name="Warning"
     * @generated
     * @ordered
     */
    public static final int WARNING_VALUE = 1;

    /**
     * The '<em><b>Error</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #ERROR
     * @model name="Error"
     * @generated
     * @ordered
     */
    public static final int ERROR_VALUE = 2;

    /**
     * An array of all the '<em><b>DValidation Message Severity</b></em>' enumerators. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    private static final DValidationMessageSeverity[] VALUES_ARRAY = new DValidationMessageSeverity[] { INFO, WARNING, ERROR, };

    /**
     * A public read-only list of all the '<em><b>DValidation Message Severity</b></em>' enumerators. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final List<DValidationMessageSeverity> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>DValidation Message Severity</b></em>' literal with the specified literal value. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param literal
     *            the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static DValidationMessageSeverity get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            DValidationMessageSeverity result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>DValidation Message Severity</b></em>' literal with the specified name. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @param name
     *            the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static DValidationMessageSeverity getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            DValidationMessageSeverity result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>DValidation Message Severity</b></em>' literal with the specified integer value. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static DValidationMessageSeverity get(int value) {
        switch (value) {
            case INFO_VALUE:
                return INFO;
            case WARNING_VALUE:
                return WARNING;
            case ERROR_VALUE:
                return ERROR;
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
    private DValidationMessageSeverity(int value, String name, String literal) {
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

} // DValidationMessageSeverity
