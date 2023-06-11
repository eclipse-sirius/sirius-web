/**
 * Copyright (c) 2021, 2023 Obeo.
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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Dynamic Dialog Folder</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.DynamicDialogFolder#getSubFolders <em>Sub Folders</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.DynamicDialogFolder#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.DynamicDialogFolder#getDynamicDialogs <em>Dynamic Dialogs</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getDynamicDialogFolder()
 * @model
 * @generated
 */
public interface DynamicDialogFolder extends EObject {
    /**
     * Returns the value of the '<em><b>Sub Folders</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.DynamicDialogFolder}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Sub Folders</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getDynamicDialogFolder_SubFolders()
     * @model containment="true"
     * @generated
     */
    EList<DynamicDialogFolder> getSubFolders();

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getDynamicDialogFolder_Name()
     * @model
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.DynamicDialogFolder#getName <em>Name</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Dynamic Dialogs</b></em>' containment reference list. The list contents are of
     * type {@link org.eclipse.sirius.components.view.DynamicDialogDescription}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Dynamic Dialogs</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getDynamicDialogFolder_DynamicDialogs()
     * @model containment="true"
     * @generated
     */
    EList<DynamicDialogDescription> getDynamicDialogs();

} // DynamicDialogFolder
