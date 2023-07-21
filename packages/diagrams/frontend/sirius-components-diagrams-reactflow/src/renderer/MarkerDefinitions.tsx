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

const getMarker = (markerProps: MarkerProps) => {
  const markerType = markerProps.id.split('--')[0];
  switch (markerType) {
    case 'None':
      return null;
    case 'OutputArrow':
      return <OutputArrow {...markerProps} key={markerProps.id} />;
    case 'InputArrow':
      return <InputArrow {...markerProps} key={markerProps.id} />;
    case 'OutputClosedArrow':
      return <OutputClosedArrow {...markerProps} key={markerProps.id} />;
    case 'InputClosedArrow':
      return <InputClosedArrow {...markerProps} key={markerProps.id} />;
    case 'OutputFillClosedArrow':
      return <OutputFillClosedArrow {...markerProps} key={markerProps.id} />;
    case 'InputFillClosedArrow':
      return <InputFillClosedArrow {...markerProps} key={markerProps.id} />;
    case 'Diamond':
      return <Diamond {...markerProps} key={markerProps.id} />;
    case 'FillDiamond':
      return <FillDiamond {...markerProps} key={markerProps.id} />;
    case 'InputArrowWithDiamond':
      return <InputArrowWithDiamond {...markerProps} key={markerProps.id} />;
    case 'InputArrowWithFillDiamond':
      return <InputArrowWithFillDiamond {...markerProps} key={markerProps.id} />;
    case 'Circle':
      return <Circle {...markerProps} key={markerProps.id} />;
    case 'FillCircle':
      return <FillCircle {...markerProps} key={markerProps.id} />;
    case 'CrossedCircle':
      return <CrossedCircle {...markerProps} key={markerProps.id} />;
    case 'ClosedArrowWithVerticalBar':
      return <ClosedArrowWithVerticalBar {...markerProps} key={markerProps.id} />;
    case 'ClosedArrowWithDots':
      return <ClosedArrowWithDots {...markerProps} key={markerProps.id} />;
    default:
      return <InputFillClosedArrow {...markerProps} key={markerProps.id} />;
  }
};

const OutputArrowPath = 'm 10 1 L 1 5 M 1 5 L 10 10';
const InputArrowPath = 'm 1 1 L 10 5 M 10 5 L 1 10';
const OutputClosedArrowPath = 'm 10 1 L 1 5 L 10 9 L 10 1';
const InputClosedArrowPath = 'm 1 1 L 9 5 L 1 9 L 1 1';
const DiamondPath = 'm 8 1 L 1 5 L 8 9 L 15 5 L 8 1';

const buildMarkerAttributes = (
  id: string,
  markerWidth: number,
  markerHeight: number,
  refX: number,
  refY: number
): React.SVGProps<SVGMarkerElement> => {
  return {
    id: id,
    markerUnits: 'strokeWidth',
    markerWidth: markerWidth,
    markerHeight: markerHeight,
    refX: refX,
    refY: refY,
    orient: 'auto-start-reverse',
  };
};

const buildArrow = (id: string, path: string, edgeColor: string, fill: boolean) => {
  return (
    <marker {...buildMarkerAttributes(id, 10, 10, 10, 5)}>
      <path fill={fill ? edgeColor : 'white'} d={path} stroke={edgeColor} strokeWidth={0.5} />
    </marker>
  );
};

const buildDiamond = (id: string, path: string, edgeColor: string, fill: boolean) => {
  return (
    <marker {...buildMarkerAttributes(id, 16, 10, 15, 5)}>
      <path fill={fill ? edgeColor : 'white'} d={path} stroke={edgeColor} strokeWidth={0.5} />
    </marker>
  );
};

const buildCircle = (id: string, edgeColor: string, fill: boolean) => {
  return (
    <marker {...buildMarkerAttributes(id, 10, 10, 10, 5)}>
      <circle r={4} cx={5} cy={5} fill={fill ? edgeColor : 'white'} stroke={edgeColor} strokeWidth={0.5} />
    </marker>
  );
};

