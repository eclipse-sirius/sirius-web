/**
 * Copyright (c) 2021, 2022 Obeo.
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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Link Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.LinkDescription#getValueExpression <em>Value Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.LinkDescription#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.LinkDescription#getConditionalStyles <em>Conditional Styles</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getLinkDescription()
 * @model
 * @generated
 */
public interface LinkDescription extends WidgetDescription {
    /**
     * Returns the value of the '<em><b>Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Value Expression</em>' attribute.
     * @see #setValueExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getLinkDescription_ValueExpression()
     * @model
     * @generated
     */
    String getValueExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.LinkDescription#getValueExpression <em>Value
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Value Expression</em>' attribute.
     * @see #getValueExpression()
     * @generated
     */
    void setValueExpression(String value);

    /**
     * Returns the value of the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Style</em>' containment reference.
     * @see #setStyle(LinkDescriptionStyle)
     * @see org.eclipse.sirius.components.view.ViewPackage#getLinkDescription_Style()
     * @model containment="true"
     * @generated
     */
    LinkDescriptionStyle getStyle();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.LinkDescription#getStyle <em>Style</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Style</em>' containment reference.
     * @see #getStyle()
     * @generated
     */
    void setStyle(LinkDescriptionStyle value);

    /**
     * Returns the value of the '<em><b>Conditional Styles</b></em>' containment reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.ConditionalLinkDescriptionStyle}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Conditional Styles</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getLinkDescription_ConditionalStyles()
     * @model containment="true"
     * @generated
     */
    EList<ConditionalLinkDescriptionStyle> getConditionalStyles();

} // LinkDescription
