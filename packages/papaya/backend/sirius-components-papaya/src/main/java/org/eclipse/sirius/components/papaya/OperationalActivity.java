/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.components.papaya;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Operational Activity</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.papaya.OperationalActivity#getPrecondition <em>Precondition</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.papaya.OperationalActivity#getPostcondition <em>Postcondition</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getOperationalActivity()
 * @model
 * @generated
 */
public interface OperationalActivity extends NamedElement, FolderElement {
    /**
	 * Returns the value of the '<em><b>Precondition</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Precondition</em>' attribute.
	 * @see #setPrecondition(String)
	 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getOperationalActivity_Precondition()
	 * @model
	 * @generated
	 */
    String getPrecondition();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.papaya.OperationalActivity#getPrecondition <em>Precondition</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Precondition</em>' attribute.
	 * @see #getPrecondition()
	 * @generated
	 */
    void setPrecondition(String value);

    /**
	 * Returns the value of the '<em><b>Postcondition</b></em>' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Postcondition</em>' attribute.
	 * @see #setPostcondition(String)
	 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getOperationalActivity_Postcondition()
	 * @model
	 * @generated
	 */
    String getPostcondition();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.papaya.OperationalActivity#getPostcondition <em>Postcondition</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Postcondition</em>' attribute.
	 * @see #getPostcondition()
	 * @generated
	 */
    void setPostcondition(String value);

} // OperationalActivity
