/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Container</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.Container#getFolders <em>Folders</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Container#getElements <em>Elements</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getContainer()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Container extends EObject {
    /**
     * Returns the value of the '<em><b>Folders</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Folder}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Folders</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getContainer_Folders()
     * @model containment="true"
     * @generated
     */
    EList<Folder> getFolders();

    /**
     * Returns the value of the '<em><b>Elements</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.FolderElement}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Elements</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getContainer_Elements()
     * @model containment="true"
     * @generated
     */
    EList<FolderElement> getElements();

} // Container
