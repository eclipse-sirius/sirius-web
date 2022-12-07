/**
 * Copyright (c) 2021, 2022 Obeo.
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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Form Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.FormDescription#getWidgets <em>Widgets</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getFormDescription()
 * @model
 * @generated
 */
public interface FormDescription extends RepresentationDescription {
    /**
     * Returns the value of the '<em><b>Groups</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.GroupDescription}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Groups</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getFormDescription_Groups()
     * @model containment="true"
     * @generated
     */
    EList<GroupDescription> getGroups();

} // FormDescription
