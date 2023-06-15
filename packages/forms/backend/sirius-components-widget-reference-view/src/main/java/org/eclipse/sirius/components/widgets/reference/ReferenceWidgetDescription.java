/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.widgets.reference;

import org.eclipse.sirius.components.view.WidgetDescription;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Widget Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getReferenceOwnerExpression <em>Reference Owner Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getReferenceNameExpression <em>Reference Name Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.widgets.reference.ReferencePackage#getReferenceWidgetDescription()
 * @model
 * @generated
 */
public interface ReferenceWidgetDescription extends WidgetDescription {
    /**
     * Returns the value of the '<em><b>Reference Owner Expression</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the value of the '<em>Reference Owner Expression</em>' attribute.
     * @see #setReferenceOwnerExpression(String)
     * @see org.eclipse.sirius.components.widgets.reference.ReferencePackage#getReferenceWidgetDescription_ReferenceOwnerExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getReferenceOwnerExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getReferenceOwnerExpression <em>Reference Owner Expression</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Reference Owner Expression</em>' attribute.
     * @see #getReferenceOwnerExpression()
     * @generated
     */
    void setReferenceOwnerExpression(String value);

    /**
     * Returns the value of the '<em><b>Reference Name Expression</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the value of the '<em>Reference Name Expression</em>' attribute.
     * @see #setReferenceNameExpression(String)
     * @see org.eclipse.sirius.components.widgets.reference.ReferencePackage#getReferenceWidgetDescription_ReferenceNameExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression" required="true"
     * @generated
     */
    String getReferenceNameExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription#getReferenceNameExpression <em>Reference Name Expression</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Reference Name Expression</em>' attribute.
     * @see #getReferenceNameExpression()
     * @generated
     */
    void setReferenceNameExpression(String value);

} // ReferenceWidgetDescription
