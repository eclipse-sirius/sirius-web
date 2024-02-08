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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Outside Label Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.OutsideLabelDescription#getPosition <em>Position</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.OutsideLabelDescription#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.OutsideLabelDescription#getConditionalStyles <em>Conditional
 * Styles</em>}</li>
 * </ul>
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getOutsideLabelDescription()
 */
public interface OutsideLabelDescription extends LabelDescription {

    /**
     * Returns the value of the '<em><b>Position</b></em>' attribute. The literals are from the enumeration
     * {@link org.eclipse.sirius.components.view.diagram.OutsideLabelPosition}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Position</em>' attribute.
     * @model required="true"
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.OutsideLabelPosition
     * @see #setPosition(OutsideLabelPosition)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getOutsideLabelDescription_Position()
     */
    OutsideLabelPosition getPosition();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.OutsideLabelDescription#getPosition
     * <em>Position</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Position</em>' attribute.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.OutsideLabelPosition
     * @see #getPosition()
     */
    void setPosition(OutsideLabelPosition value);

    /**
     * Returns the value of the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Style</em>' containment reference.
     * @model containment="true"
     * @generated
     * @see #setStyle(OutsideLabelStyle)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getOutsideLabelDescription_Style()
     */
    OutsideLabelStyle getStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.OutsideLabelDescription#getStyle
     * <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Style</em>' containment reference.
     * @generated
     * @see #getStyle()
     */
    void setStyle(OutsideLabelStyle value);

    /**
     * Returns the value of the '<em><b>Conditional Styles</b></em>' containment reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.diagram.ConditionalOutsideLabelStyle}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Conditional Styles</em>' containment reference list.
     * @model containment="true"
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getOutsideLabelDescription_ConditionalStyles()
     */
    EList<ConditionalOutsideLabelStyle> getConditionalStyles();

} // OutsideLabelDescription
