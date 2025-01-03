/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.components.view.tree;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Fetch Tree Item Context Menu Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry#getUrlExression <em>Url
 * Exression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry#getKind <em>Kind</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry#getLabelExpression <em>Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry#getIconURLExpression <em>Icon URL
 * Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.tree.TreePackage#getFetchTreeItemContextMenuEntry()
 * @model
 * @generated
 */
public interface FetchTreeItemContextMenuEntry extends TreeItemContextMenuEntry {
    /**
     * Returns the value of the '<em><b>Url Exression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Url Exression</em>' attribute.
     * @see #setUrlExression(String)
     * @see org.eclipse.sirius.components.view.tree.TreePackage#getFetchTreeItemContextMenuEntry_UrlExression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getUrlExression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry#getUrlExression <em>Url
     * Exression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Url Exression</em>' attribute.
     * @see #getUrlExression()
     * @generated
     */
    void setUrlExression(String value);

    /**
     * Returns the value of the '<em><b>Kind</b></em>' attribute. The literals are from the enumeration
     * {@link org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntryKind}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Kind</em>' attribute.
     * @see org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntryKind
     * @see #setKind(FetchTreeItemContextMenuEntryKind)
     * @see org.eclipse.sirius.components.view.tree.TreePackage#getFetchTreeItemContextMenuEntry_Kind()
     * @model
     * @generated
     */
    FetchTreeItemContextMenuEntryKind getKind();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry#getKind
     * <em>Kind</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Kind</em>' attribute.
     * @see org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntryKind
     * @see #getKind()
     * @generated
     */
    void setKind(FetchTreeItemContextMenuEntryKind value);

    /**
     * Returns the value of the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Label Expression</em>' attribute.
     * @see #setLabelExpression(String)
     * @see org.eclipse.sirius.components.view.tree.TreePackage#getFetchTreeItemContextMenuEntry_LabelExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getLabelExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry#getLabelExpression <em>Label
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Label Expression</em>' attribute.
     * @see #getLabelExpression()
     * @generated
     */
    void setLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>Icon URL Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Icon URL Expression</em>' attribute.
     * @see #setIconURLExpression(String)
     * @see org.eclipse.sirius.components.view.tree.TreePackage#getFetchTreeItemContextMenuEntry_IconURLExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getIconURLExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry#getIconURLExpression <em>Icon URL
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Icon URL Expression</em>' attribute.
     * @see #getIconURLExpression()
     * @generated
     */
    void setIconURLExpression(String value);

} // FetchTreeItemContextMenuEntry
