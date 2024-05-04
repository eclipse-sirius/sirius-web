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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Record</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.Record#getComponents <em>Components</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Record#getImplements <em>Implements</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Record#getOperations <em>Operations</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getRecord()
 * @model
 * @generated
 */
public interface Record extends Classifier {
    /**
     * Returns the value of the '<em><b>Components</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.RecordComponent}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Components</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getRecord_Components()
     * @model containment="true"
     * @generated
     */
    EList<RecordComponent> getComponents();

    /**
     * Returns the value of the '<em><b>Implements</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Interface}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Implements</em>' reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getRecord_Implements()
     * @model
     * @generated
     */
    EList<Interface> getImplements();

    /**
     * Returns the value of the '<em><b>Operations</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Operation}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Operations</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getRecord_Operations()
     * @model containment="true"
     * @generated
     */
    EList<Operation> getOperations();

} // Record
