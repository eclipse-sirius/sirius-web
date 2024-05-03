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
package org.eclipse.sirius.components.view;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>For</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.For#getExpression <em>Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.For#getIteratorName <em>Iterator Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getFor()
 * @model
 * @generated
 */
public interface For extends Operation {
    /**
     * Returns the value of the '<em><b>Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Expression</em>' attribute.
     * @see #setExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getFor_Expression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression" required="true"
     * @generated
     */
    String getExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.For#getExpression <em>Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Expression</em>' attribute.
     * @see #getExpression()
     * @generated
     */
    void setExpression(String value);

    /**
     * Returns the value of the '<em><b>Iterator Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Iterator Name</em>' attribute.
     * @see #setIteratorName(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getFor_IteratorName()
     * @model required="true"
     * @generated
     */
    String getIteratorName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.For#getIteratorName <em>Iterator Name</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Iterator Name</em>' attribute.
     * @see #getIteratorName()
     * @generated
     */
    void setIteratorName(String value);

} // For
