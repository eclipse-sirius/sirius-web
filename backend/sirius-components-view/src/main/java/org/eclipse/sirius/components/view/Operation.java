/**
 * Copyright (c) 2021 Obeo.
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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Operation</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.Operation#getChildren <em>Children</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getOperation()
 * @model abstract="true"
 * @generated
 */
public interface Operation extends EObject {

    /**
     * Returns the value of the '<em><b>Children</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.Operation}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Children</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getOperation_Children()
     * @model containment="true"
     * @generated
     */
    EList<Operation> getChildren();
} // Operation
