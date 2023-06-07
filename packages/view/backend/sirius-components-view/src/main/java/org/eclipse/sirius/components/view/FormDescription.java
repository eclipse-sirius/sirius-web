/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.components.view;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Form Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.FormDescription#getPages <em>Pages</em>}</li>
 * </ul>
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.ViewPackage#getFormDescription()
 */
public interface FormDescription extends RepresentationDescription {

    /**
     * Returns the value of the '<em><b>Pages</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.PageDescription}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Pages</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getFormDescription_Pages()
     * @model containment="true"
     * @generated
     */
    EList<PageDescription> getPages();

} // FormDescription
