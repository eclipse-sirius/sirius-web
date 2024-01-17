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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.deck.DeckFactory
 * @model kind="package"
 * @generated
 */
public interface DeckPackage extends EPackage {
    /**
     * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNAME = "deck";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_URI = "http://www.eclipse.org/sirius-web/deck";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_PREFIX = "deck";

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    DeckPackage eINSTANCE = org.eclipse.sirius.components.view.deck.impl.DeckPackageImpl.init();

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.deck.impl.DeckDescriptionImpl
     * <em>Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.deck.impl.DeckDescriptionImpl
     * @see org.eclipse.sirius.components.view.deck.impl.DeckPackageImpl#getDeckDescription()
     * @generated
     */
    int DECK_DESCRIPTION = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DECK_DESCRIPTION__NAME = ViewPackage.REPRESENTATION_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DECK_DESCRIPTION__DOMAIN_TYPE = ViewPackage.REPRESENTATION_DESCRIPTION__DOMAIN_TYPE;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DECK_DESCRIPTION__PRECONDITION_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Title Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DECK_DESCRIPTION__TITLE_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION__TITLE_EXPRESSION;

    /**
     * The feature id for the '<em><b>Lane Descriptions</b></em>' containment reference list. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DECK_DESCRIPTION__LANE_DESCRIPTIONS = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Background Color</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DECK_DESCRIPTION__BACKGROUND_COLOR = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int DECK_DESCRIPTION_FEATURE_COUNT = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The number of operations of the '<em>Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DECK_DESCRIPTION_OPERATION_COUNT = ViewPackage.REPRESENTATION_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.deck.impl.LaneDescriptionImpl <em>Lane
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.deck.impl.LaneDescriptionImpl
     * @see org.eclipse.sirius.components.view.deck.impl.DeckPackageImpl#getLaneDescription()
     * @generated
     */
    int LANE_DESCRIPTION = 1;

    /**
     * The feature id for the '<em><b>Semantic Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LANE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = 0;

    /**
     * The feature id for the '<em><b>Title Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LANE_DESCRIPTION__TITLE_EXPRESSION = 1;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int LANE_DESCRIPTION__LABEL_EXPRESSION = 2;

    /**
     * The feature id for the '<em><b>Owned Card Descriptions</b></em>' containment reference list. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LANE_DESCRIPTION__OWNED_CARD_DESCRIPTIONS = 3;

    /**
     * The feature id for the '<em><b>Edit Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LANE_DESCRIPTION__EDIT_TOOL = 4;

    /**
     * The feature id for the '<em><b>Create Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LANE_DESCRIPTION__CREATE_TOOL = 5;

    /**
     * The feature id for the '<em><b>Card Drop Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LANE_DESCRIPTION__CARD_DROP_TOOL = 6;

    /**
     * The number of structural features of the '<em>Lane Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LANE_DESCRIPTION_FEATURE_COUNT = 7;

    /**
     * The number of operations of the '<em>Lane Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LANE_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.deck.impl.CardDescriptionImpl <em>Card
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.deck.impl.CardDescriptionImpl
     * @see org.eclipse.sirius.components.view.deck.impl.DeckPackageImpl#getCardDescription()
     * @generated
     */
    int CARD_DESCRIPTION = 2;

    /**
     * The feature id for the '<em><b>Semantic Candidates Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CARD_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = 0;

    /**
     * The feature id for the '<em><b>Title Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CARD_DESCRIPTION__TITLE_EXPRESSION = 1;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int CARD_DESCRIPTION__LABEL_EXPRESSION = 2;

    /**
     * The feature id for the '<em><b>Description Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CARD_DESCRIPTION__DESCRIPTION_EXPRESSION = 3;

    /**
     * The feature id for the '<em><b>Edit Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CARD_DESCRIPTION__EDIT_TOOL = 4;

    /**
     * The feature id for the '<em><b>Delete Tool</b></em>' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CARD_DESCRIPTION__DELETE_TOOL = 5;

    /**
     * The number of structural features of the '<em>Card Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CARD_DESCRIPTION_FEATURE_COUNT = 6;

    /**
     * The number of operations of the '<em>Card Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CARD_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.deck.impl.DeckToolImpl <em>Tool</em>}'
     * class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.deck.impl.DeckToolImpl
     * @see org.eclipse.sirius.components.view.deck.impl.DeckPackageImpl#getDeckTool()
     * @generated
     */
    int DECK_TOOL = 3;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DECK_TOOL__NAME = 0;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DECK_TOOL__PRECONDITION_EXPRESSION = 1;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DECK_TOOL__BODY = 2;

