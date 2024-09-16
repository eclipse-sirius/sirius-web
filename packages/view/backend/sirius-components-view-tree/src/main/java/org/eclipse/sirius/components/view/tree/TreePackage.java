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
package org.eclipse.sirius.components.view.tree;

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
 * @see org.eclipse.sirius.components.view.tree.TreeFactory
 * @model kind="package"
 * @generated
 */
public interface TreePackage extends EPackage {
    /**
     * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNAME = "tree";

    /**
     * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_URI = "http://www.eclipse.org/sirius-web/tree";

    /**
     * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    String eNS_PREFIX = "tree";

    /**
     * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    TreePackage eINSTANCE = org.eclipse.sirius.components.view.tree.impl.TreePackageImpl.init();

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.tree.impl.TreeDescriptionImpl
     * <em>Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.tree.impl.TreeDescriptionImpl
     * @see org.eclipse.sirius.components.view.tree.impl.TreePackageImpl#getTreeDescription()
     * @generated
     */
    int TREE_DESCRIPTION = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TREE_DESCRIPTION__NAME = ViewPackage.REPRESENTATION_DESCRIPTION__NAME;

    /**
     * The feature id for the '<em><b>Domain Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TREE_DESCRIPTION__DOMAIN_TYPE = ViewPackage.REPRESENTATION_DESCRIPTION__DOMAIN_TYPE;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TREE_DESCRIPTION__PRECONDITION_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION__PRECONDITION_EXPRESSION;

    /**
     * The feature id for the '<em><b>Title Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TREE_DESCRIPTION__TITLE_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION__TITLE_EXPRESSION;

    /**
     * The feature id for the '<em><b>Kind Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TREE_DESCRIPTION__KIND_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Icon URL Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TREE_DESCRIPTION__ICON_URL_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Tree Item Id Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TREE_DESCRIPTION__TREE_ITEM_ID_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Tree Item Object Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TREE_DESCRIPTION__TREE_ITEM_OBJECT_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Elements Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TREE_DESCRIPTION__ELEMENTS_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Has Children Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TREE_DESCRIPTION__HAS_CHILDREN_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Children Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TREE_DESCRIPTION__CHILDREN_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Parent Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TREE_DESCRIPTION__PARENT_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Editable Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TREE_DESCRIPTION__EDITABLE_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Selectable Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TREE_DESCRIPTION__SELECTABLE_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Deletable Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TREE_DESCRIPTION__DELETABLE_EXPRESSION = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 10;

    /**
     * The feature id for the '<em><b>Tree Item Label Descriptions</b></em>' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TREE_DESCRIPTION__TREE_ITEM_LABEL_DESCRIPTIONS = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 11;

    /**
     * The number of structural features of the '<em>Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TREE_DESCRIPTION_FEATURE_COUNT = ViewPackage.REPRESENTATION_DESCRIPTION_FEATURE_COUNT + 12;

    /**
     * The number of operations of the '<em>Description</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TREE_DESCRIPTION_OPERATION_COUNT = ViewPackage.REPRESENTATION_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.sirius.components.view.tree.impl.TreeItemLabelDescriptionImpl
     * <em>Item Label Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.tree.impl.TreeItemLabelDescriptionImpl
     * @see org.eclipse.sirius.components.view.tree.impl.TreePackageImpl#getTreeItemLabelDescription()
     * @generated
     */
    int TREE_ITEM_LABEL_DESCRIPTION = 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TREE_ITEM_LABEL_DESCRIPTION__NAME = 0;

    /**
     * The feature id for the '<em><b>Precondition Expression</b></em>' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TREE_ITEM_LABEL_DESCRIPTION__PRECONDITION_EXPRESSION = 1;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TREE_ITEM_LABEL_DESCRIPTION__CHILDREN = 2;

    /**
     * The number of structural features of the '<em>Item Label Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TREE_ITEM_LABEL_DESCRIPTION_FEATURE_COUNT = 3;

    /**
     * The number of operations of the '<em>Item Label Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TREE_ITEM_LABEL_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.tree.impl.TreeItemLabelElementDescriptionImpl <em>Item Label Element
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.tree.impl.TreeItemLabelElementDescriptionImpl
     * @see org.eclipse.sirius.components.view.tree.impl.TreePackageImpl#getTreeItemLabelElementDescription()
     * @generated
     */
    int TREE_ITEM_LABEL_ELEMENT_DESCRIPTION = 3;

