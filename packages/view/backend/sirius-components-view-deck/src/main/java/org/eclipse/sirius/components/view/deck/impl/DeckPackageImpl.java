/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
package org.eclipse.sirius.components.view.deck.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.deck.CardDescription;
import org.eclipse.sirius.components.view.deck.CardDropTool;
import org.eclipse.sirius.components.view.deck.ConditionalDeckDescriptionStyle;
import org.eclipse.sirius.components.view.deck.ConditionalDeckElementDescriptionStyle;
import org.eclipse.sirius.components.view.deck.CreateCardTool;
import org.eclipse.sirius.components.view.deck.DeckDescription;
import org.eclipse.sirius.components.view.deck.DeckDescriptionStyle;
import org.eclipse.sirius.components.view.deck.DeckElementDescription;
import org.eclipse.sirius.components.view.deck.DeckElementDescriptionStyle;
import org.eclipse.sirius.components.view.deck.DeckFactory;
import org.eclipse.sirius.components.view.deck.DeckPackage;
import org.eclipse.sirius.components.view.deck.DeckTool;
import org.eclipse.sirius.components.view.deck.DeleteCardTool;
import org.eclipse.sirius.components.view.deck.EditCardTool;
import org.eclipse.sirius.components.view.deck.EditLaneTool;
import org.eclipse.sirius.components.view.deck.LaneDescription;
import org.eclipse.sirius.components.view.deck.LaneDropTool;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * @generated
 */
