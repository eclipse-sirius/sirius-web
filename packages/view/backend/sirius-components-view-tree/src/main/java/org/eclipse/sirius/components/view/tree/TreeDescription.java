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

import org.eclipse.emf.common.util.EList;
import org.eclipse.sirius.components.view.RepresentationDescription;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Description</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.tree.TreeDescription#getKindExpression <em>Kind Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.TreeDescription#getTreeItemIconExpression <em>Tree Item Icon
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.TreeDescription#getTreeItemIdExpression <em>Tree Item Id
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.TreeDescription#getTreeItemObjectExpression <em>Tree Item Object
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.TreeDescription#getElementsExpression <em>Elements
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.TreeDescription#getHasChildrenExpression <em>Has Children
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.TreeDescription#getChildrenExpression <em>Children
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.TreeDescription#getParentExpression <em>Parent
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.TreeDescription#getEditableExpression <em>Editable
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.TreeDescription#getSelectableExpression <em>Selectable
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.TreeDescription#getDeletableExpression <em>Deletable
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.TreeDescription#getTreeItemLabelDescriptions <em>Tree Item Label
 * Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.TreeDescription#getContextMenuEntries <em>Context Menu
 * Entries</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.tree.TreePackage#getTreeDescription()
 * @model
 * @generated
 */
public interface TreeDescription extends RepresentationDescription {

    /**
     * Returns the value of the '<em><b>Kind Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Kind Expression</em>' attribute.
     * @see #setKindExpression(String)
     * @see org.eclipse.sirius.components.view.tree.TreePackage#getTreeDescription_KindExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getKindExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.tree.TreeDescription#getKindExpression <em>Kind
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Kind Expression</em>' attribute.
     * @see #getKindExpression()
     * @generated
     */
    void setKindExpression(String value);

    /**
     * Returns the value of the '<em><b>Tree Item Icon Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Tree Item Icon Expression</em>' attribute.
     * @see #setTreeItemIconExpression(String)
     * @see org.eclipse.sirius.components.view.tree.TreePackage#getTreeDescription_TreeItemIconExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getTreeItemIconExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.tree.TreeDescription#getTreeItemIconExpression
     * <em>Tree Item Icon Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Tree Item Icon Expression</em>' attribute.
     * @see #getTreeItemIconExpression()
     * @generated
     */
    void setTreeItemIconExpression(String value);

    /**
     * Returns the value of the '<em><b>Tree Item Id Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Tree Item Id Expression</em>' attribute.
     * @see #setTreeItemIdExpression(String)
     * @see org.eclipse.sirius.components.view.tree.TreePackage#getTreeDescription_TreeItemIdExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getTreeItemIdExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.tree.TreeDescription#getTreeItemIdExpression
     * <em>Tree Item Id Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Tree Item Id Expression</em>' attribute.
     * @see #getTreeItemIdExpression()
     * @generated
     */
    void setTreeItemIdExpression(String value);

    /**
     * Returns the value of the '<em><b>Tree Item Object Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Tree Item Object Expression</em>' attribute.
     * @see #setTreeItemObjectExpression(String)
     * @see org.eclipse.sirius.components.view.tree.TreePackage#getTreeDescription_TreeItemObjectExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getTreeItemObjectExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.tree.TreeDescription#getTreeItemObjectExpression
     * <em>Tree Item Object Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Tree Item Object Expression</em>' attribute.
     * @see #getTreeItemObjectExpression()
     * @generated
     */
    void setTreeItemObjectExpression(String value);

    /**
     * Returns the value of the '<em><b>Elements Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Elements Expression</em>' attribute.
     * @see #setElementsExpression(String)
     * @see org.eclipse.sirius.components.view.tree.TreePackage#getTreeDescription_ElementsExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getElementsExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.tree.TreeDescription#getElementsExpression
     * <em>Elements Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Elements Expression</em>' attribute.
     * @see #getElementsExpression()
     * @generated
     */
    void setElementsExpression(String value);

    /**
     * Returns the value of the '<em><b>Has Children Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Has Children Expression</em>' attribute.
     * @see #setHasChildrenExpression(String)
     * @see org.eclipse.sirius.components.view.tree.TreePackage#getTreeDescription_HasChildrenExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getHasChildrenExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.tree.TreeDescription#getHasChildrenExpression
     * <em>Has Children Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Has Children Expression</em>' attribute.
     * @see #getHasChildrenExpression()
     * @generated
     */
    void setHasChildrenExpression(String value);

    /**
     * Returns the value of the '<em><b>Children Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Children Expression</em>' attribute.
     * @see #setChildrenExpression(String)
     * @see org.eclipse.sirius.components.view.tree.TreePackage#getTreeDescription_ChildrenExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getChildrenExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.tree.TreeDescription#getChildrenExpression
     * <em>Children Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Children Expression</em>' attribute.
     * @see #getChildrenExpression()
     * @generated
     */
    void setChildrenExpression(String value);

    /**
     * Returns the value of the '<em><b>Parent Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Parent Expression</em>' attribute.
     * @see #setParentExpression(String)
     * @see org.eclipse.sirius.components.view.tree.TreePackage#getTreeDescription_ParentExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getParentExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.tree.TreeDescription#getParentExpression
     * <em>Parent Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Parent Expression</em>' attribute.
     * @see #getParentExpression()
     * @generated
     */
    void setParentExpression(String value);

    /**
     * Returns the value of the '<em><b>Editable Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Editable Expression</em>' attribute.
     * @see #setEditableExpression(String)
     * @see org.eclipse.sirius.components.view.tree.TreePackage#getTreeDescription_EditableExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getEditableExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.tree.TreeDescription#getEditableExpression
     * <em>Editable Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Editable Expression</em>' attribute.
     * @see #getEditableExpression()
     * @generated
     */
    void setEditableExpression(String value);

    /**
     * Returns the value of the '<em><b>Selectable Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Selectable Expression</em>' attribute.
     * @see #setSelectableExpression(String)
     * @see org.eclipse.sirius.components.view.tree.TreePackage#getTreeDescription_SelectableExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getSelectableExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.tree.TreeDescription#getSelectableExpression
     * <em>Selectable Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Selectable Expression</em>' attribute.
     * @see #getSelectableExpression()
     * @generated
     */
    void setSelectableExpression(String value);

    /**
     * Returns the value of the '<em><b>Deletable Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Deletable Expression</em>' attribute.
     * @see #setDeletableExpression(String)
     * @see org.eclipse.sirius.components.view.tree.TreePackage#getTreeDescription_DeletableExpression()
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getDeletableExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.tree.TreeDescription#getDeletableExpression
     * <em>Deletable Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Deletable Expression</em>' attribute.
     * @see #getDeletableExpression()
     * @generated
     */
    void setDeletableExpression(String value);

    /**
     * Returns the value of the '<em><b>Tree Item Label Descriptions</b></em>' containment reference list. The list
     * contents are of type {@link org.eclipse.sirius.components.view.tree.TreeItemLabelDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Tree Item Label Descriptions</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.tree.TreePackage#getTreeDescription_TreeItemLabelDescriptions()
     * @model containment="true" keys="name"
     * @generated
     */
    EList<TreeItemLabelDescription> getTreeItemLabelDescriptions();

    /**
     * Returns the value of the '<em><b>Context Menu Entries</b></em>' containment reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.tree.TreeItemContextMenuEntry}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Context Menu Entries</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.tree.TreePackage#getTreeDescription_ContextMenuEntries()
     * @model containment="true"
     * @generated
     */
    EList<TreeItemContextMenuEntry> getContextMenuEntries();

} // TreeDescription
