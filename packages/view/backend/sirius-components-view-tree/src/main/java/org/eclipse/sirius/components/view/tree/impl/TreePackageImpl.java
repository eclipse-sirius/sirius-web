/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.components.view.tree.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.tree.CustomTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntryKind;
import org.eclipse.sirius.components.view.tree.ForTreeItemLabelElementDescription;
import org.eclipse.sirius.components.view.tree.IfTreeItemLabelElementDescription;
import org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.TreeDescription;
import org.eclipse.sirius.components.view.tree.TreeFactory;
import org.eclipse.sirius.components.view.tree.TreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.TreeItemLabelDescription;
import org.eclipse.sirius.components.view.tree.TreeItemLabelElementDescription;
import org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription;
import org.eclipse.sirius.components.view.tree.TreePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * @generated
 */
public class TreePackageImpl extends EPackageImpl implements TreePackage {

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass treeDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass treeItemLabelDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass ifTreeItemLabelElementDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass forTreeItemLabelElementDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass treeItemLabelFragmentDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass treeItemLabelElementDescriptionEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass treeItemContextMenuEntryEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass singleClickTreeItemContextMenuEntryEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass fetchTreeItemContextMenuEntryEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EClass customTreeItemContextMenuEntryEClass = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private EEnum fetchTreeItemContextMenuEntryKindEEnum = null;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private static boolean isInited = false;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private boolean isCreated = false;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    private boolean isInitialized = false;

    /**
     * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
     * EPackage.Registry} by the package package URI value.
     * <p>
     * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
     * performs initialization of the package, or returns the registered package, if one already exists. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.eclipse.sirius.components.view.tree.TreePackage#eNS_URI
     * @see #init()
     * @generated
     */
    private TreePackageImpl() {
		super(eNS_URI, TreeFactory.eINSTANCE);
	}

