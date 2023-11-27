/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
     * The number of structural features of the '<em>Lane Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int LANE_DESCRIPTION_FEATURE_COUNT = 4;

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
     * The number of structural features of the '<em>Card Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CARD_DESCRIPTION_FEATURE_COUNT = 4;

    /**
     * The number of operations of the '<em>Card Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int CARD_DESCRIPTION_OPERATION_COUNT = 0;

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

    }

} // DeckPackage
