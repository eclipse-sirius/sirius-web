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
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Header Separator Display
 * Mode</b></em>', and utility methods for working with them. <!-- end-user-doc -->
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getHeaderSeparatorDisplayMode()
 */
public enum HeaderSeparatorDisplayMode implements Enumerator {
    /**
     * The '<em><b>NEVER</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #NEVER_VALUE
     */
    NEVER(0, "NEVER", "NEVER"),

    /**
     * The '<em><b>ALWAYS</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #ALWAYS_VALUE
     */
    ALWAYS(1, "ALWAYS", "ALWAYS"),

    /**
     * The '<em><b>IF CHILDREN</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #IF_CHILDREN_VALUE
     */
    IF_CHILDREN(2, "IF_CHILDREN", "IF_CHILDREN");

    /**
     * The '<em><b>NEVER</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model
     * @generated
     * @ordered
     * @see #NEVER
     */
    public static final int NEVER_VALUE = 0;

    /**
     * The '<em><b>ALWAYS</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model
     * @generated
     * @ordered
     * @see #ALWAYS
     */
    public static final int ALWAYS_VALUE = 1;

    /**
     * The '<em><b>IF CHILDREN</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model
     * @generated
     * @ordered
     * @see #IF_CHILDREN
     */
    public static final int IF_CHILDREN_VALUE = 2;

    /**
     * An array of all the '<em><b>Header Separator Display Mode</b></em>' enumerators. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    private static final HeaderSeparatorDisplayMode[] VALUES_ARRAY = new HeaderSeparatorDisplayMode[] { NEVER, ALWAYS, IF_CHILDREN, };

    /**
     * A public read-only list of all the '<em><b>Header Separator Display Mode</b></em>' enumerators. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final List<HeaderSeparatorDisplayMode> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
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
    HeaderSeparatorDisplayMode(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * Returns the '<em><b>Header Separator Display Mode</b></em>' literal with the specified literal value. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param literal
     *         the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static HeaderSeparatorDisplayMode get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            HeaderSeparatorDisplayMode result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Header Separator Display Mode</b></em>' literal with the specified name. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @param name
     *         the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static HeaderSeparatorDisplayMode getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            HeaderSeparatorDisplayMode result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Header Separator Display Mode</b></em>' literal with the specified integer value. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static HeaderSeparatorDisplayMode get(int value) {
        switch (value) {
            case NEVER_VALUE:
                return NEVER;
            case ALWAYS_VALUE:
                return ALWAYS;
            case IF_CHILDREN_VALUE:
                return IF_CHILDREN;
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

} // HeaderSeparatorDisplayMode
