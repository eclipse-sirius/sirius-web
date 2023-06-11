/**
 * Copyright (c) 2021, 2023 Obeo.
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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>DValidation Message Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.DValidationMessageDescription#getPreCondition <em>Pre
 * Condition</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.DValidationMessageDescription#getMessageExpression <em>Message
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.DValidationMessageDescription#getSeverity <em>Severity</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.DValidationMessageDescription#isBlocksApplyDialog <em>Blocks Apply
 * Dialog</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getDValidationMessageDescription()
 * @model
 * @generated
 */
public interface DValidationMessageDescription extends EObject {
    /**
     * Returns the value of the '<em><b>Pre Condition</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Pre Condition</em>' attribute.
     * @see #setPreCondition(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getDValidationMessageDescription_PreCondition()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getPreCondition();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.DValidationMessageDescription#getPreCondition
     * <em>Pre Condition</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Pre Condition</em>' attribute.
     * @see #getPreCondition()
     * @generated
     */
    void setPreCondition(String value);

    /**
     * Returns the value of the '<em><b>Message Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Message Expression</em>' attribute.
     * @see #setMessageExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getDValidationMessageDescription_MessageExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getMessageExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.DValidationMessageDescription#getMessageExpression <em>Message
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Message Expression</em>' attribute.
     * @see #getMessageExpression()
     * @generated
     */
    void setMessageExpression(String value);

    /**
     * Returns the value of the '<em><b>Severity</b></em>' attribute. The literals are from the enumeration
     * {@link org.eclipse.sirius.components.view.DValidationMessageSeverity}. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Severity</em>' attribute.
     * @see org.eclipse.sirius.components.view.DValidationMessageSeverity
     * @see #setSeverity(DValidationMessageSeverity)
     * @see org.eclipse.sirius.components.view.ViewPackage#getDValidationMessageDescription_Severity()
     * @model
     * @generated
     */
    DValidationMessageSeverity getSeverity();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.DValidationMessageDescription#getSeverity
     * <em>Severity</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Severity</em>' attribute.
     * @see org.eclipse.sirius.components.view.DValidationMessageSeverity
     * @see #getSeverity()
     * @generated
     */
    void setSeverity(DValidationMessageSeverity value);

    /**
     * Returns the value of the '<em><b>Blocks Apply Dialog</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Blocks Apply Dialog</em>' attribute.
     * @see #setBlocksApplyDialog(boolean)
     * @see org.eclipse.sirius.components.view.ViewPackage#getDValidationMessageDescription_BlocksApplyDialog()
     * @model
     * @generated
     */
    boolean isBlocksApplyDialog();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.DValidationMessageDescription#isBlocksApplyDialog <em>Blocks Apply
     * Dialog</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Blocks Apply Dialog</em>' attribute.
     * @see #isBlocksApplyDialog()
     * @generated
     */
    void setBlocksApplyDialog(boolean value);

} // DValidationMessageDescription
