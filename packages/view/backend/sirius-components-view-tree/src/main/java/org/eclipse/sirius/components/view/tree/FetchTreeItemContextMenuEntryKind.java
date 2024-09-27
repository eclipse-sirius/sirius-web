/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.view.tree;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Fetch Tree Item Context Menu
 * Entry Kind</b></em>', and utility methods for working with them. <!-- end-user-doc -->
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.tree.TreePackage#getFetchTreeItemContextMenuEntryKind()
 */
public enum FetchTreeItemContextMenuEntryKind implements Enumerator {
    /**
     * The '<em><b>DOWNLOAD</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #DOWNLOAD_VALUE
     */
    DOWNLOAD(0, "DOWNLOAD", "DOWNLOAD"),

    /**
     * The '<em><b>OPEN</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #OPEN_VALUE
     */
    OPEN(1, "OPEN", "OPEN");

    /**
     * The '<em><b>DOWNLOAD</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model
     * @generated
     * @ordered
     * @see #DOWNLOAD
     */
    public static final int DOWNLOAD_VALUE = 0;

    /**
     * The '<em><b>OPEN</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @model
     * @generated
     * @ordered
     * @see #OPEN
     */
    public static final int OPEN_VALUE = 1;

    /**
     * An array of all the '<em><b>Fetch Tree Item Context Menu Entry Kind</b></em>' enumerators. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static final FetchTreeItemContextMenuEntryKind[] VALUES_ARRAY = new FetchTreeItemContextMenuEntryKind[] { DOWNLOAD, OPEN, };

    /**
     * A public read-only list of all the '<em><b>Fetch Tree Item Context Menu Entry Kind</b></em>' enumerators. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static final List<FetchTreeItemContextMenuEntryKind> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));
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
    FetchTreeItemContextMenuEntryKind(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * Returns the '<em><b>Fetch Tree Item Context Menu Entry Kind</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param literal
     *         the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static FetchTreeItemContextMenuEntryKind get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            FetchTreeItemContextMenuEntryKind result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Fetch Tree Item Context Menu Entry Kind</b></em>' literal with the specified name. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param name
     *         the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static FetchTreeItemContextMenuEntryKind getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            FetchTreeItemContextMenuEntryKind result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Fetch Tree Item Context Menu Entry Kind</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static FetchTreeItemContextMenuEntryKind get(int value) {
        switch (value) {
            case DOWNLOAD_VALUE:
                return DOWNLOAD;
            case OPEN_VALUE:
                return OPEN;
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

} // FetchTreeItemContextMenuEntryKind
