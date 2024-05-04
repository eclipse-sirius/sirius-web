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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Interface</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.Interface#getExtends <em>Extends</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Interface#getOperations <em>Operations</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getInterface()
 * @model
 * @generated
 */
public interface Interface extends Classifier {
    /**
     * Returns the value of the '<em><b>Extends</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Interface}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Extends</em>' reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getInterface_Extends()
     * @model
     * @generated
     */
    EList<Interface> getExtends();

    /**
     * Returns the value of the '<em><b>Operations</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Operation}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Operations</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getInterface_Operations()
     * @model containment="true"
     * @generated
     */
    EList<Operation> getOperations();

} // Interface
