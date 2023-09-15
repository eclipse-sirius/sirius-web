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
package org.eclipse.sirius.components.view;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Let</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.Let#getVariableName <em>Variable Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.Let#getValueExpression <em>Value Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getLet()
 * @model
 * @generated
 */
public interface Let extends Operation {
    /**
     * Returns the value of the '<em><b>Variable Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Variable Name</em>' attribute.
     * @see #setVariableName(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getLet_VariableName()
     * @model required="true"
     * @generated
     */
    String getVariableName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.Let#getVariableName <em>Variable Name</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Variable Name</em>' attribute.
     * @see #getVariableName()
     * @generated
     */
    void setVariableName(String value);

    /**
     * Returns the value of the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Value Expression</em>' attribute.
     * @see #setValueExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getLet_ValueExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression" required="true"
     * @generated
     */
    String getValueExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.Let#getValueExpression <em>Value
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Value Expression</em>' attribute.
     * @see #getValueExpression()
     * @generated
     */
    void setValueExpression(String value);

} // Let
