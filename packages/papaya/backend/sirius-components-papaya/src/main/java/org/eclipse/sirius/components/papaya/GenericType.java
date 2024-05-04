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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Generic Type</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.GenericType#getRawType <em>Raw Type</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.GenericType#getTypeArguments <em>Type Arguments</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getGenericType()
 * @model
 * @generated
 */
public interface GenericType extends ModelElement, AnnotableElement {
    /**
     * Returns the value of the '<em><b>Raw Type</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Raw Type</em>' reference.
     * @see #setRawType(Type)
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getGenericType_RawType()
     * @model required="true"
     * @generated
     */
    Type getRawType();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.papaya.GenericType#getRawType <em>Raw Type</em>}'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Raw Type</em>' reference.
     * @see #getRawType()
     * @generated
     */
    void setRawType(Type value);

    /**
     * Returns the value of the '<em><b>Type Arguments</b></em>' containment reference list. The list contents are of
     * type {@link org.eclipse.sirius.components.papaya.GenericType}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Type Arguments</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getGenericType_TypeArguments()
     * @model containment="true"
     * @generated
     */
    EList<GenericType> getTypeArguments();

} // GenericType
