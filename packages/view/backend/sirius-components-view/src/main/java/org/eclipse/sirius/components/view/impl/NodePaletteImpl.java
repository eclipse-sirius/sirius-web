/**
 * Copyright (c) 2021, 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.sirius.components.view.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.DeleteTool;
import org.eclipse.sirius.components.view.EdgeTool;
import org.eclipse.sirius.components.view.LabelEditTool;
import org.eclipse.sirius.components.view.NodePalette;
import org.eclipse.sirius.components.view.NodeTool;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Node Palette</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.NodePaletteImpl#getDeleteTool <em>Delete Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.NodePaletteImpl#getLabelEditTool <em>Label Edit Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.NodePaletteImpl#getNodeTools <em>Node Tools</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.NodePaletteImpl#getEdgeTools <em>Edge Tools</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NodePaletteImpl extends MinimalEObjectImpl.Container implements NodePalette {
    /**
     * The cached value of the '{@link #getDeleteTool() <em>Delete Tool</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDeleteTool()
     * @generated
     * @ordered
     */
    protected DeleteTool deleteTool;

    /**
     * The cached value of the '{@link #getLabelEditTool() <em>Label Edit Tool</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getLabelEditTool()
     * @generated
     * @ordered
     */
    protected LabelEditTool labelEditTool;

    /**
     * The cached value of the '{@link #getNodeTools() <em>Node Tools</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getNodeTools()
     * @generated
     * @ordered
     */
    protected EList<NodeTool> nodeTools;

    /**
     * The cached value of the '{@link #getEdgeTools() <em>Edge Tools</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getEdgeTools()
     * @generated
     * @ordered
     */
    protected EList<EdgeTool> edgeTools;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected NodePaletteImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.NODE_PALETTE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DeleteTool getDeleteTool() {
        return this.deleteTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetDeleteTool(DeleteTool newDeleteTool, NotificationChain msgs) {
        DeleteTool oldDeleteTool = this.deleteTool;
        this.deleteTool = newDeleteTool;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ViewPackage.NODE_PALETTE__DELETE_TOOL, oldDeleteTool, newDeleteTool);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDeleteTool(DeleteTool newDeleteTool) {
        if (newDeleteTool != this.deleteTool) {
            NotificationChain msgs = null;
            if (this.deleteTool != null)
                msgs = ((InternalEObject) this.deleteTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ViewPackage.NODE_PALETTE__DELETE_TOOL, null, msgs);
            if (newDeleteTool != null)
                msgs = ((InternalEObject) newDeleteTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ViewPackage.NODE_PALETTE__DELETE_TOOL, null, msgs);
            msgs = this.basicSetDeleteTool(newDeleteTool, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.NODE_PALETTE__DELETE_TOOL, newDeleteTool, newDeleteTool));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public LabelEditTool getLabelEditTool() {
        return this.labelEditTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetLabelEditTool(LabelEditTool newLabelEditTool, NotificationChain msgs) {
        LabelEditTool oldLabelEditTool = this.labelEditTool;
        this.labelEditTool = newLabelEditTool;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ViewPackage.NODE_PALETTE__LABEL_EDIT_TOOL, oldLabelEditTool, newLabelEditTool);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setLabelEditTool(LabelEditTool newLabelEditTool) {
        if (newLabelEditTool != this.labelEditTool) {
            NotificationChain msgs = null;
            if (this.labelEditTool != null)
                msgs = ((InternalEObject) this.labelEditTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ViewPackage.NODE_PALETTE__LABEL_EDIT_TOOL, null, msgs);
            if (newLabelEditTool != null)
                msgs = ((InternalEObject) newLabelEditTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ViewPackage.NODE_PALETTE__LABEL_EDIT_TOOL, null, msgs);
            msgs = this.basicSetLabelEditTool(newLabelEditTool, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.NODE_PALETTE__LABEL_EDIT_TOOL, newLabelEditTool, newLabelEditTool));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<NodeTool> getNodeTools() {
        if (this.nodeTools == null) {
            this.nodeTools = new EObjectContainmentEList<>(NodeTool.class, this, ViewPackage.NODE_PALETTE__NODE_TOOLS);
        }
        return this.nodeTools;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<EdgeTool> getEdgeTools() {
        if (this.edgeTools == null) {
            this.edgeTools = new EObjectContainmentEList<>(EdgeTool.class, this, ViewPackage.NODE_PALETTE__EDGE_TOOLS);
        }
        return this.edgeTools;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ViewPackage.NODE_PALETTE__DELETE_TOOL:
                return this.basicSetDeleteTool(null, msgs);
            case ViewPackage.NODE_PALETTE__LABEL_EDIT_TOOL:
                return this.basicSetLabelEditTool(null, msgs);
            case ViewPackage.NODE_PALETTE__NODE_TOOLS:
                return ((InternalEList<?>) this.getNodeTools()).basicRemove(otherEnd, msgs);
            case ViewPackage.NODE_PALETTE__EDGE_TOOLS:
                return ((InternalEList<?>) this.getEdgeTools()).basicRemove(otherEnd, msgs);
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
            case ViewPackage.NODE_PALETTE__DELETE_TOOL:
                return this.getDeleteTool();
            case ViewPackage.NODE_PALETTE__LABEL_EDIT_TOOL:
                return this.getLabelEditTool();
            case ViewPackage.NODE_PALETTE__NODE_TOOLS:
                return this.getNodeTools();
            case ViewPackage.NODE_PALETTE__EDGE_TOOLS:
                return this.getEdgeTools();
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
            case ViewPackage.NODE_PALETTE__DELETE_TOOL:
                this.setDeleteTool((DeleteTool) newValue);
                return;
            case ViewPackage.NODE_PALETTE__LABEL_EDIT_TOOL:
                this.setLabelEditTool((LabelEditTool) newValue);
                return;
            case ViewPackage.NODE_PALETTE__NODE_TOOLS:
                this.getNodeTools().clear();
                this.getNodeTools().addAll((Collection<? extends NodeTool>) newValue);
                return;
            case ViewPackage.NODE_PALETTE__EDGE_TOOLS:
                this.getEdgeTools().clear();
                this.getEdgeTools().addAll((Collection<? extends EdgeTool>) newValue);
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
            case ViewPackage.NODE_PALETTE__DELETE_TOOL:
                this.setDeleteTool((DeleteTool) null);
                return;
            case ViewPackage.NODE_PALETTE__LABEL_EDIT_TOOL:
                this.setLabelEditTool((LabelEditTool) null);
                return;
            case ViewPackage.NODE_PALETTE__NODE_TOOLS:
                this.getNodeTools().clear();
                return;
            case ViewPackage.NODE_PALETTE__EDGE_TOOLS:
                this.getEdgeTools().clear();
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
            case ViewPackage.NODE_PALETTE__DELETE_TOOL:
                return this.deleteTool != null;
            case ViewPackage.NODE_PALETTE__LABEL_EDIT_TOOL:
                return this.labelEditTool != null;
            case ViewPackage.NODE_PALETTE__NODE_TOOLS:
                return this.nodeTools != null && !this.nodeTools.isEmpty();
            case ViewPackage.NODE_PALETTE__EDGE_TOOLS:
                return this.edgeTools != null && !this.edgeTools.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // NodePaletteImpl