    /**
     * The number of structural features of the '<em>Item Label Element Description</em>' class. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TREE_ITEM_LABEL_ELEMENT_DESCRIPTION_FEATURE_COUNT = 0;

    /**
     * The number of operations of the '<em>Item Label Element Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TREE_ITEM_LABEL_ELEMENT_DESCRIPTION_OPERATION_COUNT = 0;

    /**
     * The meta object id for the
     * '{@link org.eclipse.sirius.components.view.tree.impl.TreeItemLabelFragmentDescriptionImpl <em>Item Label Fragment
     * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see org.eclipse.sirius.components.view.tree.impl.TreeItemLabelFragmentDescriptionImpl
     * @see org.eclipse.sirius.components.view.tree.impl.TreePackageImpl#getTreeItemLabelFragmentDescription()
     * @generated
     */
    int TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION = 2;

    /**
     * The feature id for the '<em><b>Label Expression</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     * @ordered
     */
    int TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION__LABEL_EXPRESSION = TREE_ITEM_LABEL_ELEMENT_DESCRIPTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Style</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION__STYLE = TREE_ITEM_LABEL_ELEMENT_DESCRIPTION_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Item Label Fragment Description</em>' class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION_FEATURE_COUNT = TREE_ITEM_LABEL_ELEMENT_DESCRIPTION_FEATURE_COUNT + 2;

    /**
     * The number of operations of the '<em>Item Label Fragment Description</em>' class. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     * @ordered
     */
    int TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION_OPERATION_COUNT = TREE_ITEM_LABEL_ELEMENT_DESCRIPTION_OPERATION_COUNT + 0;

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.tree.TreeDescription
     * <em>Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Description</em>'.
     * @see org.eclipse.sirius.components.view.tree.TreeDescription
     * @generated
     */
    EClass getTreeDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.tree.TreeDescription#getKindExpression <em>Kind Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Kind Expression</em>'.
     * @see org.eclipse.sirius.components.view.tree.TreeDescription#getKindExpression()
     * @see #getTreeDescription()
     * @generated
     */
    EAttribute getTreeDescription_KindExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.tree.TreeDescription#getIconURLExpression <em>Icon URL
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Icon URL Expression</em>'.
     * @see org.eclipse.sirius.components.view.tree.TreeDescription#getIconURLExpression()
     * @see #getTreeDescription()
     * @generated
     */
    EAttribute getTreeDescription_IconURLExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.tree.TreeDescription#getTreeItemIdExpression <em>Tree Item Id
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Tree Item Id Expression</em>'.
     * @see org.eclipse.sirius.components.view.tree.TreeDescription#getTreeItemIdExpression()
     * @see #getTreeDescription()
     * @generated
     */
    EAttribute getTreeDescription_TreeItemIdExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.tree.TreeDescription#getTreeItemObjectExpression <em>Tree Item Object
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Tree Item Object Expression</em>'.
     * @see org.eclipse.sirius.components.view.tree.TreeDescription#getTreeItemObjectExpression()
     * @see #getTreeDescription()
     * @generated
     */
    EAttribute getTreeDescription_TreeItemObjectExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.tree.TreeDescription#getElementsExpression <em>Elements
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Elements Expression</em>'.
     * @see org.eclipse.sirius.components.view.tree.TreeDescription#getElementsExpression()
     * @see #getTreeDescription()
     * @generated
     */
    EAttribute getTreeDescription_ElementsExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.tree.TreeDescription#getHasChildrenExpression <em>Has Children
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Has Children Expression</em>'.
     * @see org.eclipse.sirius.components.view.tree.TreeDescription#getHasChildrenExpression()
     * @see #getTreeDescription()
     * @generated
     */
    EAttribute getTreeDescription_HasChildrenExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.tree.TreeDescription#getChildrenExpression <em>Children
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Children Expression</em>'.
     * @see org.eclipse.sirius.components.view.tree.TreeDescription#getChildrenExpression()
     * @see #getTreeDescription()
     * @generated
     */
    EAttribute getTreeDescription_ChildrenExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.tree.TreeDescription#getParentExpression <em>Parent Expression</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Parent Expression</em>'.
     * @see org.eclipse.sirius.components.view.tree.TreeDescription#getParentExpression()
     * @see #getTreeDescription()
     * @generated
     */
    EAttribute getTreeDescription_ParentExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.tree.TreeDescription#getEditableExpression <em>Editable
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Editable Expression</em>'.
     * @see org.eclipse.sirius.components.view.tree.TreeDescription#getEditableExpression()
     * @see #getTreeDescription()
     * @generated
     */
    EAttribute getTreeDescription_EditableExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.tree.TreeDescription#getSelectableExpression <em>Selectable
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Selectable Expression</em>'.
     * @see org.eclipse.sirius.components.view.tree.TreeDescription#getSelectableExpression()
     * @see #getTreeDescription()
     * @generated
     */
    EAttribute getTreeDescription_SelectableExpression();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.tree.TreeDescription#getDeletableExpression <em>Deletable
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Deletable Expression</em>'.
     * @see org.eclipse.sirius.components.view.tree.TreeDescription#getDeletableExpression()
     * @see #getTreeDescription()
     * @generated
     */
    EAttribute getTreeDescription_DeletableExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.tree.TreeDescription#getTreeItemLabelDescriptions <em>Tree Item Label
     * Descriptions</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Tree Item Label Descriptions</em>'.
     * @see org.eclipse.sirius.components.view.tree.TreeDescription#getTreeItemLabelDescriptions()
     * @see #getTreeDescription()
     * @generated
     */
    EReference getTreeDescription_TreeItemLabelDescriptions();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.tree.TreeItemLabelDescription
     * <em>Item Label Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Item Label Description</em>'.
     * @see org.eclipse.sirius.components.view.tree.TreeItemLabelDescription
     * @generated
     */
    EClass getTreeItemLabelDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.tree.TreeItemLabelDescription#getName <em>Name</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.sirius.components.view.tree.TreeItemLabelDescription#getName()
     * @see #getTreeItemLabelDescription()
     * @generated
     */
    EAttribute getTreeItemLabelDescription_Name();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.tree.TreeItemLabelDescription#getPreconditionExpression
     * <em>Precondition Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Precondition Expression</em>'.
     * @see org.eclipse.sirius.components.view.tree.TreeItemLabelDescription#getPreconditionExpression()
     * @see #getTreeItemLabelDescription()
     * @generated
     */
    EAttribute getTreeItemLabelDescription_PreconditionExpression();

