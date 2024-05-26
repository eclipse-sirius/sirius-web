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
package org.eclipse.sirius.components.papaya;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Constructor</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.Constructor#getParameters <em>Parameters</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Constructor#getVisibility <em>Visibility</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getConstructor()
 * @model
 * @generated
 */
public interface Constructor extends AnnotableElement {
    /**
     * Returns the value of the '<em><b>Parameters</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Parameter}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Parameters</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getConstructor_Parameters()
     * @model containment="true"
     * @generated
     */
    EList<Parameter> getParameters();

    /**
     * Returns the value of the '<em><b>Visibility</b></em>' attribute. The default value is <code>"PUBLIC"</code>. The
     * literals are from the enumeration {@link org.eclipse.sirius.components.papaya.Visibility}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Visibility</em>' attribute.
     * @see org.eclipse.sirius.components.papaya.Visibility
     * @see #setVisibility(Visibility)
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getConstructor_Visibility()
     * @model default="PUBLIC" required="true"
     * @generated
     */
    Visibility getVisibility();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.papaya.Constructor#getVisibility
     * <em>Visibility</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Visibility</em>' attribute.
     * @see org.eclipse.sirius.components.papaya.Visibility
     * @see #getVisibility()
     * @generated
     */
    void setVisibility(Visibility value);

} // Constructor
