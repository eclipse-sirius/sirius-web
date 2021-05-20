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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Unset Value</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.web.view.UnsetValue#getFeatureName <em>Feature Name</em>}</li>
 * <li>{@link org.eclipse.sirius.web.view.UnsetValue#getElementExpression <em>Element Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.web.view.ViewPackage#getUnsetValue()
 * @model
 * @generated
 */
public interface UnsetValue extends Operation {
    /**
     * Returns the value of the '<em><b>Feature Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Feature Name</em>' attribute.
     * @see #setFeatureName(String)
     * @see org.eclipse.sirius.web.view.ViewPackage#getUnsetValue_FeatureName()
     * @model required="true"
     * @generated
     */
    String getFeatureName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.web.view.UnsetValue#getFeatureName <em>Feature Name</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Feature Name</em>' attribute.
     * @see #getFeatureName()
     * @generated
     */
    void setFeatureName(String value);

    /**
     * Returns the value of the '<em><b>Element Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Element Expression</em>' attribute.
     * @see #setElementExpression(String)
     * @see org.eclipse.sirius.web.view.ViewPackage#getUnsetValue_ElementExpression()
     * @model required="true"
     * @generated
     */
    String getElementExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.web.view.UnsetValue#getElementExpression <em>Element
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Element Expression</em>' attribute.
     * @see #getElementExpression()
     * @generated
     */
    void setElementExpression(String value);

} // UnsetValue
