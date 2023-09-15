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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>If</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.If#getConditionExpression <em>Condition Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getIf()
 * @model
 * @generated
 */
public interface If extends Operation {
    /**
     * Returns the value of the '<em><b>Condition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Condition Expression</em>' attribute.
     * @see #setConditionExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getIf_ConditionExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression" required="true"
     * @generated
     */
    String getConditionExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.If#getConditionExpression <em>Condition
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Condition Expression</em>' attribute.
     * @see #getConditionExpression()
     * @generated
     */
    void setConditionExpression(String value);

} // If
