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
package org.eclipse.sirius.components.view.diagram;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.view.UserColor;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Style Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.DiagramStyleDescription#getBackground <em>Background</em>}</li>
 * </ul>
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDiagramStyleDescription()
 */
public interface DiagramStyleDescription extends EObject {

    /**
     * Returns the value of the '<em><b>Background</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Background</em>' reference.
     * @model
     * @generated
     * @see #setBackground(UserColor)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getDiagramStyleDescription_Background()
     */
    UserColor getBackground();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.DiagramStyleDescription#getBackground
     * <em>Background</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Background</em>' reference.
     * @generated
     * @see #getBackground()
     */
    void setBackground(UserColor value);

} // DiagramStyleDescription
