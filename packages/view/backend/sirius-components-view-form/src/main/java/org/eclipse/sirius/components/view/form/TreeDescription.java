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
     * Returns the value of the '<em><b>Child Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Child Expression</em>' attribute.
     * @see #setChildExpression(String)
     * @see org.eclipse.sirius.components.view.form.FormPackage#getTreeDescription_ChildExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getChildExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.form.TreeDescription#getChildExpression
     * <em>Child Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Child Expression</em>' attribute.
     * @see #getChildExpression()
     * @generated
     */
    void setChildExpression(String value);

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

} // TreeDescription
