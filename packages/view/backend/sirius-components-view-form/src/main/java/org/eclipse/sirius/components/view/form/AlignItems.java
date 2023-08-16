/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Align Items</b></em>', and
 * utility methods for working with them. <!-- end-user-doc -->
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.form.FormPackage#getAlignItems()
 */
public enum AlignItems implements Enumerator {
    /**
     * The '<em><b>Stretch</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #STRETCH_VALUE
     */
    STRETCH(0, "stretch", "stretch"),

    /**
     * The '<em><b>Normal</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #NORMAL_VALUE
     */
    NORMAL(1, "normal", "normal"),

    /**
     * The '<em><b>Flex Start</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #FLEX_START_VALUE
     */
    FLEX_START(2, "flexStart", "flexStart"),

    /**
     * The '<em><b>Flex End</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #FLEX_END_VALUE
     */
    FLEX_END(3, "flexEnd", "flexEnd"),

    /**
     * The '<em><b>Center</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #CENTER_VALUE
     */
    CENTER(4, "center", "center"),

    /**
     * The '<em><b>Start</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #START_VALUE
     */
    START(5, "start", "start"),

    /**
     * The '<em><b>End</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #END_VALUE
     */
    END(6, "end", "end"),

    /**
     * The '<em><b>Self Start</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #SELF_START_VALUE
     */
    SELF_START(7, "selfStart", "selfStart"),

    /**
     * The '<em><b>Self End</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #SELF_END_VALUE
     */
    SELF_END(8, "selfEnd", "selfEnd");

    /**
     * The '<em><b>Stretch</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="stretch"
     * @generated
     * @ordered
     * @see #STRETCH
     */
    public static final int STRETCH_VALUE = 0;

    /**
     * The '<em><b>Normal</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="normal"
     * @generated
     * @ordered
     * @see #NORMAL
     */
    public static final int NORMAL_VALUE = 1;

    /**
     * The '<em><b>Flex Start</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="flexStart"
     * @generated
     * @ordered
     * @see #FLEX_START
     */
    public static final int FLEX_START_VALUE = 2;

    /**
     * The '<em><b>Flex End</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="flexEnd"
     * @generated
     * @ordered
     * @see #FLEX_END
     */
    public static final int FLEX_END_VALUE = 3;

    /**
     * The '<em><b>Center</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="center"
     * @generated
     * @ordered
     * @see #CENTER
     */
    public static final int CENTER_VALUE = 4;

    /**
     * The '<em><b>Start</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="start"
     * @generated
     * @ordered
     * @see #START
     */
    public static final int START_VALUE = 5;

    /**
     * The '<em><b>End</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="end"
     * @generated
     * @ordered
     * @see #END
     */
    public static final int END_VALUE = 6;

    /**
     * The '<em><b>Self Start</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="selfStart"
     * @generated
     * @ordered
     * @see #SELF_START
     */
    public static final int SELF_START_VALUE = 7;

    /**
     * The '<em><b>Self End</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="selfEnd"
     * @generated
     * @ordered
     * @see #SELF_END
     */
    public static final int SELF_END_VALUE = 8;

    /**
     * An array of all the '<em><b>Align Items</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static final AlignItems[] VALUES_ARRAY = new AlignItems[] { STRETCH, NORMAL, FLEX_START, FLEX_END, CENTER, START, END, SELF_START, SELF_END, };

    /**
     * A public read-only list of all the '<em><b>Align Items</b></em>' enumerators. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    public static final List<AlignItems> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
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
    AlignItems(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * Returns the '<em><b>Align Items</b></em>' literal with the specified literal value. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param literal the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static AlignItems get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            AlignItems result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Align Items</b></em>' literal with the specified name. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param name the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static AlignItems getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            AlignItems result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Align Items</b></em>' literal with the specified integer value. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param value the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static AlignItems get(int value) {
        switch (value) {
            case STRETCH_VALUE:
                return STRETCH;
            case NORMAL_VALUE:
                return NORMAL;
            case FLEX_START_VALUE:
                return FLEX_START;
            case FLEX_END_VALUE:
                return FLEX_END;
            case CENTER_VALUE:
                return CENTER;
            case START_VALUE:
                return START;
            case END_VALUE:
                return END;
            case SELF_START_VALUE:
                return SELF_START;
            case SELF_END_VALUE:
                return SELF_END;
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

} // AlignItems
