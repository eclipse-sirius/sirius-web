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
package org.eclipse.sirius.components.view.deck;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Lane Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.deck.LaneDescription#getSemanticCandidatesExpression <em>Semantic
 * Candidates Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.LaneDescription#getLabelExpression <em>Label Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.LaneDescription#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.LaneDescription#getConditionalStyles <em>Conditional
 * Styles</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.LaneDescription#getOwnedCardDescriptions <em>Owned Card
 * Descriptions</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.deck.DeckPackage#getLaneDescription()
 * @model
 * @generated
 */
public interface LaneDescription extends DeckElementDescription {
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. The default value is
     * <code>"New Lane Description"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.eclipse.sirius.components.view.deck.DeckPackage#getLaneDescription_Name()
     * @model default="New Lane Description" dataType="org.eclipse.sirius.components.view.Identifier"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.deck.LaneDescription#getName <em>Name</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Owned Card Descriptions</b></em>' containment reference list. The list contents
     * are of type {@link org.eclipse.sirius.components.view.deck.CardDescription}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Owned Card Descriptions</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.deck.DeckPackage#getLaneDescription_OwnedCardDescriptions()
     * @model containment="true"
     * @generated
     */
    EList<CardDescription> getOwnedCardDescriptions();

    /**
     * Returns the value of the '<em><b>Edit Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Edit Tool</em>' containment reference.
     * @see #setEditTool(EditLaneTool)
     * @see org.eclipse.sirius.components.view.deck.DeckPackage#getLaneDescription_EditTool()
     * @model containment="true"
     * @generated
     */
    EditLaneTool getEditTool();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.deck.LaneDescription#getEditTool <em>Edit
     * Tool</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Edit Tool</em>' containment reference.
     * @see #getEditTool()
     * @generated
     */
    void setEditTool(EditLaneTool value);

    /**
     * Returns the value of the '<em><b>Create Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Create Tool</em>' containment reference.
     * @see #setCreateTool(CreateCardTool)
     * @see org.eclipse.sirius.components.view.deck.DeckPackage#getLaneDescription_CreateTool()
     * @model containment="true"
     * @generated
     */
    CreateCardTool getCreateTool();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.deck.LaneDescription#getCreateTool <em>Create
     * Tool</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Create Tool</em>' containment reference.
     * @see #getCreateTool()
     * @generated
     */
    void setCreateTool(CreateCardTool value);

    /**
     * Returns the value of the '<em><b>Card Drop Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Card Drop Tool</em>' containment reference.
     * @see #setCardDropTool(CardDropTool)
     * @see org.eclipse.sirius.components.view.deck.DeckPackage#getLaneDescription_CardDropTool()
     * @model containment="true"
     * @generated
     */
    CardDropTool getCardDropTool();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.deck.LaneDescription#getCardDropTool <em>Card
     * Drop Tool</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Card Drop Tool</em>' containment reference.
     * @see #getCardDropTool()
     * @generated
     */
    void setCardDropTool(CardDropTool value);

    /**
     * Returns the value of the '<em><b>Is Collapsible Expression</b></em>' attribute. The default value is
     * <code>"aql:true"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Is Collapsible Expression</em>' attribute.
     * @see #setIsCollapsibleExpression(String)
     * @see org.eclipse.sirius.components.view.deck.DeckPackage#getLaneDescription_IsCollapsibleExpression()
     * @model default="aql:true" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getIsCollapsibleExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.deck.LaneDescription#getIsCollapsibleExpression
     * <em>Is Collapsible Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Is Collapsible Expression</em>' attribute.
     * @see #getIsCollapsibleExpression()
     * @generated
     */
    void setIsCollapsibleExpression(String value);

} // LaneDescription
