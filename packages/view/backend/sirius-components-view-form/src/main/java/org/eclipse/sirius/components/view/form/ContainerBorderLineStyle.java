/**
 * Copyright (c) 2023 Obeo.
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
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Container Border Line
 * Style</b></em>', and utility methods for working with them. <!-- end-user-doc -->
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.form.FormPackage#getContainerBorderLineStyle()
 */
public enum ContainerBorderLineStyle implements Enumerator {
    /**
     * The '<em><b>Solid</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #SOLID_VALUE
     */
    SOLID(0, "Solid", "Solid"),

    /**
     * The '<em><b>Dashed</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #DASHED_VALUE
     */
    DASHED(1, "Dashed", "Dashed"),

    /**
     * The '<em><b>Dotted</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #DOTTED_VALUE
     */
    DOTTED(2, "Dotted", "Dotted");

    /**
     * The '<em><b>Solid</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="Solid"
     * @generated
     * @ordered
     * @see #SOLID
     */
    public static final int SOLID_VALUE = 0;

    /**
     * The '<em><b>Dashed</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="Dashed"
     * @generated
     * @ordered
     * @see #DASHED
     */
    public static final int DASHED_VALUE = 1;

    /**
     * The '<em><b>Dotted</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="Dotted"
     * @generated
     * @ordered
     * @see #DOTTED
     */
    public static final int DOTTED_VALUE = 2;

    /**
     * An array of all the '<em><b>Container Border Line Style</b></em>' enumerators. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    private static final ContainerBorderLineStyle[] VALUES_ARRAY = new ContainerBorderLineStyle[]{SOLID, DASHED, DOTTED,};

    /**
     * A public read-only list of all the '<em><b>Container Border Line Style</b></em>' enumerators. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final List<ContainerBorderLineStyle> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Container Border Line Style</b></em>' literal with the specified literal value. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param literal the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ContainerBorderLineStyle get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ContainerBorderLineStyle result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Container Border Line Style</b></em>' literal with the specified name. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @param name
     *            the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ContainerBorderLineStyle getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ContainerBorderLineStyle result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Container Border Line Style</b></em>' literal with the specified integer value. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ContainerBorderLineStyle get(int value) {
        switch (value) {
            case SOLID_VALUE:
                return SOLID;
            case DASHED_VALUE:
                return DASHED;
            case DOTTED_VALUE:
                return DOTTED;
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
    ContainerBorderLineStyle(int value, String name, String literal) {
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

} // ContainerBorderLineStyle