    /**
     * The number of structural features of the '<em>Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DECK_TOOL_FEATURE_COUNT = 3;

    /**
     * The number of operations of the '<em>Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DECK_TOOL_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.deck.impl.CreateCardToolImpl <em>Create
     * Card Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.deck.impl.CreateCardToolImpl
     * @see org.eclipse.sirius.components.view.deck.impl.DeckPackageImpl#getCreateCardTool()
     * @generated
     */
    int CREATE_CARD_TOOL = 4;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_CARD_TOOL__NAME = DECK_TOOL__NAME;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_CARD_TOOL__PRECONDITION_EXPRESSION = DECK_TOOL__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_CARD_TOOL__BODY = DECK_TOOL__BODY;

    /**
     * The number of structural features of the '<em>Create Card Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_CARD_TOOL_FEATURE_COUNT = DECK_TOOL_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Create Card Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CREATE_CARD_TOOL_OPERATION_COUNT = DECK_TOOL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.deck.impl.EditCardToolImpl <em>Edit Card
     * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.deck.impl.EditCardToolImpl
     * @see org.eclipse.sirius.components.view.deck.impl.DeckPackageImpl#getEditCardTool()
     * @generated
     */
    int EDIT_CARD_TOOL = 5;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDIT_CARD_TOOL__NAME = DECK_TOOL__NAME;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDIT_CARD_TOOL__PRECONDITION_EXPRESSION = DECK_TOOL__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDIT_CARD_TOOL__BODY = DECK_TOOL__BODY;

    /**
     * The number of structural features of the '<em>Edit Card Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDIT_CARD_TOOL_FEATURE_COUNT = DECK_TOOL_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Edit Card Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDIT_CARD_TOOL_OPERATION_COUNT = DECK_TOOL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.deck.impl.DeleteCardToolImpl <em>Delete
     * Card Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.deck.impl.DeleteCardToolImpl
     * @see org.eclipse.sirius.components.view.deck.impl.DeckPackageImpl#getDeleteCardTool()
     * @generated
     */
    int DELETE_CARD_TOOL = 6;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_CARD_TOOL__NAME = DECK_TOOL__NAME;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_CARD_TOOL__PRECONDITION_EXPRESSION = DECK_TOOL__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_CARD_TOOL__BODY = DECK_TOOL__BODY;

    /**
     * The number of structural features of the '<em>Delete Card Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_CARD_TOOL_FEATURE_COUNT = DECK_TOOL_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Delete Card Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int DELETE_CARD_TOOL_OPERATION_COUNT = DECK_TOOL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.deck.impl.EditLaneToolImpl <em>Edit Lane
     * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.deck.impl.EditLaneToolImpl
     * @see org.eclipse.sirius.components.view.deck.impl.DeckPackageImpl#getEditLaneTool()
     * @generated
     */
    int EDIT_LANE_TOOL = 7;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDIT_LANE_TOOL__NAME = DECK_TOOL__NAME;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDIT_LANE_TOOL__PRECONDITION_EXPRESSION = DECK_TOOL__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDIT_LANE_TOOL__BODY = DECK_TOOL__BODY;

    /**
     * The number of structural features of the '<em>Edit Lane Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDIT_LANE_TOOL_FEATURE_COUNT = DECK_TOOL_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Edit Lane Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int EDIT_LANE_TOOL_OPERATION_COUNT = DECK_TOOL_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.deck.impl.CardDropToolImpl <em>Card Drop
     * Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.deck.impl.CardDropToolImpl
     * @see org.eclipse.sirius.components.view.deck.impl.DeckPackageImpl#getCardDropTool()
     * @generated
     */
    int CARD_DROP_TOOL = 8;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CARD_DROP_TOOL__NAME = DECK_TOOL__NAME;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CARD_DROP_TOOL__PRECONDITION_EXPRESSION = DECK_TOOL__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Body</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CARD_DROP_TOOL__BODY = DECK_TOOL__BODY;

