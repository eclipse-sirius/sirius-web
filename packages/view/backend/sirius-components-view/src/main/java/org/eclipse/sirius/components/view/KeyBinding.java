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
package org.eclipse.sirius.components.view;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Key Binding</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.KeyBinding#isCtrl <em>Ctrl</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.KeyBinding#isAlt <em>Alt</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.KeyBinding#isMeta <em>Meta</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.KeyBinding#getKey <em>Key</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getKeyBinding()
 * @model
 * @generated
 */
public interface KeyBinding extends EObject {
    /**
     * Returns the value of the '<em><b>Ctrl</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Ctrl</em>' attribute.
     * @see #setCtrl(boolean)
     * @see org.eclipse.sirius.components.view.ViewPackage#getKeyBinding_Ctrl()
     * @model
     * @generated
     */
    boolean isCtrl();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.KeyBinding#isCtrl <em>Ctrl</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Ctrl</em>' attribute.
     * @see #isCtrl()
     * @generated
     */
    void setCtrl(boolean value);

    /**
     * Returns the value of the '<em><b>Alt</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Alt</em>' attribute.
     * @see #setAlt(boolean)
     * @see org.eclipse.sirius.components.view.ViewPackage#getKeyBinding_Alt()
     * @model
     * @generated
     */
    boolean isAlt();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.KeyBinding#isAlt <em>Alt</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Alt</em>' attribute.
     * @see #isAlt()
     * @generated
     */
    void setAlt(boolean value);

    /**
     * Returns the value of the '<em><b>Meta</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Meta</em>' attribute.
     * @see #setMeta(boolean)
     * @see org.eclipse.sirius.components.view.ViewPackage#getKeyBinding_Meta()
     * @model
     * @generated
     */
    boolean isMeta();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.KeyBinding#isMeta <em>Meta</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Meta</em>' attribute.
     * @see #isMeta()
     * @generated
     */
    void setMeta(boolean value);

    /**
     * Returns the value of the '<em><b>Key</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Key</em>' attribute.
     * @see #setKey(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getKeyBinding_Key()
     * @model
     * @generated
     */
    String getKey();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.KeyBinding#getKey <em>Key</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Key</em>' attribute.
     * @see #getKey()
     * @generated
     */
    void setKey(String value);

} // KeyBinding
