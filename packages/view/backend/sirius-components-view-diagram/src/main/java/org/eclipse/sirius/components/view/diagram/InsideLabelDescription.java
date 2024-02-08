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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Inside Label Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.InsideLabelDescription#getPosition <em>Position</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.InsideLabelDescription#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.InsideLabelDescription#getConditionalStyles <em>Conditional
 * Styles</em>}</li>
 * </ul>
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getInsideLabelDescription()
 */
public interface InsideLabelDescription extends LabelDescription {

    /**
     * Returns the value of the '<em><b>Position</b></em>' attribute. The literals are from the enumeration
     * {@link org.eclipse.sirius.components.view.diagram.InsideLabelPosition}. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Position</em>' attribute.
     * @model required="true"
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.InsideLabelPosition
     * @see #setPosition(InsideLabelPosition)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getInsideLabelDescription_Position()
     */
    InsideLabelPosition getPosition();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.InsideLabelDescription#getPosition
     * <em>Position</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Position</em>' attribute.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.InsideLabelPosition
     * @see #getPosition()
     */
    void setPosition(InsideLabelPosition value);

    /**
     * Returns the value of the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Style</em>' containment reference.
     * @model containment="true"
     * @generated
     * @see #setStyle(InsideLabelStyle)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getInsideLabelDescription_Style()
     */
    InsideLabelStyle getStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.InsideLabelDescription#getStyle
     * <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Style</em>' containment reference.
     * @generated
     * @see #getStyle()
     */
    void setStyle(InsideLabelStyle value);

    /**
     * Returns the value of the '<em><b>Conditional Styles</b></em>' containment reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.diagram.ConditionalInsideLabelStyle}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Conditional Styles</em>' containment reference list.
     * @model containment="true"
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getInsideLabelDescription_ConditionalStyles()
     */
    EList<ConditionalInsideLabelStyle> getConditionalStyles();

} // InsideLabelDescription
