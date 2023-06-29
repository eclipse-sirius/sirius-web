/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Border Style</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.BorderStyle#getBorderColor <em>Border Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.BorderStyle#getBorderRadius <em>Border Radius</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.BorderStyle#getBorderSize <em>Border Size</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.BorderStyle#getBorderLineStyle <em>Border Line Style</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getBorderStyle()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface BorderStyle extends EObject {
    /**
     * Returns the value of the '<em><b>Border Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Border Color</em>' reference.
     * @see #setBorderColor(UserColor)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getBorderStyle_BorderColor()
     * @model required="true"
     * @generated
     */
    UserColor getBorderColor();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.BorderStyle#getBorderColor <em>Border
     * Color</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Border Color</em>' reference.
     * @see #getBorderColor()
     * @generated
     */
    void setBorderColor(UserColor value);

    /**
     * Returns the value of the '<em><b>Border Radius</b></em>' attribute. The default value is <code>"3"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Border Radius</em>' attribute.
     * @see #setBorderRadius(int)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getBorderStyle_BorderRadius()
     * @model default="3" dataType="org.eclipse.sirius.components.view.Length" required="true"
     * @generated
     */
    int getBorderRadius();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.BorderStyle#getBorderRadius <em>Border
     * Radius</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Border Radius</em>' attribute.
     * @see #getBorderRadius()
     * @generated
     */
    void setBorderRadius(int value);

    /**
     * Returns the value of the '<em><b>Border Size</b></em>' attribute. The default value is <code>"1"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Border Size</em>' attribute.
     * @see #setBorderSize(int)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getBorderStyle_BorderSize()
     * @model default="1" dataType="org.eclipse.sirius.components.view.Length" required="true"
     * @generated
     */
    int getBorderSize();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.BorderStyle#getBorderSize <em>Border
     * Size</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Border Size</em>' attribute.
     * @see #getBorderSize()
     * @generated
     */
    void setBorderSize(int value);

    /**
     * Returns the value of the '<em><b>Border Line Style</b></em>' attribute. The literals are from the enumeration
     * {@link org.eclipse.sirius.components.view.diagram.LineStyle}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Border Line Style</em>' attribute.
     * @see org.eclipse.sirius.components.view.diagram.LineStyle
     * @see #setBorderLineStyle(LineStyle)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getBorderStyle_BorderLineStyle()
     * @model
     * @generated
     */
    LineStyle getBorderLineStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.BorderStyle#getBorderLineStyle
     * <em>Border Line Style</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Border Line Style</em>' attribute.
     * @see org.eclipse.sirius.components.view.diagram.LineStyle
     * @see #getBorderLineStyle()
     * @generated
     */
    void setBorderLineStyle(LineStyle value);

} // BorderStyle