export const MarkerDefinitions = () => {
  const selector = useCallback(markerSelector, []);
  const allMarkerProps = useStore<MarkerProps[]>(selector, equalityFunction);
  return (
    <svg id="edge-markers" style={{ position: 'absolute', zIndex: -1, top: '0px' }}>
      <defs>{allMarkerProps.map((markerProps) => getMarker(markerProps))}</defs>
    </svg>
  );
};

const OutputArrow = ({ id, edgeColor }: MarkerProps) => {
  return buildArrow(id, OutputArrowPath, edgeColor, false);
};

const InputArrow = ({ id, edgeColor }: MarkerProps) => {
  return buildArrow(id, InputArrowPath, edgeColor, false);
};

const OutputClosedArrow = ({ id, edgeColor }: MarkerProps) => {
  return buildArrow(id, OutputClosedArrowPath, edgeColor, false);
};

const InputClosedArrow = ({ id, edgeColor }: MarkerProps) => {
  return buildArrow(id, InputClosedArrowPath, edgeColor, false);
};

const OutputFillClosedArrow = ({ id, edgeColor }: MarkerProps) => {
  return buildArrow(id, OutputClosedArrowPath, edgeColor, true);
};

const InputFillClosedArrow = ({ id, edgeColor }: MarkerProps) => {
  return buildArrow(id, InputClosedArrowPath, edgeColor, true);
};

const Diamond = ({ id, edgeColor }: MarkerProps) => {
  return buildDiamond(id, DiamondPath, edgeColor, false);
};

const FillDiamond = ({ id, edgeColor }: MarkerProps) => {
  return buildDiamond(id, DiamondPath, edgeColor, true);
};

const Circle = ({ id, edgeColor }: MarkerProps) => {
  return buildCircle(id, edgeColor, false);
};

const FillCircle = ({ id, edgeColor }: MarkerProps) => {
  return buildCircle(id, edgeColor, true);
};

const CrossedCircle = ({ id, edgeColor }: MarkerProps) => {
  return (
    <marker {...buildMarkerAttributes(id, 10, 10, 10, 5)}>
      <circle r={4} cx={5} cy={5} stroke={edgeColor} fill={'white'} />
      <path d="M 1 5 L 9 5" stroke={edgeColor} strokeWidth={0.5} />
      <path d="m 5 1 L 5 9" stroke={edgeColor} strokeWidth={0.5} />
    </marker>
  );
};

const InputArrowWithDiamond = ({ id, edgeColor }: MarkerProps) => {
  return (
    <marker {...buildMarkerAttributes(id, 10, 24, 24, 5)}>
      <path d={InputArrowPath} stroke={edgeColor} strokeWidth={0.5} />
      <path d="m 17 1 L 10 5 L 17 9 L 24 5 L 17 1" stroke={edgeColor} fill={'white'} strokeWidth={0.5} />
    </marker>
  );
};

const InputArrowWithFillDiamond = ({ id, edgeColor }: MarkerProps) => {
  return (
    <marker {...buildMarkerAttributes(id, 10, 24, 24, 5)}>
      <path d={InputArrowPath} stroke={edgeColor} strokeWidth={0.5} />
      <path d="m 17 1 L 10 5 L 17 9 L 24 5 L 17 1" stroke={edgeColor} fill={edgeColor} strokeWidth={0.5} />
    </marker>
  );
};

const ClosedArrowWithVerticalBar = ({ id, edgeColor }: MarkerProps) => {
  return (
    <marker {...buildMarkerAttributes(id, 12, 10, 12, 5)}>
      <path strokeWidth={0.5} fill={'white'} d={InputClosedArrowPath} stroke={edgeColor} />
      <path fill={edgeColor} d={'m 12 10 V 0'} stroke={edgeColor} />
    </marker>
  );
};

const ClosedArrowWithDots = ({ id, edgeColor }: MarkerProps) => {
  return (
    <marker {...buildMarkerAttributes(id, 14, 10, 14, 5)}>
      <path strokeWidth={0.5} fill={'white'} d={InputClosedArrowPath} stroke={edgeColor} />
      <circle r={1} cx={12} cy={3} fill={edgeColor} stroke={'none'} />
      <circle r={1} cx={12} cy={7} fill={edgeColor} stroke={'none'} />
    </marker>
  );
};
