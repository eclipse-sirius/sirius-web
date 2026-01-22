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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Referencing Link</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.ReferencingLink#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getReferencingLink()
 * @model
 * @generated
 */
public interface ReferencingLink extends Link {
    /**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(ModelElement)
	 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getReferencingLink_Target()
	 * @model required="true"
	 * @generated
	 */
    ModelElement getTarget();

    /**
	 * Sets the value of the '{@link org.eclipse.sirius.components.papaya.ReferencingLink#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
    void setTarget(ModelElement value);

} // ReferencingLink
