/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
package org.eclipse.sirius.components.view.form;

import org.eclipse.emf.common.util.EList;
import org.eclipse.sirius.components.view.Operation;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Tree Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.form.TreeDescription#getChildExpression <em>Child Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.TreeDescription#getTreeItemLabelExpression <em>Tree Item Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.form.TreeDescription#getIsTreeItemSelectableExpression <em>Is Tree Item
 * Selectable Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.form.FormPackage#getTreeDescription()
 * @model
 * @generated
 */
public interface TreeDescription extends WidgetDescription {
    /**
     * Returns the value of the '<em><b>Children Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Children Expression</em>' attribute.
     * @see #setChildrenExpression(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getTreeDescription_ChildrenExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getChildrenExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.TreeDescription#getChildrenExpression
     * <em>Children Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Children Expression</em>' attribute.
     * @see #getChildrenExpression()
     * @generated
     */
    void setChildrenExpression(String value);

    /**
     * Returns the value of the '<em><b>Tree Item Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Tree Item Label Expression</em>' attribute.
     * @see #setTreeItemLabelExpression(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getTreeDescription_TreeItemLabelExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getTreeItemLabelExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.TreeDescription#getTreeItemLabelExpression
     * <em>Tree Item Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Tree Item Label Expression</em>' attribute.
     * @see #getTreeItemLabelExpression()
     * @generated
     */
    void setTreeItemLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>Is Tree Item Selectable Expression</b></em>' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Is Tree Item Selectable Expression</em>' attribute.
     * @see #setIsTreeItemSelectableExpression(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getTreeDescription_IsTreeItemSelectableExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getIsTreeItemSelectableExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.form.TreeDescription#getIsTreeItemSelectableExpression <em>Is Tree
     * Item Selectable Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Is Tree Item Selectable Expression</em>' attribute.
     * @see #getIsTreeItemSelectableExpression()
     * @generated
     */
    void setIsTreeItemSelectableExpression(String value);

    /**
     * Returns the value of the '<em><b>Tree Item Begin Icon Expression</b></em>' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return the value of the '<em>Tree Item Begin Icon Expression</em>' attribute.
     * @see #setTreeItemBeginIconExpression(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getTreeDescription_TreeItemBeginIconExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getTreeItemBeginIconExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.form.TreeDescription#getTreeItemBeginIconExpression <em>Tree Item
     * Begin Icon Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Tree Item Begin Icon Expression</em>' attribute.
     * @see #getTreeItemBeginIconExpression()
     * @generated
     */
    void setTreeItemBeginIconExpression(String value);

    /**
     * Returns the value of the '<em><b>Tree Item End Icons Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Tree Item End Icons Expression</em>' attribute.
     * @see #setTreeItemEndIconsExpression(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getTreeDescription_TreeItemEndIconsExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getTreeItemEndIconsExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.form.TreeDescription#getTreeItemEndIconsExpression <em>Tree Item End
     * Icons Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Tree Item End Icons Expression</em>' attribute.
     * @see #getTreeItemEndIconsExpression()
     * @generated
     */
    void setTreeItemEndIconsExpression(String value);

    /**
     * Returns the value of the '<em><b>Is Checkable Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Is Checkable Expression</em>' attribute.
     * @see #setIsCheckableExpression(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getTreeDescription_IsCheckableExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getIsCheckableExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.TreeDescription#getIsCheckableExpression
     * <em>Is Checkable Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Is Checkable Expression</em>' attribute.
     * @see #getIsCheckableExpression()
     * @generated
     */
    void setIsCheckableExpression(String value);

    /**
     * Returns the value of the '<em><b>Checked Value Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Checked Value Expression</em>' attribute.
     * @see #setCheckedValueExpression(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getTreeDescription_CheckedValueExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getCheckedValueExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.TreeDescription#getCheckedValueExpression
     * <em>Checked Value Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Checked Value Expression</em>' attribute.
     * @see #getCheckedValueExpression()
     * @generated
     */
    void setCheckedValueExpression(String value);

    /**
     * Returns the value of the '<em><b>Is Enabled Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Is Enabled Expression</em>' attribute.
     * @see #setIsEnabledExpression(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getTreeDescription_IsEnabledExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getIsEnabledExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.TreeDescription#getIsEnabledExpression
     * <em>Is Enabled Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Is Enabled Expression</em>' attribute.
     * @see #getIsEnabledExpression()
     * @generated
     */
    void setIsEnabledExpression(String value);

    /**
     * Returns the value of the '<em><b>Body</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.Operation}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Body</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.form.FormPackage#getTreeDescription_Body()
     * @model containment="true"
     * @generated
     */
    EList<Operation> getBody();

} // TreeDescription
