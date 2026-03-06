/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import {
  addChildNode,
  addEdge,
  addNode,
  buildDiagram,
} from "./diagramConstructionUtils";

export const FiveNodeDiagram = () => {
  let d = buildDiagram("First Diagram");
  d = addNode(d, {
    id: "n1",
    label: "Noeud 1-1",
    x: 0,
    y: 0,
    width: 120,
    height: 60,
  });
  d = addNode(d, {
    id: "n2",
    label: "Noeud 2-1",
    x: 150,
    y: 0,
    width: 120,
    height: 60,
  });
  d = addNode(d, {
    id: "n3",
    label: "Noeud 2-2",
    x: 0,
    y: 80,
    width: 120,
    height: 60,
  });
  d = addNode(d, {
    id: "n4",
    label: "Noeud 3-1",
    x: 300,
    y: 0,
    width: 120,
    height: 60,
  });
  d = addNode(d, {
    id: "n5",
    label: "Noeud 3-2",
    x: 150,
    y: 80,
    width: 120,
    height: 60,
  });
  d = addEdge(d, { id: "e1", sourceId: "n1", targetId: "n2" });
  d = addEdge(d, { id: "e2", sourceId: "n1", targetId: "n3" });
  d = addEdge(d, { id: "e3", sourceId: "n2", targetId: "n4" });
  d = addEdge(d, { id: "e4", sourceId: "n2", targetId: "n5" });
  return d;
};

export const TwoNodeGroupDiagram = () => {
  let d = buildDiagram("Second Diagram");
  d = addNode(d, {
    id: "n1",
    label: "Noeud 1-1",
    x: 0,
    y: 0,
    width: 120,
    height: 60,
  });
  d = addNode(d, {
    id: "n2",
    label: "Noeud 2-1",
    x: 150,
    y: 0,
    width: 120,
    height: 60,
  });
  d = addNode(d, {
    id: "n3",
    label: "Noeud 2-2",
    x: 0,
    y: 80,
    width: 120,
    height: 60,
  });
  d = addNode(d, {
    id: "n4",
    label: "Noeud 3-1",
    x: 300,
    y: 0,
    width: 120,
    height: 60,
  });
  d = addNode(d, {
    id: "n5",
    label: "Noeud 3-2",
    x: 150,
    y: 80,
    width: 120,
    height: 60,
  });
  d = addEdge(d, { id: "e1", sourceId: "n1", targetId: "n2" });
  d = addEdge(d, { id: "e2", sourceId: "n1", targetId: "n3" });
  d = addEdge(d, { id: "e3", sourceId: "n2", targetId: "n4" });
  d = addEdge(d, { id: "e4", sourceId: "n2", targetId: "n5" });

  d = addNode(d, {
    id: "n6",
    label: "Noeud 4-1",
    x: 0,
    y: 200,
    width: 120,
    height: 60,
  });
  d = addNode(d, {
    id: "n7",
    label: "Noeud 5-1",
    x: 150,
    y: 200,
    width: 120,
    height: 60,
  });
  d = addNode(d, {
    id: "n8",
    label: "Noeud 5-2",
    x: 0,
    y: 280,
    width: 120,
    height: 60,
  });
  d = addNode(d, {
    id: "n9",
    label: "Noeud 6-1",
    x: 300,
    y: 200,
    width: 120,
    height: 60,
  });
  d = addNode(d, {
    id: "n10",
    label: "Noeud 6-2",
    x: 150,
    y: 280,
    width: 120,
    height: 60,
  });
  d = addEdge(d, { id: "e5", sourceId: "n6", targetId: "n7" });
  d = addEdge(d, { id: "e6", sourceId: "n6", targetId: "n8" });
  d = addEdge(d, { id: "e7", sourceId: "n8", targetId: "n9" });
  d = addEdge(d, { id: "e8", sourceId: "n8", targetId: "n10" });
  return d;
};

export const ThreeNodeGroupDiagram = () => {
  let d = buildDiagram("Third Diagram");
  d = addNode(d, {
    id: "n1",
    label: "Noeud 1-1",
    x: 0,
    y: 0,
    width: 120,
    height: 60,
  });
  d = addNode(d, {
    id: "n2",
    label: "Noeud 1-2",
    x: 150,
    y: 0,
    width: 120,
    height: 60,
  });
  d = addEdge(d, { id: "e1", sourceId: "n1", targetId: "n2" });

  d = addNode(d, {
    id: "n3",
    label: "Noeud 2-1",
    x: 0,
    y: 80,
    width: 120,
    height: 60,
  });
  d = addNode(d, {
    id: "n4",
    label: "Noeud 2-2",
    x: 150,
    y: 80,
    width: 120,
    height: 60,
  });
  d = addEdge(d, { id: "e2", sourceId: "n3", targetId: "n4" });

  d = addNode(d, {
    id: "n5",
    label: "Noeud 3-1",
    x: 0,
    y: 160,
    width: 120,
    height: 60,
  });
  d = addNode(d, {
    id: "n6",
    label: "Noeud 3-2",
    x: 150,
    y: 160,
    width: 120,
    height: 60,
  });
  d = addEdge(d, { id: "e3", sourceId: "n5", targetId: "n6" });

  return d;
};

export const BasiqueSiriusDiagram = () => {
  let d = buildDiagram("Fourth Diagram");
  d = addNode(d, {
    id: "n1",
    label: "Root",
    x: 0,
    y: 0,
    width: 120,
    height: 60,
  });
  d = addNode(d, {
    id: "n2",
    label: "Entity 1",
    x: 150,
    y: 0,
    width: 120,
    height: 60,
  });
  d = addNode(d, {
    id: "n3",
    label: "Entity 2",
    x: 0,
    y: 80,
    width: 120,
    height: 60,
  });

  d = addEdge(d, { id: "e1", sourceId: "n1", targetId: "n2" });
  d = addEdge(d, { id: "e2", sourceId: "n1", targetId: "n3" });
  d = addEdge(d, { id: "e3", sourceId: "n2", targetId: "n3" });

  d = addChildNode(d, {
    parentId: "n2",
    id: "enfant-1",
    label: "name",
    x: 0,
    y: 40,
    width: 120,
    height: 60,
  });
  d = addChildNode(d, {
    parentId: "n2",
    id: "enfant-2",
    label: "attribute2",
    x: 140,
    y: 40,
    width: 120,
    height: 60,
  });
  d = addChildNode(d, {
    parentId: "n2",
    id: "enfant-3",
    label: "attribute3",
    x: 0,
    y: 100,
    width: 120,
    height: 60,
  });

  d = addChildNode(d, {
    parentId: "n3",
    id: "enfant-4",
    label: "name",
    x: 0,
    y: 100,
    width: 120,
    height: 60,
  });

  return d;
};

export const AllScenarioDiagram = () => {
  const listDiagram = [
    BasiqueSiriusDiagram,
    FiveNodeDiagram,
    TwoNodeGroupDiagram,
    ThreeNodeGroupDiagram,
  ];
  return listDiagram;
};
