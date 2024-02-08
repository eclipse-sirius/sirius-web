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

import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.UserColor;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Node Label Style</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.NodeLabelStyle#getLabelColor <em>Label Color</em>}</li>
 * </ul>
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeLabelStyle()
 */
public interface NodeLabelStyle extends LabelStyle {

    /**
     * Returns the value of the '<em><b>Label Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Label Color</em>' reference.
     * @model required="true"
     * @generated
     * @see #setLabelColor(UserColor)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeLabelStyle_LabelColor()
     */
    UserColor getLabelColor();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.NodeLabelStyle#getLabelColor <em>Label
     * Color</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Label Color</em>' reference.
     * @generated
     * @see #getLabelColor()
     */
    void setLabelColor(UserColor value);

    /**
     * Returns the value of the '<em><b>Show Icon</b></em>' attribute. The default value is <code>"false"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Show Icon</em>' attribute.
     * @model default="false"
     * @generated
     * @see #setShowIcon(boolean)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeLabelStyle_ShowIcon()
     */
    boolean isShowIcon();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.NodeLabelStyle#isShowIcon <em>Show
     * Icon</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Show Icon</em>' attribute.
     * @generated
     * @see #isShowIcon()
     */
    void setShowIcon(boolean value);

    /**
     * Returns the value of the '<em><b>Label Icon</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Label Icon</em>' attribute.
     * @model
     * @generated
     * @see #setLabelIcon(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getNodeLabelStyle_LabelIcon()
     */
    String getLabelIcon();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.NodeLabelStyle#getLabelIcon <em>Label
     * Icon</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Label Icon</em>' attribute.
     * @generated
     * @see #getLabelIcon()
     */
    void setLabelIcon(String value);

} // NodeLabelStyle
