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
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Justify Content</b></em>', and
 * utility methods for working with them. <!-- end-user-doc -->
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.form.FormPackage#getJustifyContent()
 */
public enum JustifyContent implements Enumerator {
    /**
     * The '<em><b>Stretch</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #STRETCH_VALUE
     */
    STRETCH(0, "stretch", "stretch"),

    /**
     * The '<em><b>Start</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #START_VALUE
     */
    START(1, "start", "start"),

    /**
     * The '<em><b>Center</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #CENTER_VALUE
     */
    CENTER(2, "center", "center"),

    /**
     * The '<em><b>End</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #END_VALUE
     */
    END(3, "end", "end"),

    /**
     * The '<em><b>Flex Start</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #FLEX_START_VALUE
     */
    FLEX_START(4, "flexStart", "flexStart"),

    /**
     * The '<em><b>Flex End</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #FLEX_END_VALUE
     */
    FLEX_END(5, "flexEnd", "flexEnd"),

    /**
     * The '<em><b>Left</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #LEFT_VALUE
     */
    LEFT(6, "left", "left"),

    /**
     * The '<em><b>Right</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #RIGHT_VALUE
     */
    RIGHT(7, "right", "right"),

    /**
     * The '<em><b>Normal</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #NORMAL_VALUE
     */
    NORMAL(8, "normal", "normal"),

    /**
     * The '<em><b>Space Between</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #SPACE_BETWEEN_VALUE
     */
    SPACE_BETWEEN(9, "spaceBetween", "spaceBetween"),

    /**
     * The '<em><b>Space Around</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #SPACE_AROUND_VALUE
     */
    SPACE_AROUND(10, "spaceAround", "spaceAround"),

    /**
     * The '<em><b>Space Evenly</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #SPACE_EVENLY_VALUE
     */
    SPACE_EVENLY(11, "spaceEvenly", "spaceEvenly");

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
     * The '<em><b>Start</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="start"
     * @generated
     * @ordered
     * @see #START
     */
    public static final int START_VALUE = 1;

    /**
     * The '<em><b>Center</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="center"
     * @generated
     * @ordered
     * @see #CENTER
     */
    public static final int CENTER_VALUE = 2;

    /**
     * The '<em><b>End</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="end"
     * @generated
     * @ordered
     * @see #END
     */
    public static final int END_VALUE = 3;

    /**
     * The '<em><b>Flex Start</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="flexStart"
     * @generated
     * @ordered
     * @see #FLEX_START
     */
    public static final int FLEX_START_VALUE = 4;

    /**
     * The '<em><b>Flex End</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="flexEnd"
     * @generated
     * @ordered
     * @see #FLEX_END
     */
    public static final int FLEX_END_VALUE = 5;

    /**
     * The '<em><b>Left</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="left"
     * @generated
     * @ordered
     * @see #LEFT
     */
    public static final int LEFT_VALUE = 6;

    /**
     * The '<em><b>Right</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="right"
     * @generated
     * @ordered
     * @see #RIGHT
     */
    public static final int RIGHT_VALUE = 7;

    /**
     * The '<em><b>Normal</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="normal"
     * @generated
     * @ordered
     * @see #NORMAL
     */
    public static final int NORMAL_VALUE = 8;

    /**
     * The '<em><b>Space Between</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="spaceBetween"
     * @generated
     * @ordered
     * @see #SPACE_BETWEEN
     */
    public static final int SPACE_BETWEEN_VALUE = 9;

    /**
     * The '<em><b>Space Around</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="spaceAround"
     * @generated
     * @ordered
     * @see #SPACE_AROUND
     */
    public static final int SPACE_AROUND_VALUE = 10;

    /**
     * The '<em><b>Space Evenly</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model name="spaceEvenly"
     * @generated
     * @ordered
     * @see #SPACE_EVENLY
     */
    public static final int SPACE_EVENLY_VALUE = 11;

    /**
     * An array of all the '<em><b>Justify Content</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static final JustifyContent[] VALUES_ARRAY = new JustifyContent[] { STRETCH, START, CENTER, END, FLEX_START, FLEX_END, LEFT, RIGHT, NORMAL, SPACE_BETWEEN, SPACE_AROUND,
                                                                                SPACE_EVENLY, };

    /**
     * A public read-only list of all the '<em><b>Justify Content</b></em>' enumerators. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    public static final List<JustifyContent> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
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
    JustifyContent(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * Returns the '<em><b>Justify Content</b></em>' literal with the specified literal value. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param literal the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static JustifyContent get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            JustifyContent result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Justify Content</b></em>' literal with the specified name. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @param name the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static JustifyContent getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            JustifyContent result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Justify Content</b></em>' literal with the specified integer value. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param value the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static JustifyContent get(int value) {
        switch (value) {
            case STRETCH_VALUE:
                return STRETCH;
            case START_VALUE:
                return START;
            case CENTER_VALUE:
                return CENTER;
            case END_VALUE:
                return END;
            case FLEX_START_VALUE:
                return FLEX_START;
            case FLEX_END_VALUE:
                return FLEX_END;
            case LEFT_VALUE:
                return LEFT;
            case RIGHT_VALUE:
                return RIGHT;
            case NORMAL_VALUE:
                return NORMAL;
            case SPACE_BETWEEN_VALUE:
                return SPACE_BETWEEN;
            case SPACE_AROUND_VALUE:
                return SPACE_AROUND;
            case SPACE_EVENLY_VALUE:
                return SPACE_EVENLY;
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

} // JustifyContent
