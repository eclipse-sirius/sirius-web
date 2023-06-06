/*******************************************************************************
 * Copyright (c) 2023 Obeo and others.
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
import { useEffect, useState } from 'react';
import { ReactFlow, useEdgesState, useNodesInitialized, useNodesState } from 'reactflow';

import 'reactflow/dist/style.css';
import './App.css';

export const App = () => {
  const [shouldComputeLayout, setShouldComputeLayout] = useState(false);
  const nodesInitialized = useNodesInitialized({
    includeHiddenNodes: false,
  });
  const [layout, setLayout] = useState('');
  const [nodes, setNodes, onNodesChange] = useNodesState([]);
  const [edges, setEdges, onEdgesChange] = useEdgesState([]);

  useEffect(() => {
    const query = `
    query getDiagramLayout {
      viewer {
        diagramLayout
      }
    }
    `;
    const body = JSON.stringify({
      query,
    });
    fetch(`http://localhost:8081/api/graphql`, { method: 'POST', body })
      .then((response) => response.json())
      .then((data) => {
        setNodes(data.nodes);
        setEdges(data.edges);
        setShouldComputeLayout(true);
      })
      .catch((error) => {
        console.error(error);
      });
  }, [setNodes, setEdges]);

  useEffect(() => {
    if (shouldComputeLayout && nodesInitialized) {
      let layout = '';
      const elements = document.querySelectorAll('.react-flow__node');
      elements.forEach((element) => {
        layout += `${element.attributes.getNamedItem('data-id')?.value} ${element.clientWidth}x${
          element.clientHeight
        } --- `;
      });
      setLayout(layout);
      setShouldComputeLayout(false);
    }
  }, [shouldComputeLayout, nodesInitialized]);

  return (
    <>
      <h1>Vite + React</h1>
      <div style={{ backgroundColor: 'white', width: '800px', height: '800px' }}>
        <ReactFlow nodes={nodes} edges={edges} onNodesChange={onNodesChange} onEdgesChange={onEdgesChange} fitView />
      </div>
      <p className="read-the-docs">{layout}</p>
    </>
  );
};
