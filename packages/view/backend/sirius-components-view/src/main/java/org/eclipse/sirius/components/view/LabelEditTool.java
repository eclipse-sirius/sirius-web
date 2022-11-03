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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Label Edit Tool</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.LabelEditTool#getInitialDirectEditLabelExpression <em>Initial Direct
 * Edit Label Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getLabelEditTool()
 * @model
 * @generated
 */
public interface LabelEditTool extends Tool {

    /**
     * Returns the value of the '<em><b>Initial Direct Edit Label Expression</b></em>' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Initial Direct Edit Label Expression</em>' attribute.
     * @see #setInitialDirectEditLabelExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getLabelEditTool_InitialDirectEditLabelExpression()
     * @model
     * @generated
     */
    String getInitialDirectEditLabelExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.LabelEditTool#getInitialDirectEditLabelExpression <em>Initial Direct
     * Edit Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Initial Direct Edit Label Expression</em>' attribute.
     * @see #getInitialDirectEditLabelExpression()
     * @generated
     */
    void setInitialDirectEditLabelExpression(String value);
} // LabelEditTool
