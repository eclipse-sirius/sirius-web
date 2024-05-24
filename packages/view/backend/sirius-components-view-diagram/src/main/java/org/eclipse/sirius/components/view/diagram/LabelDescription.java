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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Label Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.LabelDescription#getLabelExpression <em>Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.LabelDescription#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.LabelDescription#getConditionalStyles <em>Conditional
 * Styles</em>}</li>
 * </ul>
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getLabelDescription()
 */
public interface LabelDescription extends EObject {

    /**
     * Returns the value of the '<em><b>Label Expression</b></em>' attribute. The default value is
     * <code>"aql:self.name"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Label Expression</em>' attribute.
     * @model default="aql:self.name" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setLabelExpression(String)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getLabelDescription_LabelExpression()
     */
    String getLabelExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.LabelDescription#getLabelExpression
     * <em>Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Label Expression</em>' attribute.
     * @generated
     * @see #getLabelExpression()
     */
    void setLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>Overflow Strategy</b></em>' attribute. The literals are from the enumeration
     * {@link org.eclipse.sirius.components.view.diagram.LabelOverflowStrategy}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Overflow Strategy</em>' attribute.
     * @model required="true"
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.LabelOverflowStrategy
     * @see #setOverflowStrategy(LabelOverflowStrategy)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getLabelDescription_OverflowStrategy()
     */
    LabelOverflowStrategy getOverflowStrategy();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.LabelDescription#getOverflowStrategy
     * <em>Overflow Strategy</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Overflow Strategy</em>' attribute.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.LabelOverflowStrategy
     * @see #getOverflowStrategy()
     */
    void setOverflowStrategy(LabelOverflowStrategy value);

    /**
     * Returns the value of the '<em><b>Text Align</b></em>' attribute. The literals are from the enumeration
     * {@link org.eclipse.sirius.components.view.diagram.LabelTextAlign}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Text Align</em>' attribute.
     * @model required="true"
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.LabelTextAlign
     * @see #setTextAlign(LabelTextAlign)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getLabelDescription_TextAlign()
     */
    LabelTextAlign getTextAlign();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.diagram.LabelDescription#getTextAlign <em>Text
     * Align</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Text Align</em>' attribute.
     * @generated
     * @see org.eclipse.sirius.components.view.diagram.LabelTextAlign
     * @see #getTextAlign()
     */
    void setTextAlign(LabelTextAlign value);

} // LabelDescription
