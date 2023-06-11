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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Style</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.Style#getColor <em>Color</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getStyle()
 * @model abstract="true"
 * @generated
 */
public interface Style extends EObject {
    /**
     * Returns the value of the '<em><b>Color</b></em>' attribute. The default value is <code>"#a590df"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Color</em>' attribute.
     * @model default="#a590df"
     * @generated
     * @see #setColor(UserColor)
     * @see org.eclipse.sirius.components.view.ViewPackage#getStyle_Color()
     */
    UserColor getColor();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.Style#getColor <em>Color</em>}' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Color</em>' reference.
     * @generated
     * @see #getColor()
     */
    void setColor(UserColor value);

} // Style
