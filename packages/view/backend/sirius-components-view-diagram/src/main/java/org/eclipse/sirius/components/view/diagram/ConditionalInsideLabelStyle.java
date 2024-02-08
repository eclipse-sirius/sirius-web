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

import org.eclipse.sirius.components.view.Conditional;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Conditional Inside Label Style</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.ConditionalInsideLabelStyle#getStyle <em>Style</em>}</li>
 * </ul>
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getConditionalInsideLabelStyle()
 */
public interface ConditionalInsideLabelStyle extends Conditional {

    /**
     * Returns the value of the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Style</em>' containment reference.
     * @model containment="true"
     * @generated
     * @see #setStyle(InsideLabelStyle)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getConditionalInsideLabelStyle_Style()
     */
    InsideLabelStyle getStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.ConditionalInsideLabelStyle#getStyle
     * <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Style</em>' containment reference.
     * @generated
     * @see #getStyle()
     */
    void setStyle(InsideLabelStyle value);

} // ConditionalInsideLabelStyle