    /**
     * The number of structural features of the '<em>Card Drop Tool</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CARD_DROP_TOOL_FEATURE_COUNT = DECK_TOOL_FEATURE_COUNT + 0;

    /**
     * The number of operations of the '<em>Card Drop Tool</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CARD_DROP_TOOL_OPERATION_COUNT = DECK_TOOL_OPERATION_COUNT + 0;

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.deck.DeckDescription
     * <em>Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Description</em>'.
     * @see org.eclipse.sirius.components.view.deck.DeckDescription
     * @generated
     */
    EClass getDeckDescription();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.deck.DeckDescription#getLaneDescriptions <em>Lane Descriptions</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Lane Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.deck.DeckDescription#getLaneDescriptions()
     * @see #getDeckDescription()
     * @generated
     */
    EReference getDeckDescription_LaneDescriptions();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.deck.DeckDescription#getBackgroundColor <em>Background Color</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Background Color</em>'.
     * @see org.eclipse.sirius.components.view.deck.DeckDescription#getBackgroundColor()
     * @see #getDeckDescription()
     * @generated
     */
    EReference getDeckDescription_BackgroundColor();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.deck.LaneDescription <em>Lane
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Lane Description</em>'.
     * @see org.eclipse.sirius.components.view.deck.LaneDescription
     * @generated
     */
    EClass getLaneDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.deck.LaneDescription#getSemanticCandidatesExpression <em>Semantic
     * Candidates Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Semantic Candidates Expression</em>'.
     * @see org.eclipse.sirius.components.view.deck.LaneDescription#getSemanticCandidatesExpression()
     * @see #getLaneDescription()
     * @generated
     */
    EAttribute getLaneDescription_SemanticCandidatesExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.deck.LaneDescription#getTitleExpression <em>Title Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Title Expression</em>'.
     * @see org.eclipse.sirius.components.view.deck.LaneDescription#getTitleExpression()
     * @see #getLaneDescription()
     * @generated
     */
    EAttribute getLaneDescription_TitleExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.deck.LaneDescription#getLabelExpression <em>Label Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.deck.LaneDescription#getLabelExpression()
     * @see #getLaneDescription()
     * @generated
     */
    EAttribute getLaneDescription_LabelExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.deck.LaneDescription#getOwnedCardDescriptions <em>Owned Card
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Owned Card Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.deck.LaneDescription#getOwnedCardDescriptions()
     * @see #getLaneDescription()
     * @generated
     */
    EReference getLaneDescription_OwnedCardDescriptions();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.deck.LaneDescription#getEditTool <em>Edit Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Edit Tool</em>'.
     * @see org.eclipse.sirius.components.view.deck.LaneDescription#getEditTool()
     * @see #getLaneDescription()
     * @generated
     */
    EReference getLaneDescription_EditTool();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.deck.LaneDescription#getCreateTool <em>Create Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Create Tool</em>'.
     * @see org.eclipse.sirius.components.view.deck.LaneDescription#getCreateTool()
     * @see #getLaneDescription()
     * @generated
     */
    EReference getLaneDescription_CreateTool();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.deck.LaneDescription#getCardDropTool <em>Card Drop Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Card Drop Tool</em>'.
     * @see org.eclipse.sirius.components.view.deck.LaneDescription#getCardDropTool()
     * @see #getLaneDescription()
     * @generated
     */
    EReference getLaneDescription_CardDropTool();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.deck.CardDescription <em>Card
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Card Description</em>'.
     * @see org.eclipse.sirius.components.view.deck.CardDescription
     * @generated
     */
    EClass getCardDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.deck.CardDescription#getSemanticCandidatesExpression <em>Semantic
     * Candidates Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Semantic Candidates Expression</em>'.
     * @see org.eclipse.sirius.components.view.deck.CardDescription#getSemanticCandidatesExpression()
     * @see #getCardDescription()
     * @generated
     */
    EAttribute getCardDescription_SemanticCandidatesExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.deck.CardDescription#getTitleExpression <em>Title Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Title Expression</em>'.
     * @see org.eclipse.sirius.components.view.deck.CardDescription#getTitleExpression()
     * @see #getCardDescription()
     * @generated
     */
    EAttribute getCardDescription_TitleExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.deck.CardDescription#getLabelExpression <em>Label Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.deck.CardDescription#getLabelExpression()
     * @see #getCardDescription()
     * @generated
     */
    EAttribute getCardDescription_LabelExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.deck.CardDescription#getDescriptionExpression <em>Description
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Description Expression</em>'.
     * @see org.eclipse.sirius.components.view.deck.CardDescription#getDescriptionExpression()
     * @see #getCardDescription()
     * @generated
     */
    EAttribute getCardDescription_DescriptionExpression();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.deck.CardDescription#getEditTool <em>Edit Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Edit Tool</em>'.
     * @see org.eclipse.sirius.components.view.deck.CardDescription#getEditTool()
     * @see #getCardDescription()
     * @generated
     */
    EReference getCardDescription_EditTool();

