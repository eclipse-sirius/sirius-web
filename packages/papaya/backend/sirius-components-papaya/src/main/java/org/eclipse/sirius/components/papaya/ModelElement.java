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
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Model Element</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.ModelElement#getTags <em>Tags</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getModelElement()
 * @model abstract="true"
 * @generated
 */
public interface ModelElement extends EObject {
    /**
     * Returns the value of the '<em><b>Tags</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Tag}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Tags</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getModelElement_Tags()
     * @model containment="true"
     * @generated
     */
    EList<Tag> getTags();

} // ModelElement
