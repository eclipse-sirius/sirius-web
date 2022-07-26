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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Widget Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.WidgetDescription#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.WidgetDescription#getLabelExpression <em>Label Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.WidgetDescription#getValueExpression <em>Value Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getWidgetDescription()
 * @model abstract="true"
 * @generated
 */
public interface WidgetDescription extends EObject {
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getWidgetDescription_Name()
     * @model
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.WidgetDescription#getName <em>Name</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Label Expression</em>' attribute.
     * @see #setLabelExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getWidgetDescription_LabelExpression()
     * @model
     * @generated
     */
    String getLabelExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.WidgetDescription#getLabelExpression <em>Label
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Label Expression</em>' attribute.
     * @see #getLabelExpression()
     * @generated
     */
    void setLabelExpression(String value);

} // WidgetDescription