    /**
     * Returns the meta object for the containment reference
     * '{@link org.eclipse.sirius.components.view.deck.CardDescription#getDeleteTool <em>Delete Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference '<em>Delete Tool</em>'.
     * @see org.eclipse.sirius.components.view.deck.CardDescription#getDeleteTool()
     * @see #getCardDescription()
     * @generated
     */
    EReference getCardDescription_DeleteTool();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.deck.DeckTool <em>Tool</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Tool</em>'.
     * @see org.eclipse.sirius.components.view.deck.DeckTool
     * @generated
     */
    EClass getDeckTool();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.sirius.components.view.deck.DeckTool#getName
     * <em>Name</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.sirius.components.view.deck.DeckTool#getName()
     * @see #getDeckTool()
     * @generated
     */
    EAttribute getDeckTool_Name();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.deck.DeckTool#getPreconditionExpression <em>Precondition
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Precondition Expression</em>'.
     * @see org.eclipse.sirius.components.view.deck.DeckTool#getPreconditionExpression()
     * @see #getDeckTool()
     * @generated
     */
    EAttribute getDeckTool_PreconditionExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.deck.DeckTool#getBody <em>Body</em>}'. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Body</em>'.
     * @see org.eclipse.sirius.components.view.deck.DeckTool#getBody()
     * @see #getDeckTool()
     * @generated
     */
    EReference getDeckTool_Body();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.deck.CreateCardTool <em>Create Card
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Create Card Tool</em>'.
     * @see org.eclipse.sirius.components.view.deck.CreateCardTool
     * @generated
     */
    EClass getCreateCardTool();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.deck.EditCardTool <em>Edit Card
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Edit Card Tool</em>'.
     * @see org.eclipse.sirius.components.view.deck.EditCardTool
     * @generated
     */
    EClass getEditCardTool();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.deck.DeleteCardTool <em>Delete Card
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Delete Card Tool</em>'.
     * @see org.eclipse.sirius.components.view.deck.DeleteCardTool
     * @generated
     */
    EClass getDeleteCardTool();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.deck.EditLaneTool <em>Edit Lane
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Edit Lane Tool</em>'.
     * @see org.eclipse.sirius.components.view.deck.EditLaneTool
     * @generated
     */
    EClass getEditLaneTool();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.deck.CardDropTool <em>Card Drop
     * Tool</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Card Drop Tool</em>'.
     * @see org.eclipse.sirius.components.view.deck.CardDropTool
     * @generated
     */
    EClass getCardDropTool();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the factory that creates the instances of the model.
     * @generated
     */
    DeckFactory getDeckFactory();

    /**
     * <!-- begin-user-doc --> Defines literals for the meta objects that represent
     * <ul>
     * <li>each class,</li>
     * <li>each feature of each class,</li>
     * <li>each operation of each class,</li>
     * <li>each enum,</li>
     * <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     *
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.deck.impl.DeckDescriptionImpl
         * <em>Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.deck.impl.DeckDescriptionImpl
         * @see org.eclipse.sirius.components.view.deck.impl.DeckPackageImpl#getDeckDescription()
         * @generated
         */
        EClass DECK_DESCRIPTION = eINSTANCE.getDeckDescription();

        /**
         * The meta object literal for the '<em><b>Lane Descriptions</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DECK_DESCRIPTION__LANE_DESCRIPTIONS = eINSTANCE.getDeckDescription_LaneDescriptions();

        /**
         * The meta object literal for the '<em><b>Background Color</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DECK_DESCRIPTION__BACKGROUND_COLOR = eINSTANCE.getDeckDescription_BackgroundColor();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.deck.impl.LaneDescriptionImpl
         * <em>Lane Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.deck.impl.LaneDescriptionImpl
         * @see org.eclipse.sirius.components.view.deck.impl.DeckPackageImpl#getLaneDescription()
         * @generated
         */
        EClass LANE_DESCRIPTION = eINSTANCE.getLaneDescription();