    /**
     * Returns the meta object for the containment reference list
     * '{@link org.eclipse.sirius.components.view.tree.TreeItemLabelDescription#getChildren <em>Children</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the containment reference list '<em>Children</em>'.
     * @see org.eclipse.sirius.components.view.tree.TreeItemLabelDescription#getChildren()
     * @see #getTreeItemLabelDescription()
     * @generated
     */
    EReference getTreeItemLabelDescription_Children();

    /**
     * Returns the meta object for class
     * '{@link org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription <em>Item Label Fragment
     * Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Item Label Fragment Description</em>'.
     * @see org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription
     * @generated
     */
    EClass getTreeItemLabelFragmentDescription();

    /**
     * Returns the meta object for the attribute
     * '{@link org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription#getLabelExpression <em>Label
     * Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the attribute '<em>Label Expression</em>'.
     * @see org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription#getLabelExpression()
     * @see #getTreeItemLabelFragmentDescription()
     * @generated
     */
    EAttribute getTreeItemLabelFragmentDescription_LabelExpression();

    /**
     * Returns the meta object for the reference
     * '{@link org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription#getStyle <em>Style</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for the reference '<em>Style</em>'.
     * @see org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription#getStyle()
     * @see #getTreeItemLabelFragmentDescription()
     * @generated
     */
    EReference getTreeItemLabelFragmentDescription_Style();

    /**
     * Returns the meta object for class '{@link org.eclipse.sirius.components.view.tree.TreeItemLabelElementDescription
     * <em>Item Label Element Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the meta object for class '<em>Item Label Element Description</em>'.
     * @see org.eclipse.sirius.components.view.tree.TreeItemLabelElementDescription
     * @generated
     */
    EClass getTreeItemLabelElementDescription();

    /**
     * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the factory that creates the instances of the model.
     * @generated
     */
    TreeFactory getTreeFactory();

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
         * The meta object literal for the '{@link org.eclipse.sirius.components.view.tree.impl.TreeDescriptionImpl
         * <em>Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.tree.impl.TreeDescriptionImpl
         * @see org.eclipse.sirius.components.view.tree.impl.TreePackageImpl#getTreeDescription()
         * @generated
         */
        EClass TREE_DESCRIPTION = eINSTANCE.getTreeDescription();

