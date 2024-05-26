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
 * <li>{@link org.eclipse.sirius.components.papaya.Interface#getExtendedBy <em>Extended By</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Interface#getOperations <em>Operations</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Interface#getImplementedBy <em>Implemented By</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getInterface()
 * @model
 * @generated
 */
public interface Interface extends Classifier {
    /**
     * Returns the value of the '<em><b>Extends</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Interface}. It is bidirectional and its opposite is
     * '{@link org.eclipse.sirius.components.papaya.Interface#getExtendedBy <em>Extended By</em>}'. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Extends</em>' reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getInterface_Extends()
     * @see org.eclipse.sirius.components.papaya.Interface#getExtendedBy
     * @model opposite="extendedBy"
     * @generated
     */
    EList<Interface> getExtends();

    /**
     * Returns the value of the '<em><b>Extended By</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Interface}. It is bidirectional and its opposite is
     * '{@link org.eclipse.sirius.components.papaya.Interface#getExtends <em>Extends</em>}'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Extended By</em>' reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getInterface_ExtendedBy()
     * @see org.eclipse.sirius.components.papaya.Interface#getExtends
     * @model opposite="extends"
     * @generated
     */
    EList<Interface> getExtendedBy();

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

    /**
     * Returns the value of the '<em><b>Implemented By</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.InterfaceImplementation}. It is bidirectional and its opposite is
     * '{@link org.eclipse.sirius.components.papaya.InterfaceImplementation#getImplements <em>Implements</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Implemented By</em>' reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getInterface_ImplementedBy()
     * @see org.eclipse.sirius.components.papaya.InterfaceImplementation#getImplements
     * @model opposite="implements"
     * @generated
     */
    EList<InterfaceImplementation> getImplementedBy();

} // Interface
