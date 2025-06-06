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
package org.eclipse.sirius.components.view.tree.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.tree.CustomTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.ForTreeItemLabelElementDescription;
import org.eclipse.sirius.components.view.tree.IfTreeItemLabelElementDescription;
import org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.TreeDescription;
import org.eclipse.sirius.components.view.tree.TreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.TreeItemLabelDescription;
import org.eclipse.sirius.components.view.tree.TreeItemLabelElementDescription;
import org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription;
import org.eclipse.sirius.components.view.tree.TreePackage;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter <code>createXXX</code>
 * method for each class of the model. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.tree.TreePackage
 * @generated
 */
public class TreeAdapterFactory extends AdapterFactoryImpl {

    /**
     * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected static TreePackage modelPackage;

    /**
     * The switch that delegates to the <code>createXXX</code> methods. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected TreeSwitch<Adapter> modelSwitch = new TreeSwitch<>() {
        @Override
        public Adapter caseTreeDescription(TreeDescription object) {
            return TreeAdapterFactory.this.createTreeDescriptionAdapter();
        }

        @Override
        public Adapter caseTreeItemLabelDescription(TreeItemLabelDescription object) {
            return TreeAdapterFactory.this.createTreeItemLabelDescriptionAdapter();
        }

        @Override
        public Adapter caseIfTreeItemLabelElementDescription(IfTreeItemLabelElementDescription object) {
            return TreeAdapterFactory.this.createIfTreeItemLabelElementDescriptionAdapter();
        }

        @Override
        public Adapter caseForTreeItemLabelElementDescription(ForTreeItemLabelElementDescription object) {
            return TreeAdapterFactory.this.createForTreeItemLabelElementDescriptionAdapter();
        }

        @Override
        public Adapter caseTreeItemLabelFragmentDescription(TreeItemLabelFragmentDescription object) {
            return TreeAdapterFactory.this.createTreeItemLabelFragmentDescriptionAdapter();
        }

        @Override
        public Adapter caseTreeItemLabelElementDescription(TreeItemLabelElementDescription object) {
            return TreeAdapterFactory.this.createTreeItemLabelElementDescriptionAdapter();
        }

        @Override
        public Adapter caseTreeItemContextMenuEntry(TreeItemContextMenuEntry object) {
            return TreeAdapterFactory.this.createTreeItemContextMenuEntryAdapter();
        }

        @Override
        public Adapter caseSingleClickTreeItemContextMenuEntry(SingleClickTreeItemContextMenuEntry object) {
            return TreeAdapterFactory.this.createSingleClickTreeItemContextMenuEntryAdapter();
        }

        @Override
        public Adapter caseFetchTreeItemContextMenuEntry(FetchTreeItemContextMenuEntry object) {
            return TreeAdapterFactory.this.createFetchTreeItemContextMenuEntryAdapter();
        }

        @Override
        public Adapter caseCustomTreeItemContextMenuEntry(CustomTreeItemContextMenuEntry object) {
            return TreeAdapterFactory.this.createCustomTreeItemContextMenuEntryAdapter();
        }

        @Override
        public Adapter caseRepresentationDescription(RepresentationDescription object) {
            return TreeAdapterFactory.this.createRepresentationDescriptionAdapter();
        }

        @Override
        public Adapter defaultCase(EObject object) {
            return TreeAdapterFactory.this.createEObjectAdapter();
        }
    };

    /**
     * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public TreeAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = TreePackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object. <!-- begin-user-doc --> This
     * implementation returns <code>true</code> if the object is either the model's package or is an instance object of
     * the model. <!-- end-user-doc -->
     *
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject) object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param target
     *            the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target) {
        return this.modelSwitch.doSwitch((EObject) target);
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.tree.TreeDescription
     * <em>Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.tree.TreeDescription
     * @generated
     */
    public Adapter createTreeDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.tree.TreeItemLabelDescription <em>Item Label Description</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.tree.TreeItemLabelDescription
     * @generated
     */
    public Adapter createTreeItemLabelDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.tree.IfTreeItemLabelElementDescription <em>If Tree Item Label Element
     * Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.tree.IfTreeItemLabelElementDescription
     * @generated
     */
    public Adapter createIfTreeItemLabelElementDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.tree.ForTreeItemLabelElementDescription <em>For Tree Item Label
     * Element Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.tree.ForTreeItemLabelElementDescription
     * @generated
     */
    public Adapter createForTreeItemLabelElementDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription <em>Item Label Fragment
     * Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription
     * @generated
     */
    public Adapter createTreeItemLabelFragmentDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.tree.TreeItemLabelElementDescription <em>Item Label Element
     * Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.tree.TreeItemLabelElementDescription
     * @generated
     */
    public Adapter createTreeItemLabelElementDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.tree.TreeItemContextMenuEntry <em>Item Context Menu Entry</em>}'. <!--
     * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
     * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.tree.TreeItemContextMenuEntry
     * @generated
     */
    public Adapter createTreeItemContextMenuEntryAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry <em>Single Click Tree Item
     * Context Menu Entry</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @generated
     * @see org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry
     */
    public Adapter createSingleClickTreeItemContextMenuEntryAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry <em>Fetch Tree Item Context Menu
     * Entry</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry
     * @generated
     */
    public Adapter createFetchTreeItemContextMenuEntryAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class
     * '{@link org.eclipse.sirius.components.view.tree.CustomTreeItemContextMenuEntry <em>Custom Tree Item Context Menu
     * Entry</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.tree.CustomTreeItemContextMenuEntry
     * @generated
     */
    public Adapter createCustomTreeItemContextMenuEntryAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.view.RepresentationDescription
     * <em>Representation Description</em>}'. <!-- begin-user-doc --> This default implementation returns null so that
     * we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.view.RepresentationDescription
     * @generated
     */
    public Adapter createRepresentationDescriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case. <!-- begin-user-doc --> This default implementation returns null.
     * <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} // TreeAdapterFactory
