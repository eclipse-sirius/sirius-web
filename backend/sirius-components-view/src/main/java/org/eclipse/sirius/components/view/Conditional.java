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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Conditional</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.Conditional#getCondition <em>Condition</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getConditional()
 * @model abstract="true"
 * @generated
 */
public interface Conditional extends EObject {
    /**
     * Returns the value of the '<em><b>Condition</b></em>' attribute. The default value is <code>"aql:false"</code>.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Condition</em>' attribute.
     * @see #setCondition(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getConditional_Condition()
     * @model default="aql:false" required="true"
     * @generated
     */
    String getCondition();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.Conditional#getCondition <em>Condition</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Condition</em>' attribute.
     * @see #getCondition()
     * @generated
     */
    void setCondition(String value);

} // Conditional