        /**
         * The meta object literal for the '<em><b>Kind Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TREE_DESCRIPTION__KIND_EXPRESSION = eINSTANCE.getTreeDescription_KindExpression();

        /**
         * The meta object literal for the '<em><b>Icon URL Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TREE_DESCRIPTION__ICON_URL_EXPRESSION = eINSTANCE.getTreeDescription_IconURLExpression();

        /**
         * The meta object literal for the '<em><b>Tree Item Id Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TREE_DESCRIPTION__TREE_ITEM_ID_EXPRESSION = eINSTANCE.getTreeDescription_TreeItemIdExpression();

        /**
         * The meta object literal for the '<em><b>Tree Item Object Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TREE_DESCRIPTION__TREE_ITEM_OBJECT_EXPRESSION = eINSTANCE.getTreeDescription_TreeItemObjectExpression();

        /**
         * The meta object literal for the '<em><b>Elements Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TREE_DESCRIPTION__ELEMENTS_EXPRESSION = eINSTANCE.getTreeDescription_ElementsExpression();

        /**
         * The meta object literal for the '<em><b>Has Children Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TREE_DESCRIPTION__HAS_CHILDREN_EXPRESSION = eINSTANCE.getTreeDescription_HasChildrenExpression();

        /**
         * The meta object literal for the '<em><b>Children Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TREE_DESCRIPTION__CHILDREN_EXPRESSION = eINSTANCE.getTreeDescription_ChildrenExpression();

        /**
         * The meta object literal for the '<em><b>Parent Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TREE_DESCRIPTION__PARENT_EXPRESSION = eINSTANCE.getTreeDescription_ParentExpression();

        /**
         * The meta object literal for the '<em><b>Editable Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TREE_DESCRIPTION__EDITABLE_EXPRESSION = eINSTANCE.getTreeDescription_EditableExpression();

        /**
         * The meta object literal for the '<em><b>Selectable Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TREE_DESCRIPTION__SELECTABLE_EXPRESSION = eINSTANCE.getTreeDescription_SelectableExpression();

        /**
         * The meta object literal for the '<em><b>Deletable Expression</b></em>' attribute feature. <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TREE_DESCRIPTION__DELETABLE_EXPRESSION = eINSTANCE.getTreeDescription_DeletableExpression();

        /**
         * The meta object literal for the '<em><b>Tree Item Label Descriptions</b></em>' reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TREE_DESCRIPTION__TREE_ITEM_LABEL_DESCRIPTIONS = eINSTANCE.getTreeDescription_TreeItemLabelDescriptions();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.tree.impl.TreeItemLabelDescriptionImpl <em>Item Label
         * Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.tree.impl.TreeItemLabelDescriptionImpl
         * @see org.eclipse.sirius.components.view.tree.impl.TreePackageImpl#getTreeItemLabelDescription()
         * @generated
         */
        EClass TREE_ITEM_LABEL_DESCRIPTION = eINSTANCE.getTreeItemLabelDescription();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EAttribute TREE_ITEM_LABEL_DESCRIPTION__NAME = eINSTANCE.getTreeItemLabelDescription_Name();

        /**
         * The meta object literal for the '<em><b>Precondition Expression</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TREE_ITEM_LABEL_DESCRIPTION__PRECONDITION_EXPRESSION = eINSTANCE.getTreeItemLabelDescription_PreconditionExpression();

        /**
         * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        EReference TREE_ITEM_LABEL_DESCRIPTION__CHILDREN = eINSTANCE.getTreeItemLabelDescription_Children();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.tree.impl.TreeItemLabelFragmentDescriptionImpl <em>Item Label
         * Fragment Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.tree.impl.TreeItemLabelFragmentDescriptionImpl
         * @see org.eclipse.sirius.components.view.tree.impl.TreePackageImpl#getTreeItemLabelFragmentDescription()
         * @generated
         */
        EClass TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION = eINSTANCE.getTreeItemLabelFragmentDescription();

        /**
         * The meta object literal for the '<em><b>Label Expression</b></em>' attribute feature. <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         *
         * @generated
         */
        EAttribute TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION__LABEL_EXPRESSION = eINSTANCE.getTreeItemLabelFragmentDescription_LabelExpression();

        /**
         * The meta object literal for the '<em><b>Style</b></em>' reference feature. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        EReference TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION__STYLE = eINSTANCE.getTreeItemLabelFragmentDescription_Style();

        /**
         * The meta object literal for the
         * '{@link org.eclipse.sirius.components.view.tree.impl.TreeItemLabelElementDescriptionImpl <em>Item Label
         * Element Description</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @see org.eclipse.sirius.components.view.tree.impl.TreeItemLabelElementDescriptionImpl
         * @see org.eclipse.sirius.components.view.tree.impl.TreePackageImpl#getTreeItemLabelElementDescription()
         * @generated
         */
        EClass TREE_ITEM_LABEL_ELEMENT_DESCRIPTION = eINSTANCE.getTreeItemLabelElementDescription();

    }

} // TreePackage
