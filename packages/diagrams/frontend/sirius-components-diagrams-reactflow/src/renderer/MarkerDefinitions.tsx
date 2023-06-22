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

import { useCallback } from 'react';
import { Edge, ReactFlowState, useStore } from 'reactflow';
import { MarkerProps } from './MarkerDefinitions.types';

const markerSelector = (state: ReactFlowState): MarkerProps[] => {
  const ids: string[] = [];

  const initialMarkerProps: MarkerProps[] = [];
  const reducer = (allMarkerProps: MarkerProps[], edge: Edge): MarkerProps[] => {
    [edge.markerStart, edge.markerEnd].forEach((marker) => {
      if (marker && typeof marker === 'string') {
        if (!ids.includes(marker)) {
          allMarkerProps.push({
            id: marker,
            edgeColor: edge.style.stroke,
          });
          ids.push(marker);
        }
      }
    });

    return allMarkerProps;
  };

  return state.edges
    .reduce<MarkerProps[]>(reducer, initialMarkerProps)
    .sort((firstMarkerProps, secondMarkerProps) => firstMarkerProps.id.localeCompare(secondMarkerProps.id));
};

const equalityFunction = (oldMarkerProps: MarkerProps[], newMarkerProps: MarkerProps[]) => {
  return !(
    oldMarkerProps.length !== newMarkerProps.length ||
    oldMarkerProps.some((edge, index) => edge.id !== newMarkerProps[index].id)
  );
};

export const MarkerDefinitions = () => {
  const selector = useCallback(markerSelector, []);
  const allMarkerProps = useStore<MarkerProps[]>(selector, equalityFunction);

  return (
    <svg id="edge-markers" style={{ position: 'absolute', zIndex: -1, top: '0px' }}>
      <defs>
        {allMarkerProps.map((markerProps) => (
          <OpenArrowMarker {...markerProps} key={markerProps.id} />
        ))}
      </defs>
    </svg>
  );
};

const OpenArrowMarker = ({ id, edgeColor }: MarkerProps) => {
  return (
    <marker id={id} markerUnits="strokeWidth" markerWidth={10} markerHeight={7} refX={0} refY={3.5} orient="auto">
      <polygon points="0 0, 10 3.5, 0 7" stroke={edgeColor} fill={edgeColor} />
    </marker>
  );
};
