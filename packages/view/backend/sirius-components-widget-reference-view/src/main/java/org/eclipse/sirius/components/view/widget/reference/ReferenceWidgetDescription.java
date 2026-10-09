/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
package org.eclipse.sirius.components.view.widget.reference;

import org.eclipse.emf.common.util.EList;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.form.WidgetDescription;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Widget Description</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link ReferenceWidgetDescription#getReferenceOwnerExpression
 * <em>Reference Owner Expression</em>}</li>
 * <li>{@link ReferenceWidgetDescription#getReferenceNameExpression
 * <em>Reference Name Expression</em>}</li>
 * <li>{@link ReferenceWidgetDescription#getClearButton <em>Clear Button</em>}</li>
 * <li>{@link ReferenceWidgetDescription#getAddBody <em>Add Body</em>}</li>
 * </ul>
 *
 * @model
 * @see ReferencePackage#getReferenceWidgetDescription()
 * @generated
 */
public interface ReferenceWidgetDescription extends WidgetDescription {

    /**
     * Returns the value of the '<em><b>Reference Owner Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Reference Owner Expression</em>' attribute.
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @see #setReferenceOwnerExpression(String)
     * @see ReferencePackage#getReferenceWidgetDescription_ReferenceOwnerExpression()
     * @generated
     */
    String getReferenceOwnerExpression();

    /**
     * Sets the value of the
     * '{@link ReferenceWidgetDescription#getReferenceOwnerExpression
     * <em>Reference Owner Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Reference Owner Expression</em>' attribute.
     * @see #getReferenceOwnerExpression()
     * @generated
     */
    void setReferenceOwnerExpression(String value);

    /**
     * Returns the value of the '<em><b>Reference Name Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Reference Name Expression</em>' attribute.
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression" required="true"
     * @see #setReferenceNameExpression(String)
     * @see ReferencePackage#getReferenceWidgetDescription_ReferenceNameExpression()
     * @generated
     */
    String getReferenceNameExpression();

    /**
     * Sets the value of the
     * '{@link ReferenceWidgetDescription#getReferenceNameExpression
     * <em>Reference Name Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Reference Name Expression</em>' attribute.
     * @see #getReferenceNameExpression()
     * @generated
     */
    void setReferenceNameExpression(String value);

    /**
     * Returns the value of the '<em><b>Body</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.Operation}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Body</em>' containment reference list.
     * @model containment="true"
     * @see ReferencePackage#getReferenceWidgetDescription_Body()
     * @generated
     */
    EList<Operation> getBody();

    /**
     * Returns the value of the '<em><b>Clear Button</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Clear Button</em>' containment reference.
     * @model containment="true"
     * @see #setClearButton(ReferenceWidgetClearButtonDescription)
     * @see ReferencePackage#getReferenceWidgetDescription_ClearButton()
     * @generated
     */
    ReferenceWidgetClearButtonDescription getClearButton();

    /**
     * Sets the value of the '{@link ReferenceWidgetDescription#getClearButton <em>Clear Button</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Clear Button</em>' containment reference.
     * @see #getClearButton()
     * @generated
     */
    void setClearButton(ReferenceWidgetClearButtonDescription value);

    /**
     * Returns the value of the '<em><b>Add Body</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Add Body</em>' containment reference.
     * @model containment="true"
     * @see #setAddBody(ReferenceWidgetAddBody)
     * @see ReferencePackage#getReferenceWidgetDescription_AddBody()
     * @generated
     */
    ReferenceWidgetAddBody getAddBody();

    /**
     * Sets the value of the '{@link ReferenceWidgetDescription#getAddBody <em>Add Body</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Add Body</em>' containment reference.
     * @see #getAddBody()
     * @generated
     */
    void setAddBody(ReferenceWidgetAddBody value);

    /**
     * Returns the value of the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Style</em>' containment reference.
     * @model containment="true"
     * @see #setStyle(ReferenceWidgetDescriptionStyle)
     * @see ReferencePackage#getReferenceWidgetDescription_Style()
     * @generated
     */
    ReferenceWidgetDescriptionStyle getStyle();

    /**
     * Sets the value of the '{@link ReferenceWidgetDescription#getStyle
     * <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Style</em>' containment reference.
     * @see #getStyle()
     * @generated
     */
    void setStyle(ReferenceWidgetDescriptionStyle value);

    /**
     * Returns the value of the '<em><b>Conditional Styles</b></em>' containment reference list. The list contents are
     * of type {@link ConditionalReferenceWidgetDescriptionStyle}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Conditional Styles</em>' containment reference list.
     * @model containment="true"
     * @see ReferencePackage#getReferenceWidgetDescription_ConditionalStyles()
     * @generated
     */
    EList<ConditionalReferenceWidgetDescriptionStyle> getConditionalStyles();

    /**
     * Returns the value of the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Is Enabled Expression</em>' attribute.
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @see #setIsEnabledExpression(String)
     * @see ReferencePackage#getReferenceWidgetDescription_IsEnabledExpression()
     * @generated
     */
    String getIsEnabledExpression();

    /**
     * Sets the value of the
     * '{@link ReferenceWidgetDescription#getIsEnabledExpression <em>Is
     * Enabled Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Is Enabled Expression</em>' attribute.
     * @see #getIsEnabledExpression()
     * @generated
     */
    void setIsEnabledExpression(String value);

} // ReferenceWidgetDescription
