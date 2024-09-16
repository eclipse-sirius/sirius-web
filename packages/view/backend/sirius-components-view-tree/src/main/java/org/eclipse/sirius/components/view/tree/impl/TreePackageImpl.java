/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.tree.TreeDescription;
import org.eclipse.sirius.components.view.tree.TreeFactory;
import org.eclipse.sirius.components.view.tree.TreeItemLabelDescription;
import org.eclipse.sirius.components.view.tree.TreeItemLabelElementDescription;
import org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription;
import org.eclipse.sirius.components.view.tree.TreePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class TreePackageImpl extends EPackageImpl implements TreePackage {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass treeDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass treeItemLabelDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass treeItemLabelFragmentDescriptionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private EClass treeItemLabelElementDescriptionEClass = null;

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
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     *
     * <p>
     * This method is used to initialize {@link TreePackage#eINSTANCE} when that field is accessed. Clients should not
     * invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static TreePackage init() {
        if (isInited)
            return (TreePackage) EPackage.Registry.INSTANCE.getEPackage(TreePackage.eNS_URI);

        // Obtain or create and register package
        Object registeredTreePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
        TreePackageImpl theTreePackage = registeredTreePackage instanceof TreePackageImpl ? (TreePackageImpl) registeredTreePackage : new TreePackageImpl();

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
     *
     * @generated
     */
    @Override
    public EClass getTreeDescription() {
        return this.treeDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTreeDescription_KindExpression() {
        return (EAttribute) this.treeDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTreeDescription_IconURLExpression() {
        return (EAttribute) this.treeDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTreeDescription_TreeItemIdExpression() {
        return (EAttribute) this.treeDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTreeDescription_TreeItemObjectExpression() {
        return (EAttribute) this.treeDescriptionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTreeDescription_ElementsExpression() {
        return (EAttribute) this.treeDescriptionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTreeDescription_HasChildrenExpression() {
        return (EAttribute) this.treeDescriptionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTreeDescription_ChildrenExpression() {
        return (EAttribute) this.treeDescriptionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTreeDescription_ParentExpression() {
        return (EAttribute) this.treeDescriptionEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTreeDescription_EditableExpression() {
        return (EAttribute) this.treeDescriptionEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTreeDescription_SelectableExpression() {
        return (EAttribute) this.treeDescriptionEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTreeDescription_DeletableExpression() {
        return (EAttribute) this.treeDescriptionEClass.getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTreeDescription_TreeItemLabelDescriptions() {
        return (EReference) this.treeDescriptionEClass.getEStructuralFeatures().get(11);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTreeItemLabelDescription() {
        return this.treeItemLabelDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTreeItemLabelDescription_Name() {
        return (EAttribute) this.treeItemLabelDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTreeItemLabelDescription_PreconditionExpression() {
        return (EAttribute) this.treeItemLabelDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTreeItemLabelDescription_Children() {
        return (EReference) this.treeItemLabelDescriptionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTreeItemLabelFragmentDescription() {
        return this.treeItemLabelFragmentDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EAttribute getTreeItemLabelFragmentDescription_LabelExpression() {
        return (EAttribute) this.treeItemLabelFragmentDescriptionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EReference getTreeItemLabelFragmentDescription_Style() {
        return (EReference) this.treeItemLabelFragmentDescriptionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EClass getTreeItemLabelElementDescription() {
        return this.treeItemLabelElementDescriptionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public TreeFactory getTreeFactory() {
        return (TreeFactory) this.getEFactoryInstance();
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
        this.treeDescriptionEClass = this.createEClass(TREE_DESCRIPTION);
        this.createEAttribute(this.treeDescriptionEClass, TREE_DESCRIPTION__KIND_EXPRESSION);
        this.createEAttribute(this.treeDescriptionEClass, TREE_DESCRIPTION__ICON_URL_EXPRESSION);
        this.createEAttribute(this.treeDescriptionEClass, TREE_DESCRIPTION__TREE_ITEM_ID_EXPRESSION);
        this.createEAttribute(this.treeDescriptionEClass, TREE_DESCRIPTION__TREE_ITEM_OBJECT_EXPRESSION);
        this.createEAttribute(this.treeDescriptionEClass, TREE_DESCRIPTION__ELEMENTS_EXPRESSION);
        this.createEAttribute(this.treeDescriptionEClass, TREE_DESCRIPTION__HAS_CHILDREN_EXPRESSION);
        this.createEAttribute(this.treeDescriptionEClass, TREE_DESCRIPTION__CHILDREN_EXPRESSION);
        this.createEAttribute(this.treeDescriptionEClass, TREE_DESCRIPTION__PARENT_EXPRESSION);
        this.createEAttribute(this.treeDescriptionEClass, TREE_DESCRIPTION__EDITABLE_EXPRESSION);
        this.createEAttribute(this.treeDescriptionEClass, TREE_DESCRIPTION__SELECTABLE_EXPRESSION);
        this.createEAttribute(this.treeDescriptionEClass, TREE_DESCRIPTION__DELETABLE_EXPRESSION);
        this.createEReference(this.treeDescriptionEClass, TREE_DESCRIPTION__TREE_ITEM_LABEL_DESCRIPTIONS);

        this.treeItemLabelDescriptionEClass = this.createEClass(TREE_ITEM_LABEL_DESCRIPTION);
        this.createEAttribute(this.treeItemLabelDescriptionEClass, TREE_ITEM_LABEL_DESCRIPTION__NAME);
        this.createEAttribute(this.treeItemLabelDescriptionEClass, TREE_ITEM_LABEL_DESCRIPTION__PRECONDITION_EXPRESSION);
        this.createEReference(this.treeItemLabelDescriptionEClass, TREE_ITEM_LABEL_DESCRIPTION__CHILDREN);

        this.treeItemLabelFragmentDescriptionEClass = this.createEClass(TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION);
        this.createEAttribute(this.treeItemLabelFragmentDescriptionEClass, TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION__LABEL_EXPRESSION);
        this.createEReference(this.treeItemLabelFragmentDescriptionEClass, TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION__STYLE);

        this.treeItemLabelElementDescriptionEClass = this.createEClass(TREE_ITEM_LABEL_ELEMENT_DESCRIPTION);
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
        this.treeDescriptionEClass.getESuperTypes().add(theViewPackage.getRepresentationDescription());
        this.treeItemLabelFragmentDescriptionEClass.getESuperTypes().add(this.getTreeItemLabelElementDescription());

        // Initialize classes, features, and operations; add parameters
        this.initEClass(this.treeDescriptionEClass, TreeDescription.class, "TreeDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getTreeDescription_KindExpression(), theViewPackage.getInterpretedExpression(), "kindExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTreeDescription_IconURLExpression(), theViewPackage.getInterpretedExpression(), "iconURLExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTreeDescription_TreeItemIdExpression(), theViewPackage.getInterpretedExpression(), "treeItemIdExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTreeDescription_TreeItemObjectExpression(), theViewPackage.getInterpretedExpression(), "treeItemObjectExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTreeDescription_ElementsExpression(), theViewPackage.getInterpretedExpression(), "elementsExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTreeDescription_HasChildrenExpression(), theViewPackage.getInterpretedExpression(), "hasChildrenExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTreeDescription_ChildrenExpression(), theViewPackage.getInterpretedExpression(), "childrenExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTreeDescription_ParentExpression(), theViewPackage.getInterpretedExpression(), "parentExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT, !IS_VOLATILE,
                IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTreeDescription_EditableExpression(), theViewPackage.getInterpretedExpression(), "editableExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTreeDescription_SelectableExpression(), theViewPackage.getInterpretedExpression(), "selectableExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTreeDescription_DeletableExpression(), theViewPackage.getInterpretedExpression(), "deletableExpression", null, 0, 1, TreeDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTreeDescription_TreeItemLabelDescriptions(), this.getTreeItemLabelDescription(), null, "treeItemLabelDescriptions", null, 0, -1, TreeDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.getTreeDescription_TreeItemLabelDescriptions().getEKeys().add(this.getTreeItemLabelDescription_Name());

        this.initEClass(this.treeItemLabelDescriptionEClass, TreeItemLabelDescription.class, "TreeItemLabelDescription", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getTreeItemLabelDescription_Name(), theViewPackage.getIdentifier(), "name", null, 0, 1, TreeItemLabelDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
                !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEAttribute(this.getTreeItemLabelDescription_PreconditionExpression(), theViewPackage.getInterpretedExpression(), "preconditionExpression", null, 0, 1, TreeItemLabelDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTreeItemLabelDescription_Children(), this.getTreeItemLabelElementDescription(), null, "children", null, 0, -1, TreeItemLabelDescription.class, !IS_TRANSIENT,
                !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.treeItemLabelFragmentDescriptionEClass, TreeItemLabelFragmentDescription.class, "TreeItemLabelFragmentDescription", !IS_ABSTRACT, !IS_INTERFACE,
                IS_GENERATED_INSTANCE_CLASS);
        this.initEAttribute(this.getTreeItemLabelFragmentDescription_LabelExpression(), theViewPackage.getInterpretedExpression(), "labelExpression", null, 0, 1,
                TreeItemLabelFragmentDescription.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        this.initEReference(this.getTreeItemLabelFragmentDescription_Style(), theViewPackage.getTextStyleDescription(), null, "style", null, 0, 1, TreeItemLabelFragmentDescription.class,
                !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        this.initEClass(this.treeItemLabelElementDescriptionEClass, TreeItemLabelElementDescription.class, "TreeItemLabelElementDescription", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        this.createResource(eNS_URI);
    }

} // TreePackageImpl
