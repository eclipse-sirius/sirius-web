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

import java.util.Collection;
import java.util.Objects;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.impl.RepresentationDescriptionImpl;
import org.eclipse.sirius.components.view.tree.TreeDescription;
import org.eclipse.sirius.components.view.tree.TreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.TreeItemLabelDescription;
import org.eclipse.sirius.components.view.tree.TreePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Description</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.TreeDescriptionImpl#getKindExpression <em>Kind
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.TreeDescriptionImpl#getTreeItemIconExpression <em>Tree Item
 * Icon Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.TreeDescriptionImpl#getTreeItemIdExpression <em>Tree Item Id
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.TreeDescriptionImpl#getTreeItemObjectExpression <em>Tree Item
 * Object Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.TreeDescriptionImpl#getElementsExpression <em>Elements
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.TreeDescriptionImpl#getHasChildrenExpression <em>Has Children
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.TreeDescriptionImpl#getChildrenExpression <em>Children
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.TreeDescriptionImpl#getParentExpression <em>Parent
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.TreeDescriptionImpl#getEditableExpression <em>Editable
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.TreeDescriptionImpl#getSelectableExpression <em>Selectable
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.TreeDescriptionImpl#getDeletableExpression <em>Deletable
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.TreeDescriptionImpl#getTreeItemLabelDescriptions <em>Tree
 * Item Label Descriptions</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.TreeDescriptionImpl#getContextMenuEntries <em>Context Menu
 * Entries</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TreeDescriptionImpl extends RepresentationDescriptionImpl implements TreeDescription {

    /**
     * The default value of the '{@link #getKindExpression() <em>Kind Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getKindExpression()
     */
    protected static final String KIND_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getKindExpression() <em>Kind Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getKindExpression()
     */
    protected String kindExpression = KIND_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getTreeItemIconExpression() <em>Tree Item Icon Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getTreeItemIconExpression()
     */
    protected static final String TREE_ITEM_ICON_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTreeItemIconExpression() <em>Tree Item Icon Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getTreeItemIconExpression()
     */
    protected String treeItemIconExpression = TREE_ITEM_ICON_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getTreeItemIdExpression() <em>Tree Item Id Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getTreeItemIdExpression()
     */
    protected static final String TREE_ITEM_ID_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTreeItemIdExpression() <em>Tree Item Id Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getTreeItemIdExpression()
     */
    protected String treeItemIdExpression = TREE_ITEM_ID_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getTreeItemObjectExpression() <em>Tree Item Object Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getTreeItemObjectExpression()
     */
    protected static final String TREE_ITEM_OBJECT_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTreeItemObjectExpression() <em>Tree Item Object Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getTreeItemObjectExpression()
     */
    protected String treeItemObjectExpression = TREE_ITEM_OBJECT_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getElementsExpression() <em>Elements Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getElementsExpression()
     */
    protected static final String ELEMENTS_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getElementsExpression() <em>Elements Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getElementsExpression()
     */
    protected String elementsExpression = ELEMENTS_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getHasChildrenExpression() <em>Has Children Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getHasChildrenExpression()
     */
    protected static final String HAS_CHILDREN_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getHasChildrenExpression() <em>Has Children Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getHasChildrenExpression()
     */
    protected String hasChildrenExpression = HAS_CHILDREN_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getChildrenExpression() <em>Children Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getChildrenExpression()
     */
    protected static final String CHILDREN_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getChildrenExpression() <em>Children Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getChildrenExpression()
     */
    protected String childrenExpression = CHILDREN_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getParentExpression() <em>Parent Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getParentExpression()
     */
    protected static final String PARENT_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getParentExpression() <em>Parent Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getParentExpression()
     */
    protected String parentExpression = PARENT_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getEditableExpression() <em>Editable Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getEditableExpression()
     */
    protected static final String EDITABLE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getEditableExpression() <em>Editable Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getEditableExpression()
     */
    protected String editableExpression = EDITABLE_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getSelectableExpression() <em>Selectable Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getSelectableExpression()
     */
    protected static final String SELECTABLE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSelectableExpression() <em>Selectable Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getSelectableExpression()
     */
    protected String selectableExpression = SELECTABLE_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getDeletableExpression() <em>Deletable Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getDeletableExpression()
     */
    protected static final String DELETABLE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDeletableExpression() <em>Deletable Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getDeletableExpression()
     */
    protected String deletableExpression = DELETABLE_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getTreeItemLabelDescriptions() <em>Tree Item Label Descriptions</em>}' containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getTreeItemLabelDescriptions()
     */
    protected EList<TreeItemLabelDescription> treeItemLabelDescriptions;

    /**
     * The cached value of the '{@link #getContextMenuEntries() <em>Context Menu Entries</em>}' reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     * @ordered
     * @see #getContextMenuEntries()
     */
    protected EList<TreeItemContextMenuEntry> contextMenuEntries;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected TreeDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return TreePackage.Literals.TREE_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getKindExpression() {
        return this.kindExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setKindExpression(String newKindExpression) {
        String oldKindExpression = this.kindExpression;
        this.kindExpression = newKindExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.TREE_DESCRIPTION__KIND_EXPRESSION, oldKindExpression, this.kindExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getTreeItemIconExpression() {
        return this.treeItemIconExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setTreeItemIconExpression(String newTreeItemIconExpression) {
        String oldTreeItemIconExpression = this.treeItemIconExpression;
        this.treeItemIconExpression = newTreeItemIconExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.TREE_DESCRIPTION__TREE_ITEM_ICON_EXPRESSION, oldTreeItemIconExpression, this.treeItemIconExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getTreeItemIdExpression() {
        return this.treeItemIdExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setTreeItemIdExpression(String newTreeItemIdExpression) {
        String oldTreeItemIdExpression = this.treeItemIdExpression;
        this.treeItemIdExpression = newTreeItemIdExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.TREE_DESCRIPTION__TREE_ITEM_ID_EXPRESSION, oldTreeItemIdExpression, this.treeItemIdExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getTreeItemObjectExpression() {
        return this.treeItemObjectExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setTreeItemObjectExpression(String newTreeItemObjectExpression) {
        String oldTreeItemObjectExpression = this.treeItemObjectExpression;
        this.treeItemObjectExpression = newTreeItemObjectExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.TREE_DESCRIPTION__TREE_ITEM_OBJECT_EXPRESSION, oldTreeItemObjectExpression, this.treeItemObjectExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getElementsExpression() {
        return this.elementsExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setElementsExpression(String newElementsExpression) {
        String oldElementsExpression = this.elementsExpression;
        this.elementsExpression = newElementsExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.TREE_DESCRIPTION__ELEMENTS_EXPRESSION, oldElementsExpression, this.elementsExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getHasChildrenExpression() {
        return this.hasChildrenExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setHasChildrenExpression(String newHasChildrenExpression) {
        String oldHasChildrenExpression = this.hasChildrenExpression;
        this.hasChildrenExpression = newHasChildrenExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.TREE_DESCRIPTION__HAS_CHILDREN_EXPRESSION, oldHasChildrenExpression, this.hasChildrenExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getChildrenExpression() {
        return this.childrenExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setChildrenExpression(String newChildrenExpression) {
        String oldChildrenExpression = this.childrenExpression;
        this.childrenExpression = newChildrenExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.TREE_DESCRIPTION__CHILDREN_EXPRESSION, oldChildrenExpression, this.childrenExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getParentExpression() {
        return this.parentExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setParentExpression(String newParentExpression) {
        String oldParentExpression = this.parentExpression;
        this.parentExpression = newParentExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.TREE_DESCRIPTION__PARENT_EXPRESSION, oldParentExpression, this.parentExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getEditableExpression() {
        return this.editableExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setEditableExpression(String newEditableExpression) {
        String oldEditableExpression = this.editableExpression;
        this.editableExpression = newEditableExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.TREE_DESCRIPTION__EDITABLE_EXPRESSION, oldEditableExpression, this.editableExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getSelectableExpression() {
        return this.selectableExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSelectableExpression(String newSelectableExpression) {
        String oldSelectableExpression = this.selectableExpression;
        this.selectableExpression = newSelectableExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.TREE_DESCRIPTION__SELECTABLE_EXPRESSION, oldSelectableExpression, this.selectableExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getDeletableExpression() {
        return this.deletableExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDeletableExpression(String newDeletableExpression) {
        String oldDeletableExpression = this.deletableExpression;
        this.deletableExpression = newDeletableExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.TREE_DESCRIPTION__DELETABLE_EXPRESSION, oldDeletableExpression, this.deletableExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<TreeItemLabelDescription> getTreeItemLabelDescriptions() {
        if (this.treeItemLabelDescriptions == null) {
            this.treeItemLabelDescriptions = new EObjectContainmentEList<>(TreeItemLabelDescription.class, this, TreePackage.TREE_DESCRIPTION__TREE_ITEM_LABEL_DESCRIPTIONS);
        }
        return this.treeItemLabelDescriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<TreeItemContextMenuEntry> getContextMenuEntries() {
        if (this.contextMenuEntries == null) {
            this.contextMenuEntries = new EObjectContainmentEList<>(TreeItemContextMenuEntry.class, this, TreePackage.TREE_DESCRIPTION__CONTEXT_MENU_ENTRIES);
        }
        return this.contextMenuEntries;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case TreePackage.TREE_DESCRIPTION__TREE_ITEM_LABEL_DESCRIPTIONS:
                return ((InternalEList<?>) this.getTreeItemLabelDescriptions()).basicRemove(otherEnd, msgs);
            case TreePackage.TREE_DESCRIPTION__CONTEXT_MENU_ENTRIES:
                return ((InternalEList<?>) this.getContextMenuEntries()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case TreePackage.TREE_DESCRIPTION__KIND_EXPRESSION:
                return this.getKindExpression();
            case TreePackage.TREE_DESCRIPTION__TREE_ITEM_ICON_EXPRESSION:
                return this.getTreeItemIconExpression();
            case TreePackage.TREE_DESCRIPTION__TREE_ITEM_ID_EXPRESSION:
                return this.getTreeItemIdExpression();
            case TreePackage.TREE_DESCRIPTION__TREE_ITEM_OBJECT_EXPRESSION:
                return this.getTreeItemObjectExpression();
            case TreePackage.TREE_DESCRIPTION__ELEMENTS_EXPRESSION:
                return this.getElementsExpression();
            case TreePackage.TREE_DESCRIPTION__HAS_CHILDREN_EXPRESSION:
                return this.getHasChildrenExpression();
            case TreePackage.TREE_DESCRIPTION__CHILDREN_EXPRESSION:
                return this.getChildrenExpression();
            case TreePackage.TREE_DESCRIPTION__PARENT_EXPRESSION:
                return this.getParentExpression();
            case TreePackage.TREE_DESCRIPTION__EDITABLE_EXPRESSION:
                return this.getEditableExpression();
            case TreePackage.TREE_DESCRIPTION__SELECTABLE_EXPRESSION:
                return this.getSelectableExpression();
            case TreePackage.TREE_DESCRIPTION__DELETABLE_EXPRESSION:
                return this.getDeletableExpression();
            case TreePackage.TREE_DESCRIPTION__TREE_ITEM_LABEL_DESCRIPTIONS:
                return this.getTreeItemLabelDescriptions();
            case TreePackage.TREE_DESCRIPTION__CONTEXT_MENU_ENTRIES:
                return this.getContextMenuEntries();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case TreePackage.TREE_DESCRIPTION__KIND_EXPRESSION:
                this.setKindExpression((String) newValue);
                return;
            case TreePackage.TREE_DESCRIPTION__TREE_ITEM_ICON_EXPRESSION:
                this.setTreeItemIconExpression((String) newValue);
                return;
            case TreePackage.TREE_DESCRIPTION__TREE_ITEM_ID_EXPRESSION:
                this.setTreeItemIdExpression((String) newValue);
                return;
            case TreePackage.TREE_DESCRIPTION__TREE_ITEM_OBJECT_EXPRESSION:
                this.setTreeItemObjectExpression((String) newValue);
                return;
            case TreePackage.TREE_DESCRIPTION__ELEMENTS_EXPRESSION:
                this.setElementsExpression((String) newValue);
                return;
            case TreePackage.TREE_DESCRIPTION__HAS_CHILDREN_EXPRESSION:
                this.setHasChildrenExpression((String) newValue);
                return;
            case TreePackage.TREE_DESCRIPTION__CHILDREN_EXPRESSION:
                this.setChildrenExpression((String) newValue);
                return;
            case TreePackage.TREE_DESCRIPTION__PARENT_EXPRESSION:
                this.setParentExpression((String) newValue);
                return;
            case TreePackage.TREE_DESCRIPTION__EDITABLE_EXPRESSION:
                this.setEditableExpression((String) newValue);
                return;
            case TreePackage.TREE_DESCRIPTION__SELECTABLE_EXPRESSION:
                this.setSelectableExpression((String) newValue);
                return;
            case TreePackage.TREE_DESCRIPTION__DELETABLE_EXPRESSION:
                this.setDeletableExpression((String) newValue);
                return;
            case TreePackage.TREE_DESCRIPTION__TREE_ITEM_LABEL_DESCRIPTIONS:
                this.getTreeItemLabelDescriptions().clear();
                this.getTreeItemLabelDescriptions().addAll((Collection<? extends TreeItemLabelDescription>) newValue);
                return;
            case TreePackage.TREE_DESCRIPTION__CONTEXT_MENU_ENTRIES:
                this.getContextMenuEntries().clear();
                this.getContextMenuEntries().addAll((Collection<? extends TreeItemContextMenuEntry>) newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case TreePackage.TREE_DESCRIPTION__KIND_EXPRESSION:
                this.setKindExpression(KIND_EXPRESSION_EDEFAULT);
                return;
            case TreePackage.TREE_DESCRIPTION__TREE_ITEM_ICON_EXPRESSION:
                this.setTreeItemIconExpression(TREE_ITEM_ICON_EXPRESSION_EDEFAULT);
                return;
            case TreePackage.TREE_DESCRIPTION__TREE_ITEM_ID_EXPRESSION:
                this.setTreeItemIdExpression(TREE_ITEM_ID_EXPRESSION_EDEFAULT);
                return;
            case TreePackage.TREE_DESCRIPTION__TREE_ITEM_OBJECT_EXPRESSION:
                this.setTreeItemObjectExpression(TREE_ITEM_OBJECT_EXPRESSION_EDEFAULT);
                return;
            case TreePackage.TREE_DESCRIPTION__ELEMENTS_EXPRESSION:
                this.setElementsExpression(ELEMENTS_EXPRESSION_EDEFAULT);
                return;
            case TreePackage.TREE_DESCRIPTION__HAS_CHILDREN_EXPRESSION:
                this.setHasChildrenExpression(HAS_CHILDREN_EXPRESSION_EDEFAULT);
                return;
            case TreePackage.TREE_DESCRIPTION__CHILDREN_EXPRESSION:
                this.setChildrenExpression(CHILDREN_EXPRESSION_EDEFAULT);
                return;
            case TreePackage.TREE_DESCRIPTION__PARENT_EXPRESSION:
                this.setParentExpression(PARENT_EXPRESSION_EDEFAULT);
                return;
            case TreePackage.TREE_DESCRIPTION__EDITABLE_EXPRESSION:
                this.setEditableExpression(EDITABLE_EXPRESSION_EDEFAULT);
                return;
            case TreePackage.TREE_DESCRIPTION__SELECTABLE_EXPRESSION:
                this.setSelectableExpression(SELECTABLE_EXPRESSION_EDEFAULT);
                return;
            case TreePackage.TREE_DESCRIPTION__DELETABLE_EXPRESSION:
                this.setDeletableExpression(DELETABLE_EXPRESSION_EDEFAULT);
                return;
            case TreePackage.TREE_DESCRIPTION__TREE_ITEM_LABEL_DESCRIPTIONS:
                this.getTreeItemLabelDescriptions().clear();
                return;
            case TreePackage.TREE_DESCRIPTION__CONTEXT_MENU_ENTRIES:
                this.getContextMenuEntries().clear();
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case TreePackage.TREE_DESCRIPTION__KIND_EXPRESSION:
            	return !Objects.equals(KIND_EXPRESSION_EDEFAULT, this.kindExpression);
            case TreePackage.TREE_DESCRIPTION__TREE_ITEM_ICON_EXPRESSION:
                return !Objects.equals(TREE_ITEM_ICON_EXPRESSION_EDEFAULT, this.treeItemIconExpression);
            case TreePackage.TREE_DESCRIPTION__TREE_ITEM_ID_EXPRESSION:
                return !Objects.equals(TREE_ITEM_ID_EXPRESSION_EDEFAULT, this.treeItemIdExpression);
            case TreePackage.TREE_DESCRIPTION__TREE_ITEM_OBJECT_EXPRESSION:
                return !Objects.equals(TREE_ITEM_OBJECT_EXPRESSION_EDEFAULT, this.treeItemObjectExpression);
            case TreePackage.TREE_DESCRIPTION__ELEMENTS_EXPRESSION:
                return !Objects.equals(ELEMENTS_EXPRESSION_EDEFAULT, this.elementsExpression);
            case TreePackage.TREE_DESCRIPTION__HAS_CHILDREN_EXPRESSION:
                return !Objects.equals(HAS_CHILDREN_EXPRESSION_EDEFAULT, this.hasChildrenExpression);
            case TreePackage.TREE_DESCRIPTION__CHILDREN_EXPRESSION:
                return !Objects.equals(CHILDREN_EXPRESSION_EDEFAULT, this.childrenExpression);
            case TreePackage.TREE_DESCRIPTION__PARENT_EXPRESSION:
                return !Objects.equals(PARENT_EXPRESSION_EDEFAULT, this.parentExpression);
            case TreePackage.TREE_DESCRIPTION__EDITABLE_EXPRESSION:
                return !Objects.equals(EDITABLE_EXPRESSION_EDEFAULT, this.editableExpression);
            case TreePackage.TREE_DESCRIPTION__SELECTABLE_EXPRESSION:
                return !Objects.equals(SELECTABLE_EXPRESSION_EDEFAULT, this.selectableExpression);
            case TreePackage.TREE_DESCRIPTION__DELETABLE_EXPRESSION:
                return !Objects.equals(DELETABLE_EXPRESSION_EDEFAULT, this.deletableExpression);
            case TreePackage.TREE_DESCRIPTION__TREE_ITEM_LABEL_DESCRIPTIONS:
                return this.treeItemLabelDescriptions != null && !this.treeItemLabelDescriptions.isEmpty();
            case TreePackage.TREE_DESCRIPTION__CONTEXT_MENU_ENTRIES:
                return this.contextMenuEntries != null && !this.contextMenuEntries.isEmpty();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        if (this.eIsProxy())
            return super.toString();

        String result = super.toString() + " (kindExpression: " +
                this.kindExpression +
                ", treeItemIconExpression: " +
                this.treeItemIconExpression +
                ", treeItemIdExpression: " +
                this.treeItemIdExpression +
                ", treeItemObjectExpression: " +
                this.treeItemObjectExpression +
                ", elementsExpression: " +
                this.elementsExpression +
                ", hasChildrenExpression: " +
                this.hasChildrenExpression +
                ", childrenExpression: " +
                this.childrenExpression +
                ", parentExpression: " +
                this.parentExpression +
                ", editableExpression: " +
                this.editableExpression +
                ", selectableExpression: " +
                this.selectableExpression +
                ", deletableExpression: " +
                this.deletableExpression +
                ')';
        return result;
    }

} // TreeDescriptionImpl
