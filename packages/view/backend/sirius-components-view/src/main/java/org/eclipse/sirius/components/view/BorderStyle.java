/**
 * Copyright (c) 2021, 2023 Obeo.
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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Border Style</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.BorderStyle#getBorderColor <em>Border Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.BorderStyle#getBorderRadius <em>Border Radius</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.BorderStyle#getBorderSize <em>Border Size</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.BorderStyle#getBorderLineStyle <em>Border Line Style</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getBorderStyle()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface BorderStyle extends EObject {
    /**
     * Returns the value of the '<em><b>Border Color</b></em>' attribute. The default value is <code>"#33B0C3"</code>.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Border Color</em>' attribute.
     * @see #setBorderColor(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getBorderStyle_BorderColor()
     * @model default="#33B0C3" dataType="org.eclipse.sirius.components.view.Color" required="true"
     * @generated
     */
    String getBorderColor();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.BorderStyle#getBorderColor <em>Border
     * Color</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Border Color</em>' attribute.
     * @see #getBorderColor()
     * @generated
     */
    void setBorderColor(String value);

    /**
     * Returns the value of the '<em><b>Border Radius</b></em>' attribute. The default value is <code>"3"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Border Radius</em>' attribute.
     * @see #setBorderRadius(int)
     * @see org.eclipse.sirius.components.view.ViewPackage#getBorderStyle_BorderRadius()
     * @model default="3" required="true"
     * @generated
     */
    int getBorderRadius();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.BorderStyle#getBorderRadius <em>Border
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
     * @see org.eclipse.sirius.components.view.ViewPackage#getBorderStyle_BorderSize()
     * @model default="1" required="true"
     * @generated
     */
    int getBorderSize();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.BorderStyle#getBorderSize <em>Border Size</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Border Size</em>' attribute.
     * @see #getBorderSize()
     * @generated
     */
    void setBorderSize(int value);

    /**
     * Returns the value of the '<em><b>Border Line Style</b></em>' attribute. The literals are from the enumeration
     * {@link org.eclipse.sirius.components.view.LineStyle}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Border Line Style</em>' attribute.
     * @see org.eclipse.sirius.components.view.LineStyle
     * @see #setBorderLineStyle(LineStyle)
     * @see org.eclipse.sirius.components.view.ViewPackage#getBorderStyle_BorderLineStyle()
     * @model
     * @generated
     */
    LineStyle getBorderLineStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.BorderStyle#getBorderLineStyle <em>Border Line
     * Style</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Border Line Style</em>' attribute.
     * @see org.eclipse.sirius.components.view.LineStyle
     * @see #getBorderLineStyle()
     * @generated
     */
    void setBorderLineStyle(LineStyle value);

} // BorderStyle
