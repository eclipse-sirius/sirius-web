/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Widget Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link ReferenceWidgetDescription#getReferenceOwnerExpression
 * <em>Reference Owner Expression</em>}</li>
 * <li>{@link ReferenceWidgetDescription#getReferenceNameExpression
 * <em>Reference Name Expression</em>}</li>
 * </ul>
 *
 * @model
 * @generated
 * @see ReferencePackage#getReferenceWidgetDescription()
 */
public interface ReferenceWidgetDescription extends WidgetDescription {

    /**
     * Returns the value of the '<em><b>Reference Owner Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Reference Owner Expression</em>' attribute.
     * @see #setReferenceOwnerExpression(String)
     * @see ReferencePackage#getReferenceWidgetDescription_ReferenceOwnerExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
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
     * @see #setReferenceNameExpression(String)
     * @see ReferencePackage#getReferenceWidgetDescription_ReferenceNameExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression" required="true"
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
     * @see ReferencePackage#getReferenceWidgetDescription_Body()
     * @model containment="true"
     * @generated
     */
    EList<Operation> getBody();

    /**
     * Returns the value of the '<em><b>Style</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Style</em>' containment reference.
     * @model containment="true"
     * @generated
     * @see #setStyle(ReferenceWidgetDescriptionStyle)
     * @see ReferencePackage#getReferenceWidgetDescription_Style()
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
     * @generated
     * @see ReferencePackage#getReferenceWidgetDescription_ConditionalStyles()
     */
    EList<ConditionalReferenceWidgetDescriptionStyle> getConditionalStyles();

    /**
     * Returns the value of the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Is Enabled Expression</em>' attribute.
     * @see #setIsEnabledExpression(String)
     * @see ReferencePackage#getReferenceWidgetDescription_IsEnabledExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
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
