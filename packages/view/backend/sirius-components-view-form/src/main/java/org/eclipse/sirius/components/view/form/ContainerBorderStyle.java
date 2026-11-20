/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
package org.eclipse.sirius.components.view.form;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.view.UserColor;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Container Border Style</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.ContainerBorderStyle#getBorderColor <em>Border Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.ContainerBorderStyle#getBorderRadius <em>Border Radius</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.ContainerBorderStyle#getBorderSize <em>Border Size</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.ContainerBorderStyle#getBorderLineStyle <em>Border Line
 * Style</em>}</li>
 * </ul>
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.form.FormPackage#getContainerBorderStyle()
 */
public interface ContainerBorderStyle extends EObject {

    /**
     * Returns the value of the '<em><b>Border Color</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Border Color</em>' reference.
     * @model required="true"
     * @generated
     * @see #setBorderColor(UserColor)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getContainerBorderStyle_BorderColor()
     */
    UserColor getBorderColor();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.ContainerBorderStyle#getBorderColor
     * <em>Border Color</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Border Color</em>' reference.
     * @generated
     * @see #getBorderColor()
     */
    void setBorderColor(UserColor value);

    /**
     * Returns the value of the '<em><b>Border Radius</b></em>' attribute. The default value is <code>"3"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Border Radius</em>' attribute.
     * @model default="3" dataType="org.eclipse.sirius.components.view.Length" required="true"
     * @generated
     * @see #setBorderRadius(int)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getContainerBorderStyle_BorderRadius()
     */
    int getBorderRadius();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.ContainerBorderStyle#getBorderRadius
     * <em>Border Radius</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Border Radius</em>' attribute.
     * @generated
     * @see #getBorderRadius()
     */
    void setBorderRadius(int value);

    /**
     * Returns the value of the '<em><b>Border Size</b></em>' attribute. The default value is <code>"1"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Border Size</em>' attribute.
     * @model default="1" dataType="org.eclipse.sirius.components.view.Length" required="true"
     * @generated
     * @see #setBorderSize(int)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getContainerBorderStyle_BorderSize()
     */
    int getBorderSize();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.ContainerBorderStyle#getBorderSize
     * <em>Border Size</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Border Size</em>' attribute.
     * @generated
     * @see #getBorderSize()
     */
    void setBorderSize(int value);

    /**
     * Returns the value of the '<em><b>Border Line Style</b></em>' attribute. The literals are from the enumeration
     * {@link org.eclipse.sirius.components.view.form.ContainerBorderLineStyle}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Border Line Style</em>' attribute.
     * @model
     * @generated
     * @see org.eclipse.sirius.components.view.form.ContainerBorderLineStyle
     * @see #setBorderLineStyle(ContainerBorderLineStyle)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getContainerBorderStyle_BorderLineStyle()
     */
    ContainerBorderLineStyle getBorderLineStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.ContainerBorderStyle#getBorderLineStyle
     * <em>Border Line Style</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Border Line Style</em>' attribute.
     * @generated
     * @see org.eclipse.sirius.components.view.form.ContainerBorderLineStyle
     * @see #getBorderLineStyle()
     */
    void setBorderLineStyle(ContainerBorderLineStyle value);

} // ContainerBorderStyle
