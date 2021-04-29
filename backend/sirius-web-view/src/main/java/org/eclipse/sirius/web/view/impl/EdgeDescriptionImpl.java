/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.view.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.web.view.EdgeDescription;
import org.eclipse.sirius.web.view.NodeDescription;
import org.eclipse.sirius.web.view.Style;
import org.eclipse.sirius.web.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Edge Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.web.view.impl.EdgeDescriptionImpl#isIsDomainBasedEdge <em>Is Domain Based
 * Edge</em>}</li>
 * <li>{@link org.eclipse.sirius.web.view.impl.EdgeDescriptionImpl#getSourceNodeDescription <em>Source Node
 * Description</em>}</li>
 * <li>{@link org.eclipse.sirius.web.view.impl.EdgeDescriptionImpl#getTargetNodeDescription <em>Target Node
 * Description</em>}</li>
 * <li>{@link org.eclipse.sirius.web.view.impl.EdgeDescriptionImpl#getSourceNodesExpression <em>Source Nodes
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.web.view.impl.EdgeDescriptionImpl#getTargetNodesExpression <em>Target Nodes
 * Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EdgeDescriptionImpl extends DiagramElementDescriptionImpl implements EdgeDescription {
    /**
     * The default value of the '{@link #isIsDomainBasedEdge() <em>Is Domain Based Edge</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #isIsDomainBasedEdge()
     * @generated
     * @ordered
     */
    protected static final boolean IS_DOMAIN_BASED_EDGE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isIsDomainBasedEdge() <em>Is Domain Based Edge</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #isIsDomainBasedEdge()
     * @generated
     * @ordered
     */
    protected boolean isDomainBasedEdge = IS_DOMAIN_BASED_EDGE_EDEFAULT;

    /**
     * The cached value of the '{@link #getSourceNodeDescription() <em>Source Node Description</em>}' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSourceNodeDescription()
     * @generated
     * @ordered
     */
    protected NodeDescription sourceNodeDescription;

    /**
     * The cached value of the '{@link #getTargetNodeDescription() <em>Target Node Description</em>}' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTargetNodeDescription()
     * @generated
     * @ordered
     */
    protected NodeDescription targetNodeDescription;

    /**
     * The default value of the '{@link #getSourceNodesExpression() <em>Source Nodes Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSourceNodesExpression()
     * @generated
     * @ordered
     */
    protected static final String SOURCE_NODES_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSourceNodesExpression() <em>Source Nodes Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSourceNodesExpression()
     * @generated
     * @ordered
     */
    protected String sourceNodesExpression = SOURCE_NODES_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getTargetNodesExpression() <em>Target Nodes Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTargetNodesExpression()
     * @generated
     * @ordered
     */
    protected static final String TARGET_NODES_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTargetNodesExpression() <em>Target Nodes Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTargetNodesExpression()
     * @generated
     * @ordered
     */
    protected String targetNodesExpression = TARGET_NODES_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getStyle() <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getStyle()
     * @generated
     * @ordered
     */
    protected Style style;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected EdgeDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.EDGE_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isIsDomainBasedEdge() {
        return this.isDomainBasedEdge;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setIsDomainBasedEdge(boolean newIsDomainBasedEdge) {
        boolean oldIsDomainBasedEdge = this.isDomainBasedEdge;
        this.isDomainBasedEdge = newIsDomainBasedEdge;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE, oldIsDomainBasedEdge, this.isDomainBasedEdge));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NodeDescription getSourceNodeDescription() {
        if (this.sourceNodeDescription != null && this.sourceNodeDescription.eIsProxy()) {
            InternalEObject oldSourceNodeDescription = (InternalEObject) this.sourceNodeDescription;
            this.sourceNodeDescription = (NodeDescription) this.eResolveProxy(oldSourceNodeDescription);
            if (this.sourceNodeDescription != oldSourceNodeDescription) {
                if (this.eNotificationRequired())
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, ViewPackage.EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTION, oldSourceNodeDescription, this.sourceNodeDescription));
            }
        }
        return this.sourceNodeDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NodeDescription basicGetSourceNodeDescription() {
        return this.sourceNodeDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSourceNodeDescription(NodeDescription newSourceNodeDescription) {
        NodeDescription oldSourceNodeDescription = this.sourceNodeDescription;
        this.sourceNodeDescription = newSourceNodeDescription;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTION, oldSourceNodeDescription, this.sourceNodeDescription));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NodeDescription getTargetNodeDescription() {
        if (this.targetNodeDescription != null && this.targetNodeDescription.eIsProxy()) {
            InternalEObject oldTargetNodeDescription = (InternalEObject) this.targetNodeDescription;
            this.targetNodeDescription = (NodeDescription) this.eResolveProxy(oldTargetNodeDescription);
            if (this.targetNodeDescription != oldTargetNodeDescription) {
                if (this.eNotificationRequired())
                    this.eNotify(new ENotificationImpl(this, Notification.RESOLVE, ViewPackage.EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTION, oldTargetNodeDescription, this.targetNodeDescription));
            }
        }
        return this.targetNodeDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NodeDescription basicGetTargetNodeDescription() {
        return this.targetNodeDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setTargetNodeDescription(NodeDescription newTargetNodeDescription) {
        NodeDescription oldTargetNodeDescription = this.targetNodeDescription;
        this.targetNodeDescription = newTargetNodeDescription;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTION, oldTargetNodeDescription, this.targetNodeDescription));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getSourceNodesExpression() {
        return this.sourceNodesExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSourceNodesExpression(String newSourceNodesExpression) {
        String oldSourceNodesExpression = this.sourceNodesExpression;
        this.sourceNodesExpression = newSourceNodesExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION, oldSourceNodesExpression, this.sourceNodesExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getTargetNodesExpression() {
        return this.targetNodesExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setTargetNodesExpression(String newTargetNodesExpression) {
        String oldTargetNodesExpression = this.targetNodesExpression;
        this.targetNodesExpression = newTargetNodesExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION, oldTargetNodesExpression, this.targetNodesExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Style getStyle() {
        return this.style;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetStyle(Style newStyle, NotificationChain msgs) {
        Style oldStyle = this.style;
        this.style = newStyle;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ViewPackage.EDGE_DESCRIPTION__STYLE, oldStyle, newStyle);
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
    public void setStyle(Style newStyle) {
        if (newStyle != this.style) {
            NotificationChain msgs = null;
            if (this.style != null)
                msgs = ((InternalEObject) this.style).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ViewPackage.EDGE_DESCRIPTION__STYLE, null, msgs);
            if (newStyle != null)
                msgs = ((InternalEObject) newStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ViewPackage.EDGE_DESCRIPTION__STYLE, null, msgs);
            msgs = this.basicSetStyle(newStyle, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.EDGE_DESCRIPTION__STYLE, newStyle, newStyle));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case ViewPackage.EDGE_DESCRIPTION__STYLE:
            return this.basicSetStyle(null, msgs);
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
        case ViewPackage.EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE:
            return this.isIsDomainBasedEdge();
        case ViewPackage.EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTION:
            if (resolve)
                return this.getSourceNodeDescription();
            return this.basicGetSourceNodeDescription();
        case ViewPackage.EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTION:
            if (resolve)
                return this.getTargetNodeDescription();
            return this.basicGetTargetNodeDescription();
        case ViewPackage.EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION:
            return this.getSourceNodesExpression();
        case ViewPackage.EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION:
            return this.getTargetNodesExpression();
        case ViewPackage.EDGE_DESCRIPTION__STYLE:
            return this.getStyle();
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
        case ViewPackage.EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE:
            this.setIsDomainBasedEdge((Boolean) newValue);
            return;
        case ViewPackage.EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTION:
            this.setSourceNodeDescription((NodeDescription) newValue);
            return;
        case ViewPackage.EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTION:
            this.setTargetNodeDescription((NodeDescription) newValue);
            return;
        case ViewPackage.EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION:
            this.setSourceNodesExpression((String) newValue);
            return;
        case ViewPackage.EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION:
            this.setTargetNodesExpression((String) newValue);
            return;
        case ViewPackage.EDGE_DESCRIPTION__STYLE:
            this.setStyle((Style) newValue);
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
        case ViewPackage.EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE:
            this.setIsDomainBasedEdge(IS_DOMAIN_BASED_EDGE_EDEFAULT);
            return;
        case ViewPackage.EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTION:
            this.setSourceNodeDescription((NodeDescription) null);
            return;
        case ViewPackage.EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTION:
            this.setTargetNodeDescription((NodeDescription) null);
            return;
        case ViewPackage.EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION:
            this.setSourceNodesExpression(SOURCE_NODES_EXPRESSION_EDEFAULT);
            return;
        case ViewPackage.EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION:
            this.setTargetNodesExpression(TARGET_NODES_EXPRESSION_EDEFAULT);
            return;
        case ViewPackage.EDGE_DESCRIPTION__STYLE:
            this.setStyle((Style) null);
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
        case ViewPackage.EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE:
            return this.isDomainBasedEdge != IS_DOMAIN_BASED_EDGE_EDEFAULT;
        case ViewPackage.EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTION:
            return this.sourceNodeDescription != null;
        case ViewPackage.EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTION:
            return this.targetNodeDescription != null;
        case ViewPackage.EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION:
            return SOURCE_NODES_EXPRESSION_EDEFAULT == null ? this.sourceNodesExpression != null : !SOURCE_NODES_EXPRESSION_EDEFAULT.equals(this.sourceNodesExpression);
        case ViewPackage.EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION:
            return TARGET_NODES_EXPRESSION_EDEFAULT == null ? this.targetNodesExpression != null : !TARGET_NODES_EXPRESSION_EDEFAULT.equals(this.targetNodesExpression);
        case ViewPackage.EDGE_DESCRIPTION__STYLE:
            return this.style != null;
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
        result.append(" (isDomainBasedEdge: "); //$NON-NLS-1$
        result.append(this.isDomainBasedEdge);
        result.append(", sourceNodesExpression: "); //$NON-NLS-1$
        result.append(this.sourceNodesExpression);
        result.append(", targetNodesExpression: "); //$NON-NLS-1$
        result.append(this.targetNodesExpression);
        result.append(')');
        return result.toString();
    }

} // EdgeDescriptionImpl