        /**
         * The meta object literal for the '<em><b>Semantic Candidates Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LANE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = eINSTANCE.getLaneDescription_SemanticCandidatesExpression();

        /**
         * The meta object literal for the '<em><b>Title Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LANE_DESCRIPTION__TITLE_EXPRESSION = eINSTANCE.getLaneDescription_TitleExpression();

        /**
         * The meta object literal for the '<em><b>Label Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute LANE_DESCRIPTION__LABEL_EXPRESSION = eINSTANCE.getLaneDescription_LabelExpression();

        /**
         * The meta object literal for the '<em><b>Owned Card Descriptions</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference LANE_DESCRIPTION__OWNED_CARD_DESCRIPTIONS = eINSTANCE.getLaneDescription_OwnedCardDescriptions();

        /**
         * The meta object literal for the '<em><b>Edit Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference LANE_DESCRIPTION__EDIT_TOOL = eINSTANCE.getLaneDescription_EditTool();

        /**
         * The meta object literal for the '<em><b>Create Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference LANE_DESCRIPTION__CREATE_TOOL = eINSTANCE.getLaneDescription_CreateTool();

        /**
         * The meta object literal for the '<em><b>Card Drop Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference LANE_DESCRIPTION__CARD_DROP_TOOL = eINSTANCE.getLaneDescription_CardDropTool();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.deck.impl.CardDescriptionImpl
         * <em>Card Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.deck.impl.CardDescriptionImpl
         * @see org.eclipse.sirius.components.view.deck.impl.DeckPackageImpl#getCardDescription()
         * @generated
         */
        EClass CARD_DESCRIPTION = eINSTANCE.getCardDescription();

        /**
         * The meta object literal for the '<em><b>Semantic Candidates Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CARD_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION = eINSTANCE.getCardDescription_SemanticCandidatesExpression();

        /**
         * The meta object literal for the '<em><b>Title Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CARD_DESCRIPTION__TITLE_EXPRESSION = eINSTANCE.getCardDescription_TitleExpression();

        /**
         * The meta object literal for the '<em><b>Label Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CARD_DESCRIPTION__LABEL_EXPRESSION = eINSTANCE.getCardDescription_LabelExpression();

        /**
         * The meta object literal for the '<em><b>Description Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute CARD_DESCRIPTION__DESCRIPTION_EXPRESSION = eINSTANCE.getCardDescription_DescriptionExpression();

        /**
         * The meta object literal for the '<em><b>Edit Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CARD_DESCRIPTION__EDIT_TOOL = eINSTANCE.getCardDescription_EditTool();

        /**
         * The meta object literal for the '<em><b>Delete Tool</b></em>' containment reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference CARD_DESCRIPTION__DELETE_TOOL = eINSTANCE.getCardDescription_DeleteTool();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.deck.impl.DeckToolImpl
         * <em>Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.deck.impl.DeckToolImpl
         * @see org.eclipse.sirius.components.view.deck.impl.DeckPackageImpl#getDeckTool()
         * @generated
         */
        EClass DECK_TOOL = eINSTANCE.getDeckTool();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute DECK_TOOL__NAME = eINSTANCE.getDeckTool_Name();

        /**
         * The meta object literal for the '<em><b>Precondition Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute DECK_TOOL__PRECONDITION_EXPRESSION = eINSTANCE.getDeckTool_PreconditionExpression();

        /**
         * The meta object literal for the '<em><b>Body</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference DECK_TOOL__BODY = eINSTANCE.getDeckTool_Body();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.deck.impl.CreateCardToolImpl
         * <em>Create Card Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.deck.impl.CreateCardToolImpl
         * @see org.eclipse.sirius.components.view.deck.impl.DeckPackageImpl#getCreateCardTool()
         * @generated
         */
        EClass CREATE_CARD_TOOL = eINSTANCE.getCreateCardTool();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.deck.impl.EditCardToolImpl
         * <em>Edit Card Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.deck.impl.EditCardToolImpl
         * @see org.eclipse.sirius.components.view.deck.impl.DeckPackageImpl#getEditCardTool()
         * @generated
         */
        EClass EDIT_CARD_TOOL = eINSTANCE.getEditCardTool();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.deck.impl.DeleteCardToolImpl
         * <em>Delete Card Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.deck.impl.DeleteCardToolImpl
         * @see org.eclipse.sirius.components.view.deck.impl.DeckPackageImpl#getDeleteCardTool()
         * @generated
         */
        EClass DELETE_CARD_TOOL = eINSTANCE.getDeleteCardTool();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.deck.impl.EditLaneToolImpl
         * <em>Edit Lane Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.deck.impl.EditLaneToolImpl
         * @see org.eclipse.sirius.components.view.deck.impl.DeckPackageImpl#getEditLaneTool()
         * @generated
         */
        EClass EDIT_LANE_TOOL = eINSTANCE.getEditLaneTool();

        /**
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.deck.impl.CardDropToolImpl
         * <em>Card Drop Tool</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.deck.impl.CardDropToolImpl
         * @see org.eclipse.sirius.components.view.deck.impl.DeckPackageImpl#getCardDropTool()
         * @generated
         */
        EClass CARD_DROP_TOOL = eINSTANCE.getCardDropTool();

    }

} // DeckPackage
