/**
 */
package org.eclipse.sirius.web.view;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Edge Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.web.view.EdgeDescription#isIsDomainBasedEdge <em>Is Domain Based Edge</em>}</li>
 *   <li>{@link org.eclipse.sirius.web.view.EdgeDescription#getSourceNodeDescriptions <em>Source Node Descriptions</em>}</li>
 *   <li>{@link org.eclipse.sirius.web.view.EdgeDescription#getTargetNodeDescriptions <em>Target Node Descriptions</em>}</li>
 *   <li>{@link org.eclipse.sirius.web.view.EdgeDescription#getSourceNodesExpression <em>Source Nodes Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.web.view.EdgeDescription#getTargetNodesExpression <em>Target Nodes Expression</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.web.view.ViewPackage#getEdgeDescription()
 * @model
 * @generated
 */
public interface EdgeDescription extends DiagramElementDescription {
	/**
	 * Returns the value of the '<em><b>Is Domain Based Edge</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Domain Based Edge</em>' attribute.
	 * @see #setIsDomainBasedEdge(boolean)
	 * @see org.eclipse.sirius.web.view.ViewPackage#getEdgeDescription_IsDomainBasedEdge()
	 * @model
	 * @generated
	 */
	boolean isIsDomainBasedEdge();

	/**
	 * Sets the value of the '{@link org.eclipse.sirius.web.view.EdgeDescription#isIsDomainBasedEdge <em>Is Domain Based Edge</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Domain Based Edge</em>' attribute.
	 * @see #isIsDomainBasedEdge()
	 * @generated
	 */
	void setIsDomainBasedEdge(boolean value);

	/**
	 * Returns the value of the '<em><b>Source Node Descriptions</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.sirius.web.view.NodeDescription}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Node Descriptions</em>' reference list.
	 * @see org.eclipse.sirius.web.view.ViewPackage#getEdgeDescription_SourceNodeDescriptions()
	 * @model
	 * @generated
	 */
	EList<NodeDescription> getSourceNodeDescriptions();

	/**
	 * Returns the value of the '<em><b>Target Node Descriptions</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.sirius.web.view.NodeDescription}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Node Descriptions</em>' reference list.
	 * @see org.eclipse.sirius.web.view.ViewPackage#getEdgeDescription_TargetNodeDescriptions()
	 * @model
	 * @generated
	 */
	EList<NodeDescription> getTargetNodeDescriptions();

	/**
	 * Returns the value of the '<em><b>Source Nodes Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Nodes Expression</em>' attribute.
	 * @see #setSourceNodesExpression(String)
	 * @see org.eclipse.sirius.web.view.ViewPackage#getEdgeDescription_SourceNodesExpression()
	 * @model
	 * @generated
	 */
	String getSourceNodesExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.sirius.web.view.EdgeDescription#getSourceNodesExpression <em>Source Nodes Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Nodes Expression</em>' attribute.
	 * @see #getSourceNodesExpression()
	 * @generated
	 */
	void setSourceNodesExpression(String value);

	/**
	 * Returns the value of the '<em><b>Target Nodes Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Nodes Expression</em>' attribute.
	 * @see #setTargetNodesExpression(String)
	 * @see org.eclipse.sirius.web.view.ViewPackage#getEdgeDescription_TargetNodesExpression()
	 * @model
	 * @generated
	 */
	String getTargetNodesExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.sirius.web.view.EdgeDescription#getTargetNodesExpression <em>Target Nodes Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Nodes Expression</em>' attribute.
	 * @see #getTargetNodesExpression()
	 * @generated
	 */
	void setTargetNodesExpression(String value);

} // EdgeDescription
