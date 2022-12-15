/**
 * Copyright (c) 2021, 2022 Obeo.
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
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Group Display Mode</b></em>', and
 * utility methods for working with them. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getGroupDisplayMode()
 * @model
 * @generated
 */
public enum GroupDisplayMode implements Enumerator {
    /**
     * The '<em><b>LIST</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #LIST_VALUE
     * @generated
     * @ordered
     */
    LIST(0, "LIST", "LIST"),

    /**
     * The '<em><b>TOGGLEABLE AREAS</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #TOGGLEABLE_AREAS_VALUE
     * @generated
     * @ordered
     */
    TOGGLEABLE_AREAS(1, "TOGGLEABLE_AREAS", "TOGGLEABLE_AREAS");

    /**
     * The '<em><b>LIST</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #LIST
     * @model
     * @generated
     * @ordered
     */
    public static final int LIST_VALUE = 0;

    /**
     * The '<em><b>TOGGLEABLE AREAS</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #TOGGLEABLE_AREAS
     * @model
     * @generated
     * @ordered
     */
    public static final int TOGGLEABLE_AREAS_VALUE = 1;

    /**
     * An array of all the '<em><b>Group Display Mode</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    private static final GroupDisplayMode[] VALUES_ARRAY = new GroupDisplayMode[] { LIST, TOGGLEABLE_AREAS, };

    /**
     * A public read-only list of all the '<em><b>Group Display Mode</b></em>' enumerators. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    public static final List<GroupDisplayMode> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Group Display Mode</b></em>' literal with the specified literal value. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @param literal
     *            the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static GroupDisplayMode get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            GroupDisplayMode result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Group Display Mode</b></em>' literal with the specified name. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param name
     *            the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static GroupDisplayMode getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            GroupDisplayMode result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Group Display Mode</b></em>' literal with the specified integer value. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @param value
     *            the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static GroupDisplayMode get(int value) {
        switch (value) {
        case LIST_VALUE:
            return LIST;
        case TOGGLEABLE_AREAS_VALUE:
            return TOGGLEABLE_AREAS;
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
    private GroupDisplayMode(int value, String name, String literal) {
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

} // GroupDisplayMode
