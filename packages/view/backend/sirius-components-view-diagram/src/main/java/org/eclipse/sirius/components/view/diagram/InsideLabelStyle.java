/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
     * @see #setWithHeader(boolean)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getInsideLabelStyle_WithHeader()
     * @model
     * @generated
     */
    boolean isWithHeader();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.InsideLabelStyle#isWithHeader <em>With
     * Header</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>With Header</em>' attribute.
     * @see #isWithHeader()
     * @generated
     */
    void setWithHeader(boolean value);

    /**
     * Returns the value of the '<em><b>Header Separator Display Mode</b></em>' attribute. The literals are from the
     * enumeration {@link org.eclipse.sirius.components.view.diagram.HeaderSeparatorDisplayMode}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Header Separator Display Mode</em>' attribute.
     * @see org.eclipse.sirius.components.view.diagram.HeaderSeparatorDisplayMode
     * @see #setHeaderSeparatorDisplayMode(HeaderSeparatorDisplayMode)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getInsideLabelStyle_HeaderSeparatorDisplayMode()
     * @model required="true"
     * @generated
     */
    HeaderSeparatorDisplayMode getHeaderSeparatorDisplayMode();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.InsideLabelStyle#getHeaderSeparatorDisplayMode <em>Header
     * Separator Display Mode</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Header Separator Display Mode</em>' attribute.
     * @see org.eclipse.sirius.components.view.diagram.HeaderSeparatorDisplayMode
     * @see #getHeaderSeparatorDisplayMode()
     * @generated
     */
    void setHeaderSeparatorDisplayMode(HeaderSeparatorDisplayMode value);

} // InsideLabelStyle
