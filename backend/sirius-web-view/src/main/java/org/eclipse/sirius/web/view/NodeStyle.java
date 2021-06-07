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
 * <li>{@link org.eclipse.sirius.web.view.NodeStyle#isListMode <em>List Mode</em>}</li>
 * <li>{@link org.eclipse.sirius.web.view.NodeStyle#getBorderRadius <em>Border Radius</em>}</li>
 * <li>{@link org.eclipse.sirius.web.view.NodeStyle#getShape <em>Shape</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.web.view.ViewPackage#getNodeStyle()
 * @model
 * @generated
 */
public interface NodeStyle extends Style {

    /**
     * Returns the value of the '<em><b>List Mode</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>List Mode</em>' attribute.
     * @see #setListMode(boolean)
     * @see org.eclipse.sirius.web.view.ViewPackage#getNodeStyle_ListMode()
     * @model required="true"
     * @generated
     */
    boolean isListMode();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.web.view.NodeStyle#isListMode <em>List Mode</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>List Mode</em>' attribute.
     * @see #isListMode()
     * @generated
     */
    void setListMode(boolean value);

    /**
     * Returns the value of the '<em><b>Border Radius</b></em>' attribute. The default value is <code>"0"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Border Radius</em>' attribute.
     * @see #setBorderRadius(int)
     * @see org.eclipse.sirius.web.view.ViewPackage#getNodeStyle_BorderRadius()
     * @model default="0" required="true"
     * @generated
     */
    int getBorderRadius();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.web.view.NodeStyle#getBorderRadius <em>Border Radius</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Border Radius</em>' attribute.
     * @see #getBorderRadius()
     * @generated
     */
    void setBorderRadius(int value);

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
