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
package org.eclipse.sirius.web.view;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Node Style</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.web.view.NodeStyle#getShape <em>Shape</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.web.view.ViewPackage#getNodeStyle()
 * @model
 * @generated
 */
public interface NodeStyle extends Style {

    /**
     * Returns the value of the '<em><b>Shape</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Shape</em>' attribute.
     * @see #setShape(String)
     * @see org.eclipse.sirius.web.view.ViewPackage#getNodeStyle_Shape()
     * @model required="true"
     * @generated
     */
    String getShape();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.web.view.NodeStyle#getShape <em>Shape</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Shape</em>' attribute.
     * @see #getShape()
     * @generated
     */
    void setShape(String value);
} // NodeStyle
