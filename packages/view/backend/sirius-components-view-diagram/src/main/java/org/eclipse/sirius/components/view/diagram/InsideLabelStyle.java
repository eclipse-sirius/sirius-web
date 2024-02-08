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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Inside Label Style</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.InsideLabelStyle#getLabelColor <em>Label Color</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.InsideLabelStyle#isWithHeader <em>With Header</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.InsideLabelStyle#isDisplayHeaderSeparator <em>Display Header
 * Separator</em>}</li>
 * </ul>
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getInsideLabelStyle()
 */
public interface InsideLabelStyle extends NodeLabelStyle {

    /**
     * Returns the value of the '<em><b>With Header</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>With Header</em>' attribute.
     * @model
     * @generated
     * @see #setWithHeader(boolean)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getInsideLabelStyle_WithHeader()
     */
    boolean isWithHeader();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.InsideLabelStyle#isWithHeader <em>With
     * Header</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>With Header</em>' attribute.
     * @generated
     * @see #isWithHeader()
     */
    void setWithHeader(boolean value);

    /**
     * Returns the value of the '<em><b>Display Header Separator</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Display Header Separator</em>' attribute.
     * @model
     * @generated
     * @see #setDisplayHeaderSeparator(boolean)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getInsideLabelStyle_DisplayHeaderSeparator()
     */
    boolean isDisplayHeaderSeparator();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.InsideLabelStyle#isDisplayHeaderSeparator <em>Display Header
     * Separator</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Display Header Separator</em>' attribute.
     * @generated
     * @see #isDisplayHeaderSeparator()
     */
    void setDisplayHeaderSeparator(boolean value);

} // InsideLabelStyle
