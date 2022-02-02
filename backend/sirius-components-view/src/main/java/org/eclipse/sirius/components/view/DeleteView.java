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
package org.eclipse.sirius.components.view;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Delete View</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.DeleteView#getViewExpression <em>View Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getDeleteView()
 * @model
 * @generated
 */
public interface DeleteView extends Operation {
    /**
     * Returns the value of the '<em><b>View Expression</b></em>' attribute. The default value is
     * <code>"aql:selectedNode"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>View Expression</em>' attribute.
     * @see #setViewExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getDeleteView_ViewExpression()
     * @model default="aql:selectedNode" required="true"
     * @generated
     */
    String getViewExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.DeleteView#getViewExpression <em>View
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>View Expression</em>' attribute.
     * @see #getViewExpression()
     * @generated
     */
    void setViewExpression(String value);

} // DeleteView
