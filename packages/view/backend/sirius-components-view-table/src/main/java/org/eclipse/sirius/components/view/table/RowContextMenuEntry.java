/*******************************************************************************
 * Copyright (c) 2025, 2025 Obeo.
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
import org.eclipse.sirius.components.view.Operation;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Row Context Menu Entry</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.table.RowContextMenuEntry#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.RowContextMenuEntry#getLabelExpression <em>Label
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.RowContextMenuEntry#getIconURLExpression <em>Icon URL
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.RowContextMenuEntry#getPreconditionExpression <em>Precondition
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.table.RowContextMenuEntry#getBody <em>Body</em>}</li>
 * </ul>
 *
 * @model
 * @generated
 * @see org.eclipse.sirius.components.view.table.TablePackage#getRowContextMenuEntry()
 */
public interface RowContextMenuEntry extends EObject {

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Name</em>' attribute.
     * @model dataType="org.eclipse.sirius.components.view.Identifier" required="true"
     * @generated
     * @see #setName(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getRowContextMenuEntry_Name()
     */
    String getName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.table.RowContextMenuEntry#getName
     * <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Name</em>' attribute.
     * @generated
     * @see #getName()
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Label Expression</em>' attribute.
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setLabelExpression(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getRowContextMenuEntry_LabelExpression()
     */
    String getLabelExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.table.RowContextMenuEntry#getLabelExpression
     * <em>Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Label Expression</em>' attribute.
     * @generated
     * @see #getLabelExpression()
     */
    void setLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>Icon URL Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Icon URL Expression</em>' attribute.
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setIconURLExpression(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getRowContextMenuEntry_IconURLExpression()
     */
    String getIconURLExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.table.RowContextMenuEntry#getIconURLExpression
     * <em>Icon URL Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Icon URL Expression</em>' attribute.
     * @generated
     * @see #getIconURLExpression()
     */
    void setIconURLExpression(String value);

    /**
     * Returns the value of the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Precondition Expression</em>' attribute.
     * @model dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     * @see #setPreconditionExpression(String)
     * @see org.eclipse.sirius.components.view.table.TablePackage#getRowContextMenuEntry_PreconditionExpression()
     */
    String getPreconditionExpression();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.table.RowContextMenuEntry#getPreconditionExpression <em>Precondition
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *         the new value of the '<em>Precondition Expression</em>' attribute.
     * @generated
     * @see #getPreconditionExpression()
     */
    void setPreconditionExpression(String value);

    /**
     * Returns the value of the '<em><b>Body</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.Operation}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Body</em>' containment reference list.
     * @model containment="true"
     * @generated
     * @see org.eclipse.sirius.components.view.table.TablePackage#getRowContextMenuEntry_Body()
     */
    EList<Operation> getBody();

} // RowContextMenuEntry
