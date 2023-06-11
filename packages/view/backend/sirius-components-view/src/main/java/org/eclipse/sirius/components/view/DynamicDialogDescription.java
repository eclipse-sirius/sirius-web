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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Dynamic Dialog Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.DynamicDialogDescription#getId <em>Id</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.DynamicDialogDescription#getTitleExpression <em>Title
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.DynamicDialogDescription#getDescriptionExpression <em>Description
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.DynamicDialogDescription#getWidgetDescriptions <em>Widget
 * Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.DynamicDialogDescription#getIsValidExpression <em>Is Valid
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.DynamicDialogDescription#getBody <em>Body</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.DynamicDialogDescription#getValidationMessages <em>Validation
 * Messages</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getDynamicDialogDescription()
 * @model
 * @generated
 */
public interface DynamicDialogDescription extends EObject {
    /**
     * Returns the value of the '<em><b>Id</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Id</em>' attribute.
     * @see #setId(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getDynamicDialogDescription_Id()
     * @model dataType="org.eclipse.sirius.components.view.Identifier"
     * @generated
     */
    String getId();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.DynamicDialogDescription#getId <em>Id</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(String value);

    /**
     * Returns the value of the '<em><b>Title Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Title Expression</em>' attribute.
     * @see #setTitleExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getDynamicDialogDescription_TitleExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getTitleExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.DynamicDialogDescription#getTitleExpression
     * <em>Title Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Title Expression</em>' attribute.
     * @see #getTitleExpression()
     * @generated
     */
    void setTitleExpression(String value);

    /**
     * Returns the value of the '<em><b>Description Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Description Expression</em>' attribute.
     * @see #setDescriptionExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getDynamicDialogDescription_DescriptionExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getDescriptionExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.DynamicDialogDescription#getDescriptionExpression <em>Description
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Description Expression</em>' attribute.
     * @see #getDescriptionExpression()
     * @generated
     */
    void setDescriptionExpression(String value);

    /**
     * Returns the value of the '<em><b>Widget Descriptions</b></em>' containment reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.DWidgetDescription}. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Widget Descriptions</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getDynamicDialogDescription_WidgetDescriptions()
     * @model containment="true"
     * @generated
     */
    EList<DWidgetDescription> getWidgetDescriptions();

    /**
     * Returns the value of the '<em><b>Is Valid Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Is Valid Expression</em>' attribute.
     * @see #setIsValidExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getDynamicDialogDescription_IsValidExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getIsValidExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.DynamicDialogDescription#getIsValidExpression
     * <em>Is Valid Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Is Valid Expression</em>' attribute.
     * @see #getIsValidExpression()
     * @generated
     */
    void setIsValidExpression(String value);

    /**
     * Returns the value of the '<em><b>Body</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.Operation}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Body</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getDynamicDialogDescription_Body()
     * @model containment="true"
     * @generated
     */
    EList<Operation> getBody();

    /**
     * Returns the value of the '<em><b>Validation Messages</b></em>' containment reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.DValidationMessageDescription}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Validation Messages</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getDynamicDialogDescription_ValidationMessages()
     * @model containment="true"
     * @generated
     */
    EList<DValidationMessageDescription> getValidationMessages();

} // DynamicDialogDescription