    /**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link TreePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
    public static TreePackage init() {
		if (isInited) return (TreePackage)EPackage.Registry.INSTANCE.getEPackage(TreePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredTreePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		TreePackageImpl theTreePackage = registeredTreePackage instanceof TreePackageImpl ? (TreePackageImpl)registeredTreePackage : new TreePackageImpl();

		isInited = true;

		// Initialize simple dependencies
		ViewPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theTreePackage.createPackageContents();

		// Initialize created meta-data
		theTreePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTreePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TreePackage.eNS_URI, theTreePackage);
		return theTreePackage;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getTreeDescription() {
		return treeDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTreeDescription_KindExpression() {
		return (EAttribute)treeDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTreeDescription_TreeItemIconExpression() {
		return (EAttribute)treeDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTreeDescription_TreeItemIdExpression() {
		return (EAttribute)treeDescriptionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTreeDescription_TreeItemObjectExpression() {
		return (EAttribute)treeDescriptionEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTreeDescription_ElementsExpression() {
		return (EAttribute)treeDescriptionEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTreeDescription_HasChildrenExpression() {
		return (EAttribute)treeDescriptionEClass.getEStructuralFeatures().get(5);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTreeDescription_ChildrenExpression() {
		return (EAttribute)treeDescriptionEClass.getEStructuralFeatures().get(6);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTreeDescription_ParentExpression() {
		return (EAttribute)treeDescriptionEClass.getEStructuralFeatures().get(7);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTreeDescription_EditableExpression() {
		return (EAttribute)treeDescriptionEClass.getEStructuralFeatures().get(8);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTreeDescription_SelectableExpression() {
		return (EAttribute)treeDescriptionEClass.getEStructuralFeatures().get(9);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTreeDescription_DeletableExpression() {
		return (EAttribute)treeDescriptionEClass.getEStructuralFeatures().get(10);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getTreeDescription_TreeItemLabelDescriptions() {
		return (EReference)treeDescriptionEClass.getEStructuralFeatures().get(11);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getTreeDescription_ContextMenuEntries() {
		return (EReference)treeDescriptionEClass.getEStructuralFeatures().get(12);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getTreeItemLabelDescription() {
		return treeItemLabelDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTreeItemLabelDescription_Name() {
		return (EAttribute)treeItemLabelDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTreeItemLabelDescription_PreconditionExpression() {
		return (EAttribute)treeItemLabelDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getTreeItemLabelDescription_Children() {
		return (EReference)treeItemLabelDescriptionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getIfTreeItemLabelElementDescription() {
		return ifTreeItemLabelElementDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getIfTreeItemLabelElementDescription_PredicateExpression() {
		return (EAttribute)ifTreeItemLabelElementDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getIfTreeItemLabelElementDescription_Children() {
		return (EReference)ifTreeItemLabelElementDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getForTreeItemLabelElementDescription() {
		return forTreeItemLabelElementDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getForTreeItemLabelElementDescription_Iterator() {
		return (EAttribute)forTreeItemLabelElementDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getForTreeItemLabelElementDescription_IterableExpression() {
		return (EAttribute)forTreeItemLabelElementDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getForTreeItemLabelElementDescription_Children() {
		return (EReference)forTreeItemLabelElementDescriptionEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getTreeItemLabelFragmentDescription() {
		return treeItemLabelFragmentDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTreeItemLabelFragmentDescription_LabelExpression() {
		return (EAttribute)treeItemLabelFragmentDescriptionEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getTreeItemLabelFragmentDescription_Style() {
		return (EReference)treeItemLabelFragmentDescriptionEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getTreeItemLabelElementDescription() {
		return treeItemLabelElementDescriptionEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getTreeItemContextMenuEntry() {
		return treeItemContextMenuEntryEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTreeItemContextMenuEntry_Name() {
		return (EAttribute)treeItemContextMenuEntryEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getTreeItemContextMenuEntry_PreconditionExpression() {
		return (EAttribute)treeItemContextMenuEntryEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getSingleClickTreeItemContextMenuEntry() {
		return singleClickTreeItemContextMenuEntryEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EReference getSingleClickTreeItemContextMenuEntry_Body() {
		return (EReference)singleClickTreeItemContextMenuEntryEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getSingleClickTreeItemContextMenuEntry_LabelExpression() {
		return (EAttribute)singleClickTreeItemContextMenuEntryEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getSingleClickTreeItemContextMenuEntry_IconURLExpression() {
		return (EAttribute)singleClickTreeItemContextMenuEntryEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getSingleClickTreeItemContextMenuEntry_WithImpactAnalysis() {
		return (EAttribute)singleClickTreeItemContextMenuEntryEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getFetchTreeItemContextMenuEntry() {
		return fetchTreeItemContextMenuEntryEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getFetchTreeItemContextMenuEntry_UrlExression() {
		return (EAttribute)fetchTreeItemContextMenuEntryEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getFetchTreeItemContextMenuEntry_Kind() {
		return (EAttribute)fetchTreeItemContextMenuEntryEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getFetchTreeItemContextMenuEntry_LabelExpression() {
		return (EAttribute)fetchTreeItemContextMenuEntryEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getFetchTreeItemContextMenuEntry_IconURLExpression() {
		return (EAttribute)fetchTreeItemContextMenuEntryEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EClass getCustomTreeItemContextMenuEntry() {
		return customTreeItemContextMenuEntryEClass;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getCustomTreeItemContextMenuEntry_ContributionId() {
		return (EAttribute)customTreeItemContextMenuEntryEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EAttribute getCustomTreeItemContextMenuEntry_WithImpactAnalysis() {
		return (EAttribute)customTreeItemContextMenuEntryEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EEnum getFetchTreeItemContextMenuEntryKind() {
		return fetchTreeItemContextMenuEntryKindEEnum;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public TreeFactory getTreeFactory() {
		return (TreeFactory)getEFactoryInstance();
	}

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
		treeDescriptionEClass = createEClass(TREE_DESCRIPTION);
		createEAttribute(treeDescriptionEClass, TREE_DESCRIPTION__KIND_EXPRESSION);
		createEAttribute(treeDescriptionEClass, TREE_DESCRIPTION__TREE_ITEM_ICON_EXPRESSION);
		createEAttribute(treeDescriptionEClass, TREE_DESCRIPTION__TREE_ITEM_ID_EXPRESSION);
		createEAttribute(treeDescriptionEClass, TREE_DESCRIPTION__TREE_ITEM_OBJECT_EXPRESSION);
		createEAttribute(treeDescriptionEClass, TREE_DESCRIPTION__ELEMENTS_EXPRESSION);
		createEAttribute(treeDescriptionEClass, TREE_DESCRIPTION__HAS_CHILDREN_EXPRESSION);
		createEAttribute(treeDescriptionEClass, TREE_DESCRIPTION__CHILDREN_EXPRESSION);
		createEAttribute(treeDescriptionEClass, TREE_DESCRIPTION__PARENT_EXPRESSION);
		createEAttribute(treeDescriptionEClass, TREE_DESCRIPTION__EDITABLE_EXPRESSION);
		createEAttribute(treeDescriptionEClass, TREE_DESCRIPTION__SELECTABLE_EXPRESSION);
		createEAttribute(treeDescriptionEClass, TREE_DESCRIPTION__DELETABLE_EXPRESSION);
		createEReference(treeDescriptionEClass, TREE_DESCRIPTION__TREE_ITEM_LABEL_DESCRIPTIONS);
		createEReference(treeDescriptionEClass, TREE_DESCRIPTION__CONTEXT_MENU_ENTRIES);

		treeItemLabelDescriptionEClass = createEClass(TREE_ITEM_LABEL_DESCRIPTION);
		createEAttribute(treeItemLabelDescriptionEClass, TREE_ITEM_LABEL_DESCRIPTION__NAME);
		createEAttribute(treeItemLabelDescriptionEClass, TREE_ITEM_LABEL_DESCRIPTION__PRECONDITION_EXPRESSION);
		createEReference(treeItemLabelDescriptionEClass, TREE_ITEM_LABEL_DESCRIPTION__CHILDREN);

		ifTreeItemLabelElementDescriptionEClass = createEClass(IF_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION);
		createEAttribute(ifTreeItemLabelElementDescriptionEClass, IF_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__PREDICATE_EXPRESSION);
		createEReference(ifTreeItemLabelElementDescriptionEClass, IF_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__CHILDREN);

		forTreeItemLabelElementDescriptionEClass = createEClass(FOR_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION);
		createEAttribute(forTreeItemLabelElementDescriptionEClass, FOR_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__ITERATOR);
		createEAttribute(forTreeItemLabelElementDescriptionEClass, FOR_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__ITERABLE_EXPRESSION);
		createEReference(forTreeItemLabelElementDescriptionEClass, FOR_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION__CHILDREN);

		treeItemLabelFragmentDescriptionEClass = createEClass(TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION);
		createEAttribute(treeItemLabelFragmentDescriptionEClass, TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION__LABEL_EXPRESSION);
		createEReference(treeItemLabelFragmentDescriptionEClass, TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION__STYLE);

		treeItemLabelElementDescriptionEClass = createEClass(TREE_ITEM_LABEL_ELEMENT_DESCRIPTION);

		treeItemContextMenuEntryEClass = createEClass(TREE_ITEM_CONTEXT_MENU_ENTRY);
		createEAttribute(treeItemContextMenuEntryEClass, TREE_ITEM_CONTEXT_MENU_ENTRY__NAME);
		createEAttribute(treeItemContextMenuEntryEClass, TREE_ITEM_CONTEXT_MENU_ENTRY__PRECONDITION_EXPRESSION);

		singleClickTreeItemContextMenuEntryEClass = createEClass(SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY);
		createEReference(singleClickTreeItemContextMenuEntryEClass, SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__BODY);
		createEAttribute(singleClickTreeItemContextMenuEntryEClass, SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION);
		createEAttribute(singleClickTreeItemContextMenuEntryEClass, SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION);
		createEAttribute(singleClickTreeItemContextMenuEntryEClass, SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY__WITH_IMPACT_ANALYSIS);

		fetchTreeItemContextMenuEntryEClass = createEClass(FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY);
		createEAttribute(fetchTreeItemContextMenuEntryEClass, FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__URL_EXRESSION);
		createEAttribute(fetchTreeItemContextMenuEntryEClass, FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__KIND);
		createEAttribute(fetchTreeItemContextMenuEntryEClass, FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__LABEL_EXPRESSION);
		createEAttribute(fetchTreeItemContextMenuEntryEClass, FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY__ICON_URL_EXPRESSION);

		customTreeItemContextMenuEntryEClass = createEClass(CUSTOM_TREE_ITEM_CONTEXT_MENU_ENTRY);
		createEAttribute(customTreeItemContextMenuEntryEClass, CUSTOM_TREE_ITEM_CONTEXT_MENU_ENTRY__CONTRIBUTION_ID);
		createEAttribute(customTreeItemContextMenuEntryEClass, CUSTOM_TREE_ITEM_CONTEXT_MENU_ENTRY__WITH_IMPACT_ANALYSIS);

		// Create enums
		fetchTreeItemContextMenuEntryKindEEnum = createEEnum(FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY_KIND);
	}

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
		treeDescriptionEClass.getESuperTypes().add(theViewPackage.getRepresentationDescription());
		ifTreeItemLabelElementDescriptionEClass.getESuperTypes().add(this.getTreeItemLabelElementDescription());
		forTreeItemLabelElementDescriptionEClass.getESuperTypes().add(this.getTreeItemLabelElementDescription());
		treeItemLabelFragmentDescriptionEClass.getESuperTypes().add(this.getTreeItemLabelElementDescription());
		singleClickTreeItemContextMenuEntryEClass.getESuperTypes().add(this.getTreeItemContextMenuEntry());
		fetchTreeItemContextMenuEntryEClass.getESuperTypes().add(this.getTreeItemContextMenuEntry());
		customTreeItemContextMenuEntryEClass.getESuperTypes().add(this.getTreeItemContextMenuEntry());

		// Initialize classes, features, and operations; add parameters
		initEClass(treeDescriptionEClass, TreeDescription.class, "TreeDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTreeDescription_KindExpression(), theViewPackage.getInterpretedExpression(), "kindExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTreeDescription_TreeItemIconExpression(), theViewPackage.getInterpretedExpression(), "treeItemIconExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTreeDescription_TreeItemIdExpression(), theViewPackage.getInterpretedExpression(), "treeItemIdExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTreeDescription_TreeItemObjectExpression(), theViewPackage.getInterpretedExpression(), "treeItemObjectExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTreeDescription_ElementsExpression(), theViewPackage.getInterpretedExpression(), "elementsExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTreeDescription_HasChildrenExpression(), theViewPackage.getInterpretedExpression(), "hasChildrenExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTreeDescription_ChildrenExpression(), theViewPackage.getInterpretedExpression(), "childrenExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTreeDescription_ParentExpression(), theViewPackage.getInterpretedExpression(), "parentExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTreeDescription_EditableExpression(), theViewPackage.getInterpretedExpression(), "editableExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTreeDescription_SelectableExpression(), theViewPackage.getInterpretedExpression(), "selectableExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTreeDescription_DeletableExpression(), theViewPackage.getInterpretedExpression(), "deletableExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTreeDescription_TreeItemLabelDescriptions(), this.getTreeItemLabelDescription(), null, "treeItemLabelDescriptions", null, 0, -1, TreeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getTreeDescription_TreeItemLabelDescriptions().getEKeys().add(this.getTreeItemLabelDescription_Name());
		initEReference(getTreeDescription_ContextMenuEntries(), this.getTreeItemContextMenuEntry(), null, "contextMenuEntries", null, 0, -1, TreeDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(treeItemLabelDescriptionEClass, TreeItemLabelDescription.class, "TreeItemLabelDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTreeItemLabelDescription_Name(), theViewPackage.getIdentifier(), "name", null, 0, 1, TreeItemLabelDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTreeItemLabelDescription_PreconditionExpression(), theViewPackage.getInterpretedExpression(), "preconditionExpression", null, 0, 1, TreeItemLabelDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTreeItemLabelDescription_Children(), this.getTreeItemLabelElementDescription(), null, "children", null, 0, -1, TreeItemLabelDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ifTreeItemLabelElementDescriptionEClass, IfTreeItemLabelElementDescription.class, "IfTreeItemLabelElementDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIfTreeItemLabelElementDescription_PredicateExpression(), theViewPackage.getInterpretedExpression(), "predicateExpression", null, 0, 1, IfTreeItemLabelElementDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIfTreeItemLabelElementDescription_Children(), this.getTreeItemLabelElementDescription(), null, "children", null, 1, -1, IfTreeItemLabelElementDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(forTreeItemLabelElementDescriptionEClass, ForTreeItemLabelElementDescription.class, "ForTreeItemLabelElementDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getForTreeItemLabelElementDescription_Iterator(), ecorePackage.getEString(), "iterator", null, 0, 1, ForTreeItemLabelElementDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getForTreeItemLabelElementDescription_IterableExpression(), theViewPackage.getInterpretedExpression(), "iterableExpression", null, 0, 1, ForTreeItemLabelElementDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getForTreeItemLabelElementDescription_Children(), this.getTreeItemLabelElementDescription(), null, "children", null, 1, -1, ForTreeItemLabelElementDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(treeItemLabelFragmentDescriptionEClass, TreeItemLabelFragmentDescription.class, "TreeItemLabelFragmentDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTreeItemLabelFragmentDescription_LabelExpression(), theViewPackage.getInterpretedExpression(), "labelExpression", null, 0, 1, TreeItemLabelFragmentDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTreeItemLabelFragmentDescription_Style(), theViewPackage.getTextStyleDescription(), null, "style", null, 0, 1, TreeItemLabelFragmentDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(treeItemLabelElementDescriptionEClass, TreeItemLabelElementDescription.class, "TreeItemLabelElementDescription", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(treeItemContextMenuEntryEClass, TreeItemContextMenuEntry.class, "TreeItemContextMenuEntry", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTreeItemContextMenuEntry_Name(), theViewPackage.getIdentifier(), "name", null, 1, 1, TreeItemContextMenuEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTreeItemContextMenuEntry_PreconditionExpression(), theViewPackage.getInterpretedExpression(), "preconditionExpression", null, 0, 1, TreeItemContextMenuEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(singleClickTreeItemContextMenuEntryEClass, SingleClickTreeItemContextMenuEntry.class, "SingleClickTreeItemContextMenuEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSingleClickTreeItemContextMenuEntry_Body(), theViewPackage.getOperation(), null, "body", null, 0, -1, SingleClickTreeItemContextMenuEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSingleClickTreeItemContextMenuEntry_LabelExpression(), theViewPackage.getInterpretedExpression(), "labelExpression", null, 0, 1, SingleClickTreeItemContextMenuEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSingleClickTreeItemContextMenuEntry_IconURLExpression(), theViewPackage.getInterpretedExpression(), "iconURLExpression", null, 0, 1, SingleClickTreeItemContextMenuEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSingleClickTreeItemContextMenuEntry_WithImpactAnalysis(), ecorePackage.getEBoolean(), "withImpactAnalysis", null, 0, 1, SingleClickTreeItemContextMenuEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fetchTreeItemContextMenuEntryEClass, FetchTreeItemContextMenuEntry.class, "FetchTreeItemContextMenuEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFetchTreeItemContextMenuEntry_UrlExression(), theViewPackage.getInterpretedExpression(), "urlExression", null, 0, 1, FetchTreeItemContextMenuEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFetchTreeItemContextMenuEntry_Kind(), this.getFetchTreeItemContextMenuEntryKind(), "kind", null, 0, 1, FetchTreeItemContextMenuEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFetchTreeItemContextMenuEntry_LabelExpression(), theViewPackage.getInterpretedExpression(), "labelExpression", null, 0, 1, FetchTreeItemContextMenuEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFetchTreeItemContextMenuEntry_IconURLExpression(), theViewPackage.getInterpretedExpression(), "iconURLExpression", null, 0, 1, FetchTreeItemContextMenuEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(customTreeItemContextMenuEntryEClass, CustomTreeItemContextMenuEntry.class, "CustomTreeItemContextMenuEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCustomTreeItemContextMenuEntry_ContributionId(), theViewPackage.getIdentifier(), "contributionId", null, 0, 1, CustomTreeItemContextMenuEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCustomTreeItemContextMenuEntry_WithImpactAnalysis(), ecorePackage.getEBoolean(), "withImpactAnalysis", null, 0, 1, CustomTreeItemContextMenuEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(fetchTreeItemContextMenuEntryKindEEnum, FetchTreeItemContextMenuEntryKind.class, "FetchTreeItemContextMenuEntryKind");
		addEEnumLiteral(fetchTreeItemContextMenuEntryKindEEnum, FetchTreeItemContextMenuEntryKind.DOWNLOAD);
		addEEnumLiteral(fetchTreeItemContextMenuEntryKindEEnum, FetchTreeItemContextMenuEntryKind.OPEN);

		// Create resource
		createResource(eNS_URI);
	}

} // TreePackageImpl
