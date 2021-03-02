/**
 */
package org.eclipse.sirius.web.view.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.sirius.web.view.EdgeDescription;
import org.eclipse.sirius.web.view.NodeDescription;
import org.eclipse.sirius.web.view.ViewPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Edge Description</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.web.view.impl.EdgeDescriptionImpl#isIsDomainBasedEdge <em>Is Domain Based Edge</em>}</li>
 *   <li>{@link org.eclipse.sirius.web.view.impl.EdgeDescriptionImpl#getSourceNodeDescriptions <em>Source Node Descriptions</em>}</li>
 *   <li>{@link org.eclipse.sirius.web.view.impl.EdgeDescriptionImpl#getTargetNodeDescriptions <em>Target Node Descriptions</em>}</li>
 *   <li>{@link org.eclipse.sirius.web.view.impl.EdgeDescriptionImpl#getSourceNodesExpression <em>Source Nodes Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.web.view.impl.EdgeDescriptionImpl#getTargetNodesExpression <em>Target Nodes Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EdgeDescriptionImpl extends DiagramElementDescriptionImpl implements EdgeDescription {
	/**
	 * The default value of the '{@link #isIsDomainBasedEdge() <em>Is Domain Based Edge</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsDomainBasedEdge()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_DOMAIN_BASED_EDGE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsDomainBasedEdge() <em>Is Domain Based Edge</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsDomainBasedEdge()
	 * @generated
	 * @ordered
	 */
	protected boolean isDomainBasedEdge = IS_DOMAIN_BASED_EDGE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSourceNodeDescriptions() <em>Source Node Descriptions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceNodeDescriptions()
	 * @generated
	 * @ordered
	 */
	protected EList<NodeDescription> sourceNodeDescriptions;

	/**
	 * The cached value of the '{@link #getTargetNodeDescriptions() <em>Target Node Descriptions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetNodeDescriptions()
	 * @generated
	 * @ordered
	 */
	protected EList<NodeDescription> targetNodeDescriptions;

	/**
	 * The default value of the '{@link #getSourceNodesExpression() <em>Source Nodes Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceNodesExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String SOURCE_NODES_EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSourceNodesExpression() <em>Source Nodes Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceNodesExpression()
	 * @generated
	 * @ordered
	 */
	protected String sourceNodesExpression = SOURCE_NODES_EXPRESSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getTargetNodesExpression() <em>Target Nodes Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetNodesExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String TARGET_NODES_EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTargetNodesExpression() <em>Target Nodes Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetNodesExpression()
	 * @generated
	 * @ordered
	 */
	protected String targetNodesExpression = TARGET_NODES_EXPRESSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EdgeDescriptionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ViewPackage.Literals.EDGE_DESCRIPTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIsDomainBasedEdge() {
		return isDomainBasedEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIsDomainBasedEdge(boolean newIsDomainBasedEdge) {
		boolean oldIsDomainBasedEdge = isDomainBasedEdge;
		isDomainBasedEdge = newIsDomainBasedEdge;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE,
					oldIsDomainBasedEdge, isDomainBasedEdge));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<NodeDescription> getSourceNodeDescriptions() {
		if (sourceNodeDescriptions == null) {
			sourceNodeDescriptions = new EObjectResolvingEList<NodeDescription>(NodeDescription.class, this,
					ViewPackage.EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTIONS);
		}
		return sourceNodeDescriptions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<NodeDescription> getTargetNodeDescriptions() {
		if (targetNodeDescriptions == null) {
			targetNodeDescriptions = new EObjectResolvingEList<NodeDescription>(NodeDescription.class, this,
					ViewPackage.EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTIONS);
		}
		return targetNodeDescriptions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSourceNodesExpression() {
		return sourceNodesExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSourceNodesExpression(String newSourceNodesExpression) {
		String oldSourceNodesExpression = sourceNodesExpression;
		sourceNodesExpression = newSourceNodesExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION,
					oldSourceNodesExpression, sourceNodesExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTargetNodesExpression() {
		return targetNodesExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTargetNodesExpression(String newTargetNodesExpression) {
		String oldTargetNodesExpression = targetNodesExpression;
		targetNodesExpression = newTargetNodesExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION,
					oldTargetNodesExpression, targetNodesExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ViewPackage.EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE:
			return isIsDomainBasedEdge();
		case ViewPackage.EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTIONS:
			return getSourceNodeDescriptions();
		case ViewPackage.EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTIONS:
			return getTargetNodeDescriptions();
		case ViewPackage.EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION:
			return getSourceNodesExpression();
		case ViewPackage.EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION:
			return getTargetNodesExpression();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case ViewPackage.EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE:
			setIsDomainBasedEdge((Boolean) newValue);
			return;
		case ViewPackage.EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTIONS:
			getSourceNodeDescriptions().clear();
			getSourceNodeDescriptions().addAll((Collection<? extends NodeDescription>) newValue);
			return;
		case ViewPackage.EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTIONS:
			getTargetNodeDescriptions().clear();
			getTargetNodeDescriptions().addAll((Collection<? extends NodeDescription>) newValue);
			return;
		case ViewPackage.EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION:
			setSourceNodesExpression((String) newValue);
			return;
		case ViewPackage.EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION:
			setTargetNodesExpression((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case ViewPackage.EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE:
			setIsDomainBasedEdge(IS_DOMAIN_BASED_EDGE_EDEFAULT);
			return;
		case ViewPackage.EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTIONS:
			getSourceNodeDescriptions().clear();
			return;
		case ViewPackage.EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTIONS:
			getTargetNodeDescriptions().clear();
			return;
		case ViewPackage.EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION:
			setSourceNodesExpression(SOURCE_NODES_EXPRESSION_EDEFAULT);
			return;
		case ViewPackage.EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION:
			setTargetNodesExpression(TARGET_NODES_EXPRESSION_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case ViewPackage.EDGE_DESCRIPTION__IS_DOMAIN_BASED_EDGE:
			return isDomainBasedEdge != IS_DOMAIN_BASED_EDGE_EDEFAULT;
		case ViewPackage.EDGE_DESCRIPTION__SOURCE_NODE_DESCRIPTIONS:
			return sourceNodeDescriptions != null && !sourceNodeDescriptions.isEmpty();
		case ViewPackage.EDGE_DESCRIPTION__TARGET_NODE_DESCRIPTIONS:
			return targetNodeDescriptions != null && !targetNodeDescriptions.isEmpty();
		case ViewPackage.EDGE_DESCRIPTION__SOURCE_NODES_EXPRESSION:
			return SOURCE_NODES_EXPRESSION_EDEFAULT == null ? sourceNodesExpression != null
					: !SOURCE_NODES_EXPRESSION_EDEFAULT.equals(sourceNodesExpression);
		case ViewPackage.EDGE_DESCRIPTION__TARGET_NODES_EXPRESSION:
			return TARGET_NODES_EXPRESSION_EDEFAULT == null ? targetNodesExpression != null
					: !TARGET_NODES_EXPRESSION_EDEFAULT.equals(targetNodesExpression);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (isDomainBasedEdge: ");
		result.append(isDomainBasedEdge);
		result.append(", sourceNodesExpression: ");
		result.append(sourceNodesExpression);
		result.append(", targetNodesExpression: ");
		result.append(targetNodesExpression);
		result.append(')');
		return result.toString();
	}

} //EdgeDescriptionImpl
