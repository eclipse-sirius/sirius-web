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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Group Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.GroupDescription#getToolbarActions <em>Toolbar Actions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.GroupDescription#getWidgets <em>Widgets</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getGroupDescription()
 * @model
 * @generated
 */
public interface GroupDescription extends EObject {
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getGroupDescription_Name()
     * @model dataType="org.eclipse.sirius.components.view.Identifier"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.GroupDescription#getName <em>Name</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Label Expression</em>' attribute.
     * @see #setLabelExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getGroupDescription_LabelExpression()
     * @model
     * @generated
     */
    String getLabelExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.GroupDescription#getLabelExpression <em>Label
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Label Expression</em>' attribute.
     * @see #getLabelExpression()
     * @generated
     */
    void setLabelExpression(String value);

    /**
     * Returns the value of the '<em><b>Display Mode</b></em>' attribute. The default value is <code>"LIST"</code>. The
     * literals are from the enumeration {@link org.eclipse.sirius.components.view.GroupDisplayMode}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Display Mode</em>' attribute.
     * @see org.eclipse.sirius.components.view.GroupDisplayMode
     * @see #setDisplayMode(GroupDisplayMode)
     * @see org.eclipse.sirius.components.view.ViewPackage#getGroupDescription_DisplayMode()
     * @model default="LIST" required="true"
     * @generated
     */
    GroupDisplayMode getDisplayMode();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.GroupDescription#getDisplayMode <em>Display
     * Mode</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Display Mode</em>' attribute.
     * @see org.eclipse.sirius.components.view.GroupDisplayMode
     * @see #getDisplayMode()
     * @generated
     */
    void setDisplayMode(GroupDisplayMode value);

    /**
     * Returns the value of the '<em><b>Semantic Candidates Expression</b></em>' attribute. The default value is
     * <code>"aql:self"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Semantic Candidates Expression</em>' attribute.
     * @see #setSemanticCandidatesExpression(String)
     * @see org.eclipse.sirius.components.view.ViewPackage#getGroupDescription_SemanticCandidatesExpression()
     * @model default="aql:self" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getSemanticCandidatesExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.GroupDescription#getSemanticCandidatesExpression
     * <em>Semantic Candidates Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Semantic Candidates Expression</em>' attribute.
     * @see #getSemanticCandidatesExpression()
     * @generated
     */
    void setSemanticCandidatesExpression(String value);

    /**
     * Returns the value of the '<em><b>Toolbar Actions</b></em>' containment reference list. The list contents are of
     * type {@link org.eclipse.sirius.components.view.ButtonDescription}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Toolbar Actions</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getGroupDescription_ToolbarActions()
     * @model containment="true"
     * @generated
     */
    EList<ButtonDescription> getToolbarActions();

    /**
     * Returns the value of the '<em><b>Widgets</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.WidgetDescription}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Widgets</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getGroupDescription_Widgets()
     * @model containment="true"
     * @generated
     */
    EList<WidgetDescription> getWidgets();

} // GroupDescription
