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
package org.eclipse.sirius.components.view.deck.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.deck.CardDescription;
import org.eclipse.sirius.components.view.deck.CreateCardTool;
import org.eclipse.sirius.components.view.deck.DeckDescription;
import org.eclipse.sirius.components.view.deck.DeckFactory;
import org.eclipse.sirius.components.view.deck.DeckPackage;
import org.eclipse.sirius.components.view.deck.DeckTool;
import org.eclipse.sirius.components.view.deck.DeleteCardTool;
import org.eclipse.sirius.components.view.deck.EditCardTool;
import org.eclipse.sirius.components.view.deck.EditLaneTool;
import org.eclipse.sirius.components.view.deck.LaneDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class DeckPackageImpl extends EPackageImpl implements DeckPackage {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass deckDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass laneDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass cardDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass deckToolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass createCardToolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass editCardToolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass deleteCardToolEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass editLaneToolEClass = null;

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
     *
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     *
     * <p>
     * This method is used to initialize {@link DeckPackage#eINSTANCE} when that field is accessed. Clients should not
     * invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static DeckPackage init() {
        if (isInited)
            return (DeckPackage) EPackage.Registry.INSTANCE.getEPackage(DeckPackage.eNS_URI);

        // Obtain or create and register package
        Object registeredDeckPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
        DeckPackageImpl theDeckPackage = registeredDeckPackage instanceof DeckPackageImpl ? (DeckPackageImpl) registeredDeckPackage : new DeckPackageImpl();

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
     *
     * @generated
     */
    @Override
    public EClass getDeckDescription() {
        return this.deckDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDeckDescription_LaneDescriptions() {
        return (EReference) this.deckDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDeckDescription_BackgroundColor() {
        return (EReference) this.deckDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getLaneDescription() {
        return this.laneDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLaneDescription_SemanticCandidatesExpression() {
        return (EAttribute) this.laneDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLaneDescription_TitleExpression() {
        return (EAttribute) this.laneDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getLaneDescription_LabelExpression() {
        return (EAttribute) this.laneDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getLaneDescription_OwnedCardDescriptions() {
        return (EReference) this.laneDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getLaneDescription_EditTool() {
        return (EReference) this.laneDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getLaneDescription_CreateTool() {
        return (EReference) this.laneDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getCardDescription() {
        return this.cardDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getCardDescription_SemanticCandidatesExpression() {
        return (EAttribute) this.cardDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getCardDescription_TitleExpression() {
        return (EAttribute) this.cardDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getCardDescription_LabelExpression() {
        return (EAttribute) this.cardDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getCardDescription_DescriptionExpression() {
        return (EAttribute) this.cardDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getCardDescription_EditTool() {
        return (EReference) this.cardDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getCardDescription_DeleteTool() {
        return (EReference) this.cardDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDeckTool() {
        return this.deckToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDeckTool_Name() {
        return (EAttribute) this.deckToolEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getDeckTool_PreconditionExpression() {
        return (EAttribute) this.deckToolEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getDeckTool_Body() {
        return (EReference) this.deckToolEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getCreateCardTool() {
        return this.createCardToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEditCardTool() {
        return this.editCardToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getDeleteCardTool() {
        return this.deleteCardToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getEditLaneTool() {
        return this.editLaneToolEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DeckFactory getDeckFactory() {
        return (DeckFactory) this.getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package. This method is guarded to have no affect on any invocation but
     * its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void createPackageContents() {
        if (this.isCreated)
            return;
        this.isCreated = true;

        // Create classes and their features
        this.deckDescriptionEClass = this.createEClass(DECK_DESCRIPTION);
        this.createEReference(this.deckDescriptionEClass, DECK_DESCRIPTION__LANE_DESCRIPTIONS);
        this.createEReference(this.deckDescriptionEClass, DECK_DESCRIPTION__BACKGROUND_COLOR);

        this.laneDescriptionEClass = this.createEClass(LANE_DESCRIPTION);
        this.createEAttribute(this.laneDescriptionEClass, LANE_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION);
        this.createEAttribute(this.laneDescriptionEClass, LANE_DESCRIPTION__TITLE_EXPRESSION);
        this.createEAttribute(this.laneDescriptionEClass, LANE_DESCRIPTION__LABEL_EXPRESSION);
        this.createEReference(this.laneDescriptionEClass, LANE_DESCRIPTION__OWNED_CARD_DESCRIPTIONS);
        this.createEReference(this.laneDescriptionEClass, LANE_DESCRIPTION__EDIT_TOOL);
        this.createEReference(this.laneDescriptionEClass, LANE_DESCRIPTION__CREATE_TOOL);

        this.cardDescriptionEClass = this.createEClass(CARD_DESCRIPTION);
        this.createEAttribute(this.cardDescriptionEClass, CARD_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION);
        this.createEAttribute(this.cardDescriptionEClass, CARD_DESCRIPTION__TITLE_EXPRESSION);
        this.createEAttribute(this.cardDescriptionEClass, CARD_DESCRIPTION__LABEL_EXPRESSION);
        this.createEAttribute(this.cardDescriptionEClass, CARD_DESCRIPTION__DESCRIPTION_EXPRESSION);
        this.createEReference(this.cardDescriptionEClass, CARD_DESCRIPTION__EDIT_TOOL);
        this.createEReference(this.cardDescriptionEClass, CARD_DESCRIPTION__DELETE_TOOL);

        this.deckToolEClass = this.createEClass(DECK_TOOL);
        this.createEAttribute(this.deckToolEClass, DECK_TOOL__NAME);
        this.createEAttribute(this.deckToolEClass, DECK_TOOL__PRECONDITION_EXPRESSION);
        this.createEReference(this.deckToolEClass, DECK_TOOL__BODY);

        this.createCardToolEClass = this.createEClass(CREATE_CARD_TOOL);

        this.editCardToolEClass = this.createEClass(EDIT_CARD_TOOL);

        this.deleteCardToolEClass = this.createEClass(DELETE_CARD_TOOL);

        this.editLaneToolEClass = this.createEClass(EDIT_LANE_TOOL);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model. This method is guarded to have no affect on any
     * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public void initializePackageContents() {
        if (this.isInitialized)
            return;
        this.isInitialized = true;

        // Initialize package
        this.setName(eNAME);
        this.setNsPrefix(eNS_PREFIX);
        this.setNsURI(eNS_URI);

        // Obtain other dependent packages
        ViewPackage theViewPackage = (ViewPackage) EPackage.Registry.INSTANCE.getEPackage(ViewPackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        this.deckDescriptionEClass.getESuperTypes().add(theViewPackage.getRepresentationDescription());
        this.createCardToolEClass.getESuperTypes().add(this.getDeckTool());
        this.editCardToolEClass.getESuperTypes().add(this.getDeckTool());
        this.deleteCardToolEClass.getESuperTypes().add(this.getDeckTool());
        this.editLaneToolEClass.getESuperTypes().add(this.getDeckTool());

        // Initialize classes, features, and operations; add parameters
        this.initEClass(this.deckDescriptionEClass, DeckDescription.class, "DeckDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEReference(this.getDeckDescription_LaneDescriptions(), this.getLaneDescription(), null, "laneDescriptions", null, 0, -1, DeckDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDeckDescription_BackgroundColor(), theViewPackage.getUserColor(), null, "backgroundColor", null, 0, 1, DeckDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.laneDescriptionEClass, LaneDescription.class, "LaneDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getLaneDescription_SemanticCandidatesExpression(), theViewPackage.getInterpretedExpression(), "semanticCandidatesExpression", "aql:self", 1, 1, LaneDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getLaneDescription_TitleExpression(), theViewPackage.getInterpretedExpression(), "titleExpression", "aql:self", 0, 1, LaneDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getLaneDescription_LabelExpression(), theViewPackage.getInterpretedExpression(), "labelExpression", "aql:self", 0, 1, LaneDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getLaneDescription_OwnedCardDescriptions(), this.getCardDescription(), null, "ownedCardDescriptions", null, 0, -1, LaneDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getLaneDescription_EditTool(), this.getEditLaneTool(), null, "editTool", null, 0, 1, LaneDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getLaneDescription_CreateTool(), this.getCreateCardTool(), null, "createTool", null, 0, 1, LaneDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.cardDescriptionEClass, CardDescription.class, "CardDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getCardDescription_SemanticCandidatesExpression(), theViewPackage.getInterpretedExpression(), "semanticCandidatesExpression", "aql:self", 1, 1, CardDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getCardDescription_TitleExpression(), theViewPackage.getInterpretedExpression(), "titleExpression", "aql:self", 0, 1, CardDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getCardDescription_LabelExpression(), theViewPackage.getInterpretedExpression(), "labelExpression", "aql:self", 0, 1, CardDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getCardDescription_DescriptionExpression(), theViewPackage.getInterpretedExpression(), "descriptionExpression", "aql:self", 0, 1, CardDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getCardDescription_EditTool(), this.getEditCardTool(), null, "editTool", null, 0, 1, CardDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getCardDescription_DeleteTool(), this.getDeleteCardTool(), null, "deleteTool", null, 0, 1, CardDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.deckToolEClass, DeckTool.class, "DeckTool", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getDeckTool_Name(), theViewPackage.getIdentifier(), "name", null, 1, 1, DeckTool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
                !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getDeckTool_PreconditionExpression(), theViewPackage.getInterpretedExpression(), "preconditionExpression", null, 0, 1, DeckTool.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getDeckTool_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, DeckTool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
                !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.createCardToolEClass, CreateCardTool.class, "CreateCardTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.editCardToolEClass, EditCardTool.class, "EditCardTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.deleteCardToolEClass, DeleteCardTool.class, "DeleteCardTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        this.initEClass(this.editLaneToolEClass, EditLaneTool.class, "EditLaneTool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        this.createResource(eNS_URI);
    }

} // DeckPackageImpl
