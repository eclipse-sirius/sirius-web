/**
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.view;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Set Value</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.web.view.SetValue#getFeatureName <em>Feature Name</em>}</li>
 * <li>{@link org.eclipse.sirius.web.view.SetValue#getValueExpression <em>Value Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.web.view.ViewPackage#getSetValue()
 * @model
 * @generated
 */
public interface SetValue extends Operation {

    /**
     * Returns the value of the '<em><b>Feature Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Feature Name</em>' attribute.
     * @see #setFeatureName(String)
     * @see org.eclipse.sirius.web.view.ViewPackage#getSetValue_FeatureName()
     * @model required="true"
     * @generated
     */
    String getFeatureName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.web.view.SetValue#getFeatureName <em>Feature Name</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Feature Name</em>' attribute.
     * @see #getFeatureName()
     * @generated
     */
    void setFeatureName(String value);

    /**
     * Returns the value of the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Value Expression</em>' attribute.
     * @see #setValueExpression(String)
     * @see org.eclipse.sirius.web.view.ViewPackage#getSetValue_ValueExpression()
     * @model required="true"
     * @generated
     */
    String getValueExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.web.view.SetValue#getValueExpression <em>Value Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Value Expression</em>' attribute.
     * @see #getValueExpression()
     * @generated
     */
    void setValueExpression(String value);
} // SetValue
