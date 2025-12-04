/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.view.diagram.customnodes;

import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Ellipse Node Style Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.customnodes.EllipseNodeStyleDescription#getBackground
 * <em>Background</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.customnodes.EllipseNodeStyleDescription#getOpacityExpression
 * <em>Opacity Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.diagram.customnodes.CustomnodesPackage#getEllipseNodeStyleDescription()
 * @model
 * @generated
 */
public interface EllipseNodeStyleDescription extends NodeStyleDescription {

    /**
     * Returns the value of the '<em><b>Background</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Background</em>' reference.
     * @see #setBackground(UserColor)
     * @see org.eclipse.sirius.components.view.diagram.customnodes.CustomnodesPackage#getEllipseNodeStyleDescription_Background()
     * @model
     * @generated
     */
    UserColor getBackground();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.customnodes.EllipseNodeStyleDescription#getBackground
     * <em>Background</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Background</em>' reference.
     * @see #getBackground()
     * @generated
     */
    void setBackground(UserColor value);

    /**
     * Returns the value of the '<em><b>Opacity Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Opacity Expression</em>' attribute.
     * @see #setOpacityExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.customnodes.CustomnodesPackage#getEllipseNodeStyleDescription_OpacityExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getOpacityExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.customnodes.EllipseNodeStyleDescription#getOpacityExpression
     * <em>Opacity Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Opacity Expression</em>' attribute.
     * @see #getOpacityExpression()
     * @generated
     */
    void setOpacityExpression(String value);
} // EllipseNodeStyleDescription
