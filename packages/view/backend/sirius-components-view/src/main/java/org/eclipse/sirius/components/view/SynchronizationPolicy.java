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
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Synchronization Policy</b></em>',
 * and utility methods for working with them. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getSynchronizationPolicy()
 * @model
 * @generated
 */
public enum SynchronizationPolicy implements Enumerator {
    /**
     * The '<em><b>SYNCHRONIZED</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #SYNCHRONIZED_VALUE
     * @generated
     * @ordered
     */
    SYNCHRONIZED(0, "SYNCHRONIZED", "SYNCHRONIZED"),

    /**
     * The '<em><b>UNSYNCHRONIZED</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #UNSYNCHRONIZED_VALUE
     * @generated
     * @ordered
     */
    UNSYNCHRONIZED(1, "UNSYNCHRONIZED", "UNSYNCHRONIZED");

    /**
     * The '<em><b>SYNCHRONIZED</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #SYNCHRONIZED
     * @model
     * @generated
     * @ordered
     */
    public static final int SYNCHRONIZED_VALUE = 0;

    /**
     * The '<em><b>UNSYNCHRONIZED</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #UNSYNCHRONIZED
     * @model
     * @generated
     * @ordered
     */
    public static final int UNSYNCHRONIZED_VALUE = 1;

    /**
     * An array of all the '<em><b>Synchronization Policy</b></em>' enumerators. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    private static final SynchronizationPolicy[] VALUES_ARRAY = new SynchronizationPolicy[] { SYNCHRONIZED, UNSYNCHRONIZED, };

    /**
     * A public read-only list of all the '<em><b>Synchronization Policy</b></em>' enumerators. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    public static final List<SynchronizationPolicy> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Synchronization Policy</b></em>' literal with the specified literal value. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param literal
     *            the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static SynchronizationPolicy get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            SynchronizationPolicy result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Synchronization Policy</b></em>' literal with the specified name. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param name
     *            the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static SynchronizationPolicy getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            SynchronizationPolicy result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Synchronization Policy</b></em>' literal with the specified integer value. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static SynchronizationPolicy get(int value) {
        switch (value) {
            case SYNCHRONIZED_VALUE:
                return SYNCHRONIZED;
            case UNSYNCHRONIZED_VALUE:
                return UNSYNCHRONIZED;
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
    private SynchronizationPolicy(int value, String name, String literal) {
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

} // SynchronizationPolicy
