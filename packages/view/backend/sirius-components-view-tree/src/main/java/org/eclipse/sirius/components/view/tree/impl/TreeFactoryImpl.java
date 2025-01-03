/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.sirius.components.view.tree.CustomTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntryKind;
import org.eclipse.sirius.components.view.tree.ForTreeItemLabelElementDescription;
import org.eclipse.sirius.components.view.tree.IfTreeItemLabelElementDescription;
import org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.TreeDescription;
import org.eclipse.sirius.components.view.tree.TreeFactory;
import org.eclipse.sirius.components.view.tree.TreeItemLabelDescription;
import org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription;
import org.eclipse.sirius.components.view.tree.TreePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class TreeFactoryImpl extends EFactoryImpl implements TreeFactory {
    /**
     * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static TreeFactory init() {
        try {
            TreeFactory theTreeFactory = (TreeFactory) EPackage.Registry.INSTANCE.getEFactory(TreePackage.eNS_URI);
            if (theTreeFactory != null) {
                return theTreeFactory;
            }
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new TreeFactoryImpl();
    }

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public TreeFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case TreePackage.TREE_DESCRIPTION:
                return this.createTreeDescription();
            case TreePackage.TREE_ITEM_LABEL_DESCRIPTION:
                return this.createTreeItemLabelDescription();
            case TreePackage.IF_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION:
                return this.createIfTreeItemLabelElementDescription();
            case TreePackage.FOR_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION:
                return this.createForTreeItemLabelElementDescription();
            case TreePackage.TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION:
                return this.createTreeItemLabelFragmentDescription();
            case TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY:
                return this.createSingleClickTreeItemContextMenuEntry();
            case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY:
                return this.createFetchTreeItemContextMenuEntry();
            case TreePackage.CUSTOM_TREE_ITEM_CONTEXT_MENU_ENTRY:
                return this.createCustomTreeItemContextMenuEntry();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
        switch (eDataType.getClassifierID()) {
            case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY_KIND:
                return this.createFetchTreeItemContextMenuEntryKindFromString(eDataType, initialValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
        switch (eDataType.getClassifierID()) {
            case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY_KIND:
                return this.convertFetchTreeItemContextMenuEntryKindToString(eDataType, instanceValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public TreeDescription createTreeDescription() {
        TreeDescriptionImpl treeDescription = new TreeDescriptionImpl();
        return treeDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public TreeItemLabelDescription createTreeItemLabelDescription() {
        TreeItemLabelDescriptionImpl treeItemLabelDescription = new TreeItemLabelDescriptionImpl();
        return treeItemLabelDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public IfTreeItemLabelElementDescription createIfTreeItemLabelElementDescription() {
        IfTreeItemLabelElementDescriptionImpl ifTreeItemLabelElementDescription = new IfTreeItemLabelElementDescriptionImpl();
        return ifTreeItemLabelElementDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ForTreeItemLabelElementDescription createForTreeItemLabelElementDescription() {
        ForTreeItemLabelElementDescriptionImpl forTreeItemLabelElementDescription = new ForTreeItemLabelElementDescriptionImpl();
        return forTreeItemLabelElementDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public TreeItemLabelFragmentDescription createTreeItemLabelFragmentDescription() {
        TreeItemLabelFragmentDescriptionImpl treeItemLabelFragmentDescription = new TreeItemLabelFragmentDescriptionImpl();
        return treeItemLabelFragmentDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public SingleClickTreeItemContextMenuEntry createSingleClickTreeItemContextMenuEntry() {
        SingleClickTreeItemContextMenuEntryImpl singleClickTreeItemContextMenuEntry = new SingleClickTreeItemContextMenuEntryImpl();
        return singleClickTreeItemContextMenuEntry;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public FetchTreeItemContextMenuEntry createFetchTreeItemContextMenuEntry() {
        FetchTreeItemContextMenuEntryImpl fetchTreeItemContextMenuEntry = new FetchTreeItemContextMenuEntryImpl();
        return fetchTreeItemContextMenuEntry;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public CustomTreeItemContextMenuEntry createCustomTreeItemContextMenuEntry() {
        CustomTreeItemContextMenuEntryImpl customTreeItemContextMenuEntry = new CustomTreeItemContextMenuEntryImpl();
        return customTreeItemContextMenuEntry;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public FetchTreeItemContextMenuEntryKind createFetchTreeItemContextMenuEntryKindFromString(EDataType eDataType, String initialValue) {
        FetchTreeItemContextMenuEntryKind result = FetchTreeItemContextMenuEntryKind.get(initialValue);
        if (result == null)
            throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertFetchTreeItemContextMenuEntryKindToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public TreePackage getTreePackage() {
        return (TreePackage) this.getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @deprecated
     * @generated
     */
    @Deprecated
    public static TreePackage getPackage() {
        return TreePackage.eINSTANCE;
    }

} // TreeFactoryImpl
