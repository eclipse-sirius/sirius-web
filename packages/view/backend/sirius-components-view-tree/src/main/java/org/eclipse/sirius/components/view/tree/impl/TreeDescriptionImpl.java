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
 * <li>{@link org.eclipse.sirius.components.view.tree.impl.TreeDescriptionImpl#getIconURLExpression <em>Icon URL
 * Expression</em>}</li>
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
 * </ul>
 *
 * @generated
 */
public class TreeDescriptionImpl extends RepresentationDescriptionImpl implements TreeDescription {
    /**
     * The default value of the '{@link #getKindExpression() <em>Kind Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getKindExpression()
     * @generated
     * @ordered
     */
    protected static final String KIND_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getKindExpression() <em>Kind Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getKindExpression()
     * @generated
     * @ordered
     */
    protected String kindExpression = KIND_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getIconURLExpression() <em>Icon URL Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIconURLExpression()
     * @generated
     * @ordered
     */
    protected static final String ICON_URL_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getIconURLExpression() <em>Icon URL Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getIconURLExpression()
     * @generated
     * @ordered
     */
    protected String iconURLExpression = ICON_URL_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getTreeItemIdExpression() <em>Tree Item Id Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTreeItemIdExpression()
     * @generated
     * @ordered
     */
    protected static final String TREE_ITEM_ID_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTreeItemIdExpression() <em>Tree Item Id Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTreeItemIdExpression()
     * @generated
     * @ordered
     */
    protected String treeItemIdExpression = TREE_ITEM_ID_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getTreeItemObjectExpression() <em>Tree Item Object Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTreeItemObjectExpression()
     * @generated
     * @ordered
     */
    protected static final String TREE_ITEM_OBJECT_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTreeItemObjectExpression() <em>Tree Item Object Expression</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTreeItemObjectExpression()
     * @generated
     * @ordered
     */
    protected String treeItemObjectExpression = TREE_ITEM_OBJECT_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getElementsExpression() <em>Elements Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getElementsExpression()
     * @generated
     * @ordered
     */
    protected static final String ELEMENTS_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getElementsExpression() <em>Elements Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getElementsExpression()
     * @generated
     * @ordered
     */
    protected String elementsExpression = ELEMENTS_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getHasChildrenExpression() <em>Has Children Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getHasChildrenExpression()
     * @generated
     * @ordered
     */
    protected static final String HAS_CHILDREN_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getHasChildrenExpression() <em>Has Children Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getHasChildrenExpression()
     * @generated
     * @ordered
     */
    protected String hasChildrenExpression = HAS_CHILDREN_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getChildrenExpression() <em>Children Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getChildrenExpression()
     * @generated
     * @ordered
     */
    protected static final String CHILDREN_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getChildrenExpression() <em>Children Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getChildrenExpression()
     * @generated
     * @ordered
     */
    protected String childrenExpression = CHILDREN_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getParentExpression() <em>Parent Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getParentExpression()
     * @generated
     * @ordered
     */
    protected static final String PARENT_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getParentExpression() <em>Parent Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getParentExpression()
     * @generated
     * @ordered
     */
    protected String parentExpression = PARENT_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getEditableExpression() <em>Editable Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getEditableExpression()
     * @generated
     * @ordered
     */
    protected static final String EDITABLE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getEditableExpression() <em>Editable Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getEditableExpression()
     * @generated
     * @ordered
     */
    protected String editableExpression = EDITABLE_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getSelectableExpression() <em>Selectable Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSelectableExpression()
     * @generated
     * @ordered
     */
    protected static final String SELECTABLE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSelectableExpression() <em>Selectable Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSelectableExpression()
     * @generated
     * @ordered
     */
    protected String selectableExpression = SELECTABLE_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getDeletableExpression() <em>Deletable Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDeletableExpression()
     * @generated
     * @ordered
     */
    protected static final String DELETABLE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDeletableExpression() <em>Deletable Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDeletableExpression()
     * @generated
     * @ordered
     */
    protected String deletableExpression = DELETABLE_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getTreeItemLabelDescriptions() <em>Tree Item Label Descriptions</em>}'
     * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTreeItemLabelDescriptions()
     * @generated
     * @ordered
     */
    protected EList<TreeItemLabelDescription> treeItemLabelDescriptions;

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
    public String getIconURLExpression() {
        return this.iconURLExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setIconURLExpression(String newIconURLExpression) {
        String oldIconURLExpression = this.iconURLExpression;
        this.iconURLExpression = newIconURLExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.TREE_DESCRIPTION__ICON_URL_EXPRESSION, oldIconURLExpression, this.iconURLExpression));
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
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case TreePackage.TREE_DESCRIPTION__TREE_ITEM_LABEL_DESCRIPTIONS:
                return ((InternalEList<?>) this.getTreeItemLabelDescriptions()).basicRemove(otherEnd, msgs);
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
            case TreePackage.TREE_DESCRIPTION__ICON_URL_EXPRESSION:
                return this.getIconURLExpression();
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
            case TreePackage.TREE_DESCRIPTION__ICON_URL_EXPRESSION:
                this.setIconURLExpression((String) newValue);
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
            case TreePackage.TREE_DESCRIPTION__ICON_URL_EXPRESSION:
                this.setIconURLExpression(ICON_URL_EXPRESSION_EDEFAULT);
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
                return KIND_EXPRESSION_EDEFAULT == null ? this.kindExpression != null : !KIND_EXPRESSION_EDEFAULT.equals(this.kindExpression);
            case TreePackage.TREE_DESCRIPTION__ICON_URL_EXPRESSION:
                return ICON_URL_EXPRESSION_EDEFAULT == null ? this.iconURLExpression != null : !ICON_URL_EXPRESSION_EDEFAULT.equals(this.iconURLExpression);
            case TreePackage.TREE_DESCRIPTION__TREE_ITEM_ID_EXPRESSION:
                return TREE_ITEM_ID_EXPRESSION_EDEFAULT == null ? this.treeItemIdExpression != null : !TREE_ITEM_ID_EXPRESSION_EDEFAULT.equals(this.treeItemIdExpression);
            case TreePackage.TREE_DESCRIPTION__TREE_ITEM_OBJECT_EXPRESSION:
                return TREE_ITEM_OBJECT_EXPRESSION_EDEFAULT == null ? this.treeItemObjectExpression != null : !TREE_ITEM_OBJECT_EXPRESSION_EDEFAULT.equals(this.treeItemObjectExpression);
            case TreePackage.TREE_DESCRIPTION__ELEMENTS_EXPRESSION:
                return ELEMENTS_EXPRESSION_EDEFAULT == null ? this.elementsExpression != null : !ELEMENTS_EXPRESSION_EDEFAULT.equals(this.elementsExpression);
            case TreePackage.TREE_DESCRIPTION__HAS_CHILDREN_EXPRESSION:
                return HAS_CHILDREN_EXPRESSION_EDEFAULT == null ? this.hasChildrenExpression != null : !HAS_CHILDREN_EXPRESSION_EDEFAULT.equals(this.hasChildrenExpression);
            case TreePackage.TREE_DESCRIPTION__CHILDREN_EXPRESSION:
                return CHILDREN_EXPRESSION_EDEFAULT == null ? this.childrenExpression != null : !CHILDREN_EXPRESSION_EDEFAULT.equals(this.childrenExpression);
            case TreePackage.TREE_DESCRIPTION__PARENT_EXPRESSION:
                return PARENT_EXPRESSION_EDEFAULT == null ? this.parentExpression != null : !PARENT_EXPRESSION_EDEFAULT.equals(this.parentExpression);
            case TreePackage.TREE_DESCRIPTION__EDITABLE_EXPRESSION:
                return EDITABLE_EXPRESSION_EDEFAULT == null ? this.editableExpression != null : !EDITABLE_EXPRESSION_EDEFAULT.equals(this.editableExpression);
            case TreePackage.TREE_DESCRIPTION__SELECTABLE_EXPRESSION:
                return SELECTABLE_EXPRESSION_EDEFAULT == null ? this.selectableExpression != null : !SELECTABLE_EXPRESSION_EDEFAULT.equals(this.selectableExpression);
            case TreePackage.TREE_DESCRIPTION__DELETABLE_EXPRESSION:
                return DELETABLE_EXPRESSION_EDEFAULT == null ? this.deletableExpression != null : !DELETABLE_EXPRESSION_EDEFAULT.equals(this.deletableExpression);
            case TreePackage.TREE_DESCRIPTION__TREE_ITEM_LABEL_DESCRIPTIONS:
                return this.treeItemLabelDescriptions != null && !this.treeItemLabelDescriptions.isEmpty();
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

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (kindExpression: ");
        result.append(this.kindExpression);
        result.append(", iconURLExpression: ");
        result.append(this.iconURLExpression);
        result.append(", treeItemIdExpression: ");
        result.append(this.treeItemIdExpression);
        result.append(", treeItemObjectExpression: ");
        result.append(this.treeItemObjectExpression);
        result.append(", elementsExpression: ");
        result.append(this.elementsExpression);
        result.append(", hasChildrenExpression: ");
        result.append(this.hasChildrenExpression);
        result.append(", childrenExpression: ");
        result.append(this.childrenExpression);
        result.append(", parentExpression: ");
        result.append(this.parentExpression);
        result.append(", editableExpression: ");
        result.append(this.editableExpression);
        result.append(", selectableExpression: ");
        result.append(this.selectableExpression);
        result.append(", deletableExpression: ");
        result.append(this.deletableExpression);
        result.append(')');
        return result.toString();
    }

} // TreeDescriptionImpl
