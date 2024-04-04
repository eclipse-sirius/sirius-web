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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Card Description</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.deck.CardDescription#getSemanticCandidatesExpression <em>Semantic
 * Candidates Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.CardDescription#getLabelExpression <em>Label Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.CardDescription#getStyle <em>Style</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.deck.CardDescription#getConditionalStyles <em>Conditional
 * Styles</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.deck.DeckPackage#getCardDescription()
 * @model
 * @generated
 */
public interface CardDescription extends DeckElementDescription {
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. The default value is
     * <code>"New Card Description"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.eclipse.sirius.components.view.deck.DeckPackage#getCardDescription_Name()
     * @model default="New Card Description" dataType="org.eclipse.sirius.components.view.Identifier"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.deck.CardDescription#getName <em>Name</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Domain Type</em>' attribute.
     * @see #setDomainType(String)
     * @see org.eclipse.sirius.components.view.deck.DeckPackage#getCardDescription_DomainType()
     * @model dataType="org.eclipse.sirius.components.view.DomainType"
     * @generated
     */
    String getDomainType();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.deck.CardDescription#getDomainType <em>Domain
     * Type</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Domain Type</em>' attribute.
     * @see #getDomainType()
     * @generated
     */
    void setDomainType(String value);

    /**
     * Returns the value of the '<em><b>Description Expression</b></em>' attribute. The default value is
     * <code>"aql:self"</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Description Expression</em>' attribute.
     * @see #setDescriptionExpression(String)
     * @see org.eclipse.sirius.components.view.deck.DeckPackage#getCardDescription_DescriptionExpression()
     * @model default="aql:self" dataType="org.eclipse.sirius.components.view.InterpretedExpression"
     * @generated
     */
    String getDescriptionExpression();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.deck.CardDescription#getDescriptionExpression
     * <em>Description Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Description Expression</em>' attribute.
     * @see #getDescriptionExpression()
     * @generated
     */
    void setDescriptionExpression(String value);

    /**
     * Returns the value of the '<em><b>Edit Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Edit Tool</em>' containment reference.
     * @see #setEditTool(EditCardTool)
     * @see org.eclipse.sirius.components.view.deck.DeckPackage#getCardDescription_EditTool()
     * @model containment="true"
     * @generated
     */
    EditCardTool getEditTool();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.deck.CardDescription#getEditTool <em>Edit
     * Tool</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Edit Tool</em>' containment reference.
     * @see #getEditTool()
     * @generated
     */
    void setEditTool(EditCardTool value);

    /**
     * Returns the value of the '<em><b>Delete Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the value of the '<em>Delete Tool</em>' containment reference.
     * @see #setDeleteTool(DeleteCardTool)
     * @see org.eclipse.sirius.components.view.deck.DeckPackage#getCardDescription_DeleteTool()
     * @model containment="true"
     * @generated
     */
    DeleteCardTool getDeleteTool();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.deck.CardDescription#getDeleteTool <em>Delete
     * Tool</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Delete Tool</em>' containment reference.
     * @see #getDeleteTool()
     * @generated
     */
    void setDeleteTool(DeleteCardTool value);

} // CardDescription
