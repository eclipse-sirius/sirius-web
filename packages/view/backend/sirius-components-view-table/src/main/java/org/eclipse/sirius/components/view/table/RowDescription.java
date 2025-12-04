/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
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
package org.eclipse.sirius.components.view.table;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Row Description</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.table.RowDescription#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.RowDescription#getSemanticCandidatesExpression <em>Semantic
 * Candidates Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.RowDescription#getHeaderLabelExpression <em>Header Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.RowDescription#getHeaderIconExpression <em>Header Icon
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.RowDescription#getHeaderIndexLabelExpression <em>Header Index
 * Label Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.RowDescription#getInitialHeightExpression <em>Initial Height
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.RowDescription#getIsResizableExpression <em>Is Resizable
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.RowDescription#getContextMenuEntries <em>Context Menu
 * Entries</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.RowDescription#getDepthLevelExpression <em>Depth Level
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.RowDescription#getHasChildrenExpression <em>Has Children
 * Expression</em>}</li>
 * </ul>
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.table.TablePackage#getRowDescription()
 */
public interface RowDescription extends EObject {

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Name</em>' attribute.
     * @model dataType="org.eclipse.sirius.components.view.Identifier"
     * @generated
     * @see #setName(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getRowDescription_Name()
     */
    String getName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.table.RowDescription#getName <em>Name</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Name</em>' attribute.
     * @generated
     * @see #getName()
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Semantic Candidates Expression</b></em>' attribute. The default value is
     * <code>""</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Semantic Candidates Expression</em>' attribute.
     * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setSemanticCandidatesExpression(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getRowDescription_SemanticCandidatesExpression()
     */
    String getSemanticCandidatesExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.table.RowDescription#getSemanticCandidatesExpression <em>Semantic
     * Candidates Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Semantic Candidates Expression</em>' attribute.
     * @generated
     * @see #getSemanticCandidatesExpression()
     */
    void setSemanticCandidatesExpression(String value);

    /**
     * Returns the value of the '<em><b>Header Label Expression</b></em>' attribute. The default value is
     * <code>""</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Header Label Expression</em>' attribute.
     * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setHeaderLabelExpression(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getRowDescription_HeaderLabelExpression()
     */
    String getHeaderLabelExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.table.RowDescription#getHeaderLabelExpression
     * <em>Header Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Header Label Expression</em>' attribute.
     * @generated
     * @see #getHeaderLabelExpression()
     */
    void setHeaderLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>Header Icon Expression</b></em>' attribute. The default value is
     * <code>""</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Header Icon Expression</em>' attribute.
     * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setHeaderIconExpression(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getRowDescription_HeaderIconExpression()
     */
    String getHeaderIconExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.table.RowDescription#getHeaderIconExpression
     * <em>Header Icon Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Header Icon Expression</em>' attribute.
     * @generated
     * @see #getHeaderIconExpression()
     */
    void setHeaderIconExpression(String value);

    /**
     * Returns the value of the '<em><b>Header Index Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Header Index Label Expression</em>' attribute.
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setHeaderIndexLabelExpression(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getRowDescription_HeaderIndexLabelExpression()
     */
    String getHeaderIndexLabelExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.table.RowDescription#getHeaderIndexLabelExpression <em>Header Index
     * Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Header Index Label Expression</em>' attribute.
     * @generated
     * @see #getHeaderIndexLabelExpression()
     */
    void setHeaderIndexLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>Initial Height Expression</b></em>' attribute. The default value is
     * <code>""</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Initial Height Expression</em>' attribute.
     * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setInitialHeightExpression(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getRowDescription_InitialHeightExpression()
     */
    String getInitialHeightExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.table.RowDescription#getInitialHeightExpression
     * <em>Initial Height Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Initial Height Expression</em>' attribute.
     * @generated
     * @see #getInitialHeightExpression()
     */
    void setInitialHeightExpression(String value);

    /**
     * Returns the value of the '<em><b>Is Resizable Expression</b></em>' attribute. The default value is
     * <code>""</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Is Resizable Expression</em>' attribute.
     * @model default="" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setIsResizableExpression(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getRowDescription_IsResizableExpression()
     */
    String getIsResizableExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.table.RowDescription#getIsResizableExpression
     * <em>Is Resizable Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Is Resizable Expression</em>' attribute.
     * @generated
     * @see #getIsResizableExpression()
     */
    void setIsResizableExpression(String value);

    /**
     * Returns the value of the '<em><b>Context Menu Entries</b></em>' containment reference list. The list contents are
     * of type {@link org.eclipse.sirius.components.view.table.RowContextMenuEntry}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Context Menu Entries</em>' containment reference list.
     * @model containment="true"
     * @generated
     * @see org.eclipse.sirius.components.view.table.TablePackage#getRowDescription_ContextMenuEntries()
     */
    EList<RowContextMenuEntry> getContextMenuEntries();

    /**
     * Returns the value of the '<em><b>Depth Level Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Depth Level Expression</em>' attribute.
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setDepthLevelExpression(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getRowDescription_DepthLevelExpression()
     */
    String getDepthLevelExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.table.RowDescription#getDepthLevelExpression
     * <em>Depth Level Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Depth Level Expression</em>' attribute.
     * @generated
     * @see #getDepthLevelExpression()
     */
    void setDepthLevelExpression(String value);

    /**
     * Returns the value of the '<em><b>Has Children Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Has Children Expression</em>' attribute.
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setHasChildrenExpression(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getRowDescription_HasChildrenExpression()
     */
    String getHasChildrenExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.table.RowDescription#getHasChildrenExpression
     * <em>Has Children Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Has Children Expression</em>' attribute.
     * @generated
     * @see #getHasChildrenExpression()
     */
    void setHasChildrenExpression(String value);

} // RowDescription