public class DeckPackageImpl extends EPackageImpl implements DeckPackage {
    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass deckDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass laneDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass cardDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass deckToolEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass createCardToolEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass editCardToolEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass deleteCardToolEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass editLaneToolEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass cardDropToolEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass laneDropToolEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass deckElementDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass deckDescriptionStyleEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass conditionalDeckDescriptionStyleEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass deckElementDescriptionStyleEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass conditionalDeckElementDescriptionStyleEClass = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
     * EPackage.Registry} by the package package URI value.
     * <p>
     * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
     * performs initialization of the package, or returns the registered package, if one already exists. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.eclipse.sirius.components.view.deck.DeckPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private DeckPackageImpl() {
		super(eNS_URI, DeckFactory.eINSTANCE);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private static boolean isInited = false;

    /**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link DeckPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
    public static DeckPackage init() {
		if (isInited) return (DeckPackage)EPackage.Registry.INSTANCE.getEPackage(DeckPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredDeckPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		DeckPackageImpl theDeckPackage = registeredDeckPackage instanceof DeckPackageImpl ? (DeckPackageImpl)registeredDeckPackage : new DeckPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		ViewPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theDeckPackage.createPackageContents();

		// Initialize created meta-data
		theDeckPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDeckPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DeckPackage.eNS_URI, theDeckPackage);
		return theDeckPackage;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getDeckDescription() {
		return deckDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getDeckDescription_LaneDescriptions() {
		return (EReference)deckDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getDeckDescription_LaneDropTool() {
		return (EReference)deckDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getDeckDescription_Style() {
		return (EReference)deckDescriptionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getDeckDescription_ConditionalStyles() {
		return (EReference)deckDescriptionEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getLaneDescription() {
		return laneDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getLaneDescription_Name() {
		return (EAttribute)laneDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getLaneDescription_DomainType() {
		return (EAttribute)laneDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getLaneDescription_OwnedCardDescriptions() {
		return (EReference)laneDescriptionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getLaneDescription_EditTool() {
		return (EReference)laneDescriptionEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getLaneDescription_CreateTool() {
		return (EReference)laneDescriptionEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getLaneDescription_CardDropTool() {
		return (EReference)laneDescriptionEClass.getEStructuralFeatures().get(5);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getLaneDescription_IsCollapsibleExpression() {
		return (EAttribute)laneDescriptionEClass.getEStructuralFeatures().get(6);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getCardDescription() {
		return cardDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getCardDescription_Name() {
		return (EAttribute)cardDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getCardDescription_DomainType() {
		return (EAttribute)cardDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getCardDescription_DescriptionExpression() {
		return (EAttribute)cardDescriptionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getCardDescription_EditTool() {
		return (EReference)cardDescriptionEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getCardDescription_DeleteTool() {
		return (EReference)cardDescriptionEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getDeckTool() {
		return deckToolEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getDeckTool_Name() {
		return (EAttribute)deckToolEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getDeckTool_Body() {
		return (EReference)deckToolEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getCreateCardTool() {
		return createCardToolEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getEditCardTool() {
		return editCardToolEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getDeleteCardTool() {
		return deleteCardToolEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getEditLaneTool() {
		return editLaneToolEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getCardDropTool() {
		return cardDropToolEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getLaneDropTool() {
		return laneDropToolEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getDeckElementDescription() {
		return deckElementDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getDeckElementDescription_SemanticCandidatesExpression() {
		return (EAttribute)deckElementDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getDeckElementDescription_TitleExpression() {
		return (EAttribute)deckElementDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getDeckElementDescription_LabelExpression() {
		return (EAttribute)deckElementDescriptionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getDeckElementDescription_Style() {
		return (EReference)deckElementDescriptionEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getDeckElementDescription_ConditionalStyles() {
		return (EReference)deckElementDescriptionEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getDeckDescriptionStyle() {
		return deckDescriptionStyleEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getDeckDescriptionStyle_BackgroundColor() {
		return (EReference)deckDescriptionStyleEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getConditionalDeckDescriptionStyle() {
		return conditionalDeckDescriptionStyleEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getDeckElementDescriptionStyle() {
		return deckElementDescriptionStyleEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getDeckElementDescriptionStyle_BackgroundColor() {
		return (EReference)deckElementDescriptionStyleEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getDeckElementDescriptionStyle_Color() {
		return (EReference)deckElementDescriptionStyleEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getConditionalDeckElementDescriptionStyle() {
		return conditionalDeckElementDescriptionStyleEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public DeckFactory getDeckFactory() {
		return (DeckFactory)getEFactoryInstance();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private boolean isCreated = false;

    /**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		deckDescriptionEClass = createEClass(DECK_DESCRIPTION);
		createEReference(deckDescriptionEClass, DECK_DESCRIPTION__LANE_DESCRIPTIONS);
		createEReference(deckDescriptionEClass, DECK_DESCRIPTION__LANE_DROP_TOOL);
		createEReference(deckDescriptionEClass, DECK_DESCRIPTION__STYLE);
		createEReference(deckDescriptionEClass, DECK_DESCRIPTION__CONDITIONAL_STYLES);

		laneDescriptionEClass = createEClass(LANE_DESCRIPTION);
		createEAttribute(laneDescriptionEClass, LANE_DESCRIPTION__NAME);
		createEAttribute(laneDescriptionEClass, LANE_DESCRIPTION__DOMAIN_TYPE);
		createEReference(laneDescriptionEClass, LANE_DESCRIPTION__OWNED_CARD_DESCRIPTIONS);
		createEReference(laneDescriptionEClass, LANE_DESCRIPTION__EDIT_TOOL);
		createEReference(laneDescriptionEClass, LANE_DESCRIPTION__CREATE_TOOL);
		createEReference(laneDescriptionEClass, LANE_DESCRIPTION__CARD_DROP_TOOL);
		createEAttribute(laneDescriptionEClass, LANE_DESCRIPTION__IS_COLLAPSIBLE_EXPRESSION);

		cardDescriptionEClass = createEClass(CARD_DESCRIPTION);
		createEAttribute(cardDescriptionEClass, CARD_DESCRIPTION__NAME);
		createEAttribute(cardDescriptionEClass, CARD_DESCRIPTION__DOMAIN_TYPE);
		createEAttribute(cardDescriptionEClass, CARD_DESCRIPTION__DESCRIPTION_EXPRESSION);
		createEReference(cardDescriptionEClass, CARD_DESCRIPTION__EDIT_TOOL);
		createEReference(cardDescriptionEClass, CARD_DESCRIPTION__DELETE_TOOL);

		deckToolEClass = createEClass(DECK_TOOL);
		createEAttribute(deckToolEClass, DECK_TOOL__NAME);
		createEReference(deckToolEClass, DECK_TOOL__BODY);

		createCardToolEClass = createEClass(CREATE_CARD_TOOL);

		editCardToolEClass = createEClass(EDIT_CARD_TOOL);

		deleteCardToolEClass = createEClass(DELETE_CARD_TOOL);

		editLaneToolEClass = createEClass(EDIT_LANE_TOOL);

		cardDropToolEClass = createEClass(CARD_DROP_TOOL);

		laneDropToolEClass = createEClass(LANE_DROP_TOOL);

		deckElementDescriptionEClass = createEClass(DECK_ELEMENT_DESCRIPTION);
		createEAttribute(deckElementDescriptionEClass, DECK_ELEMENT_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION);
		createEAttribute(deckElementDescriptionEClass, DECK_ELEMENT_DESCRIPTION__TITLE_EXPRESSION);
		createEAttribute(deckElementDescriptionEClass, DECK_ELEMENT_DESCRIPTION__LABEL_EXPRESSION);
		createEReference(deckElementDescriptionEClass, DECK_ELEMENT_DESCRIPTION__STYLE);
		createEReference(deckElementDescriptionEClass, DECK_ELEMENT_DESCRIPTION__CONDITIONAL_STYLES);

		deckDescriptionStyleEClass = createEClass(DECK_DESCRIPTION_STYLE);
		createEReference(deckDescriptionStyleEClass, DECK_DESCRIPTION_STYLE__BACKGROUND_COLOR);

		conditionalDeckDescriptionStyleEClass = createEClass(CONDITIONAL_DECK_DESCRIPTION_STYLE);

		deckElementDescriptionStyleEClass = createEClass(DECK_ELEMENT_DESCRIPTION_STYLE);
		createEReference(deckElementDescriptionStyleEClass, DECK_ELEMENT_DESCRIPTION_STYLE__BACKGROUND_COLOR);
		createEReference(deckElementDescriptionStyleEClass, DECK_ELEMENT_DESCRIPTION_STYLE__COLOR);

		conditionalDeckElementDescriptionStyleEClass = createEClass(CONDITIONAL_DECK_ELEMENT_DESCRIPTION_STYLE);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private boolean isInitialized = false;

    /**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		ViewPackage theViewPackage = (ViewPackage)EPackage.Registry.INSTANCE.getEPackage(ViewPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		deckDescriptionEClass.getESuperTypes().add(theViewPackage.getRepresentationDescription());
		laneDescriptionEClass.getESuperTypes().add(this.getDeckElementDescription());
		cardDescriptionEClass.getESuperTypes().add(this.getDeckElementDescription());
		createCardToolEClass.getESuperTypes().add(this.getDeckTool());
		editCardToolEClass.getESuperTypes().add(this.getDeckTool());
		deleteCardToolEClass.getESuperTypes().add(this.getDeckTool());
		editLaneToolEClass.getESuperTypes().add(this.getDeckTool());
		cardDropToolEClass.getESuperTypes().add(this.getDeckTool());
		laneDropToolEClass.getESuperTypes().add(this.getDeckTool());
		conditionalDeckDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getConditional());
		conditionalDeckDescriptionStyleEClass.getESuperTypes().add(this.getDeckDescriptionStyle());
		deckElementDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getLabelStyle());
		conditionalDeckElementDescriptionStyleEClass.getESuperTypes().add(theViewPackage.getConditional());
		conditionalDeckElementDescriptionStyleEClass.getESuperTypes().add(this.getDeckElementDescriptionStyle());

		// Initialize classes, features, and operations; add parameters
		initEClass(deckDescriptionEClass, DeckDescription.class, "DeckDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDeckDescription_LaneDescriptions(), this.getLaneDescription(), null, "laneDescriptions", null, 0, -1, DeckDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getDeckDescription_LaneDescriptions().getEKeys().add(this.getLaneDescription_Name());
		initEReference(getDeckDescription_LaneDropTool(), this.getLaneDropTool(), null, "laneDropTool", null, 0, 1, DeckDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDeckDescription_Style(), this.getDeckDescriptionStyle(), null, "style", null, 0, 1, DeckDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDeckDescription_ConditionalStyles(), this.getConditionalDeckDescriptionStyle(), null, "conditionalStyles", null, 0, -1, DeckDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(laneDescriptionEClass, LaneDescription.class, "LaneDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLaneDescription_Name(), theViewPackage.getIdentifier(), "name", "New Lane Description", 0, 1, LaneDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLaneDescription_DomainType(), theViewPackage.getDomainType(), "domainType", null, 0, 1, LaneDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLaneDescription_OwnedCardDescriptions(), this.getCardDescription(), null, "ownedCardDescriptions", null, 0, -1, LaneDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getLaneDescription_OwnedCardDescriptions().getEKeys().add(this.getCardDescription_Name());
		initEReference(getLaneDescription_EditTool(), this.getEditLaneTool(), null, "editTool", null, 0, 1, LaneDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLaneDescription_CreateTool(), this.getCreateCardTool(), null, "createTool", null, 0, 1, LaneDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLaneDescription_CardDropTool(), this.getCardDropTool(), null, "cardDropTool", null, 0, 1, LaneDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLaneDescription_IsCollapsibleExpression(), theViewPackage.getInterpretedExpression(), "isCollapsibleExpression", "aql:true", 0, 1, LaneDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cardDescriptionEClass, CardDescription.class, "CardDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCardDescription_Name(), theViewPackage.getIdentifier(), "name", "New Card Description", 0, 1, CardDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCardDescription_DomainType(), theViewPackage.getDomainType(), "domainType", null, 0, 1, CardDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCardDescription_DescriptionExpression(), theViewPackage.getInterpretedExpression(), "descriptionExpression", "aql:self", 0, 1, CardDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCardDescription_EditTool(), this.getEditCardTool(), null, "editTool", null, 0, 1, CardDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCardDescription_DeleteTool(), this.getDeleteCardTool(), null, "deleteTool", null, 0, 1, CardDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(deckToolEClass, DeckTool.class, "DeckTool", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDeckTool_Name(), theViewPackage.getIdentifier(), "name", null, 1, 1, DeckTool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDeckTool_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, DeckTool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(createCardToolEClass, CreateCardTool.class, "CreateCardTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(editCardToolEClass, EditCardTool.class, "EditCardTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(deleteCardToolEClass, DeleteCardTool.class, "DeleteCardTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(editLaneToolEClass, EditLaneTool.class, "EditLaneTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(cardDropToolEClass, CardDropTool.class, "CardDropTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(laneDropToolEClass, LaneDropTool.class, "LaneDropTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(deckElementDescriptionEClass, DeckElementDescription.class, "DeckElementDescription", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDeckElementDescription_SemanticCandidatesExpression(), theViewPackage.getInterpretedExpression(), "semanticCandidatesExpression", "aql:self", 1, 1, DeckElementDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDeckElementDescription_TitleExpression(), theViewPackage.getInterpretedExpression(), "titleExpression", "aql:self", 0, 1, DeckElementDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDeckElementDescription_LabelExpression(), theViewPackage.getInterpretedExpression(), "labelExpression", "aql:self", 0, 1, DeckElementDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDeckElementDescription_Style(), this.getDeckElementDescriptionStyle(), null, "style", null, 0, 1, DeckElementDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDeckElementDescription_ConditionalStyles(), this.getConditionalDeckElementDescriptionStyle(), null, "conditionalStyles", null, 0, -1, DeckElementDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(deckDescriptionStyleEClass, DeckDescriptionStyle.class, "DeckDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDeckDescriptionStyle_BackgroundColor(), theViewPackage.getUserColor(), null, "backgroundColor", null, 0, 1, DeckDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(conditionalDeckDescriptionStyleEClass, ConditionalDeckDescriptionStyle.class, "ConditionalDeckDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(deckElementDescriptionStyleEClass, DeckElementDescriptionStyle.class, "DeckElementDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDeckElementDescriptionStyle_BackgroundColor(), theViewPackage.getUserColor(), null, "backgroundColor", null, 0, 1, DeckElementDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDeckElementDescriptionStyle_Color(), theViewPackage.getUserColor(), null, "color", null, 0, 1, DeckElementDescriptionStyle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(conditionalDeckElementDescriptionStyleEClass, ConditionalDeckElementDescriptionStyle.class, "ConditionalDeckElementDescriptionStyle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} // DeckPackageImpl
