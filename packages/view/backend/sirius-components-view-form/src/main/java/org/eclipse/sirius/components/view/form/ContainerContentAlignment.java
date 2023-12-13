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
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Container Content
 * Alignment</b></em>', and utility methods for working with them. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.form.FormPackage#getContainerContentAlignment()
 * @model
 * @generated
 */
public enum ContainerContentAlignment implements Enumerator {
    /**
     * The '<em><b>Stretch</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #STRETCH_VALUE
     * @generated
     * @ordered
     */
    STRETCH(0, "Stretch", "Stretch"),
    /**
     * The '<em><b>Start</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #START_VALUE
     * @generated
     * @ordered
     */
    START(1, "Start", "Start"),
    /**
     * The '<em><b>Center</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #CENTER_VALUE
     * @generated
     * @ordered
     */
    CENTER(2, "Center", "Center"),
    /**
     * The '<em><b>End</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #END_VALUE
     * @generated
     * @ordered
     */
    END(3, "End", "End");

    /**
     * The '<em><b>Stretch</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #STRETCH
     * @model name="Stretch"
     * @generated
     * @ordered
     */
    public static final int STRETCH_VALUE = 0;

    /**
     * The '<em><b>Start</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #START
     * @model name="Start"
     * @generated
     * @ordered
     */
    public static final int START_VALUE = 1;

    /**
     * The '<em><b>Center</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #CENTER
     * @model name="Center"
     * @generated
     * @ordered
     */
    public static final int CENTER_VALUE = 2;

    /**
     * The '<em><b>End</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #END
     * @model name="End"
     * @generated
     * @ordered
     */
    public static final int END_VALUE = 3;

    /**
     * An array of all the '<em><b>Container Content Alignment</b></em>' enumerators. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    private static final ContainerContentAlignment[] VALUES_ARRAY = new ContainerContentAlignment[] { STRETCH, START, CENTER, END, };

    /**
     * A public read-only list of all the '<em><b>Container Content Alignment</b></em>' enumerators. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final List<ContainerContentAlignment> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

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
    private ContainerContentAlignment(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * Returns the '<em><b>Container Content Alignment</b></em>' literal with the specified literal value. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param literal
     *            the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ContainerContentAlignment get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ContainerContentAlignment result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Container Content Alignment</b></em>' literal with the specified name. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @param name
     *            the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ContainerContentAlignment getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            ContainerContentAlignment result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Container Content Alignment</b></em>' literal with the specified integer value. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static ContainerContentAlignment get(int value) {
        switch (value) {
            case STRETCH_VALUE:
                return STRETCH;
            case START_VALUE:
                return START;
            case CENTER_VALUE:
                return CENTER;
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

} // ContainerContentAlignment
