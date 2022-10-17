/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.components.view.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.ConditionalEdgeStyle;
import org.eclipse.sirius.components.view.EdgeDescription;
import org.eclipse.sirius.components.view.EdgeReconnectionTool;
import org.eclipse.sirius.components.view.EdgeStyle;
import org.eclipse.sirius.components.view.EdgeTool;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Edge Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.EdgeDescriptionImpl#isIsDomainBasedEdge <em>Is Domain Based
 * Edge</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.EdgeDescriptionImpl#getSourceNodeDescription <em>Source Node
 * Description</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.EdgeDescriptionImpl#getTargetNodeDescription <em>Target Node
 * Description</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.EdgeDescriptionImpl#getSourceNodesExpression <em>Source Nodes
 * Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.EdgeDescriptionImpl#getTargetNodesExpression <em>Target Nodes
 * Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EdgeDescriptionImpl extends DiagramElementDescriptionImpl implements EdgeDescription {
    /**
     * The default value of the '{@link #getBeginLabelExpression() <em>Begin Label Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getBeginLabelExpression()
     * @generated
     * @ordered
     */
    protected static final String BEGIN_LABEL_EXPRESSION_EDEFAULT = ""; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getBeginLabelExpression() <em>Begin Label Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getBeginLabelExpression()
     * @generated
     * @ordered
     */
    protected String beginLabelExpression = BEGIN_LABEL_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getEndLabelExpression() <em>End Label Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getEndLabelExpression()
     * @generated
     * @ordered
     */
    protected static final String END_LABEL_EXPRESSION_EDEFAULT = ""; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getEndLabelExpression() <em>End Label Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getEndLabelExpression()
     * @generated
     * @ordered
     */
    protected String endLabelExpression = END_LABEL_EXPRESSION_EDEFAULT;

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
     * The cached value of the '{@link #getSourceNodeDescriptions() <em>Source Node Descriptions</em>}' reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSourceNodeDescriptions()
     * @generated
     * @ordered
     */
    protected EList<NodeDescription> sourceNodeDescriptions;

    /**
     * The cached value of the '{@link #getTargetNodeDescriptions() <em>Target Node Descriptions</em>}' reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getTargetNodeDescriptions()
     * @generated
     * @ordered
     */
    protected EList<NodeDescription> targetNodeDescriptions;

    /**
     * The default value of the '{@link #getSourceNodesExpression() <em>Source Nodes Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSourceNodesExpression()
     * @generated
     * @ordered
     */
    protected static final String SOURCE_NODES_EXPRESSION_EDEFAULT = null; // $NON-NLS-1$

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
    protected static final String TARGET_NODES_EXPRESSION_EDEFAULT = "aql:self.eCrossReferences()"; //$NON-NLS-1$

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
    protected EdgeStyle style;

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
     * The cached value of the '{@link #getReconnectEdgeTools() <em>Reconnect Edge Tools</em>}' containment reference
     * list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getReconnectEdgeTools()
     * @generated
     * @ordered
     */
    protected EList<EdgeReconnectionTool> reconnectEdgeTools;

    /**
     * The cached value of the '{@link #getConditionalStyles() <em>Conditional Styles</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getConditionalStyles()
     * @generated
     * @ordered
     */
    protected EList<ConditionalEdgeStyle> conditionalStyles;

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
    public String getBeginLabelExpression() {
        return this.beginLabelExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setBeginLabelExpression(String newBeginLabelExpression) {
        String oldBeginLabelExpression = this.beginLabelExpression;
        this.beginLabelExpression = newBeginLabelExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.EDGE_DESCRIPTION__BEGIN_LABEL_EXPRESSION, oldBeginLabelExpression, this.beginLabelExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getEndLabelExpression() {
        return this.endLabelExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setEndLabelExpression(String newEndLabelExpression) {
        String oldEndLabelExpression = this.endLabelExpression;
        this.endLabelExpression = newEndLabelExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.EDGE_DESCRIPTION__END_LABEL_EXPRESSION, oldEndLabelExpression, this.endLabelExpression));
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
    public EList<NodeDescription> getSourceNodeDescriptions() {
        if (this.sourceNodeDescriptions == null) {
            this.sourceNodeDescriptions = new EObjectResolvingEList<>(NodeDescription.class, this, ViewPackage.EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTIONS);
        }
        return this.sourceNodeDescriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<NodeDescription> getTargetNodeDescriptions() {
        if (this.targetNodeDescriptions == null) {
            this.targetNodeDescriptions = new EObjectResolvingEList<>(NodeDescription.class, this, ViewPackage.EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTIONS);
        }
        return this.targetNodeDescriptions;
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
    public EdgeStyle getStyle() {
        return this.style;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetStyle(EdgeStyle newStyle, NotificationChain msgs) {
        EdgeStyle oldStyle = this.style;
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
    public void setStyle(EdgeStyle newStyle) {
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
    public EList<EdgeTool> getEdgeTools() {
        if (this.edgeTools == null) {
            this.edgeTools = new EObjectContainmentEList<>(EdgeTool.class, this, ViewPackage.EDGE_DESCRIPTION__EDGE_TOOLS);
        }
        return this.edgeTools;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<EdgeReconnectionTool> getReconnectEdgeTools() {
        if (this.reconnectEdgeTools == null) {
            this.reconnectEdgeTools = new EObjectContainmentEList<>(EdgeReconnectionTool.class, this, ViewPackage.EDGE_DESCRIPTION__RECONNECT_EDGE_TOOLS);
        }
        return this.reconnectEdgeTools;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ConditionalEdgeStyle> getConditionalStyles() {
        if (this.conditionalStyles == null) {
            this.conditionalStyles = new EObjectContainmentEList<>(ConditionalEdgeStyle.class, this, ViewPackage.EDGE_DESCRIPTION__CONDITIONAL_STYLES);
        }
        return this.conditionalStyles;
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
        case ViewPackage.EDGE_DESCRIPTION__EDGE_TOOLS:
            return ((InternalEList<?>) this.getEdgeTools()).basicRemove(otherEnd, msgs);
        case ViewPackage.EDGE_DESCRIPTION__RECONNECT_EDGE_TOOLS:
            return ((InternalEList<?>) this.getReconnectEdgeTools()).basicRemove(otherEnd, msgs);
        case ViewPackage.EDGE_DESCRIPTION__CONDITIONAL_STYLES:
            return ((InternalEList<?>) this.getConditionalStyles()).basicRemove(otherEnd, msgs);
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
        case ViewPackage.EDGE_DESCRIPTION__BEGIN_LABEL_EXPRESSION:
            return this.getBeginLabelExpression();
        case ViewPackage.EDGE_DESCRIPTION__END_LABEL_EXPRESSION:
            return this.getEndLabelExpression();
        case ViewPackage.EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE:
            return this.isIsDomainBasedEdge();
        case ViewPackage.EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTIONS:
            return this.getSourceNodeDescriptions();
        case ViewPackage.EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTIONS:
            return this.getTargetNodeDescriptions();
        case ViewPackage.EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION:
            return this.getSourceNodesExpression();
        case ViewPackage.EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION:
            return this.getTargetNodesExpression();
        case ViewPackage.EDGE_DESCRIPTION__STYLE:
            return this.getStyle();
        case ViewPackage.EDGE_DESCRIPTION__EDGE_TOOLS:
            return this.getEdgeTools();
        case ViewPackage.EDGE_DESCRIPTION__RECONNECT_EDGE_TOOLS:
            return this.getReconnectEdgeTools();
        case ViewPackage.EDGE_DESCRIPTION__CONDITIONAL_STYLES:
            return this.getConditionalStyles();
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
        case ViewPackage.EDGE_DESCRIPTION__BEGIN_LABEL_EXPRESSION:
            this.setBeginLabelExpression((String) newValue);
            return;
        case ViewPackage.EDGE_DESCRIPTION__END_LABEL_EXPRESSION:
            this.setEndLabelExpression((String) newValue);
            return;
        case ViewPackage.EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE:
            this.setIsDomainBasedEdge((Boolean) newValue);
            return;
        case ViewPackage.EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTIONS:
            this.getSourceNodeDescriptions().clear();
            this.getSourceNodeDescriptions().addAll((Collection<? extends NodeDescription>) newValue);
            return;
        case ViewPackage.EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTIONS:
            this.getTargetNodeDescriptions().clear();
            this.getTargetNodeDescriptions().addAll((Collection<? extends NodeDescription>) newValue);
            return;
        case ViewPackage.EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION:
            this.setSourceNodesExpression((String) newValue);
            return;
        case ViewPackage.EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION:
            this.setTargetNodesExpression((String) newValue);
            return;
        case ViewPackage.EDGE_DESCRIPTION__STYLE:
            this.setStyle((EdgeStyle) newValue);
            return;
        case ViewPackage.EDGE_DESCRIPTION__EDGE_TOOLS:
            this.getEdgeTools().clear();
            this.getEdgeTools().addAll((Collection<? extends EdgeTool>) newValue);
            return;
        case ViewPackage.EDGE_DESCRIPTION__RECONNECT_EDGE_TOOLS:
            this.getReconnectEdgeTools().clear();
            this.getReconnectEdgeTools().addAll((Collection<? extends EdgeReconnectionTool>) newValue);
            return;
        case ViewPackage.EDGE_DESCRIPTION__CONDITIONAL_STYLES:
            this.getConditionalStyles().clear();
            this.getConditionalStyles().addAll((Collection<? extends ConditionalEdgeStyle>) newValue);
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
        case ViewPackage.EDGE_DESCRIPTION__BEGIN_LABEL_EXPRESSION:
            this.setBeginLabelExpression(BEGIN_LABEL_EXPRESSION_EDEFAULT);
            return;
        case ViewPackage.EDGE_DESCRIPTION__END_LABEL_EXPRESSION:
            this.setEndLabelExpression(END_LABEL_EXPRESSION_EDEFAULT);
            return;
        case ViewPackage.EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE:
            this.setIsDomainBasedEdge(IS_DOMAIN_BASED_EDGE_EDEFAULT);
            return;
        case ViewPackage.EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTIONS:
            this.getSourceNodeDescriptions().clear();
            return;
        case ViewPackage.EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTIONS:
            this.getTargetNodeDescriptions().clear();
            return;
        case ViewPackage.EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION:
            this.setSourceNodesExpression(SOURCE_NODES_EXPRESSION_EDEFAULT);
            return;
        case ViewPackage.EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION:
            this.setTargetNodesExpression(TARGET_NODES_EXPRESSION_EDEFAULT);
            return;
        case ViewPackage.EDGE_DESCRIPTION__STYLE:
            this.setStyle((EdgeStyle) null);
            return;
        case ViewPackage.EDGE_DESCRIPTION__EDGE_TOOLS:
            this.getEdgeTools().clear();
            return;
        case ViewPackage.EDGE_DESCRIPTION__RECONNECT_EDGE_TOOLS:
            this.getReconnectEdgeTools().clear();
            return;
        case ViewPackage.EDGE_DESCRIPTION__CONDITIONAL_STYLES:
            this.getConditionalStyles().clear();
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
        case ViewPackage.EDGE_DESCRIPTION__BEGIN_LABEL_EXPRESSION:
            return BEGIN_LABEL_EXPRESSION_EDEFAULT == null ? this.beginLabelExpression != null : !BEGIN_LABEL_EXPRESSION_EDEFAULT.equals(this.beginLabelExpression);
        case ViewPackage.EDGE_DESCRIPTION__END_LABEL_EXPRESSION:
            return END_LABEL_EXPRESSION_EDEFAULT == null ? this.endLabelExpression != null : !END_LABEL_EXPRESSION_EDEFAULT.equals(this.endLabelExpression);
        case ViewPackage.EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE:
            return this.isDomainBasedEdge != IS_DOMAIN_BASED_EDGE_EDEFAULT;
        case ViewPackage.EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTIONS:
            return this.sourceNodeDescriptions != null && !this.sourceNodeDescriptions.isEmpty();
        case ViewPackage.EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTIONS:
            return this.targetNodeDescriptions != null && !this.targetNodeDescriptions.isEmpty();
        case ViewPackage.EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION:
            return SOURCE_NODES_EXPRESSION_EDEFAULT == null ? this.sourceNodesExpression != null : !SOURCE_NODES_EXPRESSION_EDEFAULT.equals(this.sourceNodesExpression);
        case ViewPackage.EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION:
            return TARGET_NODES_EXPRESSION_EDEFAULT == null ? this.targetNodesExpression != null : !TARGET_NODES_EXPRESSION_EDEFAULT.equals(this.targetNodesExpression);
        case ViewPackage.EDGE_DESCRIPTION__STYLE:
            return this.style != null;
        case ViewPackage.EDGE_DESCRIPTION__EDGE_TOOLS:
            return this.edgeTools != null && !this.edgeTools.isEmpty();
        case ViewPackage.EDGE_DESCRIPTION__RECONNECT_EDGE_TOOLS:
            return this.reconnectEdgeTools != null && !this.reconnectEdgeTools.isEmpty();
        case ViewPackage.EDGE_DESCRIPTION__CONDITIONAL_STYLES:
            return this.conditionalStyles != null && !this.conditionalStyles.isEmpty();
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
        result.append(" (beginLabelExpression: "); //$NON-NLS-1$
        result.append(this.beginLabelExpression);
        result.append(", endLabelExpression: "); //$NON-NLS-1$
        result.append(this.endLabelExpression);
        result.append(", isDomainBasedEdge: "); //$NON-NLS-1$
        result.append(this.isDomainBasedEdge);
        result.append(", sourceNodesExpression: "); //$NON-NLS-1$
        result.append(this.sourceNodesExpression);
        result.append(", targetNodesExpression: "); //$NON-NLS-1$
        result.append(this.targetNodesExpression);
        result.append(')');
        return result.toString();
    }

} // EdgeDescriptionImpl
